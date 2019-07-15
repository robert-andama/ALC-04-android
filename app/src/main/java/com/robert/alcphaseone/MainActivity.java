package com.robert.alcphaseone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button about_alc = findViewById(R.id.btn_about_alc);
        Button my_profile = findViewById(R.id.btn_my_profile);

        // set click listeners
        about_alc.setOnClickListener(v -> startActivity(new Intent(this, AboutAlcActivity.class)));
        my_profile.setOnClickListener(v -> startActivity(new Intent(this, MyProfileActivity.class)));

    }
}
