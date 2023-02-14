package com.example.captainjumperboy.game.scripts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.component.Scriptable

class Highscore : Scriptable() {
    override fun start() {
        transform.position.x = 500F
        transform.scale.x = 1F
        transform.scale.y = 0.8F
        transform.rotation = 0F
    }

    override fun update() {
        Camera.transform.position.y += 1
        transform.position.y += 1
    }
}