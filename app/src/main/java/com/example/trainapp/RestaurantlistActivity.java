package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantlistActivity extends AppCompatActivity {

    private String pnrNumber;
    private String currentStation;
    private String nextStation;
    private String arrivalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);

        // Get data from FoodorderActivity
        Intent intent = getIntent();
        pnrNumber = intent.getStringExtra("pnrNumber");
        currentStation = intent.getStringExtra("currentStation");
        nextStation = intent.getStringExtra("nextStation");
        arrivalTime = intent.getStringExtra("arrivalTime");

        setupRestaurantCards();
    }

    private void setupRestaurantCards() {
        // Restaurant 1 - Railway Food Plaza
        findViewById(R.id.restaurantCard1).setOnClickListener(v ->
                openFoodActivity(
                        "Railway Food Plaza",
                        "North Indian • Chinese • Fast Food",
                        4.2f,
                        "20-30 mins"
                )
        );

        // Restaurant 2 - Platform 1 Diner
        findViewById(R.id.restaurantCard2).setOnClickListener(v ->
                openFoodActivity(
                        "Platform 1 Diner",
                        "South Indian • Continental",
                        4.5f,
                        "15-25 mins"
                )
        );

        // Restaurant 3 - Express Bites
        findViewById(R.id.restaurantCard3).setOnClickListener(v ->
                openFoodActivity(
                        "Express Bites",
                        "Fast Food • Snacks • Beverages",
                        3.9f,
                        "10-20 mins"
                )
        );
    }

    private void openFoodActivity(String name, String cuisine, float rating, String deliveryTime) {
        Intent intent = new Intent(this, FoodActivity.class);

        // Pass all relevant data
        intent.putExtra("restaurantName", name);
        intent.putExtra("cuisineType", cuisine);
        intent.putExtra("rating", rating);
        intent.putExtra("deliveryTime", deliveryTime);

        // Pass through the journey data
        intent.putExtra("pnrNumber", pnrNumber);
        intent.putExtra("currentStation", currentStation);
        intent.putExtra("nextStation", nextStation);
        intent.putExtra("arrivalTime", arrivalTime);

        startActivity(intent);
    }
}