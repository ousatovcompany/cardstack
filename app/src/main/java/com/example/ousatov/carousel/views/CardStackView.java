package com.example.ousatov.carousel.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Adapter;
import android.widget.FrameLayout;

import java.util.LinkedList;
import java.util.ListIterator;

public class CardStackView extends FrameLayout {

    private Adapter adapter;
    private View topCard;
    private float topCardY;
    private float topCardScaleX;
    private float topCardScaleY;
    private LinkedList<View> linkedStack;
    private final int DURATION_ANIMATION_TOPCARD = 150;
    private final int ROTATION_ANIMATION_TOPCARD = 30;

    public CardStackView(Context context) {
        super(context);
    }

    public CardStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(Adapter a) {
        adapter = a;
    }

    public void draw() {
        removeAllViews();
        int size = adapter.getCount();
        linkedStack = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            topCard = adapter.getView(i, null, this);
            linkedStack.add(topCard);
            addView(topCard);
        }
        topCardScaleX = topCard.getScaleX();
        topCardScaleY = topCard.getScaleY();
        topCardY = topCard.getY();
    }

    public ViewPropertyAnimator animateTopcardRight() {
        return topCard.animate()
                .setDuration(DURATION_ANIMATION_TOPCARD)
                .x(topCard.getWidth() * 2)
                .y(0)
                .rotation(ROTATION_ANIMATION_TOPCARD);
    }

    public ViewPropertyAnimator animateTopcardLeft() {
        return topCard.animate()
                .setDuration(DURATION_ANIMATION_TOPCARD)
                .x(-(topCard.getWidth() * 2))
                .y(0)
                .rotation(-ROTATION_ANIMATION_TOPCARD);
    }


    public ViewPropertyAnimator animateStack() {
        if (linkedStack.size() <= 1) {
            return null;
        }
        ViewPropertyAnimator vpa = null;

        ListIterator<View> stackIterator = linkedStack.listIterator();

        View previousView = stackIterator.next();

        while (stackIterator.hasNext()) {
            View currentView = stackIterator.next();
            if (!stackIterator.hasNext()) {
                currentView = new View(getContext());
                currentView.setScaleX(topCardScaleX);
                currentView.setScaleY(topCardScaleY);
                currentView.setY(topCardY);
            }
            vpa = previousView.animate()
                    .scaleX(currentView.getScaleX())
                    .scaleY(currentView.getScaleY())
                    .y(currentView.getY())
                    .setDuration(DURATION_ANIMATION_TOPCARD);
            previousView = currentView;
        }
        return vpa;
    }
}
