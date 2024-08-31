package com.example.asgn1ngjunthye;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password, passwordConfirmation;

    String usernameStr, passwordStr, passwordConfirmationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);
        passwordConfirmation = findViewById(R.id.rePasswordInput);
    }

    public void SignUpButtonClicked (View view) {
        usernameStr = username.getText().toString();
        passwordStr = password.getText().toString();
        passwordConfirmationStr = passwordConfirmation.getText().toString();

        if (!usernameStr.isEmpty() && !passwordStr.isEmpty() && !passwordConfirmationStr.isEmpty()){
            if (passwordStr.equals(passwordConfirmationStr)){
                saveUserPassData(usernameStr,passwordStr);
                Intent logIn = new Intent(this, LogInActivity.class);
                Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                startActivity(logIn);
            }
            else {
                Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Username and Passwords must not be empty", Toast.LENGTH_SHORT).show();
        }

    }
    public void LogInButtonClicked (View view) {
        Intent logIn = new Intent(this, LogInActivity.class);
        startActivity(logIn);
    }

    private void saveUserPassData (String userValue, String passValue){

        SharedPreferences sharedPreferences = getSharedPreferences("UserNPass",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username",userValue);
        editor.putString("password", passValue);

        editor.apply();
    }


}