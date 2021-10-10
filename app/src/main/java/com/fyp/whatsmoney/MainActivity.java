package com.fyp.whatsmoney;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        TextView tv = findViewById(R.id.tv);
        ImageView iv = findViewById(R.id.iv);
        iv.animate().alpha(1).setDuration(1000);
        tv.setAlpha(0);
        tv.setTranslationY(-200);
        tv.animate().translationYBy(50).alpha(1).setDuration(1200);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Intent = new Intent(getApplicationContext (), BMICalculator.class);
                startActivity(Intent);
                finish();
            }
        }, 1500);



    }
}