package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.mapsdemo.databinding.SlidescreenBinding;

public class slideActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    slideviewpageradapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_slide);
        viewPager = findViewById(R.id.viewpager);
        adapter = new slideviewpageradapter(this);
        viewPager.setAdapter(adapter);
        //run onboarding screen always
        //Intent intent = new Intent(slideActivity.this,MapsActivity.class);
        //startActivity(intent);
        //run onboarding screen only once when installed
        if(isOpenAlread())
        {
            Intent intent = new Intent(slideActivity.this,MapsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            SharedPreferences.Editor editor = getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }
    }

    private boolean isOpenAlread() {
        SharedPreferences sharedPreferences = getSharedPreferences("slide",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("slide",false);
        return result;
    }
}