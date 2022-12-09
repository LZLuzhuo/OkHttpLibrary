package me.luzhuo.okhttpdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_core.ui.toast.ToastManager;
import me.luzhuo.lib_okhttp.OKHttpManager;
import me.luzhuo.lib_okhttp.bean.PostType;
import me.luzhuo.lib_okhttp.callback.IContentCallback;
import me.luzhuo.lib_okhttp.exception.NetErrorException;

// get 和 post 请求
public class GetPostActivity extends AppCompatActivity {
    private OKHttpManager ok;
    private static final String url = "https://www.baidu.com";

    public static void start(Context context) {
        context.startActivity(new Intent(context, GetPostActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_post);

        try {
            ok = new OKHttpManager();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void syncGet(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message_get = ok.get(url);
                    ToastManager.show(GetPostActivity.this, message_get);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NetErrorException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void asyncGet(View view) {
        ok.get(url, new IContentCallback() {
            @Override
            public void onSuccess(int code, String data) {
                Toast.makeText(GetPostActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String error) {
                Toast.makeText(GetPostActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void syncPost(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message_post = ok.post(url, "{}", PostType.JSON);
                    Log.e("TAG", "" + message_post);
                    ToastManager.show(GetPostActivity.this, message_post);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NetErrorException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void asyncPost(View view) {
        ok.post(url, "{}", PostType.JSON, new IContentCallback() {
            @Override
            public void onSuccess(int code, String data) {
                Toast.makeText(GetPostActivity.this, data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String error) {
                Toast.makeText(GetPostActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
