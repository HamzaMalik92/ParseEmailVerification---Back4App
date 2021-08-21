package com.example.parseemailverification;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {
  TextView userName;
  ProgressDialog progressDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    // get current user id and show it in a textView
    ParseUser currentUser = ParseUser.getCurrentUser();
    userName = findViewById(R.id.textView_Username);

    userName.setText(currentUser.getUsername());

    progressDialog = new ProgressDialog(this);
    // Setting Title
    progressDialog.setTitle("Login into Account");
    // Setting Message
    progressDialog.setMessage("Loading...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  }

  // create an action bar button
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.mymenu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  // handle button activities
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.btnLogout_menu) {
      userIsLogOut();
    }
    return super.onOptionsItemSelected(item);
  }

  public void userIsLogOut() {
    progressDialog.show();
    ParseUser.logOutInBackground(
        e -> {
          progressDialog.dismiss();
          if (e == null) showAlert("So, you're going...", "Ok...Bye-bye then", false);
        });
  }

  private void showAlert(String title, String message, boolean error) {
    AlertDialog.Builder builder =
        new AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "OK",
                (dialog, which) -> {
                  dialog.cancel();
                  // don't forget to change the line below with the names of your Activities
                  if (!error) {
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                  }
                });
    AlertDialog ok = builder.create();
    ok.show();
  }
}
