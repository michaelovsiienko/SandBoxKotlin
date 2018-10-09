package com.helpers.sandboxkotlin

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.joda.time.Period


class MainActivity : AppCompatActivity(), Runnable {

    var totalTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        totalTime = 60L

        clock.resume(totalTime)
        clocl_fantasy.resume(totalTime)

        clocks.scaleX = -1f

        totalTime *= 1000

        mCountDownTimer.start(totalTime)
        mHourGlassView.start(totalTime)
        Period(totalTime).let {
            tvTime.text = getString(R.string.time_format, toTripleViewTime(it.hours),
                    toDoubleViewTime(0),
                    toDoubleViewTime(it.seconds))
        }



        ViewCompat.postOnAnimationDelayed(clocks, this, 1000)
    }

    override fun run() {
        totalTime -= 1000
        Period(totalTime)?.let {
            clocks.animateToTime(it.minutes, it.seconds)

            tvTime.text = getString(R.string.time_format, toTripleViewTime(it.hours),
                    toDoubleViewTime(it.minutes),
                    toDoubleViewTime(it.seconds))
        }
        ViewCompat.postOnAnimationDelayed(clocks, this, 1000)
        clocks.visibility = View.VISIBLE
    }

    private fun toDoubleViewTime(time: Int): String {
        return String.format("%02d", time)
    }

    private fun toTripleViewTime(time: Int): String {
        return String.format("%03d", time)
    }
}
