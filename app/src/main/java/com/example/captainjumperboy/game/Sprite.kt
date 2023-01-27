package com.example.captainjumperboy.game

import android.graphics.Bitmap
import android.graphics.Canvas

class Sprite(private var image : Bitmap) {
    public fun draw(canvas : Canvas){
        canvas.drawBitmap(image, 100F, 100F, null)
    }
}