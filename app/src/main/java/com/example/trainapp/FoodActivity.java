package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Get data passed from FoodorderActivity
        String currentStation = getIntent().getStringExtra("currentStation");
        String nextStation = getIntent().getStringExtra("nextStation");
        String arrivalTime = getIntent().getStringExtra("arrivalTime");

        // Use these values as needed in your FoodActivity
        TextView tvStationInfo = findViewById(R.id.tvStationInfo1);
        tvStationInfo.setText("Ordering food for " + nextStation + " (Arriving at " + arrivalTime + ")");

        // First dish details
        ImageView dishImage1 = findViewById(R.id.dishImage1);
        TextView dishName1 = findViewById(R.id.dishName1);
        TextView dishPrice1 = findViewById(R.id.dishPrice1);
        Button addButton1 = findViewById(R.id.addButton1);

        // Second dish details
        ImageView dishImage2 = findViewById(R.id.dishImage2);
        TextView dishName2 = findViewById(R.id.dishName2);
        TextView dishPrice2 = findViewById(R.id.dishPrice2);
        Button addButton2 = findViewById(R.id.addButton2);

        // Third dish details
        ImageView dishImage3 = findViewById(R.id.dishImage3);
        TextView dishName3 = findViewById(R.id.dishName3);
        TextView dishPrice3 = findViewById(R.id.dishPrice3);
        Button addButton3 = findViewById(R.id.addButton3);

        // Set up click listener for the first ADD button
        addButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get dish details for the first dish
                String itemName = dishName1.getText().toString();
                String priceText = dishPrice1.getText().toString().replace("₹", "");
                double itemPrice = Double.parseDouble(priceText);
                double discount = 10.0; // Example discount value

                // Create intent to navigate to PaymentActivity
                Intent intent = new Intent(FoodActivity.this, PaymentActivity.class);

                // Pass data to PaymentActivity
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemPrice", itemPrice);
                intent.putExtra("discount", discount);
                intent.putExtra("currentStation", currentStation);
                intent.putExtra("nextStation", nextStation);
                intent.putExtra("arrivalTime", arrivalTime);

                startActivity(intent);
            }
        });

        // Set up click listener for the second ADD button
        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get dish details for the second dish
                String itemName = dishName2.getText().toString();
                String priceText = dishPrice2.getText().toString().replace("₹", "");
                double itemPrice = Double.parseDouble(priceText);
                double discount = 10.0; // Example discount value

                // Create intent to navigate to PaymentActivity
                Intent intent = new Intent(FoodActivity.this, PaymentActivity.class);

                // Pass data to PaymentActivity
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemPrice", itemPrice);
                intent.putExtra("discount", discount);
                intent.putExtra("currentStation", currentStation);
                intent.putExtra("nextStation", nextStation);
                intent.putExtra("arrivalTime", arrivalTime);

                startActivity(intent);
            }
        });

        // Set up click listener for the third ADD button
        addButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get dish details for the third dish
                String itemName = dishName3.getText().toString();
                String priceText = dishPrice3.getText().toString().replace("₹", "");
                double itemPrice = Double.parseDouble(priceText);
                double discount = 10.0; // Example discount value

                // Create intent to navigate to PaymentActivity
                Intent intent = new Intent(FoodActivity.this, PaymentActivity.class);

                // Pass data to PaymentActivity
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemPrice", itemPrice);
                intent.putExtra("discount", discount);
                intent.putExtra("currentStation", currentStation);
                intent.putExtra("nextStation", nextStation);
                intent.putExtra("arrivalTime", arrivalTime);

                startActivity(intent);
            }
        });
    }
}
