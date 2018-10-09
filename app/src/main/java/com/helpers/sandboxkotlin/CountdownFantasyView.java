package com.helpers.sandboxkotlin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.joda.time.Period;

@SuppressLint("DefaultLocale")
public class CountdownFantasyView extends LinearLayout implements Runnable {

    private AppCompatTextView mFantasyTime;
    private View mClock = this;

    private boolean mPause = true;

    private int veryHeight, hourHeight, hourLow, minuteHeight, minuteLow, secHeight, secLow;

    private long totalTime;


    public CountdownFantasyView(Context context) {
        this(context, null);
    }

    public CountdownFantasyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownFantasyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CountdownFantasyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.clock_fantasy, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFantasyTime = (AppCompatTextView) findViewById(R.id.tvTimer);
    }

    public void pause() {
        mPause = true;
    }

    public void resume(long getTime) {
        mPause = false;

        totalTime = getTime * 1000;
        Period period = new Period(totalTime);
        if (getContext() != null) {
            mFantasyTime.setText(getContext().getString(R.string.time_format, toTripleViewTime(period.getHours()),
                    toDoubleViewTime(period.getMinutes()),
                    toDoubleViewTime(period.getSeconds())));
        }
        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }

    private String toDoubleViewTime(int time) {
        return String.format("%02d", time);
    }

    private String toTripleViewTime(int time) {
        return String.format("%03d", time);
    }

    @Override
    public void run() {
        if (mPause) {
            return;
        }
        totalTime-= 1000;

        Period period = new Period(totalTime);
        if (getContext() != null) {
            mFantasyTime.setText(getContext().getString(R.string.time_format, toTripleViewTime(period.getHours()),
                    toDoubleViewTime(period.getMinutes()),
                    toDoubleViewTime(period.getSeconds())));
        }

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }
}