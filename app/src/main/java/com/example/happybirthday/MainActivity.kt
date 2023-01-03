package com.example.happybirthday

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

//global constants
const val GRAVITY = -10.0f
const val FLAP_VELOCITY = 100.0f
const val UPDATE_INTERVAL = 30L

class MainActivity : AppCompatActivity() {

    //thread stuff
    private lateinit var updateThread: HandlerThread
    private lateinit var updateHandler: Handler

    class Game(act: MainActivity, hand : Handler) : Runnable
    {
        private var activity: MainActivity
        private var handler: Handler

        //views
        private var gameLayout: ConstraintLayout
        private var bird: ImageView
        private var ground: ImageView

        //game stuff
        private var birdX = 0.0f
        private var birdY = 0.0f
        private var groundY = 0.0f
        private var velocity = 0.0f

        init{
            activity = act
            handler = hand

            // Initialize bird
            birdX = 100.0f
            birdY = 300.0f

            groundY = 0.0f

            //initialize view refs
            gameLayout = activity.findViewById(R.id.gameLayout)
            bird = activity.findViewById(R.id.bird)
            ground = activity.findViewById(R.id.ground)

            //all of the following calculation assumes that y = 0 is for some reason in the center of the screen
            //somewhat scuffed method for calculating ground height
            // https://stackoverflow.com/questions/3591784/views-getwidth-and-getheight-returns-0
            ground.post {groundY += getScreenHeight() * 0.5f - ground.height }
            bird.post {groundY -= bird.height * 0.5f}

            // Set up touch listener
            gameLayout.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // Flap the bird when the screen is tapped
                    velocity = -FLAP_VELOCITY
                    gameLayout.performClick() //gets rid of some warning
                }
                true
            }
        }

        override fun run(){
            update()
            handler.postDelayed(this, UPDATE_INTERVAL)
        }

        private fun update() {
            // Update bird position
            birdY += velocity
            velocity -= GRAVITY

            // Check for collision with ground
            if (birdY > groundY - bird.height) {
                birdY = groundY - bird.height
                velocity = 0.0f
            }

            // Update positions
            bird.translationX = birdX
            bird.translationY = birdY
        }

        private fun getScreenHeight(): Int {
            // get device dimensions
            return activity.windowManager.currentWindowMetrics.bounds.height()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a background thread for updates
        // aka game loop
        updateThread = HandlerThread("updateThread")
        updateThread.start()
        updateHandler = Handler(updateThread.looper)

        val game = Game(this, updateHandler)

        // Start the update loop
        updateHandler.post(game)
    }

}

