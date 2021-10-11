package com.fyp.whatsmoney;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Camera extends AppCompatActivity {

    private static final String MODEL_PATH = "model_unquant.tflite";
    private static final String LABEL_PATH = "labels.txt";

    private static final boolean QUANT = false;
    private static final int INPUT_SIZE = 224;
    public static ImageView ivImage;
    Bitmap bitmap;
    boolean doubleBackToExitPressedOnce = false;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();
    private Button btnDetectObject;
    private CameraView cameraView;
    private Button micButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraView = findViewById(R.id.cameraView);
        // btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
        micButton = findViewById(R.id.micButton);
        //TextToSpeech Initializer

        ivImage = findViewById(R.id.iv_prev);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

//                ivImage.setVisibility(View.VISIBLE);
//                ivImage.setImageBitmap(bitmap);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);

                Intent produceResult = new Intent(getApplicationContext(), Result.class);

                produceResult.putExtra("result", results.toString());
                produceResult.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(produceResult);
                finishActivity();
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
                cameraView.stop();
                btnDetectObject.setVisibility(View.INVISIBLE);
                micButton.setVisibility(View.INVISIBLE);
            }
        });
        micButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        initTensorFlowAndLoadModel();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void openGallery() {
        GalleryUtils.openGallery(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryUtils.GALLERY_REQUEST_CODE) {
            bitmap = GalleryUtils.getBitmap(Camera.this, resultCode, data);

//            if (true) {
//                ivImage.setVisibility(View.VISIBLE);
//                ivImage.setImageBitmap(bitmap);
//
//                return;
//            }

            final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
            Intent produceResult = new Intent(getApplicationContext(), Result.class);
            produceResult.putExtra("result", results.toString());
            produceResult.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(produceResult);
            finishActivity();
        }
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
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void finishActivity() {
        this.finish();
    }

}
