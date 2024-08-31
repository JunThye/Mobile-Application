package com.example.asgn1ngjunthye;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.usernameInput2);
        password = findViewById(R.id.passwordInput2);
        SharedPreferences sharedPreferences = getSharedPreferences("UserNPass",MODE_PRIVATE);
        String usernameRes = sharedPreferences.getString("username","");
        username.setText(usernameRes);
    }

    public void registerButton (View view) {
        Intent register = new Intent(this, MainActivity.class);
        startActivity(register);
    }

    public void logInButtonOnClicked (View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserNPass",MODE_PRIVATE);
        String usernameRes = sharedPreferences.getString("username","");
        String passwordRes = sharedPreferences.getString("password","");
        if (username.getText().toString().equals(usernameRes) && password.getText().toString().equals(passwordRes)) {String message = "Login Successful";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            Intent Dashboard = new Intent(this, DashboardActivity.class);
            startActivity(Dashboard);
        }
        else {
            String message = "Authentication failure: Username or Password incorrect";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

}