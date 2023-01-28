package com.example.captainjumperboy.game.scenes

import android.graphics.BitmapFactory
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.game.scripts.Keith
import com.example.captainjumperboy.game.scripts.Matthias
import com.example.captainjumperboy.game.scripts.PlatformSpawner
import com.example.captainjumperboy.ui.GameView

class CaptainJumperBoy(view : GameView) : Scene(view){
    init {
        val platformSpawner = createObject()
        platformSpawner.addScript<PlatformSpawner>()

        val keithObject = createObject()
        keithObject.addScript<Keith>()

        //val birdBitmap = BitmapFactory.decodeResource(view.resources, R.drawable.bird)
//        val keithObject = createObject()
//        val keithSprite = Sprite(birdBitmap)
//        keithObject.addComponent(keithSprite)
//        keithObject.addScript<Keith>()
//
//        val matthiasObject = createObject()
//        val matthiasSprite = Sprite(birdBitmap)
//        matthiasObject.addComponent(matthiasSprite)
//        matthiasObject.addScript<Matthias>()

    }

}