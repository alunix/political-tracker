package me.muhammadshah.politicaltracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TYPE = "type";
    public static final String LOGIN = "Log In";
    public static final String SIGNUP = "Sign Up";

    protected Button mLoginButton;
    protected Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mLoginButton = (Button) findViewById(R.id.button1);
        mSignupButton = (Button) findViewById(R.id.button2);

		/*
		 * Check for cached user using ParseUser.getCurrentUser()
		 */
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(this, MainFeedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, AuthenticateActivity.class);
                    intent.putExtra(TYPE, LOGIN);
                    startActivity(intent);
                }
            });

            mSignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, AuthenticateActivity.class);
                    intent.putExtra(TYPE, SIGNUP);
                    startActivity(intent);
                }
            });
        }
    }
}
