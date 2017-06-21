package com.example.user.mysettingspreferencefragment.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.mysettingspreferencefragment.widget.ViewPagerTabStrip;

import com.example.user.mysettingspreferencefragment.R;

/**
 * Created by a.gogia on 1/4/2017.
 */
public class ViewPagerTabs extends FrameLayout implements ViewPager.OnPageChangeListener {
    ViewPager mPager;

    private ViewPagerTabStrip mTabStrip;
    private View mDimTap;

    private int[] mTabDescriptions;

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int tabStripChildCount = mTabStrip.getChildCount();
        if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
            return;
        }

        mTabStrip.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if (mPrevSelected >= 0) {
            mTabStrip.getChildAt(mPrevSelected).setSelected(false);
        }
        // Update scroll position
        setSelectedTab(position);

        mPrevSelected = position;

        mTabStrip.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface Listener {

        /*Called when the user selects a tab. */
        void onSelectedTabChanged(int pos);

        boolean useClickInFocus();
    }

    /**
     * LinearLayout that will contain the TextViews serving as tabs. This is the only child
     * of the parent HorizontalScrollView.
     */
    int mTextStyle;

    ColorStateList mTextColor;

    float mTextSize;

    boolean mTextAllCaps;

    int mLeftRightMostPadding;

    int mTextPadding;

    int mPrevSelected = -1;

    private Listener mListener;

    private boolean mHasFocus;

    private int mCurrentTabPosition;

    private HorizontalScrollView mHorizontalScrollView;

    private int mMinWidth;

    private static final ViewOutlineProvider VIEW_BOUNDS_OUTLINE_PROVIDER =
            new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRect(0, 0, view.getWidth(), view.getHeight());
                }
            };

    private static final int TAB_SIDE_PADDING_IN_DPS = 10;

    private int TAB_INDEX_COUNT = 2;

    // TODO: This should use <declare-styleable> in the future
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textStyle,
            android.R.attr.textColor,
            android.R.attr.textAllCaps
    };

    public ViewPagerTabs(Context context) {
        this(context, null);
    }

    public ViewPagerTabs(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerTabs(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        // set textSize up to Large size (1.2f)
        TypedValue value = a.peekValue(0);
        float defaultTextSize = 15.0f;
        float MAX_FONT_SCALE = 1.2f;
        float fontScale = getContext().getResources().getConfiguration().fontScale;
        if (fontScale > MAX_FONT_SCALE) {
            fontScale = MAX_FONT_SCALE;
        }
        mTextSize = defaultTextSize * fontScale;

        mTextStyle = a.getInt(1, 0);
        mTextColor = a.getColorStateList(2);
        mTextAllCaps = a.getBoolean(3, false);
        mTextPadding = getContext().getResources().getDimensionPixelOffset(R.dimen.ditaltacts_tab_text_padding);
        mLeftRightMostPadding = getContext().getResources().getDimensionPixelOffset(R.dimen.ditaltacts_tab_left_right_most_padding);

        mTabStrip = new ViewPagerTabStrip(context);
        mHorizontalScrollView = new HorizontalScrollView(context);
        mHorizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.w_fixed_tabs_for_text_only_height)));
        mHorizontalScrollView.setHorizontalScrollBarEnabled(false);
        addView(mHorizontalScrollView);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        //int margin = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        int margin = 0;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mMinWidth = displaymetrics.heightPixels / TAB_INDEX_COUNT;
        } else {
            mMinWidth = (displaymetrics.widthPixels - 2 * margin) / TAB_INDEX_COUNT;
        }

        mHorizontalScrollView.addView(mTabStrip, new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        a.recycle();

        // enable shadow casting from view bounds
        setOutlineProvider(VIEW_BOUNDS_OUTLINE_PROVIDER);
    }

    public void setViewPager(ViewPager viewPager) {
        mPager = viewPager;
        mPager.addOnPageChangeListener(this);
        mPrevSelected = mPager.getCurrentItem();
        addTabs(mPager.getAdapter());
        mTabStrip.onPageSelected(mPrevSelected);
    }

    public void addDummyTabs(String[] TabTitles) {
        mTabStrip.removeAllViews();
        for (int i = 0; i < TAB_INDEX_COUNT; i++) {
            addTab(TabTitles[i], i);
        }
    }

    public void setTabDescriptions(int[] descriptions) {
        mTabDescriptions = descriptions;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    private void addTabs(PagerAdapter adapter) {
        mTabStrip.removeAllViews();

        //final int count = adapter.getCount(); // Not used
        for (int i = 0; i < TAB_INDEX_COUNT; i++) {
            addTab(getResources().getString(mTabDescriptions[i]), i);
        }
    }

    private void addTab(CharSequence tabTitle, final int position) {
        final TextView textView = new TextView(getContext());
        int descriptionID = -1;
        String description;

        textView.setText(tabTitle);
        textView.setMinWidth(mMinWidth);
        textView.setBackgroundResource(R.drawable.view_pager_tab_background);
        textView.setGravity(Gravity.CENTER);
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextColor(getResources().getColor(R.color.tab_text_color_normal));
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    Log.d("bazinga", "mListener onSelectedTabChanged " + position);
                    // textView.setTextColor(getResources().getColor(R.color.tab_text_color_selected));
                    mCurrentTabPosition = position;
                    mListener.onSelectedTabChanged(position);

                }
                mPager.setCurrentItem(position);
            }
        });

        if (position == 0)
            textView.setPadding(mLeftRightMostPadding, 0, mTextPadding, 0);
        else if (position == 3)
            textView.setPadding(mTextPadding, 0, mLeftRightMostPadding, 0);
        else
            textView.setPadding(mTextPadding, 0, mTextPadding, 0);

//        textView.setOnLongClickListener(new OnTabLongClickListener(position));

        // Assign various text appearance related attributes to child views.
        if (mTextStyle > 0) {
            textView.setTypeface(textView.getTypeface(), mTextStyle);
        }
        if (mTextSize > 0) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mTextSize);
        }
        if (mTextColor != null) {
            textView.setTextColor(getResources().getColor(R.color.tab_color_selector));
        }

        textView.setAllCaps(mTextAllCaps);
        textView.setFocusable(true);
        textView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                Log.d("bazinga", "onFocusChange focus : " + focus);
                if (mHasFocus) {
                    if (mListener.useClickInFocus()
                            && mCurrentTabPosition != position) {
                        mCurrentTabPosition = position;
                        mListener.onSelectedTabChanged(position);
                        textView.setTextColor(getResources().getColor(R.color.tab_text_color_selected));
                    }
                } else {
                    if (focus) {
                        // it can give a focus to current tab
                        View childView = mTabStrip.getChildAt(mCurrentTabPosition);
                        if (childView != null) {
                            childView.requestFocus();
                        }
                    }
                    textView.setTextColor(getResources().getColor(R.color.tab_text_color_normal));
                }

                mHasFocus = false;
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    if (mTabStrip.getChildAt(i).hasFocus())
                        mHasFocus = true;
                }
            }
        });

        if (mTabDescriptions != null && mTabDescriptions.length == TAB_INDEX_COUNT) {
            descriptionID = mTabDescriptions[position];
        }
        if (descriptionID > 0) {
            description = getResources().getString(descriptionID);
        } else {
            description = tabTitle.toString();
        }
        description = description + " ";
//        textView.setContentDescription(description
//                + getResources().getString(R.string.tab_tts, position + 1, TAB_INDEX_COUNT));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        //  mTabStrip.addView(textView, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        mTabStrip.addView(textView, lp);
        // Default to the last tab being selected
        if (position == mPrevSelected) {
            textView.setSelected(true);
        }
    }

    public void setCurrentTabPosition(int position) {
        mCurrentTabPosition = position;
    }

    public void setSelectedTab(int position) {
        int childCount = mTabStrip.getChildCount();
        if (position >= 0 && position < childCount) {
            final View selectedChild = mTabStrip.getChildAt(position);
            selectedChild.setSelected(true);
            mTabStrip.setSelection(true);
            final int scrollPos = selectedChild.getLeft() - (getWidth() - selectedChild.getWidth()) / 2;
            mHorizontalScrollView.smoothScrollTo(scrollPos, 0);
        } else {
            for (int i = 0; i < TAB_INDEX_COUNT; i++) {
                mTabStrip.getChildAt(i).setSelected(false);
            }
            mTabStrip.setSelection(false);
        }
        mTabStrip.invalidate();
    }
}
