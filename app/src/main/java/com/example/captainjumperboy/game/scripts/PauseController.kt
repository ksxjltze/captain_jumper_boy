package com.example.captainjumperboy.game.scripts

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.engine.Input
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision

class PauseController : Scriptable() {
    private lateinit var pauseButton : GameObject
    private lateinit var pauseButtonAABB : Collision.AABB

    var isPaused = false

    override fun start() {
        pauseButton = findObject("PauseButton")
        pauseButtonAABB = pauseButton.getComponentForced<Collision.AABB>()
    }

    override fun update() {
        //not paused, check for button press
        if (!isPaused){
            //pause button was pressed
            if (Input.touchEvent && pauseButtonAABB.isPointInside(Input.touchPos)){
                isPaused = true
                Log.i("GAME", "PAUSE BUTTON PRESSED")
            }
            return
        }

        Log.i("GAME", "GAME IS PAUSED")

        //paused
        //todo: pause menu
        //test
        GameThread.deltaTime = 0F

    }

}