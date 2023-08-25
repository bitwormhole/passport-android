package com.bitwormhole.passport;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class VerifyNineDotsActivity extends Activity {

    private SurfaceView mSurfaceView;
    private SurfaceHolder.Callback mSurfaceCallback;
    private SurfaceHolder mSurfaceHolder;

    private Vibrator mVibrator;

    private List<myDot> mListByIndex;
    private List<myDot> mListByOrder;
    private myDot mCurrentPos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_verify_nine_dots);

        mSurfaceCallback = new mySurfaceCallback();
        mSurfaceView = this.findViewById(R.id.surface_9dots);
        mListByIndex = new ArrayList<>();
        mListByOrder = new ArrayList<>();

        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mSurfaceView.setOnTouchListener(new myTouchListener());

        initDots();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSurfaceView.getHolder().addCallback(this.mSurfaceCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
    }

    private class myDot {
        int x;
        int y;
        int r; // 作用半径
        int index; // 排版顺序
        int order; // 输入顺序

        void move(int deltaX, int deltaY) {
            x += deltaX;
            y += deltaY;
        }
    }

    private class mySurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
            mSurfaceHolder = surfaceHolder;
            redraw(surfaceHolder);
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int fmt, int w, int h) {
            mSurfaceHolder = surfaceHolder;
            redraw(surfaceHolder);
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            mSurfaceHolder = null;
        }
    }

    private class myTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_MOVE) {
                onMove(view, motionEvent);
            } else if (action == MotionEvent.ACTION_UP) {
                onUp(view, motionEvent);
            } else if (action == MotionEvent.ACTION_DOWN) {
                onDown(view, motionEvent);
            }
            redraw(null);
            return true;
        }

        void onUp(View view, MotionEvent motionEvent) {
            resetDots();
        }

        void onDown(View view, MotionEvent motionEvent) {
            resetDots();
            mCurrentPos = new myDot();
            this.onMove(view, motionEvent);
        }

        void onMove(View view, MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            mCurrentPos.x = x;
            mCurrentPos.y = y;
            myDot target = findHit(x, y);
            handleHit(target);
        }
    }

    private myDot findHit(int x, int y) {
        for (myDot item : mListByIndex) {
            float dx = item.x - x;
            float dy = item.y - y;
            float r = item.r;
            if (((dx * dx) + (dy * dy)) < (r * r)) {
                return item;
            }
        }
        return null;
    }

    private void handleHit(myDot d) {
        if (d == null) {
            return;
        }
        if (d.order > 0) {
            return;
        }
        List<myDot> list = mListByOrder;
        list.add(d);
        d.order = list.size();
        shake();
    }

    private void shake() {
        VibrationEffect effect = VibrationEffect.createOneShot(50, 80);
        mVibrator.vibrate(effect);
    }

    private void redraw(SurfaceHolder h) {
        if (h == null) {
            h = mSurfaceHolder;
        }
        if (h == null) {
            return;
        }
        Canvas c = h.lockCanvas();
        if (c == null) {
            return;
        }
        try {
            int svw = mSurfaceView.getWidth();
            int svh = mSurfaceView.getHeight();
            ondraw(c, svw, svh);
        } finally {
            h.unlockCanvasAndPost(c);
        }
    }

    private void ondraw(Canvas c, int w, int h) {

        this.layoutDots(w, h);

        Paint p = new Paint();
        Paint p2 = new Paint();

        p2.setStrokeWidth(3);
        p2.setColor(Color.GRAY);
        p2.setStyle(Paint.Style.STROKE);

        // background
        p.setColor(Color.DKGRAY);
        c.drawRect(0, 0, w, h, p);

        // dots
        p.setColor(Color.WHITE);
        mListByIndex.forEach((item) -> {
            int r = 10;
            if (item.order > 0) {
                c.drawCircle(item.x, item.y, r * 2, p2);
            }
            c.drawCircle(item.x, item.y, r, p);
        });

        // lines
        p.setStrokeWidth(5);
        myDot prev = null;
        for (myDot item : mListByOrder) {
            if (prev != null) {
                c.drawLine(prev.x, prev.y, item.x, item.y, p);
            }
            prev = item;
        }
        myDot current = mCurrentPos;
        if (current != null && prev != null) {
            c.drawLine(prev.x, prev.y, current.x, current.y, p);
        }
    }

    private void initDots() {
        List<myDot> list = new ArrayList<>();
        final int count = 9;
        for (int i = 0; i < count; i++) {
            myDot d = new myDot();
            d.index = i;
            list.add(d);
        }
        mListByIndex = list;
    }

    private void resetDots() {
        mListByOrder.clear();
        mListByIndex.forEach((item) -> {
            item.order = 0;
        });
    }

    private void layoutDots(int w, int h) {

        int centerX = w / 2;
        int centerY = h / 2;
        int distance = Math.min(w, h) / 3;
        int r = distance / 5;

        List<myDot> list = mListByIndex;
        list.forEach((item) -> {
            item.x = centerX;
            item.y = centerY;
            item.r = r;
        });

        // layout by index:
        // 0-1-2
        // 3-4-5
        // 6-7-8
        list.get(0).move(-distance, -distance);
        list.get(1).move(0, -distance);
        list.get(2).move(distance, -distance);

        list.get(3).move(-distance, 0);
        list.get(4).move(0, 0);
        list.get(5).move(distance, 0);

        list.get(6).move(-distance, distance);
        list.get(7).move(0, distance);
        list.get(8).move(distance, distance);
    }

}
