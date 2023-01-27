package com.example.captainjumperboy.game.engine

import android.graphics.Canvas
import com.example.captainjumperboy.game.engine.component.Component

class GameObject() {
    var componentList = ArrayList<Component>()
    var active : Boolean = false

    fun update(){
        for (component in componentList){
            component.update()
        }
    }

    fun draw(canvas: Canvas){
        for (component in componentList){
            component.draw(canvas)
        }
    }

    fun addComponent(component: Component){
        componentList.add(component)
    }
}