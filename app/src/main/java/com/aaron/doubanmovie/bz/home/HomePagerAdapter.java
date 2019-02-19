package com.aaron.doubanmovie.bz.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.aaron.doubanmovie.R;
import com.aaron.doubanmovie.bz.in.InListFragment;
import com.aaron.doubanmovie.bz.soon.SoonListFragment;
import com.aaron.doubanmovie.bz.top.TopListFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aaronchan on 16/4/27.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private static final int COUNT = 3;

    private Context mContext;
    private Map<String, String> mFragmentTags;
    private int[] mPageTitles = new int[]{
            R.string.title_fragment_in,
            R.string.title_fragment_soon,
            R.string.title_fragment_top
    };

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        mContext = context;
        mFragmentTags = new HashMap<>();
    }

    public Map<String, String> getFragmentTags() {
        return mFragmentTags;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = InListFragment.newInstance();
                break;
            case 1:
                fragment = SoonListFragment.newInstance();
                break;
            case 2:
                fragment = TopListFragment.newInstance();
                break;
            default:
                fragment = InListFragment.newInstance();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(mPageTitles[position]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);

        // save the appropriate fragment tag depending on position
        switch (position) {
            case 0:
                String firstTag = createdFragment.getTag();
                mFragmentTags.put("0", firstTag);
                break;
            case 1:
                String secondTag = createdFragment.getTag();
                mFragmentTags.put("1", secondTag);
                break;
            case 2:
                String thirdTag = createdFragment.getTag();
                mFragmentTags.put("2", thirdTag);
                break;
        }
        return createdFragment;
    }
}
