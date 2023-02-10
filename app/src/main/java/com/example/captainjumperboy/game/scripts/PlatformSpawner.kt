package com.example.captainjumperboy.game.scripts

import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.*
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision
import java.sql.Time
import java.time.LocalTime
import kotlin.random.Random

class PlatformSpawner : Scriptable() {
    val platforms = ArrayList<GameObject>()
    private val count = 10

    private fun spawn(){
        val birdBitmap = Assets.getBitmap(R.drawable.bird)
        val rng = Random(LocalTime.now().second)
        val startY = 1600F
        for (i in 0 until count){
            val platform = createObject()
            platform.name="Platform"
            platform.addComponent(Sprite(birdBitmap))
            platform.transform.position.y = -i * 200F + startY
            platform.transform.scale.y = 1F
            platform.transform.scale.x = 7F
            platform.addComponent(Collision.AABB(platform.transform.position,platform.transform.scale*0.5f))
            platform.transform.position.x = rng.nextFloat() * 1000F
            platforms.add(platform)
        }
    }

    override fun startEarly(){
        spawn()
    }

    override fun start() {

    }

    override fun update() {
        platforms.forEach{ plat->
            val aabb = gameObject.getComponent<Collision.AABB>() ?: return
            aabb.pos=plat.transform.position
        }
    }

}