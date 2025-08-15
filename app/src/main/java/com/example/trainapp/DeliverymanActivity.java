package com.example.trainapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DeliverymanActivity extends AppCompatActivity {

    private MaterialButton btnAccept, btnReject, btnCompleteDelivery;
    private TextView deliveryStatusTextView, orderDetailsTextView;
    private boolean isOrderAccepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryman); // Your XML layout name

        // Initialize views
        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);
        btnCompleteDelivery = findViewById(R.id.btnCompleteDelivery);
        deliveryStatusTextView = findViewById(R.id.deliveryStatusTextView);
        orderDetailsTextView = findViewById(R.id.orderDetailsTextView);

        // Set up order details (in a real app, these would come from intent/extras)
        setupOrderDetails();

        // Button click listeners
        btnAccept.setOnClickListener(v -> acceptOrder());
        btnReject.setOnClickListener(v -> rejectOrder());
        btnCompleteDelivery.setOnClickListener(v -> completeDelivery());
    }

    private void setupOrderDetails() {
        // In a real app, you would get these from Intent extras or ViewModel
        String orderDetails = "Order #TRN789456\n" +
                "Restaurant: Food Plaza\n" +
                "Items:Dosa \n" +
                "Total: ₹30\n" +
                "Train: Rajdhani Express (12309)\n" +
                "Delivery to: Seat 45, Coach B3";

        orderDetailsTextView.setText(orderDetails);
    }

    private void acceptOrder() {
        isOrderAccepted = true;
        deliveryStatusTextView.setText("Status: Accepted - In Progress");
        btnAccept.setVisibility(View.GONE);
        btnReject.setVisibility(View.GONE);
        btnCompleteDelivery.setVisibility(View.VISIBLE);

        // In a real app, you would update order status in your database/API here
        Toast.makeText(this, "Order accepted successfully", Toast.LENGTH_SHORT).show();

        // You might also want to:
        // 1. Notify the restaurant
        // 2. Update real-time tracking
        // 3. Start delivery timer
    }

    private void rejectOrder() {
        // In a real app, you would:
        // 1. Update order status in database
        // 2. Notify the system to assign another delivery person
        // 3. Maybe ask for rejection reason

        Toast.makeText(this, "Order rejected", Toast.LENGTH_SHORT).show();
        finish(); // Close this activity and go back to available orders
    }

    private void completeDelivery() {
        // Update delivery status
        deliveryStatusTextView.setText("Status: Delivered Successfully");
        btnCompleteDelivery.setVisibility(View.GONE);

        // In a real app, you would:
        // 1. Mark order as delivered in database
        // 2. Confirm payment received
        // 3. Update delivery person's stats
        // 4. Maybe ask for customer rating

        Toast.makeText(this, "Delivery completed successfully", Toast.LENGTH_SHORT).show();

        // Optionally auto-close after delay or show completion summary
        new android.os.Handler().postDelayed(
                () -> finish(),
                2000 // 2 second delay
        );
    }

    @Override
    public void onBackPressed() {
        if (isOrderAccepted) {
            // Prevent going back if order is accepted but not delivered
            Toast.makeText(this, "Please complete the delivery first", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
