package com.example.trainapp;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class PaymentActivity extends AppCompatActivity {

    private String selectedPaymentMethod = "upi"; // Default selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Initialize payment method selection
        setupPaymentMethodSelection();

        // Initialize pay button
        MaterialButton payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(v -> {
            processPayment();
        });
    }

    private void setupPaymentMethodSelection() {
        // UPI Option
        findViewById(R.id.upiOption).setOnClickListener(v -> {
            selectPaymentMethod("upi");
        });

        // Card Option
        findViewById(R.id.cardOption).setOnClickListener(v -> {
            selectPaymentMethod("card");
        });

        // Net Banking Option
        findViewById(R.id.netBankingOption).setOnClickListener(v -> {
            selectPaymentMethod("netbanking");
        });

        // Cash on Delivery Option
        findViewById(R.id.codOption).setOnClickListener(v -> {
            selectPaymentMethod("cod");
        });
    }

    private void selectPaymentMethod(String method) {
        selectedPaymentMethod = method;
        Toast.makeText(this, "Selected: " + method.toUpperCase(), Toast.LENGTH_SHORT).show();
    }

    private void processPayment() {
        // Handle payment based on selected method
        switch (selectedPaymentMethod) {
            case "upi":
                launchUPIPayment();
                break;
            case "card":
                launchCardPayment();
                break;
            case "netbanking":
                launchNetBankingPayment();
                break;
            case "cod":
                confirmCashOnDelivery();
                break;
            default:
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchUPIPayment() {
        // Simulate UPI payment success
        Toast.makeText(this, "UPI Payment Successful!", Toast.LENGTH_SHORT).show();
        navigateToModeActivity();
    }

    private void launchCardPayment() {
        // Simulate card payment success
        Toast.makeText(this, "Card Payment Successful!", Toast.LENGTH_SHORT).show();
        navigateToModeActivity();
    }

    private void launchNetBankingPayment() {
        // Simulate net banking success
        Toast.makeText(this, "Net Banking Payment Successful!", Toast.LENGTH_SHORT).show();
        navigateToModeActivity();
    }

    private void confirmCashOnDelivery() {
        // Confirm COD order
        Toast.makeText(this, "Order confirmed! Pay when food arrives.", Toast.LENGTH_LONG).show();
        navigateToModeActivity();
    }

    private void navigateToModeActivity() {
        // Get order details from intent
        Intent receivedIntent = getIntent();
        String itemName = receivedIntent.getStringExtra("itemName");
        double itemPrice = receivedIntent.getDoubleExtra("itemPrice", 0);
        String restaurantName = receivedIntent.getStringExtra("restaurantName");
        String deliveryTime = receivedIntent.getStringExtra("deliveryTime");

        // Create intent for ModeActivity
        Intent intent = new Intent(PaymentActivity.this, ModeActivity.class);

        // Pass all relevant order details
        intent.putExtra("itemName", itemName);
        intent.putExtra("itemPrice", itemPrice);
        intent.putExtra("restaurantName", restaurantName);
        intent.putExtra("deliveryTime", deliveryTime);

        // Add any other data you need to pass
        intent.putExtra("paymentMethod", selectedPaymentMethod);

        startActivity(intent);
        finish(); // Optional: close payment activity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Handle payment gateway responses here if needed
    }
}