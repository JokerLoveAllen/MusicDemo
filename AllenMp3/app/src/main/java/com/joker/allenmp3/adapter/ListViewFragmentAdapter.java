package com.joker.allenmp3.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ListViewFragmentAdapter extends FragmentPagerAdapter {
    private List<String> flag;
    private List<Fragment> fragmentList;

    public ListViewFragmentAdapter(FragmentManager fm,
                                   List<String> flag, List<Fragment> fragmentList) {
        super(fm);
        this.flag = flag;
        this.fragmentList = fragmentList;
    }



    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return flag.get(position);
    }
}
