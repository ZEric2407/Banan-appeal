package com.example.banananatomy;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class main_activity extends AppCompatActivity {

    ImageButton ImageSelectorButton;
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

        ImageButton camera = (ImageButton)findViewById(R.id.cameraButton);
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
            showPopup(0);
        } else if (bananaScore == 1){
            Toast.makeText(this, "Barely Ripe", Toast.LENGTH_SHORT).show();
            showPopup(1);
        } else if (bananaScore == 2){
            Toast.makeText(this, "Ripe", Toast.LENGTH_SHORT).show();
            showPopup(2);
        } else if (bananaScore == 3){
            Toast.makeText(this, "Very Ripe", Toast.LENGTH_SHORT).show();
            showPopup(3);
        } else {
            Toast.makeText(this, "Overripe", Toast.LENGTH_SHORT).show();
            showPopup(4);
        }
    }

    public void showPopup(int i){
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View[] popupViews = {inflater.inflate(R.layout.popup0, null), inflater.inflate(R.layout.popup1, null),
                inflater.inflate(R.layout.popup2, null), inflater.inflate(R.layout.popup3, null), inflater.inflate(R.layout.popup4, null)};
        View popupView = popupViews[i];
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
