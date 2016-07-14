package com.example.ousatov.carousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.ousatov.carousel.data.Item;
import com.example.ousatov.carousel.events.DetailsItemEvent;
import com.example.ousatov.carousel.views.ItemView;

import java.util.List;

import de.greenrobot.event.EventBus;

public class ItemsAdapter extends BaseAdapter {

    private List<Item> data;
    private Context context;
    private ItemView.SwipeCallback callback;

    public static final int MAX_NUMBER_VOUCHERS = 4;
    private final float SCALES[] = {0.7f, 0.8f, 0.9f, 1.0f};
    private float stepYinPxl = 0.0f;
    private final int ALPHAS[] = {120, 90, 60, 30};

    public ItemsAdapter(List<Item> d, Context c) {
        data = d;
        context = c;
        stepYinPxl = context.getResources().getDimensionPixelSize(R.dimen.step_y_of_items_stack);
    }

    public void setOnSwipeListener(ItemView.SwipeCallback c) {
        callback = c;
    }

    @Override
    public int getCount() {
        return MAX_NUMBER_VOUCHERS < data.size() ? MAX_NUMBER_VOUCHERS : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        }
        ItemView itemView = (ItemView) view;
        int size = getCount();
        itemView.setScaleX(SCALES[MAX_NUMBER_VOUCHERS - size + position]);
        itemView.setScaleY(SCALES[MAX_NUMBER_VOUCHERS - size + position]);

        itemView.setTranslationY(stepYinPxl * (MAX_NUMBER_VOUCHERS - size + position));

        itemView.setImageWhiteFilter(ALPHAS[MAX_NUMBER_VOUCHERS - size + position]);
        final Item item = (Item) getItem(position);

        itemView.setItem(item);
        if (position == size - 1) {
            itemView.setSwipeCallback(callback);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new DetailsItemEvent(item));
                }
            });
        }

        return view;
    }

}
