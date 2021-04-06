/*
 *  Copyright (C) 2020 cpacm
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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.animation.Interpolator;

import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;


public class BasicActivity extends AppCompatActivity {
    private final static int DEFAULT_SCROLL_DURATION = 500;


    SimpleViewPager simpleSlider, simpleSlider2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BasicPagerAdapter adapter = new BasicPagerAdapter(this);
        simpleSlider = findViewById(R.id.simple_slider);
        simpleSlider.setAdapter(adapter);
        simpleSlider.startAutoScroll(true);
        simpleSlider.setSliderTransformDuration(DEFAULT_SCROLL_DURATION, new SpringInterpolator());

        BasicPagerAdapter adapter2 = new BasicPagerAdapter(this);
        simpleSlider2 = findViewById(R.id.simple_slider2);
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
    private static final class SpringInterpolator implements Interpolator {

        private final static float FACTOR = 0.5F;

        @Override
        public float getInterpolation(final float input) {
            return (float) (Math.pow(2.0F, -10.0F * input) *
                    Math.sin((input - FACTOR / 4.0F) * (2.0F * Math.PI) / FACTOR) + 1.0F);
        }
    }
}
