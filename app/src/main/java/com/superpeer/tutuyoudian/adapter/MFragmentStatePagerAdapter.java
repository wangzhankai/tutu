package com.superpeer.tutuyoudian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private String[] titleArray;

    public MFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titleArray) {
        super(fm);
        this.mFragments = fragments;
        this.titleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }
}