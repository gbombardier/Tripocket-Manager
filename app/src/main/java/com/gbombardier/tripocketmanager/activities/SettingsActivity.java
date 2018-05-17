package com.gbombardier.tripocketmanager.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.gbombardier.tripocketmanager.fragments.PasswordInputFragment;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout accountLayout;
    private Button logoutButton, passwordButton;
    private ImageView backButton;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_settings);

        accountLayout = (LinearLayout) findViewById(R.id.settings_account_layout);
        backButton = (ImageView) findViewById(R.id.settings_back_button);
        logoutButton = (Button) findViewById(R.id.settings_button_logout);
        passwordButton = (Button) findViewById(R.id.settings_button_password);

        logoutButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        passwordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        fm = getSupportFragmentManager();

        switch (id){
            case R.id.settings_back_button:
                Intent i = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(i);
                break;

            case R.id.settings_button_logout:
                //Se déconnecter tout simplement
                FirebaseAuth.getInstance().signOut();
                LoginActivity.show(this);
                break;

            case R.id.settings_button_password:
                PasswordInputFragment passwordInputFragment = new PasswordInputFragment();
                passwordInputFragment.show(fm, "Dialog Fragment");
                break;
        }

    }
}
