/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.flood.iou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import com.parse.ParseException;


public class MainActivity extends ActionBarActivity {

    Button registerButton;
    Button signInButton;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;

    TextView infoTextView;

    boolean registerNewUserOpened = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

//    ParseObject testObject = new ParseObject("TestObject");
//    testObject.put("foo", "bar");
//    testObject.saveInBackground();

      ParseUser currentUser = ParseUser.getCurrentUser();
      if (currentUser != null) {
          switchToRequestList();

      } else {
          // show the signup or login screen
      }

      userNameEditText = (EditText) findViewById(R.id.username_editText);
      passwordEditText = (EditText) findViewById(R.id.password_editText);
      passwordConfirmEditText = (EditText) findViewById(R.id.passwordConfirm_editText);
      infoTextView = (TextView) findViewById(R.id.loginfeedback_textview);

      signInButton = (Button) findViewById(R.id.signIn_button);
      signInButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              SignInButtonPressed();
          }
      });


      registerButton = (Button) findViewById(R.id.register_button);
      registerButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              RegisterButtonPressed();
          }
      });

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

      if(id == R.id.action_new_request) {

      }

    return super.onOptionsItemSelected(item);
  }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    private void RegisterButtonPressed(){

        if(registerNewUserOpened == false){
            passwordConfirmEditText.setVisibility(View.VISIBLE);
            registerButton.setText("Confirm Register");
            registerNewUserOpened = true;
        }else if(registerNewUserOpened){

            if(userNameEditText.getText().length() < 6){
                infoTextView.setText("username must be atleast 6 characters");
                return;
            }

            if(passwordEditText.getText().length() < 6 || passwordConfirmEditText.getText().length() < 6){
                infoTextView.setText("password must be at least 6 characters");
                return;
            }

            boolean passwordsMatch = passwordEditText.getText().toString().equals(passwordConfirmEditText.getText().toString());
            if (passwordsMatch == false){
                infoTextView.setText("make sure both password fields match");
                return;
            }

            ParseUser user = new ParseUser();
            user.setUsername(userNameEditText.getText().toString());
            user.setPassword(passwordEditText.getText().toString());
//            user.setEmail("belmont530@gmail.com");
//            user.put("phone", "650-253-0000");

            infoTextView.setText("registering...");
            disableAllFields();

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        infoTextView.setText("Registration Successful!");
                        switchToRequestList();
                    } else {
                        infoTextView.setText("error:" + e.toString());
                    }
                    enableAllFields();
                }
            });

        }
    }

    private void SignInButtonPressed(){

        if(userNameEditText.getText().length() < 6){
            infoTextView.setText("username must be atleast 6 characters");
            return;
        }

        if(passwordEditText.getText().length() < 6){
            infoTextView.setText("password must be at least 6 characters");
            return;
        }

        disableAllFields();

        ParseUser.logInInBackground(userNameEditText.getText().toString(),passwordEditText.getText().toString() , new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    infoTextView.setText("Sign In Successful!");
                    switchToRequestList();
                } else {
                    infoTextView.setText("error:" + e.toString());
                }
                enableAllFields();
            }
        });

    }

    private void switchToRequestList(){
        Intent intent = new Intent(this, RequestList.class);
        startActivity(intent);
    }

    private void disableAllFields(){
        signInButton.setEnabled(false);
        registerButton.setEnabled(false);
        userNameEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        passwordConfirmEditText.setEnabled(false);
        infoTextView.setEnabled(false);
    }

    private void enableAllFields(){
        signInButton.setEnabled(true);
        registerButton.setEnabled(true);
        userNameEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        passwordConfirmEditText.setEnabled(true);
        infoTextView.setEnabled(true);
    }
}





/*
ParseUser user = new ParseUser();
user.setUsername("my name");
user.setPassword("my pass");
user.setEmail("email@example.com");

// other fields can be set just like with ParseObject
user.put("phone", "650-253-0000");

user.signUpInBackground(new SignUpCallback() {
  public void done(ParseException e) {
    if (e == null) {
      // Hooray! Let them use the app now.
    } else {
      // Sign up didn't succeed. Look at the ParseException
      // to figure out what went wrong
    }
  }
});
 */