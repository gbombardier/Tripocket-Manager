package com.gbombardier.tripocketmanager.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    Button btnConnect, btnRegister;
    private EditText emailAddressEditText;
    private EditText passwordEditText;
    private TextView titleView;
    private static FirebaseAuth firebaseAuth;


    public static void show(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnConnect = (Button)findViewById(R.id.btnConnect);
        emailAddressEditText = (EditText)findViewById(R.id.edit_text_email);
        passwordEditText = (EditText)findViewById(R.id.edit_text_password);
        titleView = (TextView)findViewById(R.id.login_title);

        emailAddressEditText.setText("gabb_bomb@hotmail.com");
        passwordEditText.setText("AAAaaa111");

        btnRegister.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnRegister) {
            register();
        } else if(v.getId() == R.id.btnConnect) {
            signIn();
        }
    }

    private void register() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void signIn() {
        if(!TextUtils.isEmpty(emailAddressEditText.getText()) && !TextUtils.isEmpty(passwordEditText.getText())) {
            final String emailAddress = emailAddressEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            firebaseAuth.signInWithEmailAndPassword(emailAddress, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}



