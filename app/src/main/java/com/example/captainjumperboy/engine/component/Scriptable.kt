package com.example.captainjumperboy.engine.component

import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.math.Transform

interface Scriptable : Component{
    var gameObject : GameObject
    var transform: Transform
    fun start()
}