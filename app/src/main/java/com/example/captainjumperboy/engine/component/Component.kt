package com.example.captainjumperboy.engine.component

import android.graphics.Canvas
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.math.Transform

open class Component(var gameObject: GameObject) : Updatable, Renderable {
    var transform: Transform
        get() = gameObject.transform
        set(value) {gameObject.transform = value}
    override fun draw(canvas: Canvas) {}
    override fun update() {}
}