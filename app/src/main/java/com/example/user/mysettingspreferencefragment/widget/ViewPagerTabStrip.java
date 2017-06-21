/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.example.user.mysettingspreferencefragment.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.user.mysettingspreferencefragment.R;


public class ViewPagerTabStrip extends LinearLayout {

    private static final String TAG = "ViewPagerTabStrip";

    private int mIndexForSelection;

    private float mSelectionOffset;

    private Drawable mSelection;

    private boolean mIsSelectionEabled = true;

    private boolean mIsOpenTheme;

    public ViewPagerTabStrip(Context context) {
        this(context, null);
    }

    public ViewPagerTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        final Resources res = context.getResources();

//        mSelectedUnderlineThickness =
//                res.getDimensionPixelSize(R.dimen.tab_selected_underline_height);
//        int underlineColor = res.getColor(R.color.tab_selected_underline_color);
//        int backgroundColor = res.getColor(R.color.action_bar_tab_color);

//        mSelectedUnderlinePaint = new Paint();
//        mSelectedUnderlinePaint.setColor(underlineColor);

//        setBackgroundColor(backgroundColor);
//        setBackgroundResource(R.drawable.dialtacts_actionbar_bg);
        setWillNotDraw(false);
        try {
            mSelection = res.getDrawable(R.drawable.dialtacts_tab_selected);
        } catch (NotFoundException e) {
            Log.d(TAG, "Can't not found tab selected resource ID : " + e.getMessage());
        }
        mIsOpenTheme = false;
    }

    /**
     * Notifies this view that view pager has been scrolled. We save the tab index
     * and selection offset for interpolating the position and width of selection
     * underline.
     */
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!mIsOpenTheme) {
            mIndexForSelection = position;
            mSelectionOffset = positionOffset;
            invalidate();
        }
    }

    public void onPageSelected(int position) {
        if (mIsOpenTheme) {
            mIndexForSelection = position;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int childCount = getChildCount();

        // Thick colored underline below the current selection
        if (childCount > 0) {
            View selectedTitle = getChildAt(mIndexForSelection);
            int selectedLeft = selectedTitle.getLeft();
            int selectedRight = selectedTitle.getRight();

            final boolean hasNextTab = mIndexForSelection < (getChildCount() - 1);
            if (!mIsOpenTheme && (mSelectionOffset > 0.0f) && hasNextTab) {
                // Draw the selection partway between the tabs
                View nextTitle = getChildAt(mIndexForSelection + 1);
                int nextLeft = nextTitle.getLeft();
                int nextRight = nextTitle.getRight();

                selectedLeft = (int) (mSelectionOffset * nextLeft + (1.0f - mSelectionOffset)
                        * selectedLeft);
                selectedRight = (int) (mSelectionOffset * nextRight + (1.0f - mSelectionOffset)
                        * selectedRight);
            }

            if (mIsSelectionEabled) {
                int height = getHeight();
                // canvas.drawRect(selectedLeft, height -
                // mSelectedUnderlineThickness,
                // selectedRight, height, mSelectedUnderlinePaint);
                mSelection.setBounds(selectedLeft, 0, selectedRight, height);
                mSelection.draw(canvas);
            }
        }
    }

    public void setSelection(boolean enable) {
        mIsSelectionEabled = enable;
    }
}