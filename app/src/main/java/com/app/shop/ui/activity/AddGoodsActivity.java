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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.app.shop.R;
import com.app.shop.sql.helper.GoodsDBHelper;
import com.app.shop.sql.table.Goods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * 图片保存与应用内，并重命名，方便查找
 */

public class AddGoodsActivity extends BarActivity {
    private EditText goodname;
    private EditText gooddesc;
    private EditText goodprice;
    private EditText goodtype;
    private EditText goodweight;
    private EditText goodtaste;
    private Button goodPic;
    private Button addOn;
    private ImageView picture;

    private Context context;

    private SharedPreferences sp;
    public static final int TAKE_PHOTO = 1;//拍照
    public static final int CHOOSE_PHOTO = 2;//相册获取
    private String imagePath; //相册获取的图片存储路径
    private String saveImagePath;//商品样图存储路径
    private Uri imageUri;
    //private final String filePath = Environment.getExternalStorageDirectory() + File.separator + "output_image.jpg";
    private String path;//=context.getExternalCacheDir()+File.separator+"output_image.jpg";
    private int n = 0; //获取图片方式标记
//    String path=getExternalFilesDirs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        setToolbar();
        setTitle("添加商品");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initEVent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (n==1){
            setDefualtImage();
        }
        if (n==2){
            //设置再次app时显示的图片
            sp = getSharedPreferences("sp_img", MODE_PRIVATE);
            //取出上次存储的图片路径设置此次的图片展示
            String beforeImagePath = sp.getString("imgPath", null);
            displayImage(beforeImagePath);
        }
    }

    private void initView() {
        goodname = findViewById(R.id.add_good_name);
        gooddesc = findViewById(R.id.add_good_desc);
        goodprice = findViewById(R.id.add_goods_price);
        goodtype = findViewById(R.id.add_goods_type);
        goodweight = findViewById(R.id.add_goods_weight);
        goodtaste = findViewById(R.id.add_goods_taste);
        goodPic = findViewById(R.id.add_pic);
        addOn = findViewById(R.id.add_on);
        picture = findViewById(R.id.pic);
        picture.setImageBitmap(null);
    }

    private void initEVent() {
        context=AddGoodsActivity.this;
        path=context.getExternalCacheDir()+File.separator+"output_image.jpg";
        saveImagePath=context.getExternalFilesDir(null)+File.separator+"";
        //激活图片路径选择
        goodPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();
            }
        });
        //添加商品
        addOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData() {
        Goods goods = new Goods();
        goods.setGname(goodname.getText().toString()+"");
        goods.setDesc( gooddesc.getText().toString()+"");
        goods.setPrice( Double.parseDouble(goodprice.getText().toString()));
        goods.setTaste(goodtaste.getText().toString()+"");
        goods.setWeight(goodweight.getText().toString()+"");
        goods.setType(goodtype.getText().toString()+"");
        goods.setImage(saveImage(imagePath,goods.getGname())+"");
        GoodsDBHelper helper = GoodsDBHelper.getInstance(this);
        try {
            if (helper.insert(goods)) Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
             else Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        goodname.setText("");
        gooddesc.setText("");
        goodprice.setText("");
        goodtaste.setText("");
        goodtype.setText("");
        goodweight.setText("");
        picture.setImageBitmap(null);
    }

    //弹出图片选择获取路径
    private void showBottomDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.bottom_popupwindow, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.Bottom_popUpWindow);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();

        Button btn_album = dialog.findViewById(R.id.btn_pop_album);
        Button btn_camera = dialog.findViewById(R.id.btn_pop_camera);
        Button btn_cancle = dialog.findViewById(R.id.btn_pop_cancel);
        //相册
        btn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //打开相册
                requestPermissino(2);
                n=2;
            }
        });
        //相机
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                //打开相机
                requestPermissino(1);
                n=1;
            }
        });
        //取消
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                        picture.setImageBitmap(bitmap);
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
            picture.setImageBitmap(bitmap);
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
        picture.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}