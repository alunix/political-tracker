package me.muhammadshah.politicaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;

public class AuthenticateActivity extends AppCompatActivity {


    protected String mAction;

    protected EditText mEmailField;
    protected EditText mPasswordField;
    protected Button mButton;
    protected ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mEmailField = (EditText) findViewById(R.id.editText1);
        mPasswordField = (EditText) findViewById(R.id.editText2);
        mButton = (Button) findViewById(R.id.button1);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);

        Bundle bundle = getIntent().getExtras();
        mAction = bundle.getString(LoginActivity.TYPE);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);

                String username = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();

                if (mAction.equals(LoginActivity.SIGNUP)) {
					/*
					 * Sign up using ParseUser
					 */
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);

                    //Add follows/unfollows

                    user.addAllUnique("follow", Arrays.asList("World News", "Elections", "Taxes"));
                    user.addAllUnique("unfollow", Arrays.asList("Gun Control","Health Care"));
                    user.saveInBackground();

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            if (e==null){
                                //Let them use App
                                startActivity(new Intent(AuthenticateActivity.this, ProfileActivity.class));
                            } else{
                                Toast.makeText(AuthenticateActivity.this,
                                        "Sign up failed! Try again.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
					/*
					 * Login using ParseUser
					 */
                    ParseUser.logInInBackground(username, password,
                            new LogInCallback() {
                                public void done(ParseUser user,
                                                 ParseException e) {
                                    mProgressBar.setVisibility(View.INVISIBLE);
                                    if (user != null) {
                                        // Hooray! The user is logged in.
                                        startActivity(new Intent(
                                                AuthenticateActivity.this,
                                                ProfileActivity.class));
                                    } else {
                                        // Login failed. Look at the
                                        // ParseException to see what happened.
                                        Toast.makeText(
                                                AuthenticateActivity.this,
                                                "Login failed! Try again.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
