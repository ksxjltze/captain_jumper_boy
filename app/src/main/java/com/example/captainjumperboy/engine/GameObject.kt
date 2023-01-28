package com.example.captainjumperboy.engine

import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Transform
import kotlin.reflect.KClass

class GameObject() {
    private var componentList = ArrayList<Component>()
    private var scriptList = ArrayList<Scriptable>()

    var active : Boolean = true
    var transform = Transform()

    fun start(){
        scriptList.forEach{scriptable -> scriptable.start() }
    }

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
        component.gameObject = this
        componentList.add(component)
    }

    fun destroyComponent(component: Component){
        componentList.remove(component)
    }

    fun destroy(){
        active = false
        componentList.clear()
        scriptList.clear()
    }

    fun addScript(script : Scriptable){
        script.gameObject = this
        scriptList.add(script)
    }

    inline fun <reified T : Scriptable> addScript(){
        addScript(T::class)
    }
    fun <T : Scriptable> addScript(klass: KClass<T>) {
        try{
            val instance = klass.constructors.first { it.parameters.isEmpty() }.call() as Scriptable
            instance.gameObject = this
            scriptList.add(instance)
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }
}