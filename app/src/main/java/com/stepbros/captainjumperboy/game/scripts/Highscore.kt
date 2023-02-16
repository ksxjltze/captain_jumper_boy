package com.stepbros.captainjumperboy.game.scripts

import android.graphics.Color
import com.stepbros.captainjumperboy.engine.Scene
import com.stepbros.captainjumperboy.engine.Text
import com.stepbros.captainjumperboy.engine.component.Scriptable
import com.stepbros.captainjumperboy.ui.GameView

class Highscore : Scriptable() {
//    private var initialPosition = 0.0f
    private lateinit var scene: Scene
    private lateinit var scoreManager: ScoreManager

    fun setScene(s: Scene)
    {
        this.scene = s
    }
    override fun start() {
        val width= GameView.windowWidth.toFloat()
        val Height= GameView.windowHeight.toFloat()
        transform.position.x = width/2.0f
        transform.position.y = 100f
        transform.scale.x = 20F
        transform.scale.y = 20F
        transform.rotation = 0F
        val text:Text = gameObject.getComponent<Text>() ?: return
        text.str = "HIGHSCORE"
        text.textcolor = Color.RED
//        initialPosition = transform.position.y

        scoreManager = findObject("GameManager").getScript<ScoreManager>()!!
    }

    override fun update() {
        val platformSpawner = findObject("spawner")
        val text:Text = gameObject.getComponent<Text>() ?: return
        //this is usually a bad idea..
        //val score: Int? =platformSpawner.getScript<PlatformSpawner>()?.Highscore
        text.str = "HIGHSCORE: " + scoreManager.score

        //don't need this anymore, see Text.useWorldPos
//        transform.position.y = initialPosition+Camera.transform.position.y//magic number
    }
}