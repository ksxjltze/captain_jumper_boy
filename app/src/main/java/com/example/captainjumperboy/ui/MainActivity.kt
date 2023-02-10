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
import kotlin.math.atan2

interface OnSensorDataChanged {
    fun onSensorDataChanged(x: Float, y: Float, z: Float)
}

class MainActivity : Activity(), SensorEventListener {

    /**
     * Sensor variables
     */
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private var sensorDataChangedListener: OnSensorDataChanged? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowsInsetsController: WindowInsetsControllerCompat =
            WindowInsetsControllerCompat(window, window.decorView)
        windowsInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        setContentView(GameView(this))

        /**
         * Set up Sensors
         */
        // 1. Initializes SensorManager by getting reference of the sensor service
        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 2. Registers sensor event callback to listen to changes in the accelerometer
        // at a set rate. SENSOR_DELAY_GAME: Gets sensors data at a rate suitable for games
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        }
        // 3
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_GAME)
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
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            //TODO Use this to control the character?
            {
            val sides = event.values[0]
            val upDown = event.values[1]

            val rotationZX = upDown * 3f
            val rotationRight = sides * 3f
            val rotationLeft = -sides * 3f
            }
            sensorDataChangedListener?.onSensorDataChanged(x, y, z)
        }
    }

    //MAY NOT BE USED if sides/updown method is simpler and works
    fun getOrientation(accelerometer: FloatArray): FloatArray {
        /**
         * the accuracy of this approach is limited as it does not take into account of the orientation of
         * the earth's magnetic field, which is needed for a more accurate calculation of the orientation.
         */
        val gravity = FloatArray(3)
        val linearAcceleration = FloatArray(3)
        val orientation = FloatArray(3)

        // Remove the gravity contribution with the high-pass filter
        gravity[0] = 0.1F * gravity[0] + (1 - 0.1F) * accelerometer[0]
        gravity[1] = 0.1F * gravity[1] + (1 - 0.1F) * accelerometer[1]
        gravity[2] = 0.1F * gravity[2] + (1 - 0.1F) * accelerometer[2]

        // Obtain the linear acceleration of the device by subtracting the gravity from the raw accelerometer readings
        linearAcceleration[0] = accelerometer[0] - gravity[0]
        linearAcceleration[1] = accelerometer[1] - gravity[1]
        linearAcceleration[2] = accelerometer[2] - gravity[2]

        // Calculate the pitch and roll angles
        orientation[1] =
            (atan2(linearAcceleration[0], linearAcceleration[2]) * 180.0 / Math.PI).toFloat()
        orientation[2] =
            (atan2(linearAcceleration[1], linearAcceleration[2]) * 180.0 / Math.PI).toFloat()

        return orientation
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO("Not yet implemented")
    }
}