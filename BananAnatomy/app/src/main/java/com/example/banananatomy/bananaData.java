package com.example.banananatomy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class bananaData {
    private int ripeness;
    private int expiresIn;
    private Date dateRegistered = new Date();
    private Date dateExpires = new Date();
    public bananaData(int ripeness){
        this.ripeness = ripeness;
        if (ripeness == 0){
            expiresIn = 13;

        } else if (ripeness == 1) {
            expiresIn = 9;
        } else if (ripeness == 2) {
            expiresIn = 5;
        } else if (ripeness == 3) {
            expiresIn = 3;
        } else {
            expiresIn = 0;
        }
    }

    public void updateRipeness(){
        if (expiresIn < 0) {
            expiresIn -= 1;
            if (expiresIn == 0) {
                ripeness = 4;
            } else if (expiresIn == 3) {
                ripeness = 3;
            } else if (expiresIn == 5) {
                ripeness = 2;
            } else if (expiresIn == 9) {
                ripeness = 1;
            }
        }

    }

    public void saveData(){

        try {
            FileWriter fileWriter = new FileWriter("./../../res/values/data.json", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
