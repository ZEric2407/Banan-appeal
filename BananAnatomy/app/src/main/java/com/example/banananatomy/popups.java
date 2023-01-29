package com.example.banananatomy;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;

public class popups extends AppCompatActivity {

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
