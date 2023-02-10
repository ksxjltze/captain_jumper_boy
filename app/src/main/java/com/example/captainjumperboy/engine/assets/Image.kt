package com.example.captainjumperboy.engine.assets

import android.graphics.Bitmap
import androidx.core.graphics.scale

class Image(resourceId : Int, width : Int = Assets.targetWidth, height : Int = Assets.targetHeight) {
    private fun refresh(){
        bitmap = bitmap.scale(width, height)
    }

    var height = height
        set(value) {
            field = value
            refresh()
        }
    var width = width
        set(value) {
            field = value
            refresh()
        }

    var bitmap : Bitmap

    init {
        bitmap = Assets.getBitmap(resourceId)
        refresh()
    }
}