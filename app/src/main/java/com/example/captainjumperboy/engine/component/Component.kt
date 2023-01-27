package com.example.captainjumperboy.engine.component

import android.graphics.Canvas

interface Component : Updatable, Renderable {
    override fun draw(canvas: Canvas) {}
    override fun update() {}
}