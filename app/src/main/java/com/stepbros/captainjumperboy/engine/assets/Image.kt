package com.stepbros.captainjumperboy.engine.assets

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.core.graphics.scale

class Image(resourceId : Int, val autoScale : Boolean = true, width : Int = Assets.targetWidth, height : Int = Assets.targetHeight) {
    var paint= Paint()
    private fun refresh(){
        if (autoScale)
            bitmap = bitmap.scale(width, height)
    }

    val originalWidth : Int
    val originalHeight : Int

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
    var Alpha = 255
        set(value) {
            field = value
            paint.alpha=Alpha
        }

    var bitmap : Bitmap

    init {
        bitmap = Assets.getBitmap(resourceId)
        originalWidth = bitmap.width
        originalHeight = bitmap.height
        refresh()
    }
}