package com.example.captainjumperboy.engine

import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.ui.GameView

open class Scene(var view: GameView) {
    private var gameObjectList = ArrayList<GameObject>()

    //get first inactive object, creates a new game object if none found
    fun createObject() : GameObject{
        return try {
            gameObjectList.first { gameObject -> !gameObject.active }
        }
        catch (_ : Exception){
            if (!gameObjectList.add(GameObject()))
                throw Exception()
            gameObjectList.last()
        }
    }

    //same as createObject, but calls start()
    fun spawnObject() : GameObject{
        val gameObject = createObject()
        gameObject.start()
        return gameObject
    }

    fun findObject(name : String) : GameObject{
        return gameObjectList.first{gameObject -> gameObject.name == name }
    }

    fun start(){
        Component.scene = this
        for (i in 0..gameObjectList.count()){
            gameObjectList[i].start()
        }
    }

    fun update(){
        gameObjectList.forEach {gameObject ->  gameObject.update()}
    }

    fun draw(canvas: Canvas){
        gameObjectList.forEach {gameObject ->  gameObject.draw(canvas)}
    }
}