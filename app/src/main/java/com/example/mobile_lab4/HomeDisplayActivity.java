package com.example.mobile_lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeDisplayActivity extends AppCompatActivity {
    private Button btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_display);
        String fullName = getIntent().getStringExtra("fullName");
        TextView txtName = findViewById(R.id.txtName);
        txtName.setText(fullName);

        btnLogOut = findViewById(R.id.button2);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }
    private void logOut() {

        // Kết thúc HomeDisplayActivity và khởi chạy SignInActivity
        Intent intent = new Intent(HomeDisplayActivity.this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}