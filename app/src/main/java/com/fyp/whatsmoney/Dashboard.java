package com.fyp.whatsmoney;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Dashboard extends AppCompatActivity {
    private static final String MODEL_PATH = "model_unquant.tflite";
    private static final String LABEL_PATH = "labels.txt";

    private static final boolean QUANT = false;
    private static final int INPUT_SIZE = 224;

    Bitmap bitmap;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initTensorFlowAndLoadModel();

    }

    public void bmiCal(View view) {
        startActivity(new Intent(this, BMICalculator.class));
    }

    public void cam(View view) {
        startActivity(new Intent(this, Camera.class));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void chose(View view) {
        GalleryUtils.openGallery(this);
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryUtils.GALLERY_REQUEST_CODE) {
            bitmap = GalleryUtils.getBitmap(Dashboard.this, resultCode, data);
            final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
            Intent produceResult = new Intent(getApplicationContext(), Result.class);
            produceResult.putExtra("result", results.toString());
            produceResult.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(produceResult);
        }
    }

}