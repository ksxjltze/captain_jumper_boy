package com.example.captainjumperboy.game.scripts

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import android.view.WindowManager
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.*
import com.example.captainjumperboy.engine.assets.Assets
import com.example.captainjumperboy.engine.component.Scriptable
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.math.Vector2D
import com.example.captainjumperboy.ui.GameView
import com.example.captainjumperboy.ui.MainActivity
import com.example.captainjumperboy.ui.OnSensorDataChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.Math.abs

class Player : Scriptable(), OnSensorDataChanged, OnCollidedListener
{
    var velocity = Vector2D()
    var Isjump:Boolean=false
    var firsttouch:Boolean=false
    var start : Boolean = false
    var isdead:Boolean=false
    lateinit var aabb:Collision.AABB
    private lateinit var mainActivity: MainActivity
    private lateinit var scene:Scene
    val mediaplayer = MediaPlayer.create(Assets.view.context, R.raw.jump2)
    //private var spritesheet = gameObject.getComponent<SpriteSheet>()
    private lateinit var anim : SpriteSheet

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
        mediaplayer.isLooping = false
        mediaplayer.setVolume(10f,10f)
        val firstPlatform = spawner.platforms[0]
        transform.position.x = GameView.windowWidth / 2.0F
        transform.position.y = firstPlatform.transform.position.y - 100.0F
        isdead=false
        anim = gameObject.getComponent<SpriteSheet>() ?: return
    }

    fun jump(){
        start = true
        val dt = GameThread.deltaTime
        velocity.y = -20F ;

        if(firsttouch)
        {
            anim.start()
            anim.playOnce()
            mediaplayer.seekTo(0);
            mediaplayer.start();
            firsttouch=false
        }

    }

    @SuppressLint("SuspiciousIndentation")
    override fun update() {
        val distance = abs(Camera.transform.position.y - transform.position.y)
        val distanceToBottom = Camera.screenHeight - distance

        if(distanceToBottom < 0.0f)
        {
            isdead=true
        }

        if(isdead)return

        val Width = GameView.windowWidth.toFloat()
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
        if (Scene.touchEvent && !Isjump) {
            Isjump=true
            firsttouch=true
            jump()
            Scene.touchEvent = false
        }
        else velocity.y += 0.5F
        if(transform.position.y<100f)
        {
            Camera.transform.position.y -= 3.0f
        }
        else
        Camera.transform.position.y -= 2.0f//camera movement
    }

    override fun onSensorDataChanged(x: Float, y: Float, z: Float) {
        velocity.x -=(x*1.5f)
    }

    override fun onCollided(obj: GameObject) {
        //Log.d("MainActivity","Player Collided")
        val platformAABB = obj.getComponent<Collision.AABB>()
        var platformTop = 0.0F
        var playerBottom = 0.0F
        if (platformAABB != null)
        {
            platformTop = platformAABB.pos.y - platformAABB.absoluteHalfSize.y
        }
        playerBottom = transform.position.y + aabb.absoluteHalfSize.y
        if(obj.name=="Platform" && velocity.y>0 &&
            ((playerBottom) <= platformTop + velocity.y)) //only collide if its going down
        {
            //val overlap = platformAABB?.let { aabb.getOverlap(it) }
            //Log.d("MainActivity","Player Collided w/ Platform")
            transform.position.y -= velocity.y
            velocity.y = 0.0F //collision resolution
            Isjump=false
            Scene.touchEvent = false
        }
        else
            return
    }
}

