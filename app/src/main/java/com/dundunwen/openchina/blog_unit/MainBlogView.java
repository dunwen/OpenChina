package com.dundunwen.openchina.blog_unit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dundunwen.openchina.BaseFragment;
import com.dundunwen.openchina.R;
import com.dundunwen.openchina.adapters.BlogListVPAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dun on 16/6/21.
 */
public class MainBlogView extends BaseFragment {
    @BindView(R.id.blog_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.blog_view_pager)
    ViewPager mViewPage;

    String[] titlesOfTabLayout = new String[]{"综合", "推荐", "我的"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        getDatas();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blog, container, false);
        findViews(view);
        return view;
    }

    @Override
    public void findViews() {
    }
    public void findViews(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.blog_tab_layout);
        mViewPage = (ViewPager) view.findViewById(R.id.blog_view_pager);
    }

    @Override
    public void initViews() {
        List<Fragment> fragmentList = getFragments();
        BlogListVPAdapter adapter = new BlogListVPAdapter(getChildFragmentManager(),fragmentList,titlesOfTabLayout);
        mViewPage.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPage);
    }

    @Override
    public void getDatas() {

    }


    public List<Fragment> getFragments() {
        List<Fragment> list = new ArrayList<>(3);
        Fragment sunthesize = new SubBlogView();
        Bundle bundleOfSunthesize = new Bundle();
        bundleOfSunthesize.putString(SubBlogView.KEY_FRAGMENT_TYPE, SubBlogView.TYPE_SYNTHESIZE);
        sunthesize.setArguments(bundleOfSunthesize);

        Fragment recommend = new SubBlogView();
        Bundle bundleOfRecommend = new Bundle();
        bundleOfRecommend.putString(SubBlogView.KEY_FRAGMENT_TYPE, SubBlogView.TYPE_RECOMMEND);
        recommend.setArguments(bundleOfRecommend);

        Fragment me = new SubBlogView();
        Bundle bundleOfMe = new Bundle();
        bundleOfMe.putString(SubBlogView.KEY_FRAGMENT_TYPE, SubBlogView.TYPE_ME);
        me.setArguments(bundleOfMe);

        list.add(sunthesize);
        list.add(recommend);
        list.add(me);

        return list;
    }
}
