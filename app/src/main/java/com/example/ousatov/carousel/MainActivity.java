package com.example.ousatov.carousel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.ousatov.carousel.data.Item;
import com.example.ousatov.carousel.events.DetailsItemEvent;
import com.example.ousatov.carousel.fragments.ItemDetailsFragment;
import com.example.ousatov.carousel.fragments.ItemOverviewFragment;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ItemOverviewFragment overviewFragment = new ItemOverviewFragment();
        displayFragment(overviewFragment, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void displayFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContent_MainActivity, fragment);
        if (addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private Item itemDetails;

    public Item getItemDetails() {
        return itemDetails;
    }

    @SuppressWarnings("unused")
    public void onEventMainThread(DetailsItemEvent event) {
        showItemDetailsFragment();
        itemDetails = event.getItem();
    }

    private void showItemDetailsFragment() {
        Fragment fragment = new ItemDetailsFragment();
        displayFragment(fragment, true);
    }
}
