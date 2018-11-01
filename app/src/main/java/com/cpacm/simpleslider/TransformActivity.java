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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.AccordionTransformer;
import com.cpacm.library.transformers.BackgroundToForegroundTransformer;
import com.cpacm.library.transformers.BaseTransformer;
import com.cpacm.library.transformers.CubeInTransformer;
import com.cpacm.library.transformers.CubeOutTransformer;
import com.cpacm.library.transformers.DefaultTransformer;
import com.cpacm.library.transformers.DepthPageTransformer;
import com.cpacm.library.transformers.DrawerTransformer;
import com.cpacm.library.transformers.FadeTransformer;
import com.cpacm.library.transformers.FlipHorizontalTransformer;
import com.cpacm.library.transformers.FlipPageViewTransformer;
import com.cpacm.library.transformers.ForegroundToBackgroundTransformer;
import com.cpacm.library.transformers.RotateDownTransformer;
import com.cpacm.library.transformers.RotateUpTransformer;
import com.cpacm.library.transformers.ScaleInOutTransformer;
import com.cpacm.library.transformers.StackTransformer;
import com.cpacm.library.transformers.TabletTransformer;
import com.cpacm.library.transformers.ZoomInTransformer;
import com.cpacm.library.transformers.ZoomOutSlideTransformer;
import com.cpacm.library.transformers.ZoomOutTransformer;

import java.util.ArrayList;
import java.util.List;

public class TransformActivity extends AppCompatActivity {

    private SimpleViewPager simpleViewPager;
    private RecyclerView transformList;
    private List<BaseTransformer> transformers;
    private List<String> transformerNames;
    private TransformAdapter transformAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        simpleViewPager = (SimpleViewPager) findViewById(R.id.simple_slider);
        simpleViewPager.startAutoScroll(true);

        initTransforms();

        transformList = (RecyclerView) findViewById(R.id.transform_list);
        transformList.setLayoutManager(new LinearLayoutManager(this));
        transformAdapter = new TransformAdapter();
        transformList.setAdapter(transformAdapter);

        simpleViewPager.setAdapter(new BasicPagerAdapter(this));
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

    public class TransformAdapter extends RecyclerView.Adapter<TransformAdapter.NormalTextViewHolder> {

        private int selectIndex = -1;

        @Override
        public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NormalTextViewHolder(LayoutInflater.from(TransformActivity.this).inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final NormalTextViewHolder holder, final int position) {
            holder.mTextView.setText(transformerNames.get(position));
            if (selectIndex == position) {
                holder.mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            } else {
                holder.mTextView.setTextColor(getResources().getColor(android.R.color.black));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    simpleViewPager.setPageTransformer(transformers.get(position));
                    int lastPosition = selectIndex;
                    selectIndex = position;
                    notifyItemChanged(lastPosition);
                    notifyItemChanged(selectIndex);
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
        transformerNames.add(AccordionTransformer.class.getSimpleName());

        transformers.add(new BackgroundToForegroundTransformer());
        transformerNames.add(BackgroundToForegroundTransformer.class.getSimpleName());

        transformers.add(new CubeInTransformer());
        transformerNames.add(CubeInTransformer.class.getSimpleName());

        transformers.add(new CubeOutTransformer());
        transformerNames.add(CubeOutTransformer.class.getSimpleName());

        transformers.add(new DefaultTransformer());
        transformerNames.add(DefaultTransformer.class.getSimpleName());

        transformers.add(new DepthPageTransformer());
        transformerNames.add(DepthPageTransformer.class.getSimpleName());

        transformers.add(new DrawerTransformer());
        transformerNames.add(DrawerTransformer.class.getSimpleName());

        transformers.add(new FadeTransformer());
        transformerNames.add(FadeTransformer.class.getSimpleName());

        transformers.add(new FlipHorizontalTransformer());
        transformerNames.add(FlipHorizontalTransformer.class.getSimpleName());

        transformers.add(new FlipPageViewTransformer());
        transformerNames.add(FlipPageViewTransformer.class.getSimpleName());

        transformers.add(new ForegroundToBackgroundTransformer());
        transformerNames.add(ForegroundToBackgroundTransformer.class.getSimpleName());

        transformers.add(new RotateDownTransformer());
        transformerNames.add(RotateDownTransformer.class.getSimpleName());

        transformers.add(new RotateUpTransformer());
        transformerNames.add(RotateUpTransformer.class.getSimpleName());

        transformers.add(new ScaleInOutTransformer());
        transformerNames.add(ScaleInOutTransformer.class.getSimpleName());

        transformers.add(new StackTransformer());
        transformerNames.add(StackTransformer.class.getSimpleName());

        transformers.add(new TabletTransformer());
        transformerNames.add(TabletTransformer.class.getSimpleName());

        transformers.add(new ZoomInTransformer());
        transformerNames.add(ZoomInTransformer.class.getSimpleName());

        transformers.add(new ZoomOutSlideTransformer());
        transformerNames.add(ZoomOutSlideTransformer.class.getSimpleName());

        transformers.add(new ZoomOutTransformer());
        transformerNames.add(ZoomOutTransformer.class.getSimpleName());
    }
}
