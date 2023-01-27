package com.example.captainjumperboy.game.engine

import android.graphics.Bitmap
import android.graphics.Canvas
import com.example.captainjumperboy.game.engine.component.Component
import com.example.captainjumperboy.game.engine.component.Renderable

class Sprite(private var image : Bitmap) : Renderable, Component{
    override fun update() {

    }

    public override fun draw(canvas: Canvas){
        canvas.drawBitmap(image, 100F, 100F, null)
    }
}