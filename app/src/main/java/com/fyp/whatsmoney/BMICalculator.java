package com.fyp.whatsmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BMICalculator extends AppCompatActivity {

    double weight, height;

    TextView tvResult;
    EditText etHeight;
    EditText etWeight;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        tvResult = findViewById(R.id.tv_result);
        etHeight = findViewById(R.id.editText);
        etWeight = findViewById(R.id.et_weight);
        iv = findViewById(R.id.back);

    }

    public void next(View view) {
        if (Utils.bmi == 0) {
            Toast.makeText(this, "Please get your BMI first", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(this, Camera.class));
        finish();
    }

    public void calculate(View view) {
        weight = Double.parseDouble(etWeight.getText().toString());
        height = Double.parseDouble(etHeight.getText().toString());
        Utils.bmi = (weight / height / height) * 10000;
        tvResult.setText(String.valueOf(Utils.bmi));
        iv.animate().alpha(0.8f).rotation(405).setDuration(2000);
    }
}