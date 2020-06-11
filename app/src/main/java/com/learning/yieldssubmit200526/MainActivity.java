package com.learning.yieldssubmit200526;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.learning.utils.LogUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView)super.findViewById(R.id.imageView);
        try{
            Glide.with(this).load("http://192.168.1.113:80/Show_Products_Cost/Products_Pic/东极格-6053.jpg").into(imageView);
        }
        catch (Exception e){

            LogUtil.d("MainActivity",e.toString());
        }
    }
}