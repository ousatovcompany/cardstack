package com.example.ousatov.carousel.views;

import android.content.Context;
import android.graphics.Color;
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
        image.setImageDrawable(item.getColor());
        tvId.setText(item.getData());
    }

//    public void setActivationEnd() {
//        image.setColorFilter(Color.argb(200, 255, 255, 255));
//        tvExpDate.setTextColor(getContext().getResources().getColor(R.color.activate_end_text_color));
//    }

    public void setImageWhiteFilter(int alpha) {
        image.setColorFilter(Color.argb(alpha, 255, 255, 255));
    }

    public void setSwipeCallback(SwipeCallback c) {
        callback = c;
    }

    private boolean click = true;

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

                break;
            }

            case MotionEvent.ACTION_UP: {
                checkCardForEvent();

                if (click) performClick();

                break;
            }
            default:
                return false;
        }
        return true;
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
