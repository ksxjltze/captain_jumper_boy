package com.example.captainjumperboy.engine.component

import android.graphics.Canvas
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.math.Transform

open class Component : Updatable, Renderable {
    companion object{ //laziness
        //assumes that only one scene can exist at a time
        lateinit var scene : Scene
    }

    lateinit var gameObject : GameObject
    var transform: Transform
        get() = gameObject.transform
        set(value) {gameObject.transform = value}

    //gets an available object from the scene and calls start() on it
    fun createObject() : GameObject{
        return scene.spawnObject()
    }

    fun findObject(name : String) : GameObject{
        return scene.findObject(name)
    }

    override fun draw(canvas: Canvas) {}
    override fun update() {}

    fun destroy(){
        gameObject.destroyComponent(this)
    }
}