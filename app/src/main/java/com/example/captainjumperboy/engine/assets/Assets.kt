package com.example.captainjumperboy.engine.assets

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.captainjumperboy.ui.GameView

class Assets {
    companion object{
        val targetWidth = 100
        val targetHeight = 100

        lateinit var view : GameView //this is probably bad?

        fun getBitmap(resourceId : Int) : Bitmap{
            return BitmapFactory.decodeResource(view.resources, resourceId)
        }
    }
}