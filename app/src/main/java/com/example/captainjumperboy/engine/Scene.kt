package com.example.captainjumperboy.engine

import android.graphics.Canvas

class Scene {
    var gameObjectList = ArrayList<GameObject>()

    //get first inactive object, creates a new game object if none found
    fun getObject() : GameObject{
        return try {
            gameObjectList.first { gameObject -> gameObject.active }
        }
        catch (_ : Exception){
            if (!gameObjectList.add(GameObject()))
                throw Exception()
            gameObjectList.last()
        }
    }

    fun update(){
        gameObjectList.forEach {gameObject ->  gameObject.update()}
    }

    fun draw(canvas: Canvas){
        gameObjectList.forEach {gameObject ->  gameObject.draw(canvas)}
    }
}