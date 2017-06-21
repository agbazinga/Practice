package com.example.user.mysettingspreferencefragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.widget.TextView;

public class PagerTabStripTest extends FragmentActivity implements IntroOne.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private PagerAdapter viewPagerAdapter;
    private PagerTabStrip pagerTabStrip;
    private TextView tabLayoutBottomTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_normal);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(mViewPagerOnChangeListener
        );
        pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTabStrip);
        tabLayoutBottomTv = (TextView) findViewById(R.id.tab_layout_bottom_text_view);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
        tabLayoutBottomTv.setTextColor(getResources().getColor(typedValue.resourceId));

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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

        @Override
        public CharSequence getPageTitle(int position) {
            return "TAB " + position;
        }
    }

    ViewPager.OnPageChangeListener mViewPagerOnChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            TypedValue typedValue = new TypedValue();
            if ((position & 0x1) == 0) {
                getTheme().resolveAttribute(android.R.attr.colorPrimaryDark, typedValue, true);
            } else {
                getTheme().resolveAttribute(android.R.attr.textColorSecondary, typedValue, true);
            }
             tabLayoutBottomTv.setTextColor(getResources().getColor(typedValue.resourceId));
         //   tabLayoutBottomTv.setTextColor(getResources().getColor(android.R.color.secondary_text_dark));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
