package com.example.captainjumperboy.math

import com.example.captainjumperboy.engine.component.Component
import kotlin.math.abs

class Collision {
    class AABB(var pos: Vector2D, var halfSize: Vector2D) : Component()
    {

        //for collision detection
        fun collidesWith(other: AABB): Boolean {
            val distance = pos.add(other.pos.add(other.halfSize.add(halfSize.negate())))
            //if distance with other AABB is < halfSize = COLLIDE
            return abs(distance.x) <= (halfSize.x + other.halfSize.x) &&
                    abs(distance.y) <= (halfSize.y + other.halfSize.y)
        }

        //for collision resolution between 2 AABBs
        fun resolveOverlap(other: AABB) {
            // get vector between the centers of the two rectangles
            val distance = pos.add(other.pos.add(other.halfSize.add(halfSize.negate())))

            // GET amount of overlap in the x and y axis
            val overlap = Vector2D(
                (halfSize.x + other.halfSize.x) - abs(distance.x),
                (halfSize.y + other.halfSize.y) - abs(distance.y)
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