package com.example.banananatomy;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class main_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setTitle("BananAnantomy");

        String[] perms = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (int i = 0; i < perms.length; i++){
            if (ContextCompat.checkSelfPermission(main_activity.this, perms[i]) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[] {perms[i]}, i);
            } else {
                Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT);
            }
        }

    }
}
