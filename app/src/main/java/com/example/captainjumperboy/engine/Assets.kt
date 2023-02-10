package com.example.captainjumperboy.engine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.scale
import com.example.captainjumperboy.ui.GameView

class Assets {
    companion object{
        val targetWidth = 100
        val targetHeight = 100

        lateinit var view : GameView //this is probably bad?

        fun getBitmap(resourceId : Int) : Bitmap{
            val bitmap = BitmapFactory.decodeResource(view.resources, resourceId)
            return bitmap.scale(targetWidth, targetHeight)
        }
    }
}