package com.example.captainjumperboy.game.scripts

import android.graphics.Canvas
import android.util.Log
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Transform

class Keith() : Scriptable() {
    override fun start() {

    }

    override fun update() {
        Camera.transform.position.y += 1
        //transform.position.y += 1
    }
}