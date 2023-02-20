package com.stepbros.captainjumperboy.engine

import com.stepbros.captainjumperboy.engine.assets.Assets
import com.stepbros.captainjumperboy.engine.component.Component
import com.stepbros.captainjumperboy.math.Collision
import com.stepbros.captainjumperboy.math.Vector2D
import kotlin.math.abs

//syntactic sugar for dealing with UI collisions (i.e. with touch input)
class UIRect(var pos: Vector2D, var halfSize: Vector2D) : Component(){
    var absoluteHalfSize : Vector2D
    var autoRescale = true //SEE SCENE UPDATE

    init {
        //Log.d("MainActivity: ", "Created AABB w/ pos = $pos and halfSize = $halfSize")
        absoluteHalfSize = halfSize.times(Assets.targetHeight.toFloat())
    }
    var iscollided:Boolean=false
    fun RecalculateHalfSize(halfSize: Vector2D)
    {
        absoluteHalfSize = halfSize.times(Assets.targetHeight.toFloat())
    }
    fun isPointInside(point : Vector2D) : Boolean{
        val distance = pos.sub(point)
        return abs(distance.x) <= absoluteHalfSize.x && abs(distance.y) <= absoluteHalfSize.y
    }
}