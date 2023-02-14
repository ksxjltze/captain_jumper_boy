package com.example.captainjumperboy.engine

import android.graphics.Canvas
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.assets.Image
import com.example.captainjumperboy.engine.component.Component

class Sprite(var image : Image) : Component() {

    var layer : Layer = Layer.FOREGROUND
    override fun draw(renderer: Renderer){ //delegate drawing to renderer (for layer sorting)
        renderer.enqueueSprite(this)
    }
}

