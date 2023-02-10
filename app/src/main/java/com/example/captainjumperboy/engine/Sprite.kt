package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.assets.Image
import com.example.captainjumperboy.engine.component.Component

class Sprite(var image : Image) : Component() {
    override fun draw(canvas: Canvas){
        val matrix = transform.getMatrix()
        matrix.postConcat(Camera.transform.getMatrix()) //View * Model

        //apply transform and draw
        canvas.withMatrix(matrix) {
            canvas.drawBitmap(image.bitmap, -image.width/2F, -image.height/2F, null) //draw centered in canvas to apply transform correctly
        }

    }
}

