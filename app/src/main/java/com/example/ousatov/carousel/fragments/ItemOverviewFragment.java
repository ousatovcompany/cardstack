package com.example.ousatov.carousel.fragments;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.ousatov.carousel.ItemsAdapter;
import com.example.ousatov.carousel.R;
import com.example.ousatov.carousel.data.Item;
import com.example.ousatov.carousel.utils.DialogAlertUtils;
import com.example.ousatov.carousel.views.CardStackView;
import com.example.ousatov.carousel.views.ItemView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemOverviewFragment extends Fragment {

    private List<Item> list;
    private ItemsAdapter iadapter;
    private CardStackView rootStack;
    private TextView addBtn;
    private TextView deleteBtn;
    private int currentItem = 0;
    private TextView itemsInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_items, container, false);
        currentItem = 1;
        initViews(parent);

        fillViews();
        return parent;
    }

    private void initViews(View parent) {
        rootStack = (CardStackView) parent.findViewById(R.id.items_stack);
        itemsInfo = (TextView) parent.findViewById(R.id.itemsInfo);
        addBtn = (TextView) parent.findViewById(R.id.addTv);
        deleteBtn = (TextView) parent.findViewById(R.id.deleteTv);
        addBtn.setOnClickListener(clickListener);
        deleteBtn.setOnClickListener(clickListener);
    }

    private void fillViews() {
        loadItems();

        Collections.sort(list);
        list = prepareOrderElementOfListForAdapter();

        iadapter = new ItemsAdapter(list, getContext());

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
            setItemsInfo();
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

    private List<Item> prepareOrderElementOfListForAdapter() {
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
                    updateCurrentItem();
                    setItemsInfo();
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


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.addTv:
                    break;
                case R.id.deleteTv:
                    if (list.isEmpty()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), R.string.str_nothing_delete, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DialogAlertUtils.showDeleteItemAlert(getActivity(), new MaterialDialog.ButtonCallback() {
                                    @Override
                                    public void onPositive(MaterialDialog dialog) {
                                        super.onPositive(dialog);
                                        if (list.size() <= ItemsAdapter.MAX_NUMBER_VOUCHERS) {
                                            list.remove(list.size() - 1);
                                        } else {
                                            list.remove(ItemsAdapter.MAX_NUMBER_VOUCHERS - 1);
                                            Item obj = list.get(list.size() - 1);
                                            list.remove(list.size() - 1);
                                            list.add(0, obj);
                                        }
                                        iadapter.setList(list);
                                        iadapter.notifyDataSetChanged();
                                        rootStack.animateTopcardRight().setListener(animationListener);
                                    }

                                    @Override
                                    public void onNegative(MaterialDialog dialog) {
                                        super.onNegative(dialog);
                                    }
                                });
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void updateCurrentItem() {
        currentItem++;
        if (currentItem > list.size()) {
            currentItem = 1;
        }
    }

    private void setItemsInfo() {
        String strInfo = String.format(getString(R.string.str_items_info), currentItem, list.size());
        String strCurrentItem = "" + currentItem;
        String strListSize = "" + list.size();
        int indStart1 = strInfo.indexOf(strCurrentItem);
        int indEnd1 = strInfo.indexOf(" ", indStart1);
        int indStart2 = strInfo.indexOf(strListSize);

        SpannableString span = new SpannableString(strInfo);

        int color = getResources().getColor(R.color.green);
        span.setSpan(new ForegroundColorSpan(color), indStart1, indEnd1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(color), indStart2, strInfo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD), indStart1, indEnd1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        span.setSpan(new StyleSpan(Typeface.BOLD), indStart2, strInfo.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        itemsInfo.setText(span);
    }
}
