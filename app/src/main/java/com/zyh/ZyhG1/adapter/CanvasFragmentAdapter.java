package com.zyh.ZyhG1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zyh.ZyhG1.ui.Canvas.CanvasFragment;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CanvasFragmentAdapter extends FragmentStateAdapter {
    private final LinkedHashMap<String, String> _tabTitles = new LinkedHashMap<>();
    private final LinkedHashMap<String, CanvasFragment> _fragments = new LinkedHashMap<>();

    public CanvasFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public CanvasFragment createFragment(int position) {
        return Objects.requireNonNull(getFragmentAtPosition(position));
    }

    @Override
    public int getItemCount() {
        return _fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return getStringAtPosition(position);
    }

    public void addFragment(String uuid, String title, CanvasFragment fragment) {
        _tabTitles.put(uuid, title);
        _fragments.put(uuid, fragment);
    }
    public void delFragment(String uuid) {
        _tabTitles.remove(uuid);
        _fragments.remove(uuid);
    }
    public void clearFragment() {
        _tabTitles.clear();
        _fragments.clear();
    }

    private CanvasFragment getFragmentAtPosition(int position) {
        if (_fragments == null || position < 0 || position >= _fragments.size()) {
            return null; // 位置无效或map为空
        }
        Iterator<Map.Entry<String, CanvasFragment>> iterator = _fragments.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, CanvasFragment> entry = iterator.next();
            if (index == position) {
                return entry.getValue(); // 返回找到的Fragment
            }
            index++;
        }

        return null; // 如果遍历完map还没有返回，说明位置无效
    }
    private String getStringAtPosition(int position) {

        if (_tabTitles == null || position < 0 || position >= _tabTitles.size()) {
            return null; // 位置无效或map为空
        }
        Iterator<Map.Entry<String, String>> iterator = _tabTitles.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (index == position) {
                return entry.getValue(); // 返回找到的Fragment
            }
            index++;
        }

        return null; // 如果遍历完map还没有返回，说明位置无效
    }
}
