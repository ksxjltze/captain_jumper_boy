package com.example.captainjumperboy.game.scripts

import android.content.res.Resources.NotFoundException
import android.util.Log
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.engine.Input
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.ui.GameView

class PauseController : Scriptable() {
    private lateinit var pauseButton : GameObject
    private lateinit var pauseMenu : GameObject
    private lateinit var resumeButton : GameObject
    private lateinit var quitButton : GameObject

    private lateinit var pauseButtonAABB : Collision.AABB
    private lateinit var resumeButtonAABB : Collision.AABB
    private lateinit var quitButtonAABB : Collision.AABB

    var isPaused = false

    override fun start() {
        pauseButton = findObject("PauseButton")
        pauseMenu = findObject("PauseMenu")
        resumeButton = findObject("ResumeButton")
        quitButton = findObject("QuitButton")

        pauseMenu.active = false

        //kotlin is so pog
        pauseButtonAABB = pauseButton.getComponentForced()
        resumeButtonAABB = resumeButton.getComponentForced()
        quitButtonAABB = quitButton.getComponentForced()
    }

    override fun pausedUpdate() {
        //not paused, check for button press
        if (!isPaused){
            //pause button was pressed
            if (Input.touchEvent && pauseButtonAABB.isPointInside(Input.touchPos)){
                isPaused = true
                pauseMenu.active = true
                pauseButton.visible = false
            }
            return
        }

        //paused
        //update pause menu
        if (Input.touchEvent){
            var unpause = false

            if (resumeButtonAABB.isPointInside(Input.touchPos)){
                unpause = true
            }
            else if(quitButtonAABB.isPointInside(Input.touchPos)){
                unpause = true
                GameThread.exit()
            }

            if (unpause){
                isPaused = false
                pauseMenu.active = false
                pauseButton.visible = true
            }

            Scene.activeScene.paused = isPaused
        }

    }

}