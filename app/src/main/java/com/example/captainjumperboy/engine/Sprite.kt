package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Renderable

class Sprite(private var image : Bitmap) : Renderable, Component() {
    public override fun draw(canvas: Canvas){

        //@todo could move elsewhere
        //@todo camera stuff
        val matrix = Matrix()
        matrix.postScale(transform.scale.x, transform.scale.y)
        matrix.postRotate(  transform.rotation)
        matrix.postTranslate(transform.position.x, transform.position.y);

        //apply transform
        canvas.withMatrix(matrix) {
            canvas.drawBitmap(image, 0F, 0F, null)
        }

    }
}