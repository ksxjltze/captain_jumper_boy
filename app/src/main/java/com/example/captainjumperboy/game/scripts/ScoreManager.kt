package com.example.captainjumperboy.game.scripts

import com.example.captainjumperboy.engine.component.Scriptable

class ScoreManager : Scriptable() {
    var score = 0
        private set
    private val scoreIncrement = 100

    fun incrementScore(){
        score += scoreIncrement
    }

    override fun start() {

    }
}