package com.example.captainjumperboy.game.scripts

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.SpriteSheet
import com.example.captainjumperboy.engine.Text
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.ui.GameView

class Highscore : Scriptable() {
    private var initialPosition = 0.0f
    private lateinit var scene: Scene

    fun setScene(s: Scene)
    {
        this.scene=s
    }
    override fun start() {
        val Width= GameView.windowWidth.toFloat()
        val Height= GameView.windowHeight.toFloat()
        transform.position.x = Width/2.0f
        transform.position.y = 100f
        transform.scale.x = 20F
        transform.scale.y = 20F
        transform.rotation = 0F
        val text:Text = gameObject.getComponent<Text>() ?: return
        text.paint.setTextSize(5F);
        text.str="HIGHSCORE"
        text.textcolor=Color.RED
        initialPosition = transform.position.y
    }

    override fun update() {
        transform.position.y = initialPosition+Camera.transform.position.y//magic number
    }
}