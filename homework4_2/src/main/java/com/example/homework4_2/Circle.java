package com.example.homework4_2;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Circle extends View{

    private int centerX;
    private int centerY;

    private Paint paint0 = new Paint();
    private Paint paint90 = new Paint();
    private Paint paint180 = new Paint();
    private Paint paint270 = new Paint();
    private Paint centerPaint = new Paint();

    private final int smallRadius = 100;
    private final int bigRadius = 300;


    public Circle(Context context) {
        super(context);
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Circle);
        paint0.setColor(Color.YELLOW);
        paint90.setColor(Color.BLUE);
        paint180.setColor(Color.RED);
        paint270.setColor(Color.GREEN);
        centerPaint.setColor(Color.LTGRAY);

        typedArray.recycle();
    }

    interface Listener{
        void onEvent(float x, float y, Paint paint);
    }

    private Listener listener;

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2;
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(centerX - 300, centerY - 300, centerX + 300, centerY + 300, 0, 90, true, paint0);
        canvas.drawArc(centerX - 300, centerY - 300, centerX + 300, centerY + 300, 90, 90, true, paint90);
        canvas.drawArc(centerX - 300, centerY - 300, centerX + 300, centerY + 300, 180, 90, true, paint180);
        canvas.drawArc(centerX - 300, centerY - 300, centerX + 300, centerY + 300, 270, 90, true, paint270);
        canvas.drawCircle(centerX, centerY, smallRadius, centerPaint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            colorChange(event.getX(), event.getY());
            Intent intent = new Intent();
            intent.putExtra("X", event.getX());
            intent.putExtra("Y", event.getY());
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void colorChange(float touchX, float touchY) {
        if (checkInstance(touchX, touchY, centerX, centerY, smallRadius)) {
            Random rand = new Random();
            paint0.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            paint90.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            paint180.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            paint270.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            listener.onEvent(touchX, touchY, centerPaint);

        } else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX > centerX && touchY < centerY){
            Random rand = new Random();
            paint270.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            listener.onEvent(touchX, touchY, paint270);
        }else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX > centerX && touchY > centerY){
            Random rand = new Random();
            paint0.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            listener.onEvent(touchX, touchY, paint0);
        }else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX < centerX && touchY < centerY){
            Random rand = new Random();
            paint180.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            listener.onEvent(touchX, touchY, paint180);
        }else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX < centerX && touchY > centerY){
            Random rand = new Random();
            paint90.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            listener.onEvent(touchX, touchY, paint90);
        }
    }

    private boolean checkInstance(float x, float y, float centerX, float centerY, float radius) {
        return Math.abs(x - centerX) * Math.abs(x - centerX) + Math.abs(y - centerY) * Math.abs(y - centerY) <= radius * radius;
    }



}
