package com.example.trainapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RestaurantActivity extends AppCompatActivity {

    private Button btnMarkPreparing, btnMarkReady, btnAssignDelivery, btnConfirmAssignment;
    private LinearLayout assignDeliveryLayout;
    private Spinner deliveryPersonSpinner;
    private CardView orderCard;
    private TextView orderDetailsText, orderStatusText, restaurantNameText;

    // In-memory data storage
    private String currentOrderStatus = "Received"; // Default status
    private String currentOrderDetails = "Sample Order: 1x Dosa";
    private final String[] deliveryPersons = {"John Doe", "Jane Smith", "Mike Johnson"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        initializeViews();

        // Get restaurant name from intent
        String restaurantName = getIntent().getStringExtra("RESTAURANT_NAME");
        restaurantNameText.setText(restaurantName != null ? restaurantName : "Restaurant");

        // Load sample order data
        loadSampleOrderData();
        setupDeliveryPersonsSpinner();
        setupButtonListeners();
    }

    private void initializeViews() {
        btnMarkPreparing = findViewById(R.id.btnMarkPreparing);
        btnMarkReady = findViewById(R.id.btnMarkReady);
        btnAssignDelivery = findViewById(R.id.btnAssignDelivery);
        btnConfirmAssignment = findViewById(R.id.btnConfirmAssignment);
        assignDeliveryLayout = findViewById(R.id.assignDeliveryLayout);
        deliveryPersonSpinner = findViewById(R.id.deliveryPersonSpinner);

        orderCard = findViewById(R.id.orderCard);
        orderDetailsText = findViewById(R.id.orderDetailsText);
        orderStatusText = findViewById(R.id.orderStatusText);
        restaurantNameText = findViewById(R.id.restaurantName);
    }

    private void loadSampleOrderData() {
        orderDetailsText.setText(currentOrderDetails);
        orderStatusText.setText("Status: " + currentOrderStatus);
        updateButtonStates(currentOrderStatus);
    }

    private void updateButtonStates(String status) {
        btnMarkPreparing.setEnabled(status.equals("Received"));
        btnMarkReady.setEnabled(status.equals("Preparing"));
        btnAssignDelivery.setEnabled(status.equals("Ready"));

        if (status.equals("Assigned")) {
            disableAllButtons();
            btnAssignDelivery.setText("Assigned to Delivery");
        }
    }

    private void disableAllButtons() {
        btnMarkPreparing.setEnabled(false);
        btnMarkReady.setEnabled(false);
        btnAssignDelivery.setEnabled(false);
    }

    private void setupDeliveryPersonsSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                deliveryPersons
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryPersonSpinner.setAdapter(adapter);
    }

    private void setupButtonListeners() {
        btnMarkPreparing.setOnClickListener(v -> {
            currentOrderStatus = "Preparing";
            orderStatusText.setText("Status: " + currentOrderStatus);
            updateButtonStates(currentOrderStatus);
            Toast.makeText(this, "Order marked as Preparing", Toast.LENGTH_SHORT).show();
        });

        btnMarkReady.setOnClickListener(v -> {
            currentOrderStatus = "Ready";
            orderStatusText.setText("Status: " + currentOrderStatus);
            updateButtonStates(currentOrderStatus);
            Toast.makeText(this, "Order marked as Ready", Toast.LENGTH_SHORT).show();
        });

        btnAssignDelivery.setOnClickListener(v -> {
            assignDeliveryLayout.setVisibility(View.VISIBLE);
        });

        btnConfirmAssignment.setOnClickListener(v -> {
            String selectedPerson = deliveryPersonSpinner.getSelectedItem().toString();
            currentOrderStatus = "Assigned";

            orderStatusText.setText("Status: " + currentOrderStatus);
            btnAssignDelivery.setText("Assigned to Delivery");
            btnAssignDelivery.setEnabled(false);
            assignDeliveryLayout.setVisibility(View.GONE);

            Toast.makeText(this,
                    "Order assigned to " + selectedPerson,
                    Toast.LENGTH_SHORT).show();

            updateButtonStates(currentOrderStatus);
        });
    }
}