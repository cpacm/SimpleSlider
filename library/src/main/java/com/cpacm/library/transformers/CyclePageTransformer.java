package com.cpacm.library.transformers;

import android.os.Build;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.cpacm.library.SimpleViewPager;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * <p>
 *
 * @author cpacm 2018/5/18
 */
public class CyclePageTransformer extends BaseTransformer {

    private final static int MIN_CYCLE_COUNT = 3;

    // Default ViewPager constants and flags
    protected final static int DEFAULT_OFFSCREEN_PAGE_LIMIT = 2;
    protected final static int DEFAULT_PAGE_MARGIN = 0;
    protected final static boolean DEFAULT_DISABLE_FLAG = false;
    protected final static boolean DEFAULT_ENABLE_FLAG = true;

    // Default attributes constants
    private final static float DEFAULT_MIN_SCALE = 0.55F;
    private final static float DEFAULT_MAX_SCALE = 0.8F;
    private final static int DEFAULT_MIN_PAGE_SCALE_OFFSET = 30;
    private final static int DEFAULT_CENTER_PAGE_SCALE_OFFSET = 50;
    private final static boolean DEFAULT_IS_MEDIUM_SCALED = true;

    // Page scrolled info positions
    private float mPageScrolledPositionOffset;
    private float mPageScrolledPosition;

    private PageScrolledState mInnerPageScrolledState = PageScrolledState.IDLE;
    private PageScrolledState mOuterPageScrolledState = PageScrolledState.IDLE;

    // When item count equals to 3 we need stack count for know is item on position -2 or 2 is placed
    private int mStackCount;

    // Flag to know is left page need bring to front for correct scrolling present
    private boolean mIsLeftPageBringToFront;
    // Flag to know is right page need bring to front for correct scrolling present
    private boolean mIsRightPageBringToFront;
    // Detect if was minus one position of transform page for correct handle of page bring to front
    private boolean mWasMinusOne;
    // Detect if was plus one position of transform page for correct handle of page bring to front
    private boolean mWasPlusOne;

    // Detect is ViewPager state
    private int mState;

    // Page scale offset at minimum scale(left and right bottom pages)
    private float mMinPageScaleOffset;
    // Page scale offset at maximum scale(center top page)
    private float mCenterPageScaleOffset;

    // Minimum page scale(left and right pages)
    private float mMinPageScale;
    // Maximum page scale(center page)
    private float mMaxPageScale;
    // Center scale by when scroll position is on half
    private float mCenterScaleBy;

    // Use medium scale or just from max to min
    private boolean mIsMediumScaled;

    private SimpleViewPager viewPager;

    public CyclePageTransformer(SimpleViewPager viewPager) {
        this.viewPager = viewPager;
        //viewPager.setPageTransformer(false, this);
        viewPager.addOnPageChangeListener(mInfinityCyclePageChangeListener);
        viewPager.setClipChildren(DEFAULT_DISABLE_FLAG);
        viewPager.setDrawingCacheEnabled(DEFAULT_DISABLE_FLAG);
        viewPager.setWillNotCacheDrawing(DEFAULT_ENABLE_FLAG);
        viewPager.setPageMargin(DEFAULT_PAGE_MARGIN);
        viewPager.setOffscreenPageLimit(DEFAULT_OFFSCREEN_PAGE_LIMIT);
        viewPager.setDisableDrawChildOrder(true);
        viewPager.setOverScrollMode(OVER_SCROLL_NEVER);

        mMinPageScaleOffset = DEFAULT_MIN_PAGE_SCALE_OFFSET;
        mCenterPageScaleOffset = DEFAULT_CENTER_PAGE_SCALE_OFFSET;
        mMinPageScale = DEFAULT_MIN_SCALE;
        mMaxPageScale = DEFAULT_MAX_SCALE;
        mIsMediumScaled = DEFAULT_IS_MEDIUM_SCALED;

        resetScaleBy();
    }

    @Override
    public void transformPage(View page, float position) {
        onTransform(page, position);
    }


    @Override
    public void onTransform(final View page, final float position) {
        // Handle page layer and bounds visibility
        enableHardwareLayer(page);
        if (viewPager.getCount() == MIN_CYCLE_COUNT) {
            if (position > 2.0F || position < -2.0F ||
                    (mStackCount != 0 && position > 1.0F) ||
                    (mStackCount != 0 && position < -1.0F)) {
                page.setVisibility(View.GONE);
                return;
            } else page.setVisibility(View.VISIBLE);
        }

        final float pageSize = page.getMeasuredWidth();

        // Page offsets relative to scale
        final float pageMinScaleOffset = pageSize * mMinPageScale;
        final float pageSubScaleByOffset = pageSize * mCenterScaleBy;

        // Page offsets from bounds
        final float pageMinScaleEdgeOffset = (pageSize - pageMinScaleOffset) * 0.5F;
        final float pageMaxScaleEdgeOffset = (pageSize - (pageSize * mMaxPageScale)) * 0.5F;
        final float pageSubScaleEdgeOffset =
                (pageSize - (pageSize * (mMinPageScale + mCenterScaleBy))) * 0.5F;

        final float scale;
        final float translation;

        // Detect when the count <= 3 and another page of side stack not placed
        if (viewPager.getCount() < MIN_CYCLE_COUNT + 1 && mStackCount == 0 &&
                position > -2.0F && position < -1.0F) {
            final float fraction = -1.0F - position;

            scale = mMinPageScale;
            translation = (pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset) +
                    (pageSize * 2.0F - pageMinScaleOffset - mMinPageScaleOffset * 2.0F) * fraction;

            mStackCount++;
        }
        // Detect when the count > 3 and pages at the center of bottom
        else if (viewPager.getCount() > MIN_CYCLE_COUNT && position >= -2.0F && position < -1.0F) {
            final float fraction = 1.0F + (position + 1.0F);

            scale = mMinPageScale;
            translation = (pageSize * 2.0F) - ((pageSize +
                    pageMinScaleEdgeOffset - mMinPageScaleOffset) * fraction);

        }
        // Transform from minimum scale to medium scale or max
        else if (position >= -1.0F && position <= -0.5F) {
            final float positiveFraction = 1.0F + (position + 0.5F) * 2.0F;
            final float negativeFraction = 1.0F - positiveFraction;

            if (mIsMediumScaled) {
                final float startOffset = pageSize - pageSubScaleByOffset -
                        pageMaxScaleEdgeOffset + mMinPageScaleOffset;

                scale = (mMinPageScale + mCenterScaleBy) - (mCenterScaleBy * negativeFraction);
                translation = startOffset - ((startOffset - pageSubScaleEdgeOffset +
                        mCenterPageScaleOffset) * positiveFraction);
            } else {
                final float startOffset =
                        pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset;

                scale = (mMaxPageScale) - ((mMaxPageScale - mMinPageScale) * negativeFraction);
                translation = (startOffset) - ((startOffset - pageMaxScaleEdgeOffset +
                        mCenterPageScaleOffset) * positiveFraction);
            }
        }
        // Transform from center or max to max
        else if (position >= -0.5F && position <= 0.0F) {
            final float fraction = -position * 2.0F;

            scale = mMaxPageScale - (mIsMediumScaled ? mCenterScaleBy * fraction : 0.0F);
            translation = ((mIsMediumScaled ? pageSubScaleEdgeOffset : pageMaxScaleEdgeOffset) -
                    mCenterPageScaleOffset) * fraction;
        }
        // Transform from max to center or max
        else if (position >= 0.0F && position <= 0.5F) {
            final float negativeFraction = position * 2.0F;
            final float positiveFraction = 1.0F - negativeFraction;

            scale = !mIsMediumScaled ? mMaxPageScale :
                    (mMinPageScale + mCenterScaleBy) + (mCenterScaleBy * positiveFraction);
            translation = (-(mIsMediumScaled ? pageSubScaleEdgeOffset : pageMaxScaleEdgeOffset) +
                    mCenterPageScaleOffset) * negativeFraction;
        }
        // Transform from center or max to min
        else if (position >= 0.5F && position <= 1.0F) {
            final float negativeFraction = (position - 0.5F) * 2.0F;
            final float positiveFraction = 1.0F - negativeFraction;

            if (mIsMediumScaled) {
                scale = mMinPageScale + (mCenterScaleBy * positiveFraction);
                translation = (-pageSubScaleEdgeOffset + mCenterPageScaleOffset) + ((-pageSize +
                        pageSubScaleByOffset + pageMaxScaleEdgeOffset + pageSubScaleEdgeOffset
                        - mMinPageScaleOffset - mCenterPageScaleOffset) * negativeFraction);
            } else {
                scale = mMinPageScale + ((mMaxPageScale - mMinPageScale) * positiveFraction);
                translation = (-pageMaxScaleEdgeOffset + mCenterPageScaleOffset) +
                        ((-pageSize + pageMaxScaleEdgeOffset + pageMinScaleEdgeOffset -
                                mMinPageScaleOffset - mCenterPageScaleOffset) * negativeFraction);
            }
        }
        // Detect when the count > 3 and pages at the center of bottom
        else if (viewPager.getCount() > MIN_CYCLE_COUNT && position > 1.0F && position <= 2.0F) {
            final float negativeFraction = 1.0F + (position - 1.0F);
            final float positiveFraction = 1.0F - negativeFraction;

            scale = mMinPageScale;
            translation = -(pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset) +
                    ((pageSize + pageMinScaleEdgeOffset - mMinPageScaleOffset) * positiveFraction);
        }
        // Detect when the count <= 3 and another page of side stack not placed
        else if (viewPager.getCount() < MIN_CYCLE_COUNT + 1 && mStackCount == 0 &&
                position > 1.0F && position < 2.0F) {
            final float fraction = 1.0F - position;

            scale = mMinPageScale;
            translation = -(pageSize - pageMinScaleEdgeOffset + mMinPageScaleOffset) +
                    ((pageSize * 2.0F - pageMinScaleOffset - mMinPageScaleOffset * 2.0F) * fraction);

            mStackCount++;
        } else {
            // Reset values
            scale = mMinPageScale;
            translation = 0.0F;
        }

        // Scale page
        ViewCompat.setScaleX(page, scale);
        ViewCompat.setScaleY(page, scale);

        // Translate page
        ViewCompat.setTranslationX(page, translation);

        boolean needBringToFront = false;
        if (viewPager.getCount() == MIN_CYCLE_COUNT - 1) mIsLeftPageBringToFront = true;

        // Switch to handle what direction we move to know how need bring to front out pages
        switch (mOuterPageScrolledState) {
            case GOING_LEFT:
                // Reset left page is bring
                mIsLeftPageBringToFront = false;
                // Now we handle where we scroll in outer and inner left direction
                if (mInnerPageScrolledState == PageScrolledState.GOING_LEFT) {
                    // This is another flag which detect if right was not bring to front
                    // and set positive flag
                    if (position > -0.5F && position <= 0.0F) {
                        if (!mIsRightPageBringToFront) {
                            mIsRightPageBringToFront = true;
                            needBringToFront = true;
                        }
                    }
                    // Position of center page and we need bring to front immediately
                    else if (position >= 0.0F && position < 0.5F) needBringToFront = true;
                        // If right was not bring we need to set it up and detect if there no bounds
                    else if (position > 0.5F && position < 1.0F && !mIsRightPageBringToFront &&
                            viewPager.getChildCount() > MIN_CYCLE_COUNT)
                        needBringToFront = true;
                } else {
                    // We move to the right and detect if position if under half of path
                    if (mPageScrolledPositionOffset < 0.5F &&
                            position > -0.5F && position <= 0.0F) needBringToFront = true;
                }
                break;
            case GOING_RIGHT:
                // Reset right page is bring
                mIsRightPageBringToFront = false;
                // Now we handle where we scroll in outer and inner right direction
                if (mInnerPageScrolledState == PageScrolledState.GOING_RIGHT) {
                    // This is another flag which detect if left was not bring to front
                    // and set positive flag
                    if (position >= 0.0F && position < 0.5F) {
                        if (!mIsLeftPageBringToFront) {
                            mIsLeftPageBringToFront = true;
                            needBringToFront = true;
                        }
                    }
                    // Position of center page and we need bring to front immediately
                    else if (position > -0.5F && position <= 0.0F) needBringToFront = true;
                        // If left was not bring we need to set it up and detect if there no bounds
                    else if (position > -1.0F && position < -0.5F && !mIsLeftPageBringToFront &&
                            viewPager.getChildCount() > MIN_CYCLE_COUNT)
                        needBringToFront = true;
                } else {
                    // We move to the left and detect if position if over half of path
                    if (mPageScrolledPositionOffset > 0.5F &&
                            position >= 0.0F && position < 0.5F) needBringToFront = true;
                }
                break;
            default:
                // If is data set changed we need to hard reset page bring flags
                if (viewPager.ismIsDataSetChanged()) {
                    mIsLeftPageBringToFront = false;
                    mIsRightPageBringToFront = false;
                }
                // There is one of the general logic which is calculate
                // what page must be arrived first from idle state
                else {
                    // Detect different situations of is there a page was bring or
                    // we just need to bring it again to override drawing order

                    if (!mWasPlusOne && position == 1.0F) mWasPlusOne = true;
                    else if (mWasPlusOne && position == -1.0F) mIsLeftPageBringToFront = true;
                    else if ((!mWasPlusOne && position == -1.0F) ||
                            (mWasPlusOne && mIsLeftPageBringToFront && position == -2.0F))
                        mIsLeftPageBringToFront = false;

                    if (!mWasMinusOne && position == -1.0F) mWasMinusOne = true;
                    else if (mWasMinusOne && position == 1.0F) mIsRightPageBringToFront = true;
                    else if ((!mWasMinusOne && position == 1.0F) ||
                            (mWasMinusOne && mIsRightPageBringToFront && position == 2.0F))
                        mIsRightPageBringToFront = false;
                }

                // Always bring to front is center position
                if (position == 0.0F) needBringToFront = true;
                break;
        }

        // Bring to front if needed
        if (needBringToFront) {
            page.bringToFront();
            viewPager.invalidate();
        }

    }

    // Enable hardware layer when transform pages
    private void enableHardwareLayer(final View v) {
        final int layerType = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT ?
                View.LAYER_TYPE_NONE : View.LAYER_TYPE_HARDWARE;
        if (v.getLayerType() != layerType) v.setLayerType(layerType, null);
    }

    // Disable hardware layer when idle
    private void disableHardwareLayers() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) return;
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            final View child = viewPager.getChildAt(i);
            if (child.getLayerType() != View.LAYER_TYPE_NONE)
                child.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    // Detect is we are idle in pageScrolled() callback, not in scrollStateChanged()
    private boolean isSmallPositionOffset(float positionOffset) {
        return Math.abs(positionOffset) < 0.0001F;
    }

    // Recalculate scale by variable
    private void resetScaleBy() {
        mCenterScaleBy = (mMaxPageScale - mMinPageScale) * 0.5F;
    }

    // Page scrolled state
    private enum PageScrolledState {
        IDLE, GOING_LEFT, GOING_RIGHT
    }

    protected final ViewPager.OnPageChangeListener mInfinityCyclePageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
            // Reset stack count on each scroll offset
            mStackCount = 0;

            // We need to rewrite states when is dragging and when setCurrentItem() from idle
            if (mState != ViewPager.SCROLL_STATE_SETTLING || viewPager.isInitialItem()) {
                // Detect first state from idle
                if (mOuterPageScrolledState == PageScrolledState.IDLE && positionOffset > 0) {
                    mPageScrolledPosition = viewPager.getCurrentItem();
                    mOuterPageScrolledState = position == mPageScrolledPosition ?
                            PageScrolledState.GOING_LEFT : PageScrolledState.GOING_RIGHT;
                }

                // Rewrite scrolled state when switch to another edge
                final boolean goingRight = position == mPageScrolledPosition;
                if (mOuterPageScrolledState == PageScrolledState.GOING_LEFT && !goingRight)
                    mOuterPageScrolledState = PageScrolledState.GOING_RIGHT;
                else if (mOuterPageScrolledState == PageScrolledState.GOING_RIGHT && goingRight)
                    mOuterPageScrolledState = PageScrolledState.GOING_LEFT;
            }

            // Rewrite inner dynamic scrolled state
            if (mPageScrolledPositionOffset <= positionOffset)
                mInnerPageScrolledState = PageScrolledState.GOING_LEFT;
            else mInnerPageScrolledState = PageScrolledState.GOING_RIGHT;
            mPageScrolledPositionOffset = positionOffset;

            // Detect if is idle in pageScrolled() callback to transform pages last time
            if ((isSmallPositionOffset(positionOffset) ? 0 : positionOffset) == 0) {
                // Reset states and flags on idle
                disableHardwareLayers();

                mInnerPageScrolledState = PageScrolledState.IDLE;
                mOuterPageScrolledState = PageScrolledState.IDLE;

                mWasMinusOne = false;
                mWasPlusOne = false;
                mIsLeftPageBringToFront = false;
                mIsRightPageBringToFront = false;

                viewPager.setInitialItem(false);
            }

        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mState = state;
        }
    };


    public static class Builder {
        private float minPageScaleOffset;
        private float centerPageScaleOffset;
        private float minPageScale;
        private float maxPageScale;
        private float centerScaleBy;
        private boolean isMediumScaled;

        private SimpleViewPager viewPager;

        public Builder(SimpleViewPager viewPager) {
            this.viewPager = viewPager;
        }

        public Builder setMinPageScaleOffset(float scaleOffset) {
            this.minPageScaleOffset = scaleOffset;
            return this;
        }

        public Builder setCenterPageScaleOffset(float centerPageScaleOffset) {
            this.centerPageScaleOffset = centerPageScaleOffset;
            return this;
        }

        public Builder setMinPageScale(float minPageScale) {
            this.minPageScale = minPageScale;
            return this;
        }

        public Builder setMaxPageScale(float maxPageScale) {
            this.maxPageScale = maxPageScale;
            return this;
        }

    }

}
