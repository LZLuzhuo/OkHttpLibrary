package me.luzhuo.okhttpdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_okhttp.OKHttpManager;
import me.luzhuo.lib_okhttp.callback.IContentCallback;
import me.luzhuo.lib_okhttp.interceptor.TokenInterceptor;

public class InterceptorActivity extends AppCompatActivity {
    private OKHttpManager ok;
    private static final String url = "https://www.baidu.com";

    public static void start(Context context) {
        context.startActivity(new Intent(context, InterceptorActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercapt);

        try {
            ok = new OKHttpManager(new TokenInterceptor() { // 添加一个token拦截器
                @Override
                public String getToken() {
                    return "adsad";
                }
            });
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void send(View view) {
        ok.get(url, new IContentCallback() {
            @Override
            public void onSuccess(int code, String data) { }

            @Override
            public void onError(int code, String error) { }
        });
    }
}
