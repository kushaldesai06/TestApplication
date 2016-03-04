package com.example.kushaldesai.testapplication;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kushaldesai.Fragments.NavigationAppDrawerFragment;

/**
 * Created by kushal.desai on 3/3/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public LayoutInflater inflater;
    private ImageView ivMenuList;
    public LinearLayout llBaseMid;
    private NavigationAppDrawerFragment navigationAppDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        toolbar = (Toolbar) findViewById(R.id.home_scren_bar);
        llBaseMid = (LinearLayout)findViewById(R.id.llBaseMid);
        ivMenuList = (ImageView)findViewById(R.id.ivMenuList);
        setSupportActionBar(toolbar);

        navigationAppDrawerFragment = (NavigationAppDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigationDrawerFragment);
        navigationAppDrawerFragment.setUp((DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

        ivMenuList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationAppDrawerFragment.openDrawer();
            }
        });
        inflater = getLayoutInflater();
        initialize();
    }


    public abstract void initialize();
}
