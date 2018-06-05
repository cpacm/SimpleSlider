package com.cpacm.simpleslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;

import com.cpacm.library.indicator.ViewpagerIndicator.CirclePageIndicator;

public class BasicActivity extends AppCompatActivity {

    private SimpleViewPager simpleSlider;
    private CirclePageIndicator circlePageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpleSlider = (SimpleViewPager) findViewById(R.id.simple_slider);
        simpleSlider.setAdapter(new BasicPagerAdapter(this));
        simpleSlider.startAutoScroll(true);
        simpleSlider.setPageTransformer(new CyclePageTransformer(simpleSlider));//翻页动画
        //circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        //simpleSlider.setViewPagerIndicator(circlePageIndicator);//为viewpager设置指示器
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
}
