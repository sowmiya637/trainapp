package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode); // Use your XML filename here

        // Track Order Button
        MaterialButton trackOrderBtn = findViewById(R.id.btnViewOrderButton);
        trackOrderBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ModeActivity.this, OrderconfirmActivity.class);
            startActivity(intent);
        });


    }
}