package com.helpers.sandboxkotlin;

import android.support.v4.view.ViewCompat;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class CountdownView extends LinearLayout implements Runnable {

    private static final char[] SEXAGISIMAL = new char[]{'5', '4', '3', '2', '1', '0'};
    private static final char[] DECIMAL = new char[]{'9', '8', '7', '6', '5', '4', '3', '2', '1', '0'};

    private TabDigit mCharHighSecond;
    private TabDigit mCharLowSecond;
    private TabDigit mCharHighMinute;
    private TabDigit mCharLowMinute;
    private TabDigit mCharHighHour;
    private TabDigit mCharLowHour;
    private TabDigit mCharVeryHour;
    private View mClock = this;

    private boolean mPause = true;

    private int veryHeight, hourHeight, hourLow, minuteHeight, minuteLow, secHeight, secLow;

    private long totalTime;


    public CountdownView(Context context) {
        this(context, null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        setOrientation(HORIZONTAL);
        inflate(getContext(), R.layout.clock, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCharHighSecond = (TabDigit) findViewById(R.id.charHighSecond);
        mCharLowSecond = (TabDigit) findViewById(R.id.charLowSecond);
        mCharHighMinute = (TabDigit) findViewById(R.id.charHighMinute);
        mCharLowMinute = (TabDigit) findViewById(R.id.charLowMinute);
        mCharVeryHour = (TabDigit) findViewById(R.id.charVeryHighHour);
        mCharHighHour = (TabDigit) findViewById(R.id.charHighHour);
        mCharLowHour = (TabDigit) findViewById(R.id.charLowHour);

        mCharHighSecond.setTextSize(100);
        mCharHighSecond.setChars(SEXAGISIMAL);
        mCharLowSecond.setTextSize(100);
        mCharLowSecond.setChars(DECIMAL);

        mCharHighMinute.setTextSize(100);
        mCharHighMinute.setChars(SEXAGISIMAL);
        mCharLowMinute.setTextSize(100);
        mCharLowMinute.setChars(DECIMAL);

        mCharVeryHour.setTextSize(100);
        mCharVeryHour.setChars(DECIMAL);
        mCharHighHour.setTextSize(100);
        mCharHighHour.setChars(DECIMAL);
        mCharLowHour.setTextSize(100);
        mCharLowHour.setChars(DECIMAL);
    }

    public void pause() {
        mPause = true;
        mCharVeryHour.sync();
        mCharHighSecond.sync();
        mCharLowSecond.sync();
        mCharHighMinute.sync();
        mCharLowMinute.sync();
        mCharHighHour.sync();
        mCharLowHour.sync();
    }

    public void resume(long getTime) {
        mPause = false;

        totalTime = getTime;

        veryHeight = (int) (getTime / 360000);
        mCharVeryHour.setChar(9 - veryHeight);

        getTime -= veryHeight * 360000;

        hourHeight = (int) (getTime / 36000);
        mCharHighHour.setChar(9 - hourHeight);

        getTime -= hourHeight * 36000;

        hourLow = (int) (getTime / 3600);
        mCharLowHour.setChar(9 - hourLow);

        getTime -= hourLow * 3600;

        minuteHeight = (int) (getTime / 600);
        mCharHighMinute.setChar(5 - minuteHeight);

        getTime -= minuteHeight * 600;

        minuteLow = (int) (getTime / 60);
        mCharLowMinute.setChar(9 - minuteLow);

        getTime -= minuteLow * 60;

        secHeight = (int) (getTime / 10);
        mCharHighSecond.setChar(5 - secHeight);

        getTime -= secHeight * 10;

        secLow = (int) getTime;
        mCharLowSecond.setChar(9 - secLow);

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }

    @Override
    public void run() {
        if (mPause) {
            return;
        }
        mCharLowSecond.start();

        if (totalTime % 10 == 0) {
            mCharHighSecond.start();
        }
        if (totalTime % 60 == 0) {
            mCharLowMinute.start();
        }
        if (totalTime % 600 == 0) {
            mCharHighMinute.start();
        }
        if (totalTime % 3600 == 0) {
            mCharLowHour.start();
        }
        if (totalTime % 36000 == 0) {
            mCharHighHour.start();
        }

        if (totalTime % 360000 == 0) {
            mCharVeryHour.start();
        }

        totalTime--;

        ViewCompat.postOnAnimationDelayed(mClock, this, 1000);
    }
}