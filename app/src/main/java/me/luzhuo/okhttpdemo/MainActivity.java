package me.luzhuo.okhttpdemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void interceptor(View view) {
        InterceptorActivity.start(this);
    }

    public void get_post(View view) {
        GetPostActivity.start(this);
    }

    public void downBitmap(View view) {
        BitmapActivity.start(this);
    }

    public void downFile(View view) {
        FileActivity.start(this);
    }
}