package com.example.ousatov.carousel.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ousatov.carousel.data.Item;
import com.example.ousatov.carousel.views.ItemView;
import com.example.ousatov.carousel.MainActivity;
import com.example.ousatov.carousel.R;

public class ItemDetailsFragment extends Fragment {

    private ItemView itemView;
    private Item itemDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View parent = inflater.inflate(R.layout.fragment_item_details, container, false);
        initFragmentViews(parent);

        return parent;
    }

    private void initFragmentViews(View parent) {
        itemView = (ItemView) parent.findViewById(R.id.itemView);
        itemDetails = ((MainActivity) getActivity()).getItemDetails();
        itemView.setItem(itemDetails);
    }
}
