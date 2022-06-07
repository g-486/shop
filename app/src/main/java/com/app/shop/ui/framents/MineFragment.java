package com.app.shop.ui.framents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.app.shop.R;
import com.app.shop.databinding.FragmentMineBinding;
import com.app.shop.ui.activity.AboutActivity;
import com.app.shop.ui.activity.AddGoodsActivity;
import com.app.shop.ui.activity.AddorderActivity;
import com.app.shop.ui.activity.AddsayActivity;
import com.app.shop.ui.activity.AdduserActivity;
import com.app.shop.ui.activity.DataAnalyActivity;
import com.app.shop.ui.activity.LoginActivity;
import com.app.shop.ui.activity.OtherSetActivity;
import com.app.shop.ui.activity.ReadGoodsActivity;
import com.app.shop.ui.activity.ReadOrderActivity;
import com.app.shop.ui.activity.ResetGoodActivity;
import com.app.shop.utils.SharedPreferencesUtils;
import com.app.shop.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    private FragmentMineBinding binding;
    private boolean sign;

    static FragmentActivity context;

    private Intent intent;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);
        context = getActivity();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        sign = SharedPreferencesUtils.getSignState(getActivity());
        if (sign) {
            binding.mineText.setText(SharedPreferencesUtils.getAppName(getActivity()));
        } else {
            binding.mineText.setText("商店名");
        }
        initEvent();
//        show();
//        linkPost();
    }

    @Override
    public void onResume() {
        super.onResume();
        String name = SharedPreferencesUtils.getAppName(getActivity());
        binding.mineText.setText(name);
    }

    private void linkPost() {
        try {
            URL url = new URL("http://47.104.103.57/index.php/Home/Index/loginApp");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("post");
            connection.setRequestProperty("Content_type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charest", "UTF-8");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();

            String user = "username=18728701865&password=123456";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(user.getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            String message = connection.getResponseMessage();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                String result = streamToString(inputStream);
                Log.e("aaa", result);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String streamToString(InputStream stream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = stream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            stream.close();
            byte[] bytes = baos.toByteArray();
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    private void show(){
//        //接口参数 String username,String password
//
//        String url = "http://47.104.103.57/index.php/Home/Index/loginApp";
//        //第一步创建OKHttpClient
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//        //第二步创建RequestBody（Form表达）
//        RequestBody body = new FormBody.Builder()
//                .add("username", "18728701865")
//                .add("password", "123456")
//                .build();
//        //第三步创建Rquest
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        //第四步创建call回调对象
//        final Call call = client.newCall(request);
//        //第五步发起请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                Log.i("result", result);
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("onFailure", e.getMessage());
//            }
//        });
//    }

    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    private void initEvent() {
        binding.mineHead.setOnClickListener(this::onClick);
        binding.mineSetting.setOnClickListener(this::onClick);
        if (sign){
            String name = SharedPreferencesUtils.getAppName(getActivity());
            binding.mineText.setText(name);

            binding.tvGoodAdd.setOnClickListener(this);
            binding.tvGoodRe.setOnClickListener(this);
            binding.tvGoodDel.setOnClickListener(this);
            binding.tvGoodLook.setOnClickListener(this);
            binding.tvOrderLook.setOnClickListener(this);
            binding.tvOrderChose.setOnClickListener(this);
            binding.tvDataEvent.setOnClickListener(this);
        }
        //function
        binding.tvSetOther.setOnClickListener(this);
        binding.tvAbout.setOnClickListener(this);

        binding.addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), AddorderActivity.class);
                startActivity(intent);
            }
        });
        binding.addSay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), AddsayActivity.class);
                startActivity(intent);
            }
        });
        binding.addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity(), AdduserActivity.class);
                startActivity(intent);
            }
        });
//        binding.tvOut.setOnClickListener(new View.OnDialogClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferencesUtils.setSignState(getActivity(),false);
//
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_head:
                intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_setting:
                intent=new Intent(context,OtherSetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_good_add:
                if (sign){
                    intent = new Intent(context, AddGoodsActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast(getActivity(),"请检查登录");
                }
                break;
            case R.id.tv_good_del:
                if (sign){
                    intent = new Intent(context, ReadGoodsActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast(getActivity(),"请检查登录");
                }
                break;
            case R.id.tv_good_re:
                if (sign){
                    intent = new Intent(context, ResetGoodActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast(getActivity(),"请检查登录");
                }
                break;
            case R.id.tv_good_look:
                if (sign){
                    intent = new Intent(context, ReadGoodsActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast(getActivity(),"请检查登录");
                }
                break;
            case R.id.tv_order_look:
            case R.id.tv_order_chose:
                if (sign){
                    intent = new Intent(context, ReadOrderActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast(getActivity(),"请检查登录");
                }
                break;
            case R.id.tv_data_event:
                if (sign){
                    intent = new Intent(context, DataAnalyActivity.class);
                    startActivity(intent);
                }else {
                    ToastUtils.shortToast(getActivity(),"请检查登录");
                }
                break;
            case R.id.tv_set_other:
                intent = new Intent(context, OtherSetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_about:
                intent = new Intent(context, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
