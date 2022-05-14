package com.app.shop.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.app.shop.R;
import com.app.shop.databinding.ActivityResetGoodsBinding;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获取需修改商品的信息
 * 根据输入信息修改商品信息
 * 更新到数据库
 */
public class ResetGoodActivity extends BarActivity {
    private ActivityResetGoodsBinding binding;

    private SharedPreferences sp;
    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CHOOSE_PHOTO = 2;//相册获取
    private String imagePath; //相册获取的图片存储路径
    private String saveImagePath;//商品样图存储路径
    private Uri imageUri;
    private String path;//=context.getExternalCacheDir()+File.separator+"output_image.jpg";
    private int n = 0; //获取图片方式标记
    
    private final String GOOD_NAME = "good_name";

    private Intent intent=getIntent();//获取需要修改的商品名称
    private Context context;
    private GoodsDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResetGoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setToolbar();
        setTitle("修改商品");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
        initEvent();
    }

    private void initView(){
        helper = new GoodsDBHelper(this);
        helper.openWriteLink();
    }
    private void initEvent(){
        context =ResetGoodActivity.this;
        path= context.getExternalCacheDir()+File.separator+"output_image.jpg";
        saveImagePath= context.getExternalFilesDir(null)+File.separator+"";
        binding.resetPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
        binding.resetOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData(){
        Goods good=new Goods();
        good.setGname(binding.resetGoodName.getText().toString().trim()+"");
        good.setPrice(Double.parseDouble(binding.resetGoodsPrice.getText().toString().trim()+""));
        good.setDesc(binding.resetGoodDesc.getText().toString().trim()+"");
        good.setType(binding.resetGoodsType.getText().toString().trim()+"");
        good.setWeight(binding.resetGoodsWeight.getText().toString().trim()+"");
        good.setTaste(binding.resetGoodsTaste.getText().toString().trim()+"");
        good.setImage(saveImage(imagePath,good.getGname())+"");
        try {
            if (helper.update(good,good.getGname())) Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
    }
    //弹出图片选择获取路径
    private void showBottomDialog() {
        View v=getLayoutInflater().inflate(R.layout.bottom_popupwindow,null,false);
        PopupWindow window=new PopupWindow(v,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.setBackgroundDrawable(new BitmapDrawable());
        if (window.isShowing()) window.dismiss();
        window.showAtLocation(binding.getRoot(),Gravity.BOTTOM,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        v.findViewById(R.id.btn_pop_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相册
                requestPermissino(2);
                n=2;
                window.dismiss();
            }
        });
        v.findViewById(R.id.btn_pop_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开相机
                requestPermissino(1);
                n=1;
                window.dismiss();
            }
        });
        v.findViewById(R.id.btn_pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
    }

    private void requestPermissino(int way) {
        switch (way) {
            case 2:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, TAKE_PHOTO);
                } else {
                    openAlbum();
                }
                break;
            case 1:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //请求权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            CHOOSE_PHOTO);
                } else {
                    //调用
                    requestCamera();
                }
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); //打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case TAKE_PHOTO:
                requestCamera();
                break;
            case CHOOSE_PHOTO:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "你拒绝了该权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }
                }
                break;
            case TAKE_PHOTO:
                //处理返回结果，隐示Intent的返回结果的处理方式，具体见以前我所发的intent讲解
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        binding.pic.setImageBitmap(bitmap);
                        //将图片解析成Bitmap对象，并把它显现出来
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];  //解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); //根据图片路径显示图片
        Log.e("tag","imagePath="+imagePath);
        Log.e("tag","saveImagePath="+saveImagePath);
    }

    private String saveImage(String imagePath,String name){
        if (name==null)return "";
        String path=saveImagePath+name+".jpg";
        try {
            File img=new File(imagePath);
            FileInputStream fis=new FileInputStream(img);
            FileOutputStream fos=new FileOutputStream(path);
            byte[] bytes=new byte[1024];
            int count=0;
            while ((n=fis.read(bytes))!=-1){
                fos.write(bytes,0,n);
            }
            fis.close();
            fos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return path;
    }

    //将选择的图片Uri转换为路径
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                Log.e("path", "path: " + path);
            }
            cursor.close();
        }
        return path;
    }

    private void requestCamera() {
        File outputImage = new File(path);
        //Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg"
                /*
                创建一个File文件对象，用于存放摄像头拍下的图片，我们把这个图片命名为output_image.jpg
                并把它存放在应用关联缓存目录下，调用getExternalCacheDir()可以得到这个目录，为什么要
                用关联缓存目录呢？由于android6.0开始，读写sd卡列为了危险权限，使用的时候必须要有权限，
                应用关联目录则可以跳过这一步
                 */
        //判断图片是否存在，存在则删除在创建，不存在则直接创建
        try {
            if (!outputImage.getParentFile().exists()) {
                outputImage.getParentFile().mkdirs();
            }
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            if (Build.VERSION.SDK_INT >= 24) {
                imageUri = FileProvider.getUriForFile(this, "com.app.shop.fileprovider", outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }
            //使用隐示的Intent，系统会找到与它对应的活动，即调用摄像头，并把它存储
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO);
            //调用会返回结果的开启方式，返回成功的话，则把它显示出来
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //展示图片
    private void displayImage(String imagePath) {
        if (imagePath != null && !imagePath.equals("")) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            binding.pic.setImageBitmap(bitmap);
            //存储上次选择的图片路径，用以再次打开app设置图片
            sp = getSharedPreferences("sp_img", MODE_PRIVATE);  //创建xml文件存储数据，name:创建的xml文件名
            SharedPreferences.Editor editor = sp.edit(); //获取edit()
            editor.putString("imgPath", imagePath);
            editor.apply();
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    //设置保存拍照图片——>再次关闭app重新打开显示为上次拍照照片
    private void setDefualtImage() {
        File outputImage = new File(path);
        if (!outputImage.exists()) {
            return;
        }
        binding.pic.setImageBitmap(BitmapFactory.decodeFile(path));
    }

}