package com.example.captainjumperboy.game.scripts

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.engine.Input
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision

class PauseController : Scriptable() {
    private lateinit var pauseButton : GameObject
    private lateinit var pauseButtonAABB : Collision.AABB
    private lateinit var pauseMenu : GameObject

    var isPaused = false

    override fun start() {
        pauseButton = findObject("PauseButton")
        pauseButtonAABB = pauseButton.getComponentForced<Collision.AABB>()

        pauseMenu = findObject("PauseMenu")
        pauseMenu.active = false
    }

    override fun pausedUpdate() {
        //not paused, check for button press
        if (!isPaused){
            //pause button was pressed
            if (Input.touchEvent && pauseButtonAABB.isPointInside(Input.touchPos)){
                isPaused = true
                pauseMenu.active = true
            }
            return
        }

        //paused
        //todo: pause menu
        Scene.activeScene.paused = isPaused

    }

}