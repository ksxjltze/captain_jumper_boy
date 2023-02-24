package com.stepbros.captainjumperboy.game.scripts

import android.media.MediaPlayer
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.engine.*
import com.stepbros.captainjumperboy.engine.assets.Assets
import com.stepbros.captainjumperboy.engine.component.Scriptable
import com.stepbros.captainjumperboy.ui.GameView

class Gameover : Scriptable() {
    private lateinit var scene: Scene
    private var initialPosition = 0.0f

    var playonce:Boolean=true
    val mediaplayer = MediaPlayer.create(Assets.view.context, R.raw.dead)

    private lateinit var scoreManager: ScoreManager

    fun setScene(s: Scene)
    {
        this.scene=s
    }
    override fun start() {
        mediaplayer.isLooping = false
        mediaplayer.setVolume(10f,10f)

        val Width= GameView.windowWidth.toFloat()
        val Height= GameView.windowHeight.toFloat()



        transform.rotation = 0F
        initialPosition = transform.position.y
        val sprite = gameObject.getComponent<Sprite>() ?: return
        transform.scale.x = (Width+50)/sprite.image.originalWidth
        transform.scale.y = (Height+50)/sprite.image.originalHeight
        //transform.position.x = Width/2.0f
        //transform.position.y = Height/2.0f
        sprite.image.Alpha=0

        scoreManager = findObject("GameManager").getScript<ScoreManager>()!!
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
            if (Input.touchEvent)
            {
                GameThread.exit()
            }
            val text:Text = tutorial.getComponent<Text>() ?: return
            text.str="Tap on screen to continue"//remove tutorial text
        }

    }
}