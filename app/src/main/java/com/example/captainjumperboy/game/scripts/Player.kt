package com.example.captainjumperboy.game.scripts

import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.engine.Sprite
import com.example.captainjumperboy.engine.Spritesheet
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.math.Vector2D
import com.example.captainjumperboy.ui.MainActivity
import com.example.captainjumperboy.ui.OnSensorDataChanged
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Player : Scriptable(), OnSensorDataChanged
{
    var velocity = Vector2D()
    val jumpInterval = 2L
    lateinit var aabb:Collision.AABB
    val scope = CoroutineScope(Dispatchers.Default)
    private lateinit var mainActivity: MainActivity

    fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
        this.mainActivity.setSensorDataChangedListener(this)
    }
    override fun start() {
        aabb= gameObject.getComponent<Collision.AABB>() as Collision.AABB
        val platformSpawner = findObject("spawner")
        val spawner = platformSpawner.getScript<PlatformSpawner>() ?: return
        val sprite = gameObject.getComponent<Spritesheet>() ?: return

        val firstPlatform = spawner.platforms[1]
        transform.position.x = firstPlatform.transform.position.x
        transform.position.y = firstPlatform.transform.position.y - sprite.image.height * transform.scale.y / 2F

    }

    fun jump(){
        velocity.y = -5F;
    }

    override fun update() {
        val dt = GameThread.deltaTime
        aabb.pos =transform.position
        transform.position.x += velocity.x
        transform.position.y += velocity.y

        velocity.y += 10F


    }

    override fun onSensorDataChanged(x: Float, y: Float, z: Float) {
        //todo...
    }
}