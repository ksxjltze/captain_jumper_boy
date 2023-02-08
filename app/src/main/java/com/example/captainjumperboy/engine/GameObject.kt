package com.example.captainjumperboy.engine

import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Transform
import kotlin.reflect.KClass

class GameObject {
    var componentList = ArrayList<Component>()
    var scriptList = ArrayList<Scriptable>()

    var active : Boolean = true
    var name : String = "GameObject"
    var transform = Transform()

    fun startEarly() {
        scriptList.forEach{scriptable -> scriptable.startEarly() }
    }
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

    inline fun <reified T> getComponent() : T?{
        componentList.forEach{component ->
            if (component is T)
                return component
        }
        return null
    }
    inline fun <reified T> hasComponent() : Boolean{
        componentList.forEach{component ->
            if (component is T)
                return true
        }
        return false
    }
    inline fun <reified T> getScript() : T?{
        scriptList.forEach{script ->
            if (script is T)
                return script
        }
        return null
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

    inline fun <reified T : Scriptable> addScript() : T{
        return addScript(T::class)
    }
    fun <T : Scriptable> addScript(klass: KClass<T>) : T {
        val instance = klass.constructors.first { it.parameters.isEmpty() }.call()
        instance.gameObject = this

        scriptList.add(instance)
        return instance
    }

}