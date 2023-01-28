package com.example.captainjumperboy.game.scripts

import android.graphics.Canvas
import android.util.Log
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Transform

class Keith() : Scriptable() {
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