package com.example.captainjumperboy.game.scripts

import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.engine.Sprite
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
        val platformSpawner = findObject("spawner")
        val spawner = platformSpawner.getScript<PlatformSpawner>() ?: return
        val sprite = gameObject.getComponent<Sprite>() ?: return

        val firstPlatform = spawner.platforms[0]
        transform.position.x = firstPlatform.transform.position.x
        transform.position.y = firstPlatform.transform.position.y - sprite.image.height * transform.scale.y / 2F

        aabb=gameObject.getComponent<Collision.AABB>()?:return
    }

    fun jump(){
        velocity.y = -5F;
    }

    override fun update() {
        val dt = GameThread.deltaTime
        transform.position.x += velocity.x
        transform.position.y += velocity.y

        aabb.pos=transform.position
        velocity.y += 10F


    }

    override fun onSensorDataChanged(x: Float, y: Float, z: Float) {
        //todo...
    }
}