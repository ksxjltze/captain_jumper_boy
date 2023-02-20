package com.stepbros.captainjumperboy.game.scripts

import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.engine.Camera
import com.stepbros.captainjumperboy.engine.GameObject
import com.stepbros.captainjumperboy.engine.Sprite
import com.stepbros.captainjumperboy.engine.assets.Assets
import com.stepbros.captainjumperboy.engine.assets.Image
import com.stepbros.captainjumperboy.engine.component.Scriptable
import com.stepbros.captainjumperboy.math.Collision
import com.stepbros.captainjumperboy.ui.GameView
import java.time.LocalTime
import kotlin.math.abs
import kotlin.random.Random

class PlatformSpawner : Scriptable() {
    val platforms = ArrayList<GameObject>()

    private val count = 15
    private val rng = Random(LocalTime.now().second)

    //score manager
    private lateinit var scoreManager : ScoreManager

    //spawner variables
    private var spawnCounter = 0 //used to set new spawn position after initial spawning
    private val platformDistance = 250F //y distance
    private val startY = 2000
    //val startY = 2980
    // Usuable screen height is about 2980

    private fun GetRandomPosX() : Float
    {
        return rng.nextFloat() * 1000F
    }

    //initial spawn
    private fun spawn(){
        val birdImage = Image(R.drawable.broken)
        val rng = Random(LocalTime.now().second)

        for (i in 0 until count){
            val platform = createObject()
            platform.name="Platform"
            platform.addComponent(Sprite(birdImage))
            platform.transform.scale.y = 0.5F
            platform.transform.scale.x = 4F
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
        scoreManager = findObject("GameManager").getScript<ScoreManager>()!!
    }

    override fun update() {

        val player = findObject("Player")
        val playerscript = player.getScript<Player>() ?: return
        if(playerscript.isdead) {
            return
        }

        platforms.forEach{ plat->
            val aabb = plat.getComponent<Collision.AABB>() ?: return

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

                //MOVED TO CaptainJumperBoy UPDATE (UPDATE ALL AABBs)
//                aabb.pos=plat.transform.position
//                aabb.RecalculateHalfSize(plat.transform.scale*0.5f)
                //Highscore+=100

                scoreManager.incrementScore()
            }
        }
    }

}