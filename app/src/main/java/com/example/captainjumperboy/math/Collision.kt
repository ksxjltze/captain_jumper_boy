package com.example.captainjumperboy.math

import android.R.bool
import android.util.Log
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.component.Component
import kotlin.math.abs


class Collision {
    class AABB(var pos: Vector2D, var halfSize: Vector2D) : Component()
    {
        var absoluteHalfSize : Vector2D

        init {
            Log.d("MainActivity: ", "Created AABB w/ pos = $pos and halfSize = $halfSize")
            absoluteHalfSize = halfSize.times(Assets.targetHeight.toFloat())
        }
        var iscollided:Boolean=false



        //for collision detection
        fun collidesWith(other: AABB): Boolean {
            val distance = pos.sub(other.pos)
            //if distance with other AABB is < halfSize = COLLIDE

            return abs(distance.x) <= (absoluteHalfSize.x + other.absoluteHalfSize.x) &&
                    abs(distance.y) <= (absoluteHalfSize.y + other.absoluteHalfSize.y)
        }

        //for collision resolution between 2 AABBs
        fun resolveOverlap(other: AABB) {
            // get vector between the centers of the two rectangles
            val distance = pos.add(other.pos.add(other.absoluteHalfSize.add(absoluteHalfSize.negate())))

            // GET amount of overlap in the x and y axis
            val overlap = Vector2D(
                (absoluteHalfSize.x + other.absoluteHalfSize.x) - abs(distance.x),
                (absoluteHalfSize.y + other.absoluteHalfSize.y) - abs(distance.y)
            )

            //RESOLUTION IDEA: offset the position by overlap amount
            // NOTE: to prevent objects from tunneling through each other so resolve smaller overlap

            // If smallest overlap is in the x axis, resolve the overlap in that direction
            if (overlap.x < overlap.y) {
                if (distance.x > 0) {
                    pos = pos.add(Vector2D(overlap.x, 0f))
                } else {
                    pos = pos.add(Vector2D(-overlap.x, 0f)) //add negative offset to positive overlap
                }
            }
            // if the smallest overlap is in the y axis, resolve overlap y
            else {
                if (distance.y > 0) {
                    pos = pos.add(Vector2D(0f, overlap.y))
                } else {
                    pos = pos.add(Vector2D(0f, -overlap.y)) //add negative offset to positive overlap
                }
            }
        }
    }
}