/*
 *  Copyright (C) 2016 cpacm
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.cpacm.simpleslider;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cpacm.library.SimpleViewPager;

import com.cpacm.library.indicator.SpringIndicator.SpringIndicator;
import com.cpacm.library.indicator.ViewpagerIndicator.CirclePageIndicator;
import com.cpacm.library.indicator.ViewpagerIndicator.IconPageIndicator;
import com.cpacm.library.indicator.ViewpagerIndicator.LinePageIndicator;
import com.cpacm.library.indicator.ViewpagerIndicator.UnderlinePageIndicator;
import com.cpacm.library.transformers.ZoomOutSlideTransformer;

public class IndicatorActivity extends AppCompatActivity {

    private SimpleViewPager simpleSliderLayout;
    private CirclePageIndicator circlePageIndicator;
    private IconPageIndicator iconPageIndicator;
    private LinePageIndicator linePageIndicator;
    private UnderlinePageIndicator underlinePageIndicator;
    private SpringIndicator springIndicator;

    private static final int[] ICONS = new int[]{
            R.drawable.perm_group_calendar,
            R.drawable.perm_group_camera,
            R.drawable.perm_group_device_alarms,
            R.drawable.perm_group_location
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        simpleSliderLayout = (SimpleViewPager) findViewById(R.id.simple_slider);
        simpleSliderLayout.setAdapter(new BasicPagerAdapter(this));
        simpleSliderLayout.setPageTransformer(new ZoomOutSlideTransformer());//翻页动画

        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circle_indicator);
        circlePageIndicator.setViewPager(simpleSliderLayout);

        iconPageIndicator = (IconPageIndicator) findViewById(R.id.icon_indicator);
        iconPageIndicator.setIconRes(ICONS);
        iconPageIndicator.setViewPager(simpleSliderLayout);

        linePageIndicator = (LinePageIndicator) findViewById(R.id.line_indicator);
        linePageIndicator.setViewPager(simpleSliderLayout);

        underlinePageIndicator = (UnderlinePageIndicator) findViewById(R.id.underline_indicator);
        underlinePageIndicator.setViewPager(simpleSliderLayout);

        springIndicator = (SpringIndicator) findViewById(R.id.spring_indicator);
        springIndicator.setViewPager(simpleSliderLayout);
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
