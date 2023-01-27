package com.example.captainjumperboy.engine

import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.game.Keith
import com.example.captainjumperboy.math.Transform
import kotlin.reflect.KClass

class GameObject() {
    var componentList = ArrayList<Component>()
    var scriptList = ArrayList<Scriptable>()

    var active : Boolean = true
    var transform = Transform()

    fun update(){
        for (component in componentList){
            component.update()
        }

        scriptList.forEach{scriptable -> scriptable.update() }
    }

    fun draw(canvas: Canvas){
        for (component in componentList){
            component.draw(canvas)
        }
    }

    fun addComponent(component: Component){
        componentList.add(component)
    }

    fun <T : Any> addScript(klass: KClass<T>) {
        //scriptList.add(klass.constructors.first { it.parameters.size==1 }.call(this) as Scriptable)
    }
}