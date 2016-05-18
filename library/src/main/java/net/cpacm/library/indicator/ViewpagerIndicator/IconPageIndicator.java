/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright (C) 2012 Jake Wharton
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
 * limitations under the License.
 */
package net.cpacm.library.indicator.ViewpagerIndicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import net.cpacm.library.R;
import net.cpacm.library.indicator.PageIndicator;
import net.cpacm.library.infinite.InfinitePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class IconPageIndicator extends HorizontalScrollView implements PageIndicator {
    private final IcsLinearLayout mIconsLayout;

    private ViewPager mViewPager;
    private OnPageChangeListener mListener;
    private Runnable mIconSelector;
    private int mSelectedIndex;
    private List<Integer> iconList;

    public IconPageIndicator(Context context) {
        this(context, null);
    }

    public IconPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
        mIconsLayout = new IcsLinearLayout(context, R.attr.vpiIconPageIndicatorStyle);
        addView(mIconsLayout, new LayoutParams(WRAP_CONTENT, MATCH_PARENT, Gravity.CENTER));
        iconList = new ArrayList<>();
    }

    private void animateToIcon(final int position) {
        final View iconView = mIconsLayout.getChildAt(position);
        if (mIconSelector != null) {
            removeCallbacks(mIconSelector);
        }
        mIconSelector = new Runnable() {
            public void run() {
                final int scrollPos = iconView.getLeft() - (getWidth() - iconView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mIconSelector = null;
            }
        };
        post(mIconSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mIconSelector != null) {
            // Re-post the selector we saved
            post(mIconSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mIconSelector != null) {
            removeCallbacks(mIconSelector);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (mListener != null) {
            mListener.onPageSelected(arg0);
        }
    }

    @Override
    public void setViewPager(ViewPager view) {
        if (mViewPager == view) {
            return;
        }
        if (mViewPager != null) {
            mViewPager.addOnPageChangeListener(null);
        }
        PagerAdapter adapter = view.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        mViewPager = view;
        view.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (iconList.size() == 0) return;
        mIconsLayout.removeAllViews();
        int count = mViewPager.getAdapter().getCount();
        if (mViewPager.getAdapter() instanceof InfinitePagerAdapter) {
            count = ((InfinitePagerAdapter) mViewPager.getAdapter()).getRealCount();
        }
        for (int i = 0; i < count; i++) {
            ImageView view = new ImageView(getContext(), null, R.attr.vpiIconPageIndicatorStyle);
            view.setImageResource(iconList.get(i%iconList.size()));
            mIconsLayout.addView(view);
        }
        setCurrentItem(mSelectedIndex);
        requestLayout();
    }

    /**
     * set icon ids
     *
     * @param ids
     */
    public void setIconRes(int[] ids) {
        iconList.clear();
        for (Integer id : ids)
            iconList.add(id);
        if (mViewPager != null) notifyDataSetChanged();
    }

    /**
     * set icon ids
     *
     * @param ids
     */
    public void setIconRes(List<Integer> ids) {
        iconList.clear();
        iconList.addAll(ids);
        if (mViewPager != null) notifyDataSetChanged();
    }

    @Override
    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedIndex = item;
        if (mViewPager.getAdapter() instanceof InfinitePagerAdapter) {
            int count = ((InfinitePagerAdapter) mViewPager.getAdapter()).getRealCount();
            mSelectedIndex = item % count;
        }
        mViewPager.setCurrentItem(item);

        int tabCount = mIconsLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            View child = mIconsLayout.getChildAt(i);
            boolean isSelected = (i == mSelectedIndex);
            child.setSelected(isSelected);
            if (isSelected) {
                animateToIcon(mSelectedIndex);
            }
        }
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        mListener = listener;
    }
}
