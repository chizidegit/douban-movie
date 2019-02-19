package com.aaron.doubanmovie.bz.home;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.common.BaseActivity;
import com.aaron.doubanmovie.bz.in.InListFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tab)
    TabLayout mTab;
    @Bind(R.id.pager)
    ViewPager mPager;
    @Bind(R.id.app_bar)
    AppBarLayout mAppBar;

    private HomePagerAdapter mPagerAdapter;

    @OnClick(R.id.fab)
    void fabClicked() {
        String tag = mPagerAdapter.getFragmentTags().get(mTab.getSelectedTabPosition() + "");

        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment != null && currentFragment instanceof InListFragment) {
            ((InListFragment) currentFragment).refreshMovies();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initData() {
        super.initData();

        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), this);
    }

    @Override
    protected void initView() {
        super.initView();

        setSupportActionBar(mToolbar);

        mPager.setAdapter(mPagerAdapter);
        mTab.setupWithViewPager(mPager);
    }

}
