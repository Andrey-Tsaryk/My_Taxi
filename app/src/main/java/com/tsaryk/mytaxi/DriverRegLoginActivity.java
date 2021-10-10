package com.tsaryk.mytaxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverRegLoginActivity extends AppCompatActivity {

    TextView driverStatus, question;
    Button signInBtn, signUpBtn;
    EditText emailET, passwordET;

    FirebaseAuth mAuth;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg_login);

        driverStatus = (TextView)findViewById(R.id.statusDriver);
        question = (TextView)findViewById(R.id.accountCreate);
        signInBtn = (Button) findViewById(R.id.signInDriver);
        signUpBtn = (Button) findViewById(R.id.signUpDriver);
        emailET = (EditText) findViewById(R.id.driverEmail);
        passwordET = (EditText) findViewById(R.id.driverPassword);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        signUpBtn.setVisibility(View.INVISIBLE);  //кнопки не видна
        signUpBtn.setEnabled(false);              // она не нажимается!!!

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInBtn.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
                signInBtn.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(true);
                driverStatus.setText("Registration for driver");
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                RegisterDiver(email, password);
            }
        });
    }

    private void RegisterDiver(String email, String password) {
        loadingBar.setTitle("Registration driver");
        loadingBar.setMessage("Please wait while loading");
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DriverRegLoginActivity.this, "Registration completed successfully", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
                else{
                    Toast.makeText(DriverRegLoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}