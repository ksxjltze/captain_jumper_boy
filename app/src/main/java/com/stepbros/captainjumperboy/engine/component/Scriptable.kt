package com.stepbros.captainjumperboy.engine.component

abstract class Scriptable : Component() {
    open fun startEarly() {}
    abstract fun start()
}