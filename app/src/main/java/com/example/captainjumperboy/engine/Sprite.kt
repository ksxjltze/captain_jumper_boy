package com.example.captainjumperboy.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Renderable

class Sprite(gameObject: GameObject, private var image : Bitmap) : Renderable, Component(gameObject) {
    public override fun draw(canvas: Canvas){
        canvas.drawBitmap(image, transform.position.x, transform.position.y, null)
    }
}