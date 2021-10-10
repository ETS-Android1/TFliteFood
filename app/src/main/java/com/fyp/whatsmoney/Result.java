package com.fyp.whatsmoney;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        TextView Suggestion = findViewById(R.id.textView2);
        resultTextView.setText(str);

        try {
            str = str.split(":")[1];
            str = str.replace(" ", "");
            str = str.split(",")[0];
            str = str.replace(")", "");
            double calories = Double.parseDouble(str);
            double factor;
            if (Utils.bmi < 18.5) {
                factor = 18.5 - Utils.bmi;
                int sugItems = (int) ((int) (factor * 3 * 3500) / calories);
                String sug = "Eat " + sugItems + " these food items within 8 days to maintain your BMI";
                Suggestion.setText(sug);
            } else if (Utils.bmi > 25) {
                factor = Utils.bmi - 25;
                int sugItems = (int) ((int) (factor * 3 * 3500) / calories);
                String sug = "Eat healthy - calories equivalent to " + sugItems + " of these food items must be burned to maintain BMI";
                Suggestion.setText(sug);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onPause() {
        finish();
        System.exit(0);
        super.onPause();
    }

}
