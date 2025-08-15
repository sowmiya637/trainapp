package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class OrderconfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderconfirm);

        // Get order details from intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        double itemPrice = intent.getDoubleExtra("itemPrice", 0);
        String restaurantName = intent.getStringExtra("restaurantName");
        String deliveryTime = intent.getStringExtra("deliveryTime");
        String paymentMethod = intent.getStringExtra("paymentMethod");

        // Back to Home button
        MaterialButton backToHomeButton = findViewById(R.id.backtohomeButton);
        backToHomeButton.setOnClickListener(v -> navigateToHome());
    }

    @Override
    public void onBackPressed() {
        // First call super to maintain framework behavior
        super.onBackPressed();

        // Then perform our custom navigation
        navigateToHome();
    }

    private void navigateToHome() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish();
    }
}