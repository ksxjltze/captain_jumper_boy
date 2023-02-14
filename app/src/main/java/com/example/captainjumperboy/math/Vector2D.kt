package com.example.captainjumperboy.math

data class Vector2D(var x : Float = 0F, var y: Float = 0F){
    fun add(v: Vector2D): Vector2D {
        return Vector2D(x + v.x, y + v.y)
    }
    fun sub(v : Vector2D) : Vector2D {
        return Vector2D(x - v.x, y - v.y)
    }
    fun negate(): Vector2D {
        return Vector2D(x * -1, y * -1)
    }
    operator fun plus(fl: Float): Vector2D {
        return Vector2D(x + fl, y + fl)
    }
    operator fun minus(fl: Float): Vector2D {
        return Vector2D(x - fl, y - fl)
    }
    operator fun times(fl: Float): Vector2D {
        return Vector2D(x * fl, y * fl)
    }
    operator fun div(fl: Float): Vector2D {
        return Vector2D(x / fl, y / fl)
    }
    fun distance(v1: Vector2D, v2: Vector2D): Float {
        val dx = v2.x - v1.x
        val dy = v2.y - v1.y
        return kotlin.math.sqrt(dx * dx + dy * dy)
    }
}