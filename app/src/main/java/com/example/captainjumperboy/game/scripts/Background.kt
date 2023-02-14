package com.example.captainjumperboy.game.scripts

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.component.Scriptable


class Background : Scriptable() {
    private lateinit var scene: Scene

    fun setScene(s: Scene)
    {
        this.scene=s
    }
    override fun start() {
        val Width=scene.view.windowWidth.toFloat()
        val Height=scene.view.windowHeight.toFloat()

        transform.position.x = Width/2.0f
        transform.position.y = Height/2.0f
        transform.scale.x =Width/100.0f
        transform.scale.y = Height/100.0f
        transform.rotation = 0F
    }

//    override fun update() {
//        Camera.transform.position.y += 1
//        transform.position.y += 1
//    }
}