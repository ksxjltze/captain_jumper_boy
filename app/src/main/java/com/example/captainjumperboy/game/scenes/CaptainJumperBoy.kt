package com.example.captainjumperboy.game.scenes

import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.*
import com.example.captainjumperboy.engine.assets.Image
import com.example.captainjumperboy.game.scripts.PlatformSpawner
import com.example.captainjumperboy.game.scripts.Player
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.math.Vector2D
import com.example.captainjumperboy.ui.GameView
import com.example.captainjumperboy.ui.MainActivity


class CaptainJumperBoy(view : GameView) : Scene(view){
    private var playerObject : GameObject
    init {
        val platformSpawner = createObject("spawner")
        platformSpawner.addScript<PlatformSpawner>()

        //test background/foreground
        val background = createObject()
        background.apply {
            name = "Background"
            addComponent(Sprite(Image(R.drawable.matt_big)).apply { layer = Layer.UI })
            transform.apply {
                scale = Vector2D(10F, 10F)
                position = Vector2D(500F, 600F)
            }
        }

        playerObject = createObject()
        playerObject.name="Player"
        playerObject.transform.position.x = 300F
        playerObject.transform.scale.x = 1F
        playerObject.transform.scale.y = 1F
        playerObject.addComponent(SpriteSheet(R.drawable.spritesheet_,2,4))
        playerObject.addComponent(Collision.AABB(playerObject.transform.position,playerObject.transform.scale*0.5f))
        playerObject.addScript<Player>()
        playerObject.getScript<Player>()?.setMainActivity(this.view.context as MainActivity)
        playerObject.getScript<Player>()?.setScene(this)
    }
    override fun update() {
        super.update()

        //collision loop..need to destroy platforms out of viewport otherwise this will get slower..
        gameObjectList.forEach {gameObject ->
            if(gameObject.hasComponent<Collision.AABB>())
            {
                gameObjectList.forEach{gameObject2 ->
                    if(gameObject.name!=gameObject2.name && gameObject2.hasComponent<Collision.AABB>())//if does not equal itself and both objects has aabb,do checks
                    {
                        val aabb=gameObject.getComponent<Collision.AABB>()?:return
                        val aabb2=gameObject2.getComponent<Collision.AABB>()?:return
                        if(aabb.collidesWith(aabb2))
                        {
                            collisionListener?.onCollided(gameObject2)
                        }
                        else if(aabb2.collidesWith(aabb)){
                            collisionListener?.onCollided(gameObject)
                        }

                    }
                }
            }
        }
    }
}