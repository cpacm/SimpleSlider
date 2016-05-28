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

package net.cpacm.simpleslider;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cpacm.library.SimpleSliderLayout;
import net.cpacm.library.transformers.AccordionTransformer;
import net.cpacm.library.transformers.BackgroundToForegroundTransformer;
import net.cpacm.library.transformers.BaseTransformer;
import net.cpacm.library.transformers.CubeInTransformer;
import net.cpacm.library.transformers.DefaultTransformer;
import net.cpacm.library.transformers.DepthPageTransformer;
import net.cpacm.library.transformers.FadeTransformer;
import net.cpacm.library.transformers.FlipHorizontalTransformer;
import net.cpacm.library.transformers.FlipPageViewTransformer;
import net.cpacm.library.transformers.ForegroundToBackgroundTransformer;
import net.cpacm.library.transformers.RotateDownTransformer;
import net.cpacm.library.transformers.RotateUpTransformer;
import net.cpacm.library.transformers.StackTransformer;
import net.cpacm.library.transformers.TabletTransformer;
import net.cpacm.library.transformers.ZoomInTransformer;
import net.cpacm.library.transformers.ZoomOutSlideTransformer;
import net.cpacm.library.slider.BaseSliderView;
import net.cpacm.library.slider.ImageSliderView;
import net.cpacm.library.slider.OnSliderClickListener;

import java.util.ArrayList;
import java.util.List;

public class TransformActivity extends AppCompatActivity {

    private SimpleSliderLayout simpleSliderLayout;
    private RecyclerView transformList;
    private List<BaseTransformer> transformers;
    private List<String> transformerNames;
    private TransformAdapter transformAdapter;

    private String[] strs = {"Android", "H5", "Github", "iOS", "cpacm", "Java"};
    private int[] ids = {R.mipmap.android, R.mipmap.html5, R.mipmap.github, R.mipmap.ios, R.mipmap.cpacm, R.mipmap.java};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        simpleSliderLayout = (SimpleSliderLayout) findViewById(R.id.simple_slider);

        initTransforms();
        initBanner();

        transformList = (RecyclerView) findViewById(R.id.transform_list);
        transformList.setLayoutManager(new LinearLayoutManager(this));
        transformAdapter = new TransformAdapter();
        transformList.setAdapter(transformAdapter);
    }

    private void initBanner() {
        simpleSliderLayout.removeAllSlider();
        for (int i = 0; i < ids.length; i++) {
            ImageSliderView sliderView = new ImageSliderView(getApplicationContext());
            sliderView.empty(R.drawable.image_empty);
            sliderView.getImageView().setImageResource(ids[i]);
            sliderView.setPageTitle(strs[i]);
            sliderView.bundle(new Bundle());
            sliderView.getBundle()
                    .putString("type", strs[i]);
            sliderView.setOnSliderClickListener(new OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Snackbar.make(getWindow().getDecorView(), slider.getPageTitle(), Snackbar.LENGTH_SHORT)
                            .show();
                }
            });
            simpleSliderLayout.addSlider(sliderView);
        }
        simpleSliderLayout.setSliderTransformDuration(2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class TransformAdapter extends RecyclerView.Adapter<TransformAdapter.NormalTextViewHolder> {

        @Override
        public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalTextViewHolder(LayoutInflater.from(TransformActivity.this).inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(NormalTextViewHolder holder, final int position) {
            holder.mTextView.setText(transformerNames.get(position));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initBanner();
                    simpleSliderLayout.setPageTransformer(transformers.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return transformerNames.size();
        }

        public class NormalTextViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public NormalTextViewHolder(View view) {
                super(view);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }
        }
    }

    private void initTransforms() {
        transformers = new ArrayList<>();
        transformerNames = new ArrayList<>();

        transformers.add(new AccordionTransformer());
        transformerNames.add("AccordionTransformer");

        transformers.add(new BackgroundToForegroundTransformer());
        transformerNames.add("BackgroundToForegroundTransformer");

        transformers.add(new CubeInTransformer());
        transformerNames.add("CubeInTransformer");

        transformers.add(new DefaultTransformer());
        transformerNames.add("DefaultTransformer");

        transformers.add(new DepthPageTransformer());
        transformerNames.add("DepthPageTransformer");

        transformers.add(new FadeTransformer());
        transformerNames.add("FadeTransformer");

        transformers.add(new FlipHorizontalTransformer());
        transformerNames.add("FlipHorizontalTransformer");

        transformers.add(new FlipPageViewTransformer());
        transformerNames.add("FlipPageViewTransformer");

        transformers.add(new ForegroundToBackgroundTransformer());
        transformerNames.add("ForegroundToBackgroundTransformer");

        transformers.add(new RotateDownTransformer());
        transformerNames.add("RotateDownTransformer");

        transformers.add(new RotateUpTransformer());
        transformerNames.add("RotateUpTransformer");

        transformers.add(new StackTransformer());
        transformerNames.add("StackTransformer");

        transformers.add(new TabletTransformer());
        transformerNames.add("TabletTransformer");

        transformers.add(new ZoomInTransformer());
        transformerNames.add("ZoomInTransformer");

        transformers.add(new ZoomOutSlideTransformer());
        transformerNames.add("ZoomOutSlideTransformer");
    }
}
