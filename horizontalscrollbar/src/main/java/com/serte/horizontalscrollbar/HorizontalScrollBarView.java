package com.serte.horizontalscrollbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by s.venturini on 26/10/2017.
 */

public class HorizontalScrollBarView extends View {

    private float maxScroll = 0f;
    private float xcurrentScroll = 0f;
    private int countItem = 0;
    private float pixRound;
    private Paint paint;
    private Path path;
    private boolean enableAnim = false;
    private int staticAlpha;                //from 0 to 255
    private int scrollingAlpha;             //from 0 to 255
    private int toScrollingAlphaDuration;   //milliseconds
    private int toStaticAlphaDuration;      //milliseconds
    private ObjectAnimator animation;

    public HorizontalScrollBarView(Context context) {
        this(context, null);
    }

    public HorizontalScrollBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.HorizontalScrollBarView, 0, 0);
        pixRound = attributes.getDimension(R.styleable.HorizontalScrollBarView_roundDimen, 2f);
        int scrollColor = (attributes.getColor(R.styleable.HorizontalScrollBarView_scrollColor, Color.RED));

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        paint.setColor(scrollColor);

        enableAnim = (attributes.getBoolean(R.styleable.HorizontalScrollBarView_enableAnim, false));
        if (enableAnim) {
            float staticAlpha = attributes.getFloat(R.styleable.HorizontalScrollBarView_staticAlpha, 0.3f);
            float scrollingAlpha = attributes.getFloat(R.styleable.HorizontalScrollBarView_scrollingAlpha, 0.75f);
            if (staticAlpha > 1) {
                staticAlpha  = 1f;

            } else if (staticAlpha < 0) {
                staticAlpha = 0f;

            }
            if (scrollingAlpha > 1) {
                scrollingAlpha  = 1f;

            } else if (scrollingAlpha < 0) {
                scrollingAlpha = 0f;

            }
            this.staticAlpha = (int)(staticAlpha * 255);
            this.scrollingAlpha = (int)(scrollingAlpha * 255);
            paint.setAlpha(this.staticAlpha);

            toStaticAlphaDuration = attributes.getInteger(R.styleable.HorizontalScrollBarView_animToStaticDuration, 1500);
            toScrollingAlphaDuration = attributes.getInteger(R.styleable.HorizontalScrollBarView_animToScrollingDuration, 40);
        }
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = (float) getWidth();
        float height = (float) getHeight();
        float xv = 0f;
        if (countItem != 0) {
            width /= countItem;

        }
        if (maxScroll != 0f) {
            xv = ((xcurrentScroll * (getWidth() - width)) / maxScroll);

        }
        path.reset();
        path.moveTo(xv + pixRound, 0);
        path.lineTo(xv + width - pixRound, 0);
        path.quadTo(xv + width, 0, xv + width, pixRound);
        path.lineTo(xv + width, height - pixRound);
        path.quadTo(xv + width, height, xv + width - pixRound, height);
        path.lineTo(xv + pixRound, height);
        path.quadTo(xv, height, xv, height - pixRound);
        path.lineTo(xv, pixRound);
        path.quadTo(xv, 0, xv + pixRound, 0);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     *
     * @param value
     */
    public void setAlphaScroll(int value) {
        paint.setAlpha(value);
        invalidate();
    }

    /**
     *
     * @param recyclerView
     */
    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                countItem = recyclerView.getAdapter().getItemCount();
                maxScroll = recyclerView.computeHorizontalScrollRange() - recyclerView.computeHorizontalScrollExtent();
                xcurrentScroll = recyclerView.computeHorizontalScrollOffset();
                invalidate();
            }
        });

        if (enableAnim) {
            recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    if (MotionEvent.ACTION_DOWN == e.getAction()) {
                        setAlphaWithAnim(toScrollingAlphaDuration, scrollingAlpha, null);

                    } else if (MotionEvent.ACTION_UP == e.getAction()) {
                        setAlphaWithAnim(toStaticAlphaDuration, staticAlpha, null);

                    } else if (MotionEvent.ACTION_CANCEL == e.getAction()) {
                        setAlphaWithAnim(toStaticAlphaDuration, staticAlpha, null);

                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            });
        }
    }

    /**
     *
     * @param duration
     * @param toAlpha
     * @param listener
     */
    private void setAlphaWithAnim(long duration, int toAlpha, Animator.AnimatorListener listener) {
        if (animation != null) {
            animation.end();
            animation.cancel();

        }
        animation = ObjectAnimator.ofInt(this, "alphaScroll", paint.getAlpha(), toAlpha).setDuration(duration);
        if (listener != null) {
            animation.addListener(listener);

        }
        animation.start();
    }
}
