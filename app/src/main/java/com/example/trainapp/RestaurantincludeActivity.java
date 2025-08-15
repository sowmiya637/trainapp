package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RestaurantincludeActivity extends AppCompatActivity {

    private static final int MIN_RESTAURANT_NAME_LENGTH = 3;
    private static final int PHONE_NUMBER_LENGTH = 10;

    private EditText restaurantNameEditText, contactNoEditText, addressEditText, cityEditText;
    private CheckBox vegCheckBox, nonVegCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantinclude);

        initializeViews();

        Button finishButton = findViewById(R.id.button2);
        finishButton.setOnClickListener(v -> handleFormSubmission());
    }

    private void initializeViews() {
        restaurantNameEditText = findViewById(R.id.editTextText);
        contactNoEditText = findViewById(R.id.editTextPhone);
        addressEditText = findViewById(R.id.editTextText3);
        cityEditText = findViewById(R.id.editTextText4);
        vegCheckBox = findViewById(R.id.checkBox2);
        nonVegCheckBox = findViewById(R.id.checkBox);
    }

    private void handleFormSubmission() {
        if (!validateForm()) return;

        String name = restaurantNameEditText.getText().toString().trim();
        String phone = contactNoEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String category = getFoodCategory();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean success = dbHelper.insertRestaurant(name, phone, address, city, category);
        dbHelper.close();

        if (success) {
            Toast.makeText(this, "Restaurant Saved Successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RestaurantdisplayActivity.class);
            intent.putExtra("RESTAURANT_NAME", name);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error Saving Restaurant", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (TextUtils.isEmpty(restaurantNameEditText.getText()) ||
                restaurantNameEditText.getText().length() < MIN_RESTAURANT_NAME_LENGTH) {
            restaurantNameEditText.setError("Enter valid restaurant name");
            isValid = false;
        }

        if (TextUtils.isEmpty(contactNoEditText.getText()) ||
                contactNoEditText.getText().length() != PHONE_NUMBER_LENGTH) {
            contactNoEditText.setError("Enter 10-digit phone number");
            isValid = false;
        }

        if (TextUtils.isEmpty(addressEditText.getText())) {
            addressEditText.setError("Address required");
            isValid = false;
        }

        if (TextUtils.isEmpty(cityEditText.getText())) {
            cityEditText.setError("City required");
            isValid = false;
        }

        if (!vegCheckBox.isChecked() && !nonVegCheckBox.isChecked()) {
            Toast.makeText(this, "Select at least one category", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private String getFoodCategory() {
        boolean isVeg = vegCheckBox.isChecked();
        boolean isNonVeg = nonVegCheckBox.isChecked();

        if (isVeg && isNonVeg) return "Veg & Non-Veg";
        if (isVeg) return "Veg";
        return "Non-Veg";
    }
}
