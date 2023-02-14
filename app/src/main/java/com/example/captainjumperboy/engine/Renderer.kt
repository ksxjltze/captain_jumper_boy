package com.example.captainjumperboy.engine

import android.graphics.Canvas
import androidx.core.graphics.withMatrix
import com.example.captainjumperboy.engine.component.Renderable
import java.util.LinkedList
import java.util.PriorityQueue

class Renderer {
    private val compareByLayer : Comparator<Renderable> = compareByDescending { it.layer }
    private val renderQueue : PriorityQueue<Renderable> = PriorityQueue<Renderable>(compareByLayer) //queue interface backed by a linked list, sorted by sprite layer

    fun enqueue(renderable: Renderable){
        renderQueue.add(renderable)
    }

    fun draw(canvas: Canvas){
        while (renderQueue.isNotEmpty()){
            val renderable = renderQueue.peek()
            renderable?.draw(canvas)
            renderQueue.remove()
        }
    }

    private fun drawSprite(sprite: Sprite,  canvas: Canvas){ //flush sprite queue and render
        sprite.apply {

        }

    }
}