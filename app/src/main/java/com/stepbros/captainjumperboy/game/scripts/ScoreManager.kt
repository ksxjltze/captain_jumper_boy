package com.stepbros.captainjumperboy.game.scripts

import com.stepbros.captainjumperboy.engine.component.Scriptable

class ScoreManager : Scriptable() {
    var score = 0
        private set
    val scoreIncrement = 100

    fun incrementScore(){
        score += scoreIncrement
    }

    override fun start() {
        score = 0
    }
}