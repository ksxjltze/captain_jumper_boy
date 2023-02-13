package com.example.captainjumperboy.game.scripts

import android.util.Log
import android.view.WindowManager
import com.example.captainjumperboy.engine.*
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.math.Vector2D
import com.example.captainjumperboy.ui.MainActivity
import com.example.captainjumperboy.ui.OnSensorDataChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Player : Scriptable(), OnSensorDataChanged, OnCollidedListener
{
    var velocity = Vector2D()
    var Isjump:Boolean=false

    lateinit var aabb:Collision.AABB
    private lateinit var mainActivity: MainActivity
    private lateinit var scene:Scene

    fun setScene(s:Scene)
    {
        this.scene=s
        s.registerCollisionListener(this)
    }
    fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity

        this.mainActivity.setSensorDataChangedListener(this)
    }
    override fun start() {
        aabb= gameObject.getComponent<Collision.AABB>() as Collision.AABB
        val platformSpawner = findObject("spawner")
        val spawner = platformSpawner.getScript<PlatformSpawner>() ?: return
        val sprite = gameObject.getComponent<SpriteSheet>() ?: return

       val firstPlatform = spawner.platforms[5]
       // transform.position.x = firstPlatform.transform.position.x
       transform.position.y = firstPlatform.transform.position.y +transform.position.y

    }

    fun jump(){
        val dt = GameThread.deltaTime
        velocity.y = -15F ;
    }

    override fun update() {
        val Width=scene.view.windowWidth.toFloat()
        val dt = GameThread.deltaTime
        aabb.pos = transform.position
        transform.position.x += velocity.x
        transform.position.y += velocity.y
        if(transform.position.x>= Width)
        {
            transform.position.x=0.0f
        }
        else if(transform.position.x<=0.0f)
        {
            transform.position.x=Width
        }
//        else if(transform.position.x<= -(Width as Float)/2.0f)
//        {
//            transform.position.x=(Width as Float)/2.0f
//        }
        if (Scene.touchEvent && !Isjump) {
            Isjump=true
            jump()
            Scene.touchEvent = false
        }
        else velocity.y += 0.5F
    }

    override fun onSensorDataChanged(x: Float, y: Float, z: Float) {
        velocity.x -=x
        //todo...
    }

    override fun onCollided(obj: GameObject) {
        Log.d("MainActivity","Player Collided")
        val platformAABB = obj.getComponent<Collision.AABB>()
        var platformHeight = 0.0F
        if (platformAABB != null)
        {
            platformHeight = platformAABB.absoluteHalfSize.y + platformAABB.pos.y
        }

        if(obj.name=="Platform" && velocity.y>0 &&
            transform.position.y < platformHeight) //only collide if its going down
        {
            Log.d("MainActivity","Player Collided w/ Platform")
            velocity.y = 0.0F
            Isjump=false
        }
        else
            return
    }
}

