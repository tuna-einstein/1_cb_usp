package com.winfiny.cb.smenu;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.winfiny.cb.GamesListFragment;
import com.winfiny.cb.MainActivity;
import com.winfiny.cb.R;
import com.winfiny.cb.fragments.CommunityFragment;
import com.winfiny.cb.fragments.FindPeopleFragment;
import com.winfiny.cb.fragments.PagesFragment;
import com.winfiny.cb.fragments.PhotosFragment;
import com.winfiny.cb.fragments.WhatsHotFragment;

import java.util.ArrayList;

public class SlidingMenuListFragment extends ListFragment {

    private ArrayList<SMenuItem> items;
    private SMenuAdapter adapter;

    // slide menu items
    private String[] sMenuTitles;
    private TypedArray sMenuIcons;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smenu_list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // load slide menu items
        sMenuTitles = getResources().getStringArray(R.array.smenu_drawer_items);

        // nav drawer icons from resources
        sMenuIcons = getResources()
                .obtainTypedArray(R.array.smenu_drawer_icons);

        items = new ArrayList<SMenuItem>();

        // adding nav drawer items to array
        // Home
        items.add(new SMenuItem(sMenuTitles[0], sMenuIcons.getResourceId(0, -1)));
        // Find People
        items.add(new SMenuItem(sMenuTitles[1], sMenuIcons.getResourceId(1, -1)));
        // Photos
        items.add(new SMenuItem(sMenuTitles[2], sMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        items.add(new SMenuItem(sMenuTitles[3], sMenuIcons.getResourceId(3, -1), true, "22"));
        // Pages
        items.add(new SMenuItem(sMenuTitles[4], sMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        items.add(new SMenuItem(sMenuTitles[5], sMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        sMenuIcons.recycle();


        // setting the nav drawer list adapter
        adapter = new SMenuAdapter(getActivity().getApplicationContext(), items);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick (ListView list, View v, int position, long id) {
        displayView(position);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FindPeopleFragment();
                break;
            case 1:
                fragment = new FindPeopleFragment();
                break;
            case 2:
                fragment = new PhotosFragment();
                break;
            case 3:
                fragment = new CommunityFragment();
                break;
            case 4:
                fragment = new PagesFragment();
                break;
            case 5:
                fragment = new WhatsHotFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            ((MainActivity) getActivity()).replaceContentFragment(fragment);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}