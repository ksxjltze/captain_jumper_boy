package com.example.captainjumperboy.game.scenes

import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Assets
import com.example.captainjumperboy.engine.Scene
import com.example.captainjumperboy.engine.Spritesheet
import com.example.captainjumperboy.game.scripts.Keith
import com.example.captainjumperboy.game.scripts.PlatformSpawner
import com.example.captainjumperboy.game.scripts.Player
import com.example.captainjumperboy.ui.GameView
import com.example.captainjumperboy.ui.MainActivity

class CaptainJumperBoy(view : GameView) : Scene(view){

    init {
        val platformSpawner = createObject("spawner")
        platformSpawner.addScript<PlatformSpawner>()

        val keithObject = createObject()
        keithObject.addScript<Keith>()

        val playerObject = createObject()
        playerObject.addComponent(Spritesheet(Assets.getBitmap(R.drawable.spritesheet_),2,4))
        playerObject.addScript<Player>()
        playerObject.getScript<Player>()?.setMainActivity(this.view.context as MainActivity)


//        playerObject.transform.scale.x = 0.05F
//        playerObject.transform.scale.y = 0.05F
        //playerObject.addScript<Player>()

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