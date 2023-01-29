package com.example.banananatomy;

import android.graphics.Bitmap;
import android.graphics.Color;


public class bananalysis {

    public static int analyzeBanana(Bitmap bitmap) {

        int mapWidth = bitmap.getWidth();
        int mapHeight = bitmap.getHeight();
        int[] pixelArray = new int[mapWidth * mapHeight];
        bitmap.getPixels(pixelArray, 0, mapWidth, 0, 0, mapWidth, mapHeight);
        int bananaR = 0;
        int bananaG = 0;
        int bananaB = 0;
        int bananaD = 0;

        int counter = 1;
        for (int i = pixelArray.length / 3; i < (2 * pixelArray.length / 3); i += pixelArray.length / 200){
            int pixelR = Color.red(pixelArray[i]);
            int pixelB = Color.blue(pixelArray[i]);
            int pixelG = Color.green(pixelArray[i]);
            System.out.println(pixelR);
            if ((pixelR <= 255 && pixelR >= 140 &&
                    pixelB <= 110 && pixelB >= 0 &&
                    pixelG <= 255 && pixelG >= 160) ||
                    (pixelR <= 60 && pixelR >= 0 && pixelB <= 60 && pixelB >= 0 && pixelG <= 60 && pixelG >= 0)){
                bananaR += pixelR;
                bananaB += pixelB;
                bananaG += pixelG;
                System.out.println("Passed");
                counter++;
            }
        }
        bananaB /= counter;
        bananaG /= counter;
        bananaR /= counter;

        System.out.println("Red: " + bananaR + "\n Blue: " + bananaB + "\n Green: " + bananaG);
        int underScore = (Math.abs(bananaR - 125)) + (Math.abs(bananaB - 114)) + (Math.abs(bananaG - 190));
        int barelyScore = (Math.abs(bananaR - 187) + (Math.abs(bananaB - 110)) + Math.abs(bananaG - 185));
        int ripeScore = Math.abs(bananaR - 214) + Math.abs(bananaB - 68) + Math.abs(bananaG - 185);
        int veryScore = Math.abs(bananaR - 182) + Math.abs(bananaB - 66) + Math.abs(bananaG - 131);
        int overScore = Math.abs(bananaR - 65) + Math.abs(bananaB - 29) + Math.abs(bananaG - 45);
        int[] scores = {underScore, barelyScore, ripeScore, veryScore, overScore};

        int highestIndex = 2;
        int highestScore = ripeScore;
        for (int i = 0; i < scores.length; i++){
            if (scores[i] < highestScore){
                highestIndex = i;
            }
        }
        System.out.println("Under score: " + underScore);
        System.out.println("Barely score: " + barelyScore);
        System.out.println("Ripe score: " + ripeScore);
        System.out.println(("Very score: " + veryScore));
        System.out.println(("Over score: " + overScore));

        System.out.println(highestIndex);
        return highestIndex;
    }

}
