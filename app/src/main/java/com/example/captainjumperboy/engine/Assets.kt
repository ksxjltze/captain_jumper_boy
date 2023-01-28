package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.captainjumperboy.ui.GameView

class Assets {
    companion object{ //idk..
        lateinit var view: GameView
        fun getBitmap(resourceId : Int) : Bitmap{
            return BitmapFactory.decodeResource(view.resources, resourceId)
        }
    }
}