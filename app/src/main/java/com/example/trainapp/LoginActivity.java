package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, passwordInputLayout;
    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Get user type from intent
        userType = getIntent().getStringExtra("userType");

        if (userType == null) {
            Toast.makeText(this, "User type not specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            // Log the userType for debugging
            Log.d("LoginActivity", "User type: " + userType);
        }

        initializeViews();
        setupTextWatchers();
        setupButtonClickListeners();
        updateTitle();
    }

    private void initializeViews() {
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);
    }

    private void setupTextWatchers() {
        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailInputLayout.setError(null);
                passwordInputLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        emailEditText.addTextChangedListener(clearErrorWatcher);
        passwordEditText.addTextChangedListener(clearErrorWatcher);
    }

    private void setupButtonClickListeners() {
        loginButton.setOnClickListener(v -> validateAndLogin());
        registerTextView.setOnClickListener(v -> navigateToRegistration());
    }

    private void updateTitle() {
        TextView titleTextView = findViewById(R.id.signIn);
        String capitalizedType = userType.substring(0, 1).toUpperCase() + userType.substring(1);
        titleTextView.setText(getString(R.string.sign_in_as, capitalizedType));
    }

    private void validateAndLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailInputLayout.setError("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Please enter a valid email");
            return;
        }

        if (password.isEmpty()) {
            passwordInputLayout.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters");
            return;
        }

        attemptLogin(email, password);
    }

    private void attemptLogin(String email, String password) {
        loginButton.setEnabled(false);
        loginButton.setText("Logging in...");

        new android.os.Handler().postDelayed(
                () -> {
                    if (isValidCredentials(email, password)) {
                        onLoginSuccess(email);
                    } else {
                        onLoginFailure();
                    }
                },
                1500
        );
    }

    private boolean isValidCredentials(String email, String password) {
        // You can add a real validation check here, e.g., querying a database or API.
        // For this example, we are simply checking that the password is at least 6 characters.
        return password.length() >= 6;
    }

    private void onLoginSuccess(String email) {
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

        Intent intent;

        // Log the decision process for debugging
        Log.d("LoginActivity", "User type inside onLoginSuccess: " + userType);

        if ("deliveryman".equals(userType)) {
            Log.d("LoginActivity", "Navigating to DeliverymanActivity");
            intent = new Intent(this, DeliverymanActivity.class);
            intent.putExtra("deliveryBoyEmail", email);
            intent.putExtra("deliveryBoyName", "Delivery Partner"); // Default name
        } else if ("restaurant".equals(userType)) {
            Log.d("LoginActivity", "Navigating to RestaurantincludeActivity");
            intent = new Intent(this, RestaurantincludeActivity.class);
            intent.putExtra("restaurantEmail", email);
        } else {
            Log.d("LoginActivity", "Navigating to FoodorderActivity");
            intent = new Intent(this, FoodorderActivity.class);
            intent.putExtra("userEmail", email);
        }

        intent.putExtra("userType", userType);

        // Delay before starting the next activity to ensure smooth transition
        new android.os.Handler().postDelayed(() -> {
            startActivity(intent);
            finishAffinity(); // Finish all previous activities
        }, 1000); // 1-second delay
    }

    private void onLoginFailure() {
        loginButton.setEnabled(true);
        loginButton.setText("Login");
        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
    }

    private void navigateToRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Returning to user selection", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
