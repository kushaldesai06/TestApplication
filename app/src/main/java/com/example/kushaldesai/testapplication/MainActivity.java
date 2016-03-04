package com.example.kushaldesai.testapplication;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushaldesai.adapter.CustomPagerAdapter;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button redBtn, blueBtn, greenBtn;
    private LinearLayout llBg;
    private TabLayout tabLayout;
    private int selectedColor = 3;
    private ViewPager viewPager;
    private CoordinatorLayout coordinatorLayout;
    private TextView textViewSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            viewPager.setCurrentItem(savedInstanceState.getInt("SelectedFragPostion"));
            textViewSelectedTab.setText(savedInstanceState.getString("SelectedTab"));
            tabLayout.getTabAt(savedInstanceState.getInt("SelectedPostion")).select();
            switch (selectedColor) {
                case 0:
                    redBtn.performClick();
                    break;
                case 1:
                    blueBtn.performClick();
                    break;
                case 2:
                    greenBtn.performClick();
                    break;
                default:
                    llBg.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
            }
        }
    }

    @Override
    public void initialize() {
        coordinatorLayout = (CoordinatorLayout) inflater.inflate(R.layout.activity_main, null);

        llBaseMid.addView(coordinatorLayout);
        toolbar.setVisibility(View.VISIBLE);

        defineViews();
        addTabsToLayout();

        applyListeners();
    }

    private void defineViews() {
        CustomSizes customSizes = new CustomSizes(this.getResources().getDisplayMetrics());
        redBtn = (Button) coordinatorLayout.findViewById(R.id.redBtn);
        blueBtn = (Button) coordinatorLayout.findViewById(R.id.blueBtn);
        greenBtn = (Button) coordinatorLayout.findViewById(R.id.greenBtn);
        llBg = (LinearLayout) coordinatorLayout.findViewById(R.id.bg_button_ll);
        viewPager = (ViewPager) coordinatorLayout.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) coordinatorLayout.findViewById(R.id.tab_layout);
        textViewSelectedTab = (TextView) coordinatorLayout.findViewById(R.id.selectedTabTv);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), 4));
        redBtn.setOnClickListener(this);
        blueBtn.setOnClickListener(this);
        greenBtn.setOnClickListener(this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            redBtn.getLayoutParams().height = customSizes.getRectViewHeightSize(130);
            blueBtn.getLayoutParams().height = customSizes.getRectViewHeightSize(130);
            greenBtn.getLayoutParams().height = customSizes.getRectViewHeightSize(130);
            viewPager.getLayoutParams().height = customSizes.getRectViewHeightSize(600);
            llBg.getLayoutParams().height = customSizes.getRectViewHeightSize(740);
            tabLayout.getLayoutParams().height = customSizes.getRectViewHeightSize(160);
            tabLayout.getLayoutParams().width = customSizes.getRectViewWidthSize(getResources().getDisplayMetrics().heightPixels);
        } else {
            redBtn.getLayoutParams().height = customSizes.getRectViewHeightSize(90);
            blueBtn.getLayoutParams().height = customSizes.getRectViewHeightSize(90);
            greenBtn.getLayoutParams().height = customSizes.getRectViewHeightSize(90);
            viewPager.getLayoutParams().height = customSizes.getRectViewHeightSize(400);
            llBg.getLayoutParams().height = customSizes.getRectViewHeightSize(340);
            tabLayout.getLayoutParams().height = customSizes.getRectViewHeightSize(120);
            tabLayout.getLayoutParams().width = customSizes.getRectViewWidthSize(getResources().getDisplayMetrics().widthPixels);
        }

        textViewSelectedTab.setTextSize(customSizes.getFontSize(40));
        redBtn.setTextSize(customSizes.getFontSize(28));
        blueBtn.setTextSize(customSizes.getFontSize(28));
        greenBtn.setTextSize(customSizes.getFontSize(28));
    }

    private void addTabsToLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.item1)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.item2)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.item3)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.item4)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.item5)));
    }

    private void applyListeners() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                textViewSelectedTab.setText(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, "Fragment " + (position + 1), 200).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == redBtn) {
            llBg.setBackgroundColor(Color.parseColor("#ffff4444"));
            selectedColor = 0;
        } else if (v == blueBtn) {
            llBg.setBackgroundColor(Color.parseColor("#ff00ddff"));
            selectedColor = 1;
        } else if (v == greenBtn) {
            llBg.setBackgroundColor(Color.parseColor("#ff669900"));
            selectedColor = 2;
        } else {
            llBg.setBackgroundColor(Color.parseColor("#ffffff"));
            selectedColor = 3;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("SelectedPostion", tabLayout.getSelectedTabPosition());
        outState.putInt("SelectedFragPostion", viewPager.getCurrentItem());
        outState.putString("SelectedTab", textViewSelectedTab.getText().toString());
        outState.putInt("SelectedColor", selectedColor);
    }

}
