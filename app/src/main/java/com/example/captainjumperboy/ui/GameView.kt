package com.example.captainjumperboy.ui


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.example.captainjumperboy.R
import com.example.captainjumperboy.engine.Assets
import com.example.captainjumperboy.engine.GameThread
import com.example.captainjumperboy.game.scenes.CaptainJumperBoy

class GameView(context : Context) : SurfaceView(context), SurfaceHolder.Callback,
    SensorEventListener {
    companion object{
        private lateinit var thread: GameThread
        private lateinit var sensorManager: SensorManager
        private lateinit var accelerometer : Sensor
    }

    private var scene = CaptainJumperBoy(this)
    val mediaplayer = MediaPlayer.create(Assets.view.context, R.raw.bgm)
    init {
        holder.addCallback(this) //enables callback events
        focusable = FOCUSABLE

        //Set up sensors
        // 1. Initializes SensorManager by getting reference of the sensor service
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 2. Registers sensor event callback to listen to changes in the accelerometer
        // at a set rate. SENSOR_DELAY_GAME: Gets sensors data at a rate suitable for games
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            accelerometer -> sensorManager.registerListener(this, accelerometer,
            SensorManager.SENSOR_DELAY_GAME)
        }
    }

    /**
     * Android system calls onSensorChanged every time there’s a new sensor event.
     * @param event SensorEvent parameter gives a set of array of 3 values along x,y,z.
     */
    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("GameView", "onSensorChanged()")
        //TODO "Not yet implemented"
    }

    /**
     * Android system only calls onAccuracyChanged when there’s a change in accuracy.
     * @param sensor whose accuracy changed
     * @param accuracy the new accuracy value
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("GameView", "onAccuracyChanged()")
        //TODO "Not Needed for us"
    }

    /**
     * Update function of the main GameView
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


    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = GameThread(holder, this)

        //@todo maybe should move elsewhere
        run {
            if (!started){
                scene.startEarly()
                scene.start()
                started = true
            }


        }

        thread.setRunning(true)
        thread.start()

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        while (true) { //may take a few loops to truly stop the thread
            try {
                thread.setRunning(false)
                thread.join()
                break
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

}
