package com.example.parseemailverification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

  EditText name, pwd;
  ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    name = findViewById(R.id.editTextPersonName_login);
    pwd = findViewById(R.id.editTextPassword_login);

    progressDialog = new ProgressDialog(LoginActivity.this);
    // Setting Title
    progressDialog.setTitle("Login into Account");
    // Setting Message
    progressDialog.setMessage("Loading...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

  }

  public void loginIsPressed(View view) {

    login(name.getText().toString(), pwd.getText().toString());
  }

  private void login(String username, String password) {
    progressDialog.show();
    ParseUser.logInInBackground(
        username,
        password,
        (parseUser, e) -> {
          progressDialog.dismiss();
          if (parseUser != null) {
            showAlert("Login Successful", "Welcome, " + username + "!", false);
          } else {
            ParseUser.logOut();
            showAlert("Login Fail", e.getMessage() + " Please try again", true);
          }
        });
  }

  private void showAlert(String title, String message, boolean error) {
    AlertDialog.Builder builder =
        new AlertDialog.Builder(LoginActivity.this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "OK",
                (dialog, which) -> {
                  dialog.cancel();
                  // don't forget to change the line below with the names of your Activities
                  if (!error) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                  }
                });
    AlertDialog ok = builder.create();
    ok.show();
  }
}
