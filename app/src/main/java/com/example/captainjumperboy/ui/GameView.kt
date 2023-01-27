package com.example.captainjumperboy.ui


import android.content.Context
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.captainjumperboy.game.GameThread

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback{
    companion object{
        private lateinit var thread: GameThread
    }

    init {
        holder.addCallback(this)
    }

    fun update(){

    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = GameThread(holder, this)
        focusable = FOCUSABLE
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

}
