package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;

public class SwipeAdapter extends FragmentStatePagerAdapter {


    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment pageFragment = new PageFragment();
        Bundle buddle = new Bundle();
        buddle.putInt("pageNumber",position+1);
        pageFragment.setArguments(buddle);
        return pageFragment;

    }

    @Override
    public int getCount() {
        return 2;
    }
}
