package me.luzhuo.okhttpdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.luzhuo.lib_core.ui.toast.ToastManager;
import me.luzhuo.lib_okhttp.OKHttpManager;
import me.luzhuo.lib_okhttp.callback.IFileCallback;
import me.luzhuo.lib_okhttp.exception.NetErrorException;

public class FileActivity extends AppCompatActivity {
    private OKHttpManager ok;
    private final String url = "https://***/***/Charles3.11.4.7z";
    private ProgressBar progressBar;

    public static void start(Context context) {
        context.startActivity(new Intent(context, FileActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        progressBar = findViewById(R.id.progressBar);

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
                    File saveFile = new File(getBaseContext().getExternalCacheDir(), "xxx.7z");
                    ok.downloadFile(url, saveFile);
                    ToastManager.show(FileActivity.this, saveFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NetErrorException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void asyncDown(View view) {
        File saveFile = new File(getBaseContext().getExternalCacheDir(), "xxx.7z");
        ok.downloadFile(url, saveFile, new IFileCallback() {
            @Override
            public void onStart(long total) {
                progressBar.setMax((int) total);
            }

            @Override
            public void onProgress(long progress) {
                progressBar.setProgress((int) progress);
            }

            @Override
            public void onSuccess(int code, File filepath) {
                Toast.makeText(FileActivity.this, filepath.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int code, String error) {
                Toast.makeText(FileActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
