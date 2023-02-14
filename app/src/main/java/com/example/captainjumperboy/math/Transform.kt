package com.example.captainjumperboy.math

import android.graphics.Matrix

class Transform(var position: Vector2D = Vector2D(),
                var rotation : Float = 0F,
                var scale : Vector2D = Vector2D(1F, 1F)){
    fun getMatrix() : Matrix{
        val matrix = Matrix()
        matrix.postScale(scale.x, scale.y)
        matrix.postRotate(rotation) //apparently you can do 3D rotation here if you wanted to
        matrix.postTranslate(position.x, position.y)
        return matrix
    }

    fun getViewMatrix() : Matrix{
        val matrix = Matrix()
        matrix.postScale(scale.x, scale.y)
        matrix.postRotate(rotation) //apparently you can do 3D rotation here if you wanted to
        matrix.postTranslate(-position.x, -position.y)
        return matrix
    }
}