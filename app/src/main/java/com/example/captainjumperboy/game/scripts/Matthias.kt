package com.example.captainjumperboy.game.scripts

import com.example.captainjumperboy.engine.component.Scriptable

class Matthias : Scriptable() {
    override fun start() {
        transform.position.x = 500F
        transform.position.y = 100F
        transform.scale.x = 0.6F
    }

    override fun update() {
        transform.position.y += 1
        transform.rotation += 5
    }
}