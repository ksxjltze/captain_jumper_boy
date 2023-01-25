package com.example.happybirthday

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


//global constants
const val GRAVITY = -10.0f
const val FLAP_VELOCITY = 100.0f
const val UPDATE_INTERVAL = 30L
//hello-dc
class GameActivity : AppCompatActivity() {

    //thread stuff
    private lateinit var updateThread: HandlerThread
    private lateinit var updateHandler: Handler

    class Game(act: GameActivity, hand : Handler) : Runnable
    {

        private var activity: GameActivity
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
            //wtf is this syntax man?
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) //API version 30
            {
                activity.windowManager.currentWindowMetrics.bounds.height()
            } else {
                //DEPRECATED BUT WHATEVER
                val displayMetrics = DisplayMetrics()
                @Suppress("DEPRECATION")
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
                displayMetrics.heightPixels
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //ANIMATION
        val spritesheet = BitmapFactory.decodeResource(resources,R.drawable.spritesheet_)
        val rows = 2
        val cols = 4
        val frames = ArrayList<Bitmap>()

        val width: Int = spritesheet.width / cols
        val height: Int = spritesheet.height / rows
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                frames.add(Bitmap.createBitmap(spritesheet, col * width, row * height, width, height))
            }
        }

        val imageView = findViewById<ImageView>(R.id.bird)
        val animation = AnimationDrawable()
        for (frame in frames) {
            animation.addFrame(BitmapDrawable(resources, frame), 100)
        }
        animation.isOneShot = false
        imageView.setImageDrawable(animation)
        animation.start()

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

