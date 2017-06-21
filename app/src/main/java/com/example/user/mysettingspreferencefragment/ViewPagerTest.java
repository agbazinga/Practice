package com.example.user.mysettingspreferencefragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerTest extends AppCompatActivity implements IntroOne.OnFragmentInteractionListener, View.OnClickListener, SimpleFragmentSecond.OnFragmentInteractionListenerSecond {

    private ViewPager mViewPager;
    private PagerAdapter mPageTestAdapter;
    private static final int NUM_PAGES = 3;

    private ImageView pageIndicatorOne;
    private ImageView pageIndicatorTwo;
    private ImageView pageIndicatorThree;

    private LinearLayout bottomBarRight;
    private TextView skipButton;
    private ImageView dotImageViews[];
    private LinearLayout dotContainer;

    public static int currentSlide;

    private int currentPage;
    private PageIndicator mCustomPageIndicator;

    private ImageView imageConfirm;
    private TextView textConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mCustomPageIndicator = (PageIndicator) findViewById(R.id.custom_page_indicator);
        mCustomPageIndicator.setPages(NUM_PAGES);
        mCustomPageIndicator.invalidate();
        mPageTestAdapter = new ViewPagerTestAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPageTestAdapter);
        mViewPager.addOnPageChangeListener(viewPagerOnChangeListener);

        dotImageViews = new ImageView[mPageTestAdapter.getCount() + 1];
        dotContainer = (LinearLayout) findViewById(R.id.dotsContainer);

        initialisePageIndicatorDynamic();

        pageIndicatorOne = (ImageView) findViewById(R.id.pageIndicatorOne);
        pageIndicatorTwo = (ImageView) findViewById(R.id.pageIndicatorTwo);
        pageIndicatorThree = (ImageView) findViewById(R.id.pageIndicatorThree);

        skipButton = (TextView) findViewById(R.id.skip_button);
        skipButton.setOnClickListener(this);

        bottomBarRight = (LinearLayout) findViewById(R.id.btn_confirm);
        bottomBarRight.setOnClickListener(this);

        imageConfirm = (ImageView) findViewById(R.id.image_confirm);
        textConfirm = (TextView) findViewById(R.id.text_confirm);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_view_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    ViewPager.OnPageChangeListener viewPagerOnChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Log.d("ABHI", "onPageScrolled");

            if (position == currentPage) {
                // Position is same as currentPage , so scrolling is to right.
                if (positionOffset > 0.5) {
                    setUpPageIndicator(position + 1);
                } else {
                    setUpPageIndicator(position);
                }

            } else {
                //Position is not same as currentPage , so scrolling is to left.
                if (positionOffset < 0.5) {
                    setUpPageIndicator(position);
                } else {
                    setUpPageIndicator(position + 1);
                }
            }
            mCustomPageIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
             Log.d("ABHI", "onPageScrolled position : " + position + " positionOffset : " + positionOffset + " positionOffsetPixels : " + positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            Log.d("ABHI", "onPageSelected " + position);

            currentSlide = mViewPager.getCurrentItem();
            currentPage = position;
            setUpPageIndicator(position);
            mCustomPageIndicator.onPageSelected(position);

            if (position == mPageTestAdapter.getCount() - 1) {
                imageConfirm.setVisibility(View.GONE);
                textConfirm.setVisibility(View.VISIBLE);
                skipButton.setVisibility(View.GONE);
            } else {
                imageConfirm.setVisibility(View.VISIBLE);
                textConfirm.setVisibility(View.GONE);
                skipButton.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //Log.d("ABHI", "onPageScrollStateChanged");
        }
    };

    private void setUpPageIndicator(int position) {

        for (int i = 0; i < mPageTestAdapter.getCount(); i++) {
            if (i != position) {
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_dim));
            } else {
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
            }

        }
        switch (position) {
            case 0:
                pageIndicatorOne.setBackgroundResource(R.drawable.page_indicator);
                pageIndicatorTwo.setBackgroundResource(R.drawable.page_indicator_dim);
                pageIndicatorThree.setBackgroundResource(R.drawable.page_indicator_dim);
                break;
            case 1:
                pageIndicatorOne.setBackgroundResource(R.drawable.page_indicator_dim);
                pageIndicatorTwo.setBackgroundResource(R.drawable.page_indicator);
                pageIndicatorThree.setBackgroundResource(R.drawable.page_indicator_dim);
                break;
            case 2:
                pageIndicatorOne.setBackgroundResource(R.drawable.page_indicator_dim);
                pageIndicatorTwo.setBackgroundResource(R.drawable.page_indicator_dim);
                pageIndicatorThree.setBackgroundResource(R.drawable.page_indicator);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_confirm:
                if (mViewPager.getCurrentItem() != 2) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                } else {
                    finish();
                }

                break;
        }
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
    }

    /*  @Override
      public void onBackPressed() {
          if (mViewPager.getCurrentItem() == 0) {
              super.onBackPressed();
          } else {
              mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
          }

      }*/
    private void initialisePageIndicatorDynamic() {

        int h = getResources().getDimensionPixelSize(R.dimen.indicatorHeight);
        int endMargin = getResources().getDimensionPixelSize(R.dimen.indicator_end_margin);

        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(h, h);
        imageViewLayoutParams.setMarginStart(endMargin / 2);
        imageViewLayoutParams.setMarginEnd(endMargin / 2);
//        LinearLayout.LayoutParams imageViewContainerLayoutParams = new LinearLayout.LayoutParams(h + endMargin, h);
//        imageViewContainerLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
//        imageViewContainerLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        for (int i = 0; i < mPageTestAdapter.getCount(); i++) {
            dotImageViews[i] = new ImageView(this);
            dotImageViews[i].setLayoutParams(imageViewLayoutParams);
            LinearLayout imageViewContainer = new LinearLayout(this);
            // Gravity of Linear Layout container (determines the arrangement of its children)
//            imageViewContainer.setGravity(Gravity.CENTER);
//            imageViewContainer.setLayoutParams(imageViewContainerLayoutParams);

            if (i == 0)
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator));
            else
                dotImageViews[i].setImageDrawable(getResources().getDrawable(R.drawable.page_indicator_dim));
            //imageViewContainer.addView(dotImageViews[i]);
            dotContainer.addView(dotImageViews[i]);
            //  dotContainer.addView(dotImageViews[i]);
        }
    }

}
