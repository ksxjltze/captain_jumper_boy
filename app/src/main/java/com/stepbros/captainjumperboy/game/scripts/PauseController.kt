package com.stepbros.captainjumperboy.game.scripts

import com.stepbros.captainjumperboy.engine.*
import com.stepbros.captainjumperboy.engine.component.Scriptable
import com.stepbros.captainjumperboy.math.Collision

class PauseController : Scriptable() {
    private lateinit var pauseButton : GameObject
    private lateinit var pauseMenu : GameObject
    private lateinit var resumeButton : GameObject
    private lateinit var quitButton : GameObject

    private lateinit var pauseButtonAABB : UIRect
    private lateinit var resumeButtonAABB : UIRect
    private lateinit var quitButtonAABB : UIRect

    var isPaused = false
    private lateinit var player : GameObject
    private lateinit var playerscript : Player

    override fun start() {
        pauseButton = findObject("PauseButton")
        pauseMenu = findObject("PauseMenu")
        resumeButton = findObject("ResumeButton")
        quitButton = findObject("QuitButton")

        pauseMenu.active = false
        isPaused = false
        pauseButton.visible=true
        //kotlin is so pog
        pauseButtonAABB = pauseButton.getComponentForced()
        resumeButtonAABB = resumeButton.getComponentForced()
        quitButtonAABB = quitButton.getComponentForced()
        player = findObject("Player")
        playerscript = player.getScript<Player>() ?: return
    }

    override fun pausedUpdate() {
        if(playerscript.isdead) {
            pauseButton.visible = false//cannot see pause button upon death
            return
        }
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