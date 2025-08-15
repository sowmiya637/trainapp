package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class FoodorderActivity extends AppCompatActivity {

    private TextView tvCurrentStation, tvNextStation, tvArrivalTime, tvDistance;
    private TextView tvNextStationFull, tvNextStationTime, tvPlatform;
    private Button btnOrderFood;
    private EditText etPnrNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order);

        String userEmail = getIntent().getStringExtra("userEmail");
        String userType = getIntent().getStringExtra("userType");

        initializeViews();
        setupClickListeners();
        updateJourneyData();
    }

    private void initializeViews() {
        tvCurrentStation = findViewById(R.id.tvCurrentStation);
        tvNextStation = findViewById(R.id.tvNextStation);
        tvArrivalTime = findViewById(R.id.tvArrivalTime);
        tvDistance = findViewById(R.id.tvDistance);
        tvNextStationFull = findViewById(R.id.tvNextStationFull);
        tvNextStationTime = findViewById(R.id.tvNextStationTime);
        tvPlatform = findViewById(R.id.tvPlatform);
        btnOrderFood = findViewById(R.id.btnOrderFood);
        etPnrNumber = findViewById(R.id.editTextText2);
    }

    private void setupClickListeners() {
        btnOrderFood.setOnClickListener(v -> {
            String pnrNumber = etPnrNumber.getText().toString().trim();

            if(pnrNumber.isEmpty()) {
                Snackbar.make(v, "Please enter your PNR number", Snackbar.LENGTH_SHORT).show();
                return;
            }

            // Navigate to RestaurantlistActivity instead of FoodActivity
            Intent intent = new Intent(FoodorderActivity.this, RestaurantlistActivity.class);

            // Pass all journey data
            intent.putExtra("currentStation", tvCurrentStation.getText().toString());
            intent.putExtra("nextStation", tvNextStation.getText().toString());
            intent.putExtra("arrivalTime", tvArrivalTime.getText().toString());
            intent.putExtra("pnrNumber", pnrNumber);

            startActivity(intent);
            Snackbar.make(v, "Loading restaurants near " + tvNextStation.getText(), Snackbar.LENGTH_SHORT).show();
        });
    }

    private void updateJourneyData() {
        tvCurrentStation.setText("Delhi (DEL)");
        tvNextStation.setText("Jaipur (JP)");
        tvArrivalTime.setText("Arrives at 14:30");
        tvDistance.setText("120 km remaining");
        tvNextStationFull.setText("Jaipur Junction (JP)");
        tvNextStationTime.setText("Estimated Arrival: 14:30 (25 mins)");
        tvPlatform.setText("Platform: 3");
    }
}