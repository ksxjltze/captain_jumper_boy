package com.stepbros.captainjumperboy.game.scripts

import android.media.MediaPlayer
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.engine.*
import com.stepbros.captainjumperboy.engine.assets.Assets
import com.stepbros.captainjumperboy.engine.component.Scriptable
import com.stepbros.captainjumperboy.game.scenes.CaptainJumperBoy
import com.stepbros.captainjumperboy.ui.GameView

class Gameover : Scriptable() {
    private lateinit var scene: CaptainJumperBoy
    //private var initialPosition = 0.0f

    private lateinit var restartButton : GameObject
    private lateinit var menuButton : GameObject
    private lateinit var restartButtonAABB : UIRect
    private lateinit var menuButtonAABB : UIRect

    var playonce:Boolean=true
    val mediaplayer = MediaPlayer.create(Assets.view.context, R.raw.dead)

    private lateinit var scoreManager: ScoreManager

    fun setScene(s: CaptainJumperBoy)
    {
        this.scene=s
    }
    override fun start() {
        mediaplayer.isLooping = false
        mediaplayer.setVolume(10f,10f)

        restartButton = findObject("RestartButton")
        menuButton = findObject("MenuButton")
        restartButtonAABB = restartButton.getComponentForced()
        menuButtonAABB = menuButton.getComponentForced()

        val Width= GameView.windowWidth.toFloat()
        val Height= GameView.windowHeight.toFloat()
        transform.rotation = 0F
        //initialPosition = transform.position.y
        val sprite = gameObject.getComponent<Sprite>() ?: return
        transform.scale.x = (Width+50)/sprite.image.originalWidth
        transform.scale.y = (Height+50)/sprite.image.originalHeight
        //transform.position.x = Width/2.0f
        //transform.position.y = Height/2.0f
        sprite.image.Alpha=0

        scoreManager = findObject("GameManager").getScript<ScoreManager>()!!

        restartButton.visible=false
        menuButton.visible=false
    }

    override fun update() {
        /*
         Camera.transform.position.y += 1
         transform.position.y += 1
         */

        // Does not work well because camera is tied to players position, and that
        // is not the center of the screen

        //transform.position.y = Camera.transform.position.y+initialPosition-5//magic number
        val player = findObject("Player")
        val playerscript = player.getScript<Player>() ?: return
        if(playerscript.isdead)
        {
            if(playonce)//play this audio once
            {
                GameThread.saveScoreLocal(scoreManager.score)
                GameThread.saveHighscore(scoreManager.score)

                mediaplayer.seekTo(0);
                mediaplayer.start();
                playonce=false
            }
            val sprite = gameObject.getComponent<Sprite>() ?: return
            val tutorial = findObject("Background2")
            if(sprite.image.Alpha<=255)
            {
                sprite.image.Alpha+=5
                //go to gameover activity to input score
            }
            if (Input.touchEvent ||sprite.image.Alpha>=255)//if touch or screen at full opacity, show buttons
            {
                sprite.image.Alpha=255
                restartButton.active=true
                menuButton.active=true
                restartButton.visible=true
                menuButton.visible=true
            }
            if (Input.touchEvent && sprite.image.Alpha>=255)
            {
                if (restartButtonAABB.isPointInside(Input.touchPos))
                {
                    scene.Restart()
                }
                else if (menuButtonAABB.isPointInside(Input.touchPos))
                {
                    GameThread.exit()
                }
            }
            val text:Text = tutorial.getComponent<Text>() ?: return
            text.str=""//remove tutorial text
        }

    }
}