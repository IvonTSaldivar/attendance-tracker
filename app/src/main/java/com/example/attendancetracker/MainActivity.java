package com.example.attendancetracker;

import android.content.Intent;
import android.os.Bundle;

import android.provider.Settings;

import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.gms.vision.barcode.Barcode;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Todo
 *
 */
public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;

    String first_name;
    String last_name;
    String URL;

    EditText first_name_input;
    EditText last_name_input;

    Button submit_button;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent Camera = new Intent(this, CameraActivity.class);
        startActivityForResult(Camera, 0);


        final String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        first_name_input = findViewById(R.id.first_name_input);
        last_name_input = findViewById(R.id.last_name_input);

        submit_button =  findViewById(R.id.submit_button);
        submit_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                first_name = first_name_input.getText().toString();
                last_name = last_name_input.getText().toString();

                Toast.makeText(MainActivity.this, first_name, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, last_name, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, androidId, Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, URL, Toast.LENGTH_SHORT).show();
            }


        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            URL = data.getStringExtra("URL");
        }
    }

}
