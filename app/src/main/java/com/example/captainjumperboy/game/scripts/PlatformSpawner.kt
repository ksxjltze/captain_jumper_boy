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

    private fun GetRandomPosX() : Float
    {
        return rng.nextFloat() * 1000F
    }

    private fun spawn(){
        val birdImage = Image(R.drawable.broken)
        val rng = Random(LocalTime.now().second)
        val startY = 2000
        //val startY = 2980
        // Usuable screen height is about 2980
        for (i in 0 until count){
            val platform = createObject()
            platform.name="Platform"
            platform.addComponent(Sprite(birdImage))
            platform.transform.scale.y = 0.5F
            platform.transform.scale.x = 5F
            platform.transform.position.y = -i * 250F + startY
            platform.transform.position.x = GetRandomPosX()+(platform.transform.scale.x*0.5f)
            if (i == 0)
            {
                platform.transform.scale.y = 0.5F
                platform.transform.scale.x = 12F
                platform.transform.position.x = GameView.windowWidth / 2.0F
            }
            platform.addComponent(Collision.AABB(platform.transform.position,platform.transform.scale*0.5f))
            platforms.add(platform)
        }
    }

    override fun startEarly(){
        spawn()
    }

    override fun start() {

    }

    override fun update() {

        val player = findObject("Player")
        val playerscript = player.getScript<Player>() ?: return
        if(playerscript.isdead) {
            return
        }
        platforms.forEach{ plat->
            val aabb = plat.getComponent<Collision.AABB>() ?: return
            aabb.pos=plat.transform.position

            val distance = abs(Camera.transform.position.y - plat.transform.position.y)
            val distanceToBottom = Camera.screenHeight - distance

            if(distanceToBottom < 0.0f)
            {
                val halfScaleY = plat.transform.scale.y / 2.0f * Assets.targetHeight
                plat.transform.position.y = Camera.transform.position.y - halfScaleY-20F
                plat.transform.position.x = GetRandomPosX()+(plat.transform.scale.x*0.5f)
                plat.transform.scale.x = 5F
                aabb.pos=plat.transform.position
                aabb.RecalculateHalfSize(plat.transform.scale*0.5f)
                Highscore+=100
            }
        }
    }

}