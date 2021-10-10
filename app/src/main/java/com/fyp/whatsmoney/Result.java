package com.fyp.whatsmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_voice_over);

        Bundle getResult = getIntent().getExtras();
        String result = getResult.getString("result");

        str = result.replaceAll("\\[", "").replaceAll("]", "");
        TextView resultTextView = findViewById(R.id.textView);
        resultTextView.setText(str);

        // TODO: IMPLEMENT CNN ON THE BASIS OF BMI


    }


    @Override
    protected void onPause() {
        finish();
        System.exit(0);
        super.onPause();
    }

}
