package com.example.parseemailverification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class SignUpActivity extends AppCompatActivity {

  EditText name, email, pwd;
  ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_signup);

    ParseInstallation.getCurrentInstallation().saveInBackground();
    name = findViewById(R.id.editTextName);
    email = findViewById(R.id.editTextEmail);
    pwd = findViewById(R.id.editTextPassword);
    progressDialog = new ProgressDialog(this);
    // Setting Title
    progressDialog.setTitle("Creating Account");
    // Setting Message
    progressDialog.setMessage("Loading...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  }

  public void signUpIsPressed(View view) {
    signUp(name.getText().toString(), pwd.getText().toString(), email.getText().toString());
  }

  private void signUp(String username, String password, String email) {
    progressDialog.show();
    ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    user.signUpInBackground(
        e -> {
          progressDialog.dismiss();
          if (e == null) {
            ParseUser.logOut();
            showAlert(
                "Account Created Successfully!", "Please verify your email before Login", false);
          } else {
            ParseUser.logOut();
            showAlert(
                "Error Account Creation failed",
                "Account could not be created" + " : " + e.getMessage(),
                true);
          }
        });
  }

  private void showAlert(String title, String message, boolean error) {
    AlertDialog.Builder builder =
        new AlertDialog.Builder(SignUpActivity.this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "OK",
                (dialog, which) -> {
                  dialog.cancel();
                  // don't forget to change the line below with the names of your Activities
                  if (!error) {
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                  }
                });
    AlertDialog ok = builder.create();
    ok.show();
  }
}
