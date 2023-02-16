package com.stepbros.captainjumperboy.engine

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.withMatrix
import com.stepbros.captainjumperboy.engine.component.Component
import com.stepbros.captainjumperboy.math.Vector2D

class Text(var pos:Vector2D=Vector2D(),var str:String="Some Test",var size:Float=5F) : Component() {
    override var layer = Layer.UI
    var paint : Paint=Paint()
    var textcolor:Int=Color.RED
    var useWorldPos = false

    override fun draw(canvas: Canvas){
        val matrix = transform.getMatrix()
        paint.setTextSize(size);
        paint.textAlign = Paint.Align.CENTER
        paint.setColor(textcolor);

        //without camera
        if (!useWorldPos){
            canvas.withMatrix(matrix) {
                //canvas.drawPaint(paint);
                canvas.drawText(str, pos.x, pos.y, paint);
            }
            return
        }

        matrix.postConcat(Camera.transform.getViewMatrix()) //View * Model
        //apply transform and draw
        canvas.withMatrix(matrix) {
            //canvas.drawPaint(paint);
            canvas.drawText(str, pos.x, pos.y, paint);
        }

    }

    override fun draw(renderer: Renderer){
        renderer.enqueue(this)
    }
}
