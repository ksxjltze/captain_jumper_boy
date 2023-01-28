package com.example.captainjumperboy.engine.component

abstract class Scriptable : Component() {
    open fun startEarly() {}
    abstract fun start()
}