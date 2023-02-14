package com.example.captainjumperboy.engine

import android.graphics.Canvas
import androidx.core.graphics.withMatrix
import java.util.LinkedList
import java.util.PriorityQueue

class Renderer {
    val renderQueue : PriorityQueue<Sprite> = PriorityQueue<Sprite>() //queue interface backed by a linked list

    fun drawSprite(canvas: Canvas){
        while (renderQueue.isNotEmpty()){
            val sprite = renderQueue.peek()
            sprite?.apply {
                val matrix = transform.getMatrix()
                matrix.postConcat(Camera.transform.getMatrix()) //View * Model

                //apply transform and draw
                canvas.withMatrix(matrix) {
                    canvas.drawBitmap(image.bitmap, -image.width/2F, -image.height/2F, null) //draw centered in canvas to apply transform correctly
                }
            }

            renderQueue.remove()
        }

    }
}