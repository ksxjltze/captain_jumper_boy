package com.example.captainjumperboy.game.scripts

import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Assets
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.engine.component.Scriptable
import java.sql.Time
import java.time.LocalTime
import kotlin.random.Random

class PlatformSpawner : Scriptable() {
    private val platforms = ArrayList<GameObject>()
    private val count = 10
    private fun spawn(){
        val birdBitmap = Assets.getBitmap(R.drawable.bird)
        val rng = Random(LocalTime.now().second)
        for (i in 0..count){
            val platform = createObject()
            platform.addComponent(Sprite(birdBitmap))
            platform.transform.position.y = i * 200F + Camera.transform.position.y
            platform.transform.scale.y = 0.1F
            platform.transform.scale.x = 0.7F
            platform.transform.position.x = rng.nextFloat() * 1000F
            platforms.add(platform)
        }
    }
    override fun start() {
        spawn()
    }

    override fun update() {

    }

}