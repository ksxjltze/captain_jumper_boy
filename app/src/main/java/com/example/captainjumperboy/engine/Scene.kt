package com.example.captainjumperboy.engine

import android.graphics.Canvas
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.component.Component
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.math.Vector2D
import com.example.captainjumperboy.ui.GameView

interface OnCollidedListener {
    fun onCollided(file: GameObject)
}

open class Scene(var view: GameView) {
    protected var gameObjectList = ArrayList<GameObject>()
    val renderer = Renderer()
    var paused = false

    /** LISTENER FOR COLLISION EVENTS **/
    protected var collisionListener: OnCollidedListener? = null
    fun registerCollisionListener(listener: OnCollidedListener)//call function in gameobject
    {
        this.collisionListener = listener
    }

    //TOUCH CONTROL EVENTS MOVED TO Input.kt
    companion object {
        lateinit var activeScene : Scene
            private set
    }

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
        activeScene = this
        for (i in 0 until gameObjectList.count()){
            gameObjectList[i].startEarly()
        }
    }

    fun start(){
        activeScene = this //kinda redundant (already set in startEarly)

        for (i in 0 until gameObjectList.count()){
            gameObjectList[i].start()
        }
    }

    open fun update(){
        gameObjectList.forEach {gameObject ->  gameObject.pausedUpdate()}

        if (!paused)
            gameObjectList.forEach {gameObject ->  gameObject.update()}
    }

    open fun draw(canvas: Canvas){
        gameObjectList.forEach {gameObject ->  gameObject.draw(renderer)}
        renderer.draw(canvas)
        debugDrawColliders(canvas)
    }

    fun debugDrawColliders(canvas: Canvas){
        for (gameObject in gameObjectList){
            if (!gameObject.active)
                continue

            val aabb = gameObject.getComponent<Collision.AABB>() ?: continue
            val min = Vector2D(aabb.pos.x - aabb.absoluteHalfSize.x , aabb.pos.y - aabb.absoluteHalfSize.y )
            val max = Vector2D(aabb.pos.x + aabb.absoluteHalfSize.x , aabb.pos.y + aabb.absoluteHalfSize.y )

            //DRAW UI RECT WITHOUT CAMERA MATRIX
            if (aabb is UIRect){
                canvas.drawLine(min.x, min.y, max.x, min.y, Assets.DebugPaint)
                canvas.drawLine(min.x, min.y, min.x, max.y, Assets.DebugPaint)
                canvas.drawLine(min.x, max.y, max.x, max.y, Assets.DebugPaint)
                canvas.drawLine(max.x, max.y, max.x, min.y, Assets.DebugPaint)
                continue
            }

            //apply transform and draw
            canvas.withMatrix(Camera.transform.getViewMatrix()) {
                canvas.drawLine(min.x, min.y, max.x, min.y, Assets.DebugPaint)
                canvas.drawLine(min.x, min.y, min.x, max.y, Assets.DebugPaint)
                canvas.drawLine(min.x, max.y, max.x, max.y, Assets.DebugPaint)
                canvas.drawLine(max.x, max.y, max.x, min.y, Assets.DebugPaint) }
        }
    }
}