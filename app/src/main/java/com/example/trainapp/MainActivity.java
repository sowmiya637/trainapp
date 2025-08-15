package com.example.trainapp;
// MainActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Your first XML layout

        Button userButton = findViewById(R.id.userLoginButton);
        Button restaurantButton = findViewById(R.id.restaurantLoginButton);
        Button deliverymanButton = findViewById(R.id.deliverymanLoginButton);

        // Handle User button click
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity("user");
            }
        });

        // Handle Restaurant button click
        restaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity("restaurant");
            }
        });

        // Handle Deliveryman button click
        deliverymanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginActivity("deliveryman");
            }
        });
    }

    // Helper method to start LoginActivity with user type
    private void startLoginActivity(String userType) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }
}