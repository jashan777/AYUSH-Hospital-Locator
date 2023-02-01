package com.example.mapsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash_screen_activity extends AppCompatActivity {
    final int SPLASH_SCREEN_TIME_OUT=2000;
    //making variable for animation and text view
    Animation fadein,moveupfadein;
    ImageView main_logo;//for images you imported
    TextView app_name;//for text you imported
    //variable for Media Player
    MediaPlayer sound_player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //for fullscreen splash_screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();//for hiding splash_screen.

        //fade in animation object taking in the fade in animation created in res/anim/fade_in.xml
        fadein = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        moveupfadein = AnimationUtils.loadAnimation(this,R.anim.moveup_fadein);
        sound_player = MediaPlayer.create(splash_screen_activity.this,R.raw.soundeffect);
        //hooks (finding them by there respective ID)
        app_name = findViewById(R.id.splash_text);
        main_logo = findViewById(R.id.hospital_LOGO);
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) main_logo.getDrawable();
        drawable.start();
        //Finally applying animation to them
        main_logo.setAnimation(moveupfadein);

        new Handler().postDelayed(() -> sound_player.start(),700);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(splash_screen_activity.this, slideActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_SCREEN_TIME_OUT);
    }
}