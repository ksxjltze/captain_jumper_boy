package com.example.captainjumperboy.ui

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
interface OnSensorDataChanged {
    fun onSensorDataChanged(x: Float,y:Float,z:Float)
}
class MainActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer : Sensor
    private var sensorDataChangedListener: OnSensorDataChanged? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowsInsetsController : WindowInsetsControllerCompat = WindowInsetsControllerCompat(window, window.decorView)
        windowsInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContentView(GameView(this))
        //Set up sensors
        // 1. Initializes SensorManager by getting reference of the sensor service
        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 2. Registers sensor event callback to listen to changes in the accelerometer
        // at a set rate. SENSOR_DELAY_GAME: Gets sensors data at a rate suitable for games
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_GAME)
        }
    }
    fun setSensorDataChangedListener(listener: OnSensorDataChanged?) {
        this.sensorDataChangedListener = listener
    }
    override fun onResume() {
        super.onResume()
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y= event.values[1]
                val z = event.values[2]
                sensorDataChangedListener?.onSensorDataChanged(x,y,z)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
       // TODO("Not yet implemented")
    }
}