package com.example.parseemailverification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity   {
Button btnSignUp,btnLogin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnSignUp=findViewById(R.id.btnSignUp_main);
    btnLogin=findViewById(R.id.btnLogin_main);

    btnSignUp.setOnClickListener(view -> {
      Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
      startActivity(intent);
    });

    btnLogin.setOnClickListener(view -> {
      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      startActivity(intent);
    });
  }


}
