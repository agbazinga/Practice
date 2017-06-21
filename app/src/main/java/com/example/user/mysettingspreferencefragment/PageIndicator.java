package com.example.user.mysettingspreferencefragment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by a.gogia on 8/17/2016.
 */
public class PageIndicator extends View {
    int currentPageColor = 0xfffafafa;
    int otherPageColor = 0x22000000;
    int numPages = 2;
    int currentPage = 0;
    float positionOffset = 0.0f;

    float size = 4.0f;
    float gap = 20.0f;
    Paint mPaint;


    public PageIndicator(Context context) {
        this(context, null);
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Point center = getCenter();
        float startOffSet = getStartingPoint(center.x);
        mPaint.setColor(otherPageColor);

        for (int i = 0; i < numPages; i++) {
            canvas.drawCircle(startOffSet + gap * i, center.y, size, mPaint);
        }

        mPaint.setColor(currentPageColor);
        canvas.drawCircle(startOffSet + (currentPage + positionOffset) * gap, center.y, size, mPaint);
    }

    public void setPages(int numPages) {
        this.numPages = numPages;
    }

    public void onPageSelected(int position) {
        currentPage = position;
        invalidate();
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentPage = position;
        this.positionOffset = positionOffset;
        invalidate();
    }

    private void init(Context context) {
        mPaint = new Paint();
        size *= context.getResources().getDisplayMetrics().density;
        gap *= context.getResources().getDisplayMetrics().density;
    }

    private Point getCenter() {
        return new Point((getRight() - getLeft()) / 2, (getBottom() - getTop()) / 2);
    }

    private float getStartingPoint(float centerX) {
        float factor;
        factor = numPages % 2 == 0 ? (numPages - 1) * 0.5f : (numPages - 1) * 1.0f;
        return centerX - (gap * factor);
    }
}
