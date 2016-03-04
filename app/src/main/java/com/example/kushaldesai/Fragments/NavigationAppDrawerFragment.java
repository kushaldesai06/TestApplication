package com.example.kushaldesai.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kushaldesai.testapplication.BaseActivity;
import com.example.kushaldesai.testapplication.LocationActivity;
import com.example.kushaldesai.testapplication.MainActivity;
import com.example.kushaldesai.testapplication.R;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public class NavigationAppDrawerFragment extends Fragment {

    private View view;
    private DrawerLayout drawerLayout;
    private TextView sc1, sc2;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private BaseActivity baseActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.navigation_drawer, container, false);
        view.requestFocus();
        baseActivity = (BaseActivity) getActivity();
        sc1 = (TextView) view.findViewById(R.id.sc1);
        sc2 = (TextView) view.findViewById(R.id.sc2);

        sc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(MainActivity.class);
            }
        });

        sc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(LocationActivity.class);
            }
        });
        return view;
    }

    private void startNewActivity(final Class<? extends Activity> callingActivity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent((BaseActivity) baseActivity, callingActivity);
                startActivity(intent);
            }
        }, 200);
    }

    public void setUp(DrawerLayout drawerLayout, Toolbar toolbar) {
        this.drawerLayout = drawerLayout;

        actionBarDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(baseActivity, drawerLayout, toolbar, R.string.item1, R.string.item2) {

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    public void openDrawer() {
        drawerLayout.openDrawer(view);


    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void closeDrawer() {
        drawerLayout.closeDrawer(view);
    }
}
