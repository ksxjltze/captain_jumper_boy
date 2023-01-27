package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Renderable

class Sprite(private var image : Bitmap) : Renderable, Component() {
    public override fun draw(canvas: Canvas){
        canvas.save() //poosh

        //this transform order seems to work so far
        canvas.translate(transform.position.x, transform.position.y);
        canvas.scale(transform.scale.x, transform.scale.y)
        canvas.rotate(transform.rotation)

        //draw the sprite in center of transformed canvas, effectively applying the transform to the sprite
        canvas.drawBitmap(image, image.width/2F, image.height/2F, null)
        canvas.restore() //poup
    }
}