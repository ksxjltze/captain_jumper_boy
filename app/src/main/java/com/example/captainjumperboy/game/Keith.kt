package com.example.captainjumperboy.game

import android.graphics.Canvas
import android.util.Log
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Transform

class Keith(gameObject: GameObject) : Scriptable(gameObject) {
    override fun start() {

    }

    override fun update() {
        transform.position.y += 1
    }
}