package com.example.mobile_lab4;
/*
package com.example.mobile_lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends AppCompatActivity {

    private ConstraintLayout layoutSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText edtUsername = findViewById(R.id.edtUser);
        final EditText edtPassword = findViewById(R.id.edtPass);
        final Button signinBtn = findViewById(R.id.buttonSignIn);
        initUi();
        initLister();
    }

    private void initUi() {
        layoutSignUp = findViewById(R.id.layout_sign_in);
    }

    private void initLister() {
        layoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
*/

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_lab4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnSignIn;
    private TextView txtSignUp, txtMessage;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        etUsername = findViewById(R.id.edtUser);
        etPassword = findViewById(R.id.edtPass);
        btnSignIn = findViewById(R.id.buttonSignIn);
        txtSignUp = findViewById(R.id.txtSignUp);
        txtMessage = findViewById(R.id.txtMessage);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else if (!isUsernameValid(username)) {
                    Toast.makeText(SignInActivity.this, "Username must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else if (!isPasswordValid(password)) {
                    Toast.makeText(SignInActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    signIn(username, password);
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUp();
            }
        });

        // Get the full name passed from SignUpActivity and display it
        String fullName = getIntent().getStringExtra("fullName");
        if (fullName != null && !fullName.isEmpty()) {
            txtMessage.setText("Welcome, " + fullName + "!");
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() >= 6;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private void signIn(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công, chuyển đến màn hình home
                            Toast.makeText(SignInActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, HomeDisplayActivity.class);
                            intent.putExtra("fullName", getIntent().getStringExtra("fullName"));
                            startActivity(intent);
                            finish();
                        } else {
                            // Đăng nhập thất bại
                            showCustomErrorToast();
                        }
                    }
                });
    }

    private void showCustomErrorToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error,
                findViewById(R.id.custom_toast_error_container));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
