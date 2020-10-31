package com.example.homework4_2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class Circle extends View{

    private int centerX;
    private int centerY;

    private Paint paint0 = new Paint();
    private Paint paint90 = new Paint();
    private Paint paint180 = new Paint();
    private Paint paint270 = new Paint();
    private Paint centerPaint = new Paint();
    private Random rand = new Random();

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
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void colorChange(float touchX, float touchY) {
        if (checkInstance(touchX, touchY, centerX, centerY, smallRadius)) {
            centerClick(touchX,touchY);
        } else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX > centerX && touchY < centerY){
            click270(touchX,touchY);
        }else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX > centerX && touchY > centerY){
            click0(touchX,touchY);
        }else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX < centerX && touchY < centerY){
            click180(touchX,touchY);
        }else if (checkInstance(touchX, touchY, centerX, centerY, bigRadius) && touchX < centerX && touchY > centerY){
            click90(touchX,touchY);
        }
    }

    private boolean checkInstance(float x, float y, float centerX, float centerY, float radius) {
        return Math.abs(x - centerX) * Math.abs(x - centerX) + Math.abs(y - centerY) * Math.abs(y - centerY) <= radius * radius;
    }

    private void centerClick(float touchX, float touchY){
        changePaint(paint0);
        changePaint(paint90);
        changePaint(paint180);
        changePaint(paint270);
        listener.onEvent(touchX, touchY, centerPaint);
    }

    private void click0(float touchX, float touchY){
        changePaint(paint0);
        if(listener != null) {
            listener.onEvent(touchX, touchY, paint0);
        }
    }

    private void click90(float touchX, float touchY){
        changePaint(paint90);
        if(listener != null) {
            listener.onEvent(touchX, touchY, paint90);
        }
    }

    private void click180(float touchX, float touchY){
        changePaint(paint180);
        if(listener != null) {
            listener.onEvent(touchX, touchY, paint180);
        }
    }

    private void click270(float touchX, float touchY){
        changePaint(paint270);
        if(listener != null) {
            listener.onEvent(touchX, touchY, paint270);
        }
    }

    private void changePaint(Paint paint){
        paint.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

}
