package com.dundunwen.openchina.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dun on 16/6/22.
 */
public class BlogListVPAdapter extends FragmentPagerAdapter{
    List<Fragment> fragments;
    String[] titles;
    public BlogListVPAdapter(FragmentManager fm) {
        super(fm);
    }
    public BlogListVPAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles){
        this(fm);
        this.fragments = fragments;
        this.titles = titles;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
