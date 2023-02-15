package com.example.captainjumperboy.game.scripts

import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.*
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.ui.GameView

class Gameover : Scriptable() {
    private lateinit var scene: Scene
    private var initialPosition = 0.0f
    var playonce:Boolean=true
    val mediaplayer = MediaPlayer.create(Assets.view.context, R.raw.dead)
    fun setScene(s: Scene)
    {
        this.scene=s
    }
    override fun start() {
        mediaplayer.isLooping = false
        mediaplayer.setVolume(10f,10f)

        val Width= GameView.windowWidth.toFloat()
        val Height= GameView.windowHeight.toFloat()

        transform.position.x = Width/2.0f
        transform.position.y = Height/2.0f
        transform.scale.x =Width/100.0f
        transform.scale.y = Height/100.0f
        transform.rotation = 0F
        initialPosition = transform.position.y
        val sprite = gameObject.getComponent<Sprite>() ?: return
        sprite.image.Alpha=0
    }

    override fun update() {
        /*
         Camera.transform.position.y += 1
         transform.position.y += 1
         */

        // Does not work well because camera is tied to players position, and that
        // is not the center of the screen

        transform.position.y = Camera.transform.position.y+initialPosition-5//magic number
        val player = findObject("Player")
        val playerscript = player.getScript<Player>() ?: return
        if(playerscript.isdead)
        {
            if(playonce)//play this audio once
            {

                mediaplayer.seekTo(0);
                mediaplayer.start();
                playonce=false
            }
            val sprite = gameObject.getComponent<Sprite>() ?: return
            if(sprite.image.Alpha<=255)
            {
                val tutorial = findObject("Background2")
                val text:Text = tutorial.getComponent<Text>() ?: return
               text.str=""//remove tutorial text
                sprite.image.Alpha+=5
                //go to gameover activity to input score
            }
        }

    }
}