package com.example.captainjumperboy.game.scenes

import android.graphics.BitmapFactory
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.game.scripts.Keith
import com.example.captainjumperboy.ui.GameView

class CaptainJumperBoy(view : GameView) : Scene(view){
    init {
        val birdBitmap = BitmapFactory.decodeResource(view.resources, R.drawable.bird)

        val keithObject = getObject()
        var transform = keithObject.transform

        transform.position.x = 100F
        transform.scale.x = 2F
        transform.scale.y = 2F
        transform.rotation = 0F

        val keithSprite = Sprite(birdBitmap)
        keithObject.addComponent(keithSprite)
        keithObject.addScript<Keith>()

        val matthiasObject = getObject()
        transform = matthiasObject.transform
        transform.position.y = 100F
        transform.scale.x = 1F

        val matthiasSprite = Sprite(birdBitmap)
        matthiasObject.addComponent(matthiasSprite)
        matthiasObject.addScript<Keith>()

    }

}