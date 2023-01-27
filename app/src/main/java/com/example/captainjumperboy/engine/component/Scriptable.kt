package com.example.captainjumperboy.engine.component

import com.example.captainjumperboy.engine.GameObject

abstract class Scriptable(gameObject: GameObject) : Component(gameObject) {
    abstract fun start()
}