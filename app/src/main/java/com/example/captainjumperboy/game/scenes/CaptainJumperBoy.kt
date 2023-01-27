package com.example.captainjumperboy.game.scenes

import android.graphics.BitmapFactory
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.game.scripts.Keith
import com.example.captainjumperboy.ui.GameView

class CaptainJumperBoy(view : GameView) : Scene(view){
    init {
        val keithObject = getObject()
        val sprite = Sprite(BitmapFactory.decodeResource(view.resources, R.drawable.bird))
        keithObject.addComponent(sprite)
        keithObject.addScript<Keith>()
        //keithObject.addScript(Keith::class)
    }

}