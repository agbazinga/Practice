package com.example.user.mysettingspreferencefragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.Toast;

public class ActionBarTabTest extends FragmentActivity implements IntroOne.OnFragmentInteractionListener, ActionBar.TabListener {

    ViewPager mViewPagerTab;
    FragmentPagerAdapter mViewPagerAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_tab_test);

        mViewPagerTab = (ViewPager) findViewById(R.id.tabViewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerTab.setAdapter(mViewPagerAdapter);

        actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionBar.setDisplayOptions(1, ActionBar.DISPLAY_SHOW_TITLE);

        actionBar.addTab(actionBar.newTab().setText("NOTIFICATIONS").setTabListener(this),false);
        actionBar.addTab(actionBar.newTab().setText("SMART REPLY").setTabListener(this),false);
        actionBar.addTab(actionBar.newTab().setText("MY RIDES EXTENDED").setTabListener(this),false);

        mViewPagerTab.setOnPageChangeListener(mViewPagerOnChangeListener);

//        actionBar.setDisplayHomeAsUpEnabled(true);

        //actionBar.setSelectedNavigationItem(1);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    ViewPager.OnPageChangeListener mViewPagerOnChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            getActionBar().setSelectedNavigationItem(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        Toast.makeText(getApplicationContext(), "Tab Position : " + tab.getPosition(), Toast.LENGTH_SHORT).show();
        mViewPagerTab.setCurrentItem(tab.getPosition());

//        if (tab.getPosition() == 1) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        } else {
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return IntroOne.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
