package com.zyh.ZyhG1.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ui.AndroidStudy.Fragment1;
import com.zyh.ZyhG1.ui.AndroidStudy.Fragment2;
import com.zyh.ZyhG1.ui.AndroidStudy.Fragment3;

public class SimpleFragmentAdapter extends FragmentStateAdapter {

    private final String[] _tabTitles = { "Fragment1", "Fragment2", "Fragment3"};
    private final Fragment[] _fragment = { new Fragment1(), new Fragment2(), new Fragment3() };
    private final int[] _image = { R.drawable.et, R.drawable.star, R.drawable.person };

    public SimpleFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return _fragment[position];
    }

    @Override
    public int getItemCount() {
        return _fragment.length;
    }

    public CharSequence getPageTitle(int position) {
        return _tabTitles[position];
    }

    public int getPageIcon(int position) {
        return _image[position];
    }
}
