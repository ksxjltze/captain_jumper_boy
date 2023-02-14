package com.example.captainjumperboy.game.scripts

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.SpriteSheet
import com.example.captainjumperboy.engine.Text
import com.example.captainjumperboy.engine.component.Scriptable

class Highscore : Scriptable() {
    private var initialPosition = 0.0f
    override fun start() {
        transform.position.x = 25.0F
        transform.position.y = 10.0F
        transform.scale.x = 20F
        transform.scale.y = 20F
        transform.rotation = 0F
        val text:Text = gameObject.getComponent<Text>() ?: return
        text.pos=transform.position
        text.paint.setTextSize(5F);
        text.str="HIGHSCORE"
        text.textcolor=Color.RED
        initialPosition = transform.position.y
    }

    override fun update() {
        transform.position.y = initialPosition+Camera.transform.position.y//magic number
    }
}