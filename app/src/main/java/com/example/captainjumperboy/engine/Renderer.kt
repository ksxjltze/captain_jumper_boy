package com.example.captainjumperboy.engine

import android.graphics.Canvas
import androidx.core.graphics.withMatrix
import java.util.LinkedList
import java.util.PriorityQueue

class Renderer {
    private val compareBySpriteLayer : Comparator<Sprite> = compareByDescending { it.layer }
    private val renderQueue : PriorityQueue<Sprite> = PriorityQueue<Sprite>(compareBySpriteLayer) //queue interface backed by a linked list, sorted by sprite layer

    fun enqueueSprite(sprite: Sprite){
        renderQueue.add(sprite)
    }

    fun draw(canvas: Canvas){
        drawSprites(canvas)
    }

    fun drawSprites(canvas: Canvas){ //flush sprite queue and render
        while (renderQueue.isNotEmpty()){
            val sprite = renderQueue.peek()
            sprite?.apply {
                val matrix = transform.getMatrix()
                matrix.postConcat(Camera.transform.getViewMatrix()) //View * Model

                //apply transform and draw
                canvas.withMatrix(matrix) {
                    canvas.drawBitmap(image.bitmap, -image.width/2F, -image.height/2F, null) //draw centered in canvas to apply transform correctly
                }
            }

            renderQueue.remove()
        }

    }
}