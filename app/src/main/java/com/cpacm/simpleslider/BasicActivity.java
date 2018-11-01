package com.cpacm.simpleslider;

import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Interpolator;

import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;

import com.cpacm.library.indicator.ViewpagerIndicator.CirclePageIndicator;

public class BasicActivity extends AppCompatActivity {
    private final static int DEFAULT_SCROLL_DURATION = 500;


    SimpleViewPager simpleSlider, simpleSlider2;
    private BasicPagerAdapter adapter,adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new BasicPagerAdapter(this);
        simpleSlider = (SimpleViewPager) findViewById(R.id.simple_slider);
        simpleSlider.setAdapter(adapter);
        simpleSlider.startAutoScroll(true);
        simpleSlider.setSliderTransformDuration(DEFAULT_SCROLL_DURATION, new SpringInterpolator());

        adapter2 = new BasicPagerAdapter(this);
        simpleSlider2 = (SimpleViewPager) findViewById(R.id.simple_slider2);
        simpleSlider2.setAdapter(adapter2);
        simpleSlider2.startAutoScroll(true);
        simpleSlider2.setPageTransformer(new CyclePageTransformer(simpleSlider2));//翻页动画*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //spring interpolator
    private final class SpringInterpolator implements Interpolator {

        private final static float FACTOR = 0.5F;

        @Override
        public float getInterpolation(final float input) {
            return (float) (Math.pow(2.0F, -10.0F * input) *
                    Math.sin((input - FACTOR / 4.0F) * (2.0F * Math.PI) / FACTOR) + 1.0F);
        }
    }
}
