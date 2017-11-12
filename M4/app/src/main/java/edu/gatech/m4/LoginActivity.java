package edu.gatech.m4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);


        //Firebase Users
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        mPasswordView = (EditText) findViewById(R.id.password);
        EditText errorView = (EditText) findViewById(R.id.errorText);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);


        View mLoginFormView = findViewById(R.id.login_form);
        View mProgressView = findViewById(R.id.login_progress);

        Button login = (Button) findViewById(R.id.email_sign_in_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(mEmailView.getText().toString(), mPasswordView.getText().toString());
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel_sign_in_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class );
                startActivity(intent); //go back to welcome page
            }
        });
    }

    /**
     * Validates a entered user credentials and does appropriate actions based on credentials entered.
     *
     *
     * @param userName an input user name from the Login activity
     * @param password an input password from the Login Activity
     */
    private void validate(String userName, String password) {
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Enter an email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter a password!", Toast.LENGTH_SHORT).show();
            return;
        }
        //authenticate user
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error

                            Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}


