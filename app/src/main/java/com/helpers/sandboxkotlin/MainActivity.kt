package com.helpers.sandboxkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TotalTime = 60L

        clock.resume(TotalTime)
        clocl_fantasy.resume(TotalTime)
//        analog_clock.setTime(System.currentTimeMillis() + 5556)
//        analog_clock.setAutoUpdate(true)
    }
}
