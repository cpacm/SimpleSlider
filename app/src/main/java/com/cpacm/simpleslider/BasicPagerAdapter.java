package com.cpacm.simpleslider;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * @author cpacm 2018/5/18
 */
public class BasicPagerAdapter extends PagerAdapter {

    private List<Integer> covers;
    private LayoutInflater layoutInflater;

    public BasicPagerAdapter(Context context) {
        covers = getBannerList();
        layoutInflater = LayoutInflater.from(context);
    }

    public void setCovers(){
        covers.clear();
        covers.add(R.drawable.cover00);
        covers.add(R.drawable.cover01);
        covers.add(R.drawable.cover02);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view = layoutInflater.inflate(R.layout.viewpager_item, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.banner);
        iv.setImageResource(covers.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return covers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    private List<Integer> getBannerList() {
        List<Integer> coverList = new ArrayList();
        coverList.add(R.drawable.cover00);
        coverList.add(R.drawable.cover01);
        coverList.add(R.drawable.cover02);
        coverList.add(R.drawable.cover03);
        coverList.add(R.drawable.cover04);
        coverList.add(R.drawable.cover05);
        coverList.add(R.drawable.cover06);
        coverList.add(R.drawable.cover07);

        return coverList;
    }

    private String title[] = new String[]{"P5", "十六夜泪", "屠苏", "Oricon", "白羊", "周刊", "幻音", "NEWS"};
}
