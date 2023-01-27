package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Renderable

class Sprite(private var image : Bitmap) : Renderable, Component() {
    public override fun draw(canvas: Canvas){
        //is this expensive? lol dunno
        canvas.save()
        canvas.translate(transform.position.x, transform.position.y);
        canvas.scale(transform.scale.x, transform.scale.y)
        canvas.rotate(transform.rotation)
        canvas.drawBitmap(image, 0F, 0F, null)
        canvas.restore()
    }
}