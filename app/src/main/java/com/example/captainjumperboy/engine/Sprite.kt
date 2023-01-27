package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Renderable

class Sprite(private var image : Bitmap) : Renderable, Component() {
    public override fun draw(canvas: Canvas){
        val matrix = transform.getMatrix()
        matrix.postConcat(Camera.transform.getMatrix());

        //apply transform and draw
        canvas.withMatrix(matrix) {
            canvas.drawBitmap(image, 0F, 0F, null)
        }

    }
}