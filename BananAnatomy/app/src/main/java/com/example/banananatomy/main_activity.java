package com.example.banananatomy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class main_activity extends AppCompatActivity {

    Button ImageSelectorButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ImageSelectorButton = findViewById(R.id.selectImageButton);
        ImageSelectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        Button camera = (Button)findViewById(R.id.cameraButton);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(startIntent, 123);
            }
        });

        setTitle("BananAnantomy");

        String[] perms = {android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (int i = 0; i < perms.length; i++){
            if (ContextCompat.checkSelfPermission(this, perms[i]) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[] {perms[i]}, i);
            } else {

            }
        }

    }
    void selectImage(){
        Intent action = new Intent();
        action.setType("image/*");
        action.setAction(Intent.ACTION_GET_CONTENT);
        launchActivity.launch(action);
    }

    ActivityResultLauncher<Intent> launchActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getData() != null){
                        Uri imageURL = data.getData();
                        Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURL);
                            processBitmap(selectedImageBitmap);
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                }
            });

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            processBitmap(photo);
        }
    }

    protected void processBitmap(Bitmap bitmap){
        bananalysis bananalyzer = new bananalysis();
        int bananaScore = bananalyzer.analyzeBanana(bitmap);
        System.out.println(bananaScore);
        if (bananaScore == 0){
            Toast.makeText(this, "Underripe", Toast.LENGTH_SHORT).show();
        } else if (bananaScore == 1){
            Toast.makeText(this, "Barely Ripe", Toast.LENGTH_SHORT).show();
        } else if (bananaScore == 2){
            Toast.makeText(this, "Ripe", Toast.LENGTH_SHORT).show();;
        } else if (bananaScore == 3){
            Toast.makeText(this, "Very Ripe", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Overripe", Toast.LENGTH_SHORT).show();
        }
    }
}
