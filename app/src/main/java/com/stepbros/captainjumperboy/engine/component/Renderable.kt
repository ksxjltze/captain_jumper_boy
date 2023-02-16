package com.stepbros.captainjumperboy.engine.component

import android.graphics.Canvas
import com.stepbros.captainjumperboy.engine.Layer
import com.stepbros.captainjumperboy.engine.Renderer

interface Renderable {
    var layer : Layer
    fun draw(canvas: Canvas)
    fun draw(renderer: Renderer)
}