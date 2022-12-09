package me.luzhuo.okhttpdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_okhttp.OKHttpManager;
import me.luzhuo.lib_okhttp.callback.IBitmapCallback;
import me.luzhuo.lib_okhttp.exception.NetErrorException;

public class BitmapActivity extends AppCompatActivity {
    private static final String url = "https://t7.baidu.com/it/u=1415984692,3889465312&fm=193&f=GIF";
    private OKHttpManager ok;
    private ImageView iv;

    public static void start(Context context) {
        context.startActivity(new Intent(context, BitmapActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

        iv = findViewById(R.id.iv);

        try {
            ok = new OKHttpManager();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void syncDown(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = ok.getBitmap(url);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NetErrorException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void asyncDown(View view) {
        ok.getBitmap(url, new IBitmapCallback() {
            @Override
            public void onSuccess(int code, Bitmap data) {
                iv.setImageBitmap(data);
            }

            @Override
            public void onError(int code, String error) {
                Toast.makeText(BitmapActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
