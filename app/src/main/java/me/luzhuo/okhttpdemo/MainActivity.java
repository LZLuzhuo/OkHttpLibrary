package me.luzhuo.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_okhttp.OKHttpManager;
import me.luzhuo.lib_okhttp.bean.PostType;
import me.luzhuo.lib_okhttp.callback.IContentCallback;
import me.luzhuo.lib_okhttp.exception.NetErrorException;
import me.luzhuo.lib_okhttp.interceptor.TokenInterceptor;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            OKHttpManager ok = new OKHttpManager(new TokenInterceptor() {
                @Override
                public String getToken() {
                    return "adsad";
                }
            });

            String url = "http://www.baidu.com";

            // String message_get = ok.get(url);
            ok.get(url, new IContentCallback() {
                @Override
                public void onSuccess(int code, String data) {
                    Log.e(TAG, "<<<" + data);
                }

                @Override
                public void onError(int code, String error) {
                    Toast.makeText(MainActivity.this, "网络异常, 请检查网络!", Toast.LENGTH_SHORT).show();
                }
            });

            // String message_post = ok.post(url, "{}", PostType.JSON);
            ok.post(url, "{}", PostType.JSON, new IContentCallback() {
                @Override
                public void onSuccess(int i, String data) {
                    Log.e(TAG, ">>>" + data);
                }

                @Override
                public void onError(int i, String error) {
                    Toast.makeText(MainActivity.this, "网络异常, 请检查网络!", Toast.LENGTH_SHORT).show();
                }
            });

            // Log.e(TAG, "" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}