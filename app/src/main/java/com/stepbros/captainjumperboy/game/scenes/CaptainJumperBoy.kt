package com.stepbros.captainjumperboy.game.scenes

import android.icu.text.ListFormatter
import com.stepbros.captainjumperboy.R
import com.stepbros.captainjumperboy.engine.*
import com.stepbros.captainjumperboy.engine.Camera.Companion.transform
import com.stepbros.captainjumperboy.engine.assets.Image
import com.stepbros.captainjumperboy.game.scripts.*
import com.stepbros.captainjumperboy.math.Collision
import com.stepbros.captainjumperboy.math.Vector2D
import com.stepbros.captainjumperboy.ui.GameView
import com.stepbros.captainjumperboy.ui.MainActivity


class CaptainJumperBoy(view : GameView) : Scene(view){
    private var playerObject : GameObject
    private var cameraSpeed = 2.0F
    init {
        gameObjectList.clear()
        paused = false

        //damn laggy
//        val BG = createObject("background")
//        val mainmenu = Image(R.drawable.mainmenu, false)
//        BG.addComponent(Sprite(mainmenu).apply { layer = Layer.STATIC_BACKGROUND })
//        BG.transform.apply {
//            scale = Vector2D(2F, 2F)
//            position = Vector2D(mainmenu.originalWidth / 2F, mainmenu.originalHeight / 2F)
//        }
       // BG.addScript<Background>()
//        BG.getScript<Background>()?.setScene(this)

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
        playerObject.transform.scale.x = 1F
        playerObject.transform.scale.y = 1.5F
        playerObject.addComponent(SpriteSheet(R.drawable.spritesheet_player,1,8))
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
            addComponent(Text(Vector2D(),"Tap to jump and tilt to move, don't fall",5F)
                .apply { useWorldPos = true })
            transform.apply {
                scale = Vector2D(10F, 15F)
                position = Vector2D(500f, 800f)
            }
        }
        val gameover = createObject()
        gameover.apply {
            name = "gameover"
            addComponent(Sprite(Image(R.drawable.gameover, false)).apply { layer = Layer.UI })

            addScript<Gameover>()
        }
        gameover.getScript<Gameover>()?.setScene(this)

        //GAME MANAGER
        val gameManager = createObject("GameManager")
        gameManager.apply {
            addScript<PauseController>()
            addScript<ScoreManager>()
        }

        //PAUSE BUTTON
        val pauseButton = createObject("PauseButton")
        pauseButton.apply {
            transform.apply {
                position.x = 80F
                position.y = 65F
                scale.x = 1F
                scale.y = 1F
            }

            addComponent(Sprite(Image(R.drawable.pause)).apply { layer = Layer.UI })
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
            addComponent(Sprite(Image(R.drawable.winningtile)).apply { layer = Layer.UI })

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
                addComponent(Sprite(Image(R.drawable.start)).apply { layer = Layer.UI_FOREGROUND })
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
                addComponent(Sprite(Image(R.drawable.exit)).apply { layer = Layer.UI_FOREGROUND })
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