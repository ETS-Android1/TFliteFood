package com.fyp.whatsmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    EditText etConfirmPassword;

    String strEtEmail;
    String strEtPassword;
    String strEtConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email_sign_up);
        etPassword = findViewById(R.id.et_password_sign_up);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUp.this, Login.class));
    }

    public void signUp(View view) {
        strEtConfirmPassword = etConfirmPassword.getText().toString();
        strEtEmail = etEmail.getText().toString();
        strEtPassword = etPassword.getText().toString();

        if (!strEtConfirmPassword.equals(strEtPassword))
            return;


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(strEtEmail, strEtPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(SignUp.this, Dashboard.class));
                            finish();
                        } else {
                            if (task.getException() == null)
                                return;
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void login(View view) {
        onBackPressed();
    }
}