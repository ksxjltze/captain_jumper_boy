package com.example.captainjumperboy.engine

import android.graphics.Canvas
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.ui.GameView

open class Scene(var view: GameView) {
    private var gameObjectList = ArrayList<GameObject>()

    init {
        Assets.view = view
    }

    //get first inactive object, creates a new game object if none found
    fun createObject(name : String = "") : GameObject{
        return try {
            val gameObject = gameObjectList.first { gameObject -> !gameObject.active }
            gameObject.name = name

            gameObject
        }
        catch (_ : Exception){
            val gameObject = GameObject()
            gameObject.name = name
            if (!gameObjectList.add(gameObject))
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

    fun startEarly(){
        Component.scene = this
        for (i in 0 until gameObjectList.count()){
            gameObjectList[i].startEarly()
        }
    }

    fun start(){
        Component.scene = this
        for (i in 0 until gameObjectList.count()){
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