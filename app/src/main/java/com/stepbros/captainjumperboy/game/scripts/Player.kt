package com.stepbros.captainjumperboy.game.scripts

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.util.Log
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.engine.*
import com.stepbros.captainjumperboy.engine.assets.Assets
import com.stepbros.captainjumperboy.engine.component.Scriptable
import com.stepbros.captainjumperboy.math.Collision
import com.stepbros.captainjumperboy.math.Vector2D
import com.stepbros.captainjumperboy.ui.GameView
import com.stepbros.captainjumperboy.ui.MainActivity
import com.stepbros.captainjumperboy.ui.OnSensorDataChanged
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
    val playeroffset:Float=1F
    var lastCollidedPlatformY = 2500F
    //private var spritesheet = gameObject.getComponent<SpriteSheet>()
    private lateinit var anim : SpriteSheet
    //score manager
    private lateinit var scoreManager : ScoreManager

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
        scoreManager = findObject("GameManager").getScript<ScoreManager>()!!
    }

    fun jump(){
        start = true

        velocity.y = -25F

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
        if(Isjump)
        {
            jump()
            Isjump=false
        }
        else
        {
            if(velocity.y > 0.0f)
                velocity.y += (1F + (scoreManager.score / scoreManager.scoreIncrement * 0.05F))
            else
                velocity.y += 1F
        }

        if(transform.position.y<Camera.screenHeight/2.0f && !Isjump)
        {
            Camera.transform.position.y +=velocity.y
        }
        else
        Camera.transform.position.y -= 2.0f//camera movement
    }

    override fun onSensorDataChanged(x: Float, y: Float, z: Float) {
        velocity.x -=(x*3f)
    }

    override fun onCollided(obj: GameObject) {
        val platformAABB = obj.getComponent<Collision.AABB>()
        var platformTop = 0.0F
        var playerBottom = 0.0F
        if (platformAABB != null) {
            platformTop = platformAABB.pos.y - platformAABB.absoluteHalfSize.y
        }
        playerBottom = transform.position.y + aabb.absoluteHalfSize.y+playeroffset
        if(obj.name=="Platform"  &&
            ((playerBottom) <= platformTop + velocity.y)) //only collide if its going down
        {
            if(obj.transform.position.y < lastCollidedPlatformY)
            {
                lastCollidedPlatformY = obj.transform.position.y
                scoreManager.incrementScore()
            }
            firsttouch=true
            Isjump=true

        }
        else
            return
    }
}

