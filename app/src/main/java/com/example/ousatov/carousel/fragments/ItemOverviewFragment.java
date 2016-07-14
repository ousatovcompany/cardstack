package com.example.ousatov.carousel.fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ousatov.carousel.data.Item;
import com.example.ousatov.carousel.views.ItemView;
import com.example.ousatov.carousel.ItemsAdapter;
import com.example.ousatov.carousel.R;
import com.example.ousatov.carousel.views.CardStackView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemOverviewFragment extends Fragment {

    private List<Item> list;
    private CardStackView rootStack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_items, container, false);
        Log.d("US", "onCreateView ItemOverviewFragment");
        initViews(parent);

        fillViews();
        return parent;
    }

    private void initViews(View parent) {
        rootStack = (CardStackView) parent.findViewById(R.id.items_stack);
    }
    private void fillViews() {
        loadItems();

        Collections.sort(list);
        list = prepareOrderElementOfListForAdapter(list);

        ItemsAdapter iadapter = new ItemsAdapter(list, getContext());

        ItemView.SwipeCallback swipeCallback = new ItemView.SwipeCallback() {
            @Override
            public void onSwipeRight() {
                Item obj = list.get(list.size() - 1);
                list.remove(list.size() - 1);
                list.add(0, obj);

                rootStack.animateTopcardRight().setListener(animationListener);
            }

            @Override
            public void onSwipeLeft() {
                Item obj = list.get(list.size() - 1);
                list.remove(list.size() - 1);
                list.add(0, obj);

                rootStack.animateTopcardLeft().setListener(animationListener);
            }
        };
        iadapter.setOnSwipeListener(swipeCallback);

        rootStack.setAdapter(iadapter);
        if (!list.isEmpty()) {
            rootStack.draw();
        }
    }

    private void loadItems() {
        //Stub
        list = new ArrayList<>();
        list.add(new Item("555", 5, new ColorDrawable(Color.BLACK)));
        list.add(new Item("111", 1, new ColorDrawable(Color.BLACK)));
        list.add(new Item("444", 4, new ColorDrawable(Color.BLACK)));
        list.add(new Item("333", 3, new ColorDrawable(Color.BLACK)));
        list.add(new Item("222", 2, new ColorDrawable(Color.BLACK)));
        list.add(new Item("666", 6, new ColorDrawable(Color.BLACK)));
    }

    private List<Item> prepareOrderElementOfListForAdapter(List<Item> vouchers) {
        ArrayList<Item> result = new ArrayList<>();
        if (list.size() > ItemsAdapter.MAX_NUMBER_VOUCHERS) {
            for (int i = ItemsAdapter.MAX_NUMBER_VOUCHERS - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
            for (int i = list.size() - 1; i >= ItemsAdapter.MAX_NUMBER_VOUCHERS; i--) {
                result.add(list.get(i));
            }
        } else {
            for (int i = list.size() - 1; i >= 0; i--) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            rootStack.animateStack().setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rootStack.draw();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    };

}
