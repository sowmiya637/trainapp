package com.example.trainapp;

import android.content.Intent;
import android.database.Cursor; // Import Cursor
import android.os.Bundle;
import android.util.Log; // Import Log
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast; // Import Toast
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantdisplayActivity extends AppCompatActivity {

    private ImageView restaurantImage;
    private TextView restaurantNameTextView, restaurantContactTextView, restaurantLocationTextView, restaurantCategoryTextView; // Renamed for clarity
    private Button viewOrderButton, button3;
    private DatabaseHelper dbHelper;
    private String currentRestaurantName; // To store the name for later use by buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantdisplay);

        initializeViews(); // Initialize all UI elements first
        dbHelper = new DatabaseHelper(this); // Initialize DatabaseHelper

        // Get restaurant name from intent (sent by RestaurantincludeActivity)
        String nameFromIntent = getIntent().getStringExtra("RESTAURANT_NAME");

        if (nameFromIntent != null && !nameFromIntent.isEmpty()) {
            currentRestaurantName = nameFromIntent; // Store it
            Log.d("RestaurantDisplay", "Received RESTAURANT_NAME: " + currentRestaurantName);
            showSpecificRestaurant(currentRestaurantName);
        } else {
            Toast.makeText(this, "No restaurant name passed to display.", Toast.LENGTH_LONG).show();
            Log.e("RestaurantDisplay", "RESTAURANT_NAME extra was null or empty.");
            // Optionally, you might want to finish() or show a default state
        }

        // Set up button listeners
        viewOrderButton.setOnClickListener(v -> {
            if (currentRestaurantName != null && !currentRestaurantName.isEmpty()) {
                Intent intent = new Intent(RestaurantdisplayActivity.this, RestaurantActivity.class);
                intent.putExtra("RESTAURANT_NAME", currentRestaurantName); // Pass the current restaurant's name
                startActivity(intent);
            } else {
                Toast.makeText(RestaurantdisplayActivity.this, "Restaurant name not available to view orders.", Toast.LENGTH_SHORT).show();
                // Optionally start RestaurantActivity without an extra if it can handle a general view
                // Intent intent = new Intent(this, RestaurantActivity.class);
                // startActivity(intent);
            }
        });

        button3.setOnClickListener(v -> {
            // Let's assume button3 also needs the restaurant context for now
            if (currentRestaurantName != null && !currentRestaurantName.isEmpty()) {
                Intent intent = new Intent(RestaurantdisplayActivity.this, RestaurantActivity.class);
                // If button3 is for "Update Food List" it likely needs the restaurant context
                intent.putExtra("RESTAURANT_NAME", currentRestaurantName);
                startActivity(intent);
            } else {
                Toast.makeText(RestaurantdisplayActivity.this, "Restaurant name not available.", Toast.LENGTH_SHORT).show();
                // Optionally start RestaurantActivity without an extra if it can handle a general view
                // Intent intent = new Intent(this, RestaurantActivity.class);
                // startActivity(intent);
            }
        });
    }

    private void initializeViews() {
        restaurantImage = findViewById(R.id.restaurantImage); // Assuming you have this ID
        restaurantNameTextView = findViewById(R.id.restaurantName); // Assuming R.id.restaurantName is a TextView
        restaurantContactTextView = findViewById(R.id.restaurantContact); // Assuming R.id.restaurantContact is a TextView
        restaurantLocationTextView = findViewById(R.id.restaurantLocation); // Assuming R.id.restaurantLocation is a TextView
        restaurantCategoryTextView = findViewById(R.id.restaurantCategory); // Assuming R.id.restaurantCategory is a TextView
        viewOrderButton = findViewById(R.id.viewOrderButton);
        button3 = findViewById(R.id.button3);
    }

    // Method to fetch and display restaurant details (similar to your previous version)
    private void showSpecificRestaurant(String name) {
        if (dbHelper == null) {
            Log.e("RestaurantDisplay", "DatabaseHelper not initialized in showSpecificRestaurant");
            Toast.makeText(this, "Database error.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("RestaurantDisplay", "Querying database for restaurant: " + name);
        Cursor cursor = dbHelper.getRestaurantByName(name); // Make sure this method exists in DatabaseHelper

        if (cursor != null && cursor.moveToFirst()) {
            Log.d("RestaurantDisplay", "Restaurant found in database.");
            // Use your DatabaseHelper column name constants
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME); // Replace with actual constant if different
            int contactIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT);
            int addressIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS);
            int cityIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CITY);
            int categoryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY);

            if (nameIndex != -1) restaurantNameTextView.setText(cursor.getString(nameIndex)); else restaurantNameTextView.setText("N/A");
            if (contactIndex != -1) restaurantContactTextView.setText(cursor.getString(contactIndex)); else restaurantContactTextView.setText("N/A");
            if (categoryIndex != -1) restaurantCategoryTextView.setText(cursor.getString(categoryIndex)); else restaurantCategoryTextView.setText("N/A");

            String fullAddress = "N/A";
            if (addressIndex != -1 && cityIndex != -1) {
                fullAddress = cursor.getString(addressIndex) + ", " + cursor.getString(cityIndex);
            } else if (addressIndex != -1) {
                fullAddress = cursor.getString(addressIndex);
            } else if (cityIndex != -1) {
                fullAddress = cursor.getString(cityIndex);
            }
            restaurantLocationTextView.setText(fullAddress);

            // You'll need to handle restaurantImage separately (e.g., if you store a path or byte array)

            cursor.close();
        } else {
            Log.w("RestaurantDisplay", "Restaurant NOT found in database: " + name);
            Toast.makeText(this, "Restaurant details not found for: " + name, Toast.LENGTH_LONG).show();
            // Clear fields or show 'Not Found'
            restaurantNameTextView.setText("Not Found");
            restaurantContactTextView.setText("");
            restaurantLocationTextView.setText("");
            restaurantCategoryTextView.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        if (dbHelper != null) {
            dbHelper.close(); // Close dbHelper if it was initialized
        }
        super.onDestroy();
    }
}