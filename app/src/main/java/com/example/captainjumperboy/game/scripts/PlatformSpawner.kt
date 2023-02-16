package com.example.captainjumperboy.game.scripts

import android.icu.text.ListFormatter.Width
import android.util.Log
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Camera
import com.example.captainjumperboy.engine.GameObject
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.assets.Image
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.ui.GameView
import java.time.LocalTime
import kotlin.math.abs
import kotlin.random.Random

class PlatformSpawner : Scriptable() {
    val platforms = ArrayList<GameObject>()
    var Highscore:Int=0

    private val count = 15
    private val rng = Random(LocalTime.now().second)

    //spawner variables
    private var spawnCounter = 0 //used to set new spawn position after initial spawning
    private val platformDistance = 250F //y distance
    private val startY = 2000

    private fun GetRandomPosX() : Float
    {
        return rng.nextFloat() * 1000F
    }

    //initial spawn
    private fun spawn(){
        val birdImage = Image(R.drawable.broken)
        val rng = Random(LocalTime.now().second)
        //val startY = 2980
        // Usuable screen height is about 2980
        for (i in 0 until count){
            val platform = createObject()
            platform.name="Platform"
            platform.addComponent(Sprite(birdImage))
            platform.transform.scale.y = 0.5F
            platform.transform.scale.x = 5F
            platform.transform.position.y = -i * platformDistance + startY
            platform.transform.position.x = GetRandomPosX()+(platform.transform.scale.x*0.5f)

            if (i == 0)
            {
                platform.transform.scale.y = 0.5F
                platform.transform.scale.x = 12F
                platform.transform.position.x = GameView.windowWidth / 2.0F
            }

            //add colliders
            platform.addComponent(Collision.AABB(platform.transform.position,platform.transform.scale*0.5f))
            platforms.add(platform)
        }

        spawnCounter = count
    }

    override fun startEarly(){
        spawn()
    }

    override fun start() {

    }

    override fun update() {
        platforms.forEach{ plat->
            val aabb = plat.getComponent<Collision.AABB>() ?: return
            aabb.pos = plat.transform.position

            val distance = abs(Camera.transform.position.y - plat.transform.position.y)
            val distanceToBottom = Camera.screenHeight - distance

            if(distanceToBottom < 0.0f)
            {
                val halfScaleY = plat.transform.scale.y / 2.0f * Assets.targetHeight

                //plat.transform.position.y = Camera.transform.position.y //OLD
                //more consistent spacing than camera pos
                val offset = -(spawnCounter++) * platformDistance //negate as y axis goes downward
                plat.transform.position.y = startY + offset

                plat.transform.position.x = GetRandomPosX() + (plat.transform.scale.x * 0.5f)
                plat.transform.scale.x = 5F
                aabb.halfSize = plat.transform.scale * 0.5f
                aabb.pos = plat.transform.position

                Highscore += 100
            }
        }
    }

}