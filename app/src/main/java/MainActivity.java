/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;
    EditText passwordEditText;
    TextView changeSignupModeTextView;

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            signUp(v);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignupModeTextView) {

            Button signupButton = (Button) findViewById(R.id.signupButton);

            if (signUpModeActive) {

                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");

            } else {

                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");

            }

        }

    }

    public void signUp(View view) {

        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);

        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

        } else {

            if (signUpModeActive) {

                ParseUser user = new ParseUser();

                user.setUsername(usernameEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {

                            Log.i("Signup", "Successful");

                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            } else {

                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if (user != null) {

                            Log.i("Signup", "Login successful");

                        } else {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });


            }
        }


    }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

      changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);

      changeSignupModeTextView.setOnClickListener(this);

      passwordEditText = (EditText) findViewById(R.id.passwordEditText);

      passwordEditText.setOnKeyListener(this);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
}
