package com.example.ousatov.carousel.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ousatov.carousel.data.Item;
import com.example.ousatov.carousel.R;


public class ItemView extends FrameLayout {
    private TextView tvId;
    private ImageView image;

    private int activePointerId;
    private float initialXPress;
    private float initialYPress;

    private float initialX;
    private float initialY;

    private SwipeCallback callback;

    public ItemView(Context context) {
        super(context);
        initViews();
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.item_layout, this);
        tvId = (TextView) findViewById(R.id.textMarker);
        image = (ImageView) findViewById(R.id.item_image);
    }

    public void setItem(Item item) {
        image.setImageDrawable(new ColorDrawable(item.getColor()));
        tvId.setText(item.getData());
    }

    public void setImageWhiteFilter(int alpha) {
        image.setColorFilter(Color.argb(alpha, 255, 255, 255));
    }

    public void setSwipeCallback(SwipeCallback c) {
        callback = c;
    }

    private boolean click = true;
    private final float centerDisplayX = getContext().getResources().getDisplayMetrics().widthPixels / 2;
    private final float centerDisplayY = getContext().getResources().getDisplayMetrics().heightPixels / 2;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (callback == null) {
            return false;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                click = true;

                float x;
                float y;

                clearAnimation();

                activePointerId = event.getPointerId(0);

                x = event.getX();
                y = event.getY();

                initialXPress = x;
                initialYPress = y;
                initialX = getX();
                initialY = getY();

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = event.findPointerIndex(activePointerId);

                final float xMove = event.getX(pointerIndex);
                final float yMove = event.getY(pointerIndex);

                final float dx = xMove - initialXPress;
                final float dy = yMove - initialYPress;

                float posX = getX() + dx;
                float posY = getY() + dy;

                if (Math.abs(dx + dy) > 5) click = false;

                setX(posX);
                setY(posY);

                float centerViewX = getX() + (float) getWidth() / 2;
                float centerViewY = getY() + (float) getHeight() / 2;

                setRotation(getAngle(centerViewX, centerDisplayX, 30));
                if (centerViewY >= centerDisplayY) {
                    setRotationX(getAngle(centerViewY, centerDisplayY, 40));
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                checkCardForEvent();
                setRotation(0);
                setRotationX(0);
                if (click) performClick();

                break;
            }
            default:
                return false;
        }
        return true;
    }

    private float getAngle(float centerView, float centerDisplay, float angle) {
        float v = centerView - centerDisplay;
        float v1 = angle / centerDisplay;
        return v * v1;
    }

    public void checkCardForEvent() {
        if (cardBeyondLeftBorder()) {
            if (callback != null) callback.onSwipeLeft();
        } else if (cardBeyondRightBorder()) {
            if (callback != null) callback.onSwipeRight();
        } else {
            resetCardPosition();
        }
    }

    private boolean cardBeyondLeftBorder() {
        return (initialX - getX() > 150);
    }

    private boolean cardBeyondRightBorder() {
        return (getX() - initialX > 150);
    }

    private ViewPropertyAnimator resetCardPosition() {
        return animate()
                .setDuration(200)
                .setInterpolator(new OvershootInterpolator(1.5f))
                .x(initialX)
                .y(initialY)
                .rotation(0);
    }

    public interface SwipeCallback {
        void onSwipeRight();

        void onSwipeLeft();
    }
}
