package com.example.captainjumperboy.math

data class Vector2D(var x : Float = 0F, var y: Float = 0F){
    fun add(v: Vector2D): Vector2D {
        return Vector2D(x + v.x, y + v.y)
    }
    fun negate(): Vector2D {
        x *= -1
        y *= -1
        return this
    }

    operator fun times(fl: Float): Vector2D {
        return Vector2D(x * fl, y * fl)

    }
}