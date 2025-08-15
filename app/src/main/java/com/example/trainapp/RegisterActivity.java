package com.example.trainapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout nameInputLayout, emailInputLayout,
            passwordInputLayout, confirmPasswordInputLayout;
    private TextInputEditText nameEditText, emailEditText,
            passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView loginTextView;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Get user type from intent
        userType = getIntent().getStringExtra("userType");
        if (userType == null) {
            Toast.makeText(this, "User type not specified", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initializeViews();
        setupTextWatchers();
        setupClickListeners();
        updateTitle();
    }

    private void initializeViews() {
        nameInputLayout = findViewById(R.id.nameInputLayout);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        registerButton = findViewById(R.id.registerButton);
        loginTextView = findViewById(R.id.loginTextView);
    }

    private void setupTextWatchers() {
        // Clear errors when typing
        TextWatcher clearErrorWatcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameInputLayout.setError(null);
                emailInputLayout.setError(null);
                passwordInputLayout.setError(null);
                confirmPasswordInputLayout.setError(null);
            }
            @Override public void afterTextChanged(Editable s) {}
        };

        nameEditText.addTextChangedListener(clearErrorWatcher);
        emailEditText.addTextChangedListener(clearErrorWatcher);
        passwordEditText.addTextChangedListener(clearErrorWatcher);
        confirmPasswordEditText.addTextChangedListener(clearErrorWatcher);
    }

    private void setupClickListeners() {
        registerButton.setOnClickListener(v -> validateAndRegister());
        loginTextView.setOnClickListener(v -> navigateToLogin());
    }

    private void updateTitle() {
        TextView title = findViewById(R.id.registerTitle);
        String formattedTitle = "Register as " +
                userType.substring(0, 1).toUpperCase() +
                userType.substring(1);
        title.setText(formattedTitle);
    }

    private void validateAndRegister() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate all fields
        if (name.isEmpty()) {
            nameInputLayout.setError("Name is required");
            return;
        }

        if (email.isEmpty()) {
            emailInputLayout.setError("Email is required");
            return;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.setError("Invalid email format");
            return;
        }

        if (password.isEmpty()) {
            passwordInputLayout.setError("Password is required");
            return;
        } else if (password.length() < 6) {
            passwordInputLayout.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInputLayout.setError("Passwords don't match");
            return;
        }

        // If all validations pass
        registerUser(name, email, password);
    }



    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("userType", userType);
        startActivity(intent);
        finish();
    }
    private void registerUser(String name, String email, String password) {
        // In a real app, this would call your registration API
        // For demo, we'll just simulate successful registration
        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();

        // Navigate to JourneyActivity instead of LoginActivity
        Intent intent = new Intent(this, FoodorderActivity.class);

        // You can pass any necessary data to JourneyActivity
        intent.putExtra("userName", name);
        intent.putExtra("userEmail", email);
        intent.putExtra("userType", userType);

        startActivity(intent);
        finish();  // This will remove RegisterActivity from back stack
    }
}