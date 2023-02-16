package com.stepbros.captainjumperboy.engine

import com.stepbros.captainjumperboy.math.Collision
import com.stepbros.captainjumperboy.math.Vector2D

//syntactic sugar for dealing with UI collisions (i.e. with touch input)
class UIRect(pos: Vector2D, halfSize: Vector2D) : Collision.AABB(pos, halfSize) {
}