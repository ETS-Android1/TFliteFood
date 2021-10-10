package com.fyp.whatsmoney;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class resultVoiceOver extends AppCompatActivity {

    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_result_voice_over);

        Bundle getResult = getIntent ().getExtras ();
        String result = getResult.getString ("result");

        str = result.replaceAll("\\[", "").replaceAll("\\]","");

        TextView resultTextView  = findViewById (R.id.textView);
        resultTextView.setText (str);
        Log.i("msg",str);

    }


    @Override
    protected void onPause() {
        finish();
        System.exit(0);
        super.onPause();
    }

}
