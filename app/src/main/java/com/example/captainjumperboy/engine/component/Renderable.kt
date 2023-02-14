package com.example.captainjumperboy.engine.component

import android.graphics.Canvas
import com.example.captainjumperboy.engine.Layer
import com.example.captainjumperboy.engine.Renderer

interface Renderable {
    var layer : Layer

    fun draw(canvas: Canvas)
    fun draw(renderer: Renderer)
}