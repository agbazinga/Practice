package com.example.user.mysettingspreferencefragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.mysettingspreferencefragment.widget.SlidingTabLayout;

/**
 * Created by Bazinga on 5/23/2017.
 */

public class ViewPagerSlidingTabStripTwo extends AppCompatActivity implements IntroOne.OnFragmentInteractionListener, View.OnClickListener, SimpleFragmentSecond.OnFragmentInteractionListenerSecond {

    private static int NUM_PAGES = 2;
    private ViewPager mViewPager;
    private PagerAdapter mPageTestAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private int descriptions[] = {R.string.tab_one, R.string.tab_two};
    ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_sliding_tab_strip_two);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.sliding_tab_two_view_pager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_layout_two);
        mPageTestAdapter = new ViewPagerTestAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPageTestAdapter);
        mViewPager.addOnPageChangeListener(viewPagerOnChangeListener);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteractionSecond(Uri uri) {

    }

    private class ViewPagerTestAdapter extends FragmentPagerAdapter {

        public ViewPagerTestAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position != NUM_PAGES - 1)
                return IntroOne.newInstance(position);
            else
                return SimpleFragmentSecond.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(descriptions[position]);
        }
    }

    ViewPager.OnPageChangeListener viewPagerOnChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Log.d("ABHI", "position : " + position + " positionOffset : " + positionOffset + " positionOffsetPixels : " + positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //Log.d("ABHI", "onPageScrollStateChanged");
        }
    };

}
