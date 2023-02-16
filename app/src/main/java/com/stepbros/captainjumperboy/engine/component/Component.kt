package com.stepbros.captainjumperboy.engine.component

import android.graphics.Canvas
import com.stepbros.captainjumperboy.engine.GameObject
import com.stepbros.captainjumperboy.engine.Layer
import com.stepbros.captainjumperboy.engine.Renderer
import com.stepbros.captainjumperboy.engine.Scene
import com.stepbros.captainjumperboy.math.Transform

open class Component : Updatable, Renderable {
    override var layer = Layer.FOREGROUND

    lateinit var gameObject : GameObject
    var transform: Transform
        get() = gameObject.transform
        set(value) {gameObject.transform = value}

    //gets an available object from the scene and calls start() on it
    fun createObject() : GameObject{
        return Scene.activeScene!!.spawnObject()
    }

    fun findObject(name : String) : GameObject{
        return Scene.activeScene!!.findObject(name)
    }

    override fun draw(canvas: Canvas) {}
    override fun draw(renderer: Renderer){}
    override fun update() {}

    open fun pausedUpdate(){}

    fun destroy(){
        gameObject.destroyComponent(this)
    }
}