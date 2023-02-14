package com.example.captainjumperboy.engine.component

import android.graphics.Canvas
import com.example.captainjumperboy.engine.Renderer

interface Renderable {
    fun draw(canvas: Canvas)
    fun draw(renderer: Renderer)
}