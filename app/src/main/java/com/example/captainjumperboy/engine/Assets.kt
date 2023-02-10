package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.captainjumperboy.ui.GameView

class Assets {
    companion object{ //idk..
        lateinit var view: GameView
        fun getBitmap(resourceId : Int) : Bitmap{
            val image = BitmapFactory.decodeResource(view.resources, resourceId)
            //Scales all images imported to be a unit square of 100x100
            val matrix = Matrix()
            matrix.setScale(100f / image.width, 100f / image.height)
            return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
        }
    }
}