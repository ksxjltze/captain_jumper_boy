package com.example.captainjumperboy.ui


import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.media.MediaPlayer
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.WindowManager
import android.view.WindowMetrics
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Assets
import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.game.scenes.CaptainJumperBoy

/**
 * This class will inherit SurfaceView in order to draw our Game on a Canvas and
 * not layouts. The SurfaceView contains a SurfaceHolder and we override callbacks to
 * do things when surface has been created/destroyed/etc.
 */
class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback {

    /** Creates a static GameThread that will run our game logic **/
    companion object{
        private lateinit var thread: GameThread
    }
    /** Creates a CaptainJumperBoy GameScene (contains the main logic of the scene) **/
    private var scene = CaptainJumperBoy(this)
    val mediaplayer = MediaPlayer.create(Assets.view.context, R.raw.bgm)
    init { //constructor
        holder.addCallback(this) //enables callback events to intercept events
        focusable = FOCUSABLE
    }

    /**
     * Update function of the main GameView to perform calculations
     */
    fun update(){
        scene.update()
        mediaplayer.isLooping = true
        mediaplayer.start()
        mediaplayer.setVolume(1f,1f)
    }

    /**
     * Override drawing SurfaceView using graphics operations
     * @param canvas The graphics canvas to draw to
     */
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (canvas != null) {
            canvas.drawColor(Color.WHITE)
            scene.draw(canvas)
        }
    }

    //temp hack
    private var started = false
    /**
     * Override on surfaceCreated callback to initialise things
     * @param holder The SurfaceHolder that holds the canvas
     */

    override fun surfaceCreated(holder: SurfaceHolder) {

        val windowSize = getWindowSize()
        Log.d("GameView: ", "Width: ${windowSize.x}, Height: ${windowSize.y}")

        //Creates a GameThread
        thread = GameThread(holder, this)

        //@todo maybe should move elsewhere
        run {
            if (!started){
                scene.startEarly()
                scene.start()
                started = true
            }
        }
        thread.setRunning(true) //sets boolean to indicate that the GameThread should start running
        thread.start() //actually starts GameThread once canvas is created
        //When a new thread is started, its run method is called
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    /**
     * Overrides on surfaceDestroyed callback to stop the GameThread when surface is gone
     * @param holder The SurfaceHolder that holds the canvas
     */
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        while (true) { //may take a few loops to successfully stop the thread
            try {
                thread.setRunning(false) //stop the thread
                thread.join() //wait for the thread to finish
                break
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
    fun getWindowSize() : Point
    {
        //MODERN WAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics: WindowMetrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics
            val screenWidth = metrics.bounds.width()
            val screenHeight = metrics.bounds.height()
            return Point(screenWidth, screenHeight)
        }
        else { //DEPRECATED WAY for API < 30
            val display = context.getSystemService(WindowManager::class.java).defaultDisplay
            val metrics = if (display != null) {
                DisplayMetrics().also { display.getRealMetrics(it) }
            } else {
                Resources.getSystem().displayMetrics
            }
            return Point(metrics.widthPixels, metrics.heightPixels)
        }
    }
}
