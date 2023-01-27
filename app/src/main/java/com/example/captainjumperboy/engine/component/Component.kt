package com.example.captainjumperboy.engine.component

import android.graphics.Canvas
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.math.Transform

open class Component() : Updatable, Renderable {
    lateinit var gameObject : GameObject
    var transform: Transform
        get() = gameObject.transform
        set(value) {gameObject.transform = value}
    override fun draw(canvas: Canvas) {}
    override fun update() {}

    fun destroy(){
        gameObject.destroyComponent(this)
    }
}