package com.helpers.sandboxkotlin;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CountdownFantasyView extends LinearLayout implements Runnable {


    private AppCompatTextView mCharHighSecond;
    private AppCompatTextView mCharLowSecond;
    private AppCompatTextView mCharHighMinute;
    private AppCompatTextView mCharLowMinute;
    private AppCompatTextView mCharHighHour;
    private AppCompatTextView mCharLowHour;
    private AppCompatTextView mCharVeryHour;
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
        mCharHighSecond = (AppCompatTextView) findViewById(R.id.charHighSecond);
        mCharLowSecond = (AppCompatTextView) findViewById(R.id.charLowSecond);
        mCharHighMinute = (AppCompatTextView) findViewById(R.id.charHighMinute);
        mCharLowMinute = (AppCompatTextView) findViewById(R.id.charLowMinute);
        mCharVeryHour = (AppCompatTextView) findViewById(R.id.charVeryHighHour);
        mCharHighHour = (AppCompatTextView) findViewById(R.id.charHighHour);
        mCharLowHour = (AppCompatTextView) findViewById(R.id.charLowHour);


    }

    public void pause() {
        mPause = true;
    }

    public void resume(long getTime) {
        mPause = false;

        totalTime = getTime;

        veryHeight = (int) (getTime / 360000);
        mCharVeryHour.setText(String.valueOf(9 - veryHeight));

        getTime -= veryHeight * 360000;

        hourHeight = (int) (getTime / 36000);
        mCharHighHour.setText(String.valueOf(9 - hourHeight));

        getTime -= hourHeight * 36000;

        hourLow = (int) (getTime / 3600);
        mCharLowHour.setText(String.valueOf(9 - hourLow));

        getTime -= hourLow * 3600;

        minuteHeight = (int) (getTime / 600);
        mCharHighMinute.setText(String.valueOf(5 - minuteHeight));

        getTime -= minuteHeight * 600;

        minuteLow = (int) (getTime / 60);
        mCharLowMinute.setText(String.valueOf(9 - minuteLow));

        getTime -= minuteLow * 60;

        secHeight = (int) (getTime / 10);
        mCharHighSecond.setText(String.valueOf(5 - secHeight));

        getTime -= secHeight * 10;

        secLow = (int) getTime;
        mCharLowSecond.setText(String.valueOf(9 - secLow));

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }

    @Override
    public void run() {
        if (mPause) {
            return;
        }

        totalTime--;

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }
}