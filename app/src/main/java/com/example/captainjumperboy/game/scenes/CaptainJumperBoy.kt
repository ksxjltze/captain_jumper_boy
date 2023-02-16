package com.example.captainjumperboy.game.scenes

import android.content.res.Resources
import android.util.Log
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.*
import com.example.captainjumperboy.engine.assets.Image
import com.example.captainjumperboy.game.scripts.*
import com.example.captainjumperboy.math.Collision
import com.example.captainjumperboy.math.Vector2D
import com.example.captainjumperboy.ui.GameView
import com.example.captainjumperboy.ui.MainActivity


class CaptainJumperBoy(view : GameView) : Scene(view){
    private var playerObject : GameObject
    private var cameraSpeed = 2.0F
    init {
        gameObjectList.clear()
        paused = false

        val BG = createObject("background")
        val mainmenu = Image(R.drawable.mainmenu)
        BG.addComponent(Sprite(mainmenu).apply { layer = Layer.BACKGROUND })
        BG.addScript<Background>()
        BG.getScript<Background>()?.setScene(this)

        val platformSpawner = createObject("spawner")
        platformSpawner.addScript<PlatformSpawner>()

//        //test background/foreground
//        val background = createObject()
//        background.apply {
//            name = "Background2"
//            addComponent(Sprite(Image(R.drawable.matt_big)).apply { layer = Layer.UI })
//            transform.apply {
//                scale = Vector2D(10F, 10F)
//                position = Vector2D(500F, 600F)
//            }
//        }

        playerObject = createObject()
        playerObject.name="Player"
        playerObject.transform.position.x = 300F
        playerObject.transform.scale.x = 1.5F
        playerObject.transform.scale.y = 1.5F
        playerObject.addComponent(SpriteSheet(R.drawable.spritesheet_player,1,15))
        playerObject.addComponent(Collision.AABB(playerObject.transform.position,playerObject.transform.scale*0.5f).apply { autoRescale = false })
        playerObject.addScript<Player>()
        playerObject.getScript<Player>()?.setMainActivity(this.view.context as MainActivity)
        playerObject.getScript<Player>()?.setScene(this)

        var text = createObject()
        text.name="Highscore"
        text.addComponent(Text())
        text.addScript<Highscore>()
        text.getScript<Highscore>()?.setScene(this)
        //val width= GameView.windowWidth.toFloat()

        val tutorialtext = createObject()
        tutorialtext.apply {
            name = "Background2"
            val text = Text(Vector2D(),"Tap to jump and tilt to move, don't fall",5F)
            text.useWorldPos = true
            addComponent(text)
            transform.apply {
                scale = Vector2D(10F, 15F)
                position = Vector2D(500f, 800f)
            }
        }


        val gameover = createObject()
        gameover.apply {
            name = "gameover"
            addComponent(Sprite(Image(R.drawable.gameover)).apply { layer = Layer.UI })

            addScript<Gameover>()
        }
        gameover.getScript<Gameover>()?.setScene(this)

        //GAME MANAGER
        val gameManager = createObject("GameManager")
        gameManager.apply {
            addScript<PauseController>()
        }

        //PAUSE BUTTON
        val pauseButton = createObject("PauseButton")
        pauseButton.apply {
            transform.apply {
                position.x = 800F
                position.y = 200F
                scale.x = 5F
                scale.y = 5F
            }

            addComponent(Sprite(Image(R.drawable.captain_jumper)).apply { layer = Layer.UI })
            addComponent(UIRect(transform.position, transform.scale * 0.5F))
        }

        //PAUSE MENU
        val pauseMenu = createObject("PauseMenu")
        pauseMenu.apply {
            transform.apply {
                position.x = 500F
                position.y = 1000F
                scale = Vector2D(5F, 12F)
            }
            addComponent(Sprite(Image(R.drawable.bird)).apply { layer = Layer.UI })

            val buttonSize = Vector2D(3F, 2F)

            //CREATE MENU OBJECTS
            //RESUME
            createObject("ResumeButton").apply {
                transform.apply {
                    position.x = pauseMenu.transform.position.x
                    position.y = pauseMenu.transform.position.y - 200F
                    scale = buttonSize
                }
                parent = pauseMenu
                addComponent(Sprite(Image(R.drawable.swole)).apply { layer = Layer.UI_FOREGROUND })
                addComponent(UIRect(transform.position, transform.scale * 0.5F))
            }

            //QUIT
            createObject("QuitButton").apply {
                transform.apply {
                    position.x = pauseMenu.transform.position.x
                    position.y = pauseMenu.transform.position.y + 200F
                    scale = buttonSize
                }
                parent = pauseMenu
                addComponent(Sprite(Image(R.drawable.swole)).apply { layer = Layer.UI_FOREGROUND })
                addComponent(UIRect(transform.position, transform.scale * 0.5F))
            }
        }
    }
    override fun update() {
        super.update()
//        if (playerObject.getScript<Player>()?.start == true)
//            Camera.transform.position.y += GameThread.deltaTime * cameraSpeed
        //collision loop..need to destroy platforms out of viewport otherwise this will get slower..
    }
}