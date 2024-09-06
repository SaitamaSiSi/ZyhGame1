package com.zyh.ZyhG1.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.zyh.ZyhG1.ui.Canvas.CanvasFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class CanvasFragmentAdapter extends FragmentStateAdapter {
    private LinkedHashMap<String, String> _tabTitles = new LinkedHashMap<>();
    private LinkedHashMap<String, CanvasFragment> _fragments = new LinkedHashMap<>();
    private List<Long> mFragmentHashCodes = new ArrayList<>();

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
        return _tabTitles.size();
    }

    @Override
    public long getItemId(int position) {
        return mFragmentHashCodes.get(position).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
        return mFragmentHashCodes.contains(itemId);
    }

    public CharSequence getPageTitle(int position) {
        return getStringAtPosition(position);
    }

    public void addFragment(String uuid, String title, long key, CanvasFragment fragment) {
        _tabTitles.put(uuid, title);
        _fragments.put(uuid, fragment);
        mFragmentHashCodes.add(key);
    }
    public void downFragment(String uuid) {
        ArrayList<String> keys = new ArrayList<>(_tabTitles.keySet());
        int index = keys.indexOf(uuid);
        if (index >= 1 && index < mFragmentHashCodes.size()) {
            Collections.swap(mFragmentHashCodes, index - 1, index);
        }
        _tabTitles = swapPre(_tabTitles, uuid);
        _fragments = swapPre(_fragments, uuid);
    }
    public void upFragment(String uuid) {
        ArrayList<String> keys = new ArrayList<>(_tabTitles.keySet());
        int index = keys.indexOf(uuid);
        if (index >= 0 && index < mFragmentHashCodes.size() - 1) {
            Collections.swap(mFragmentHashCodes, index, index + 1);
        }
        _tabTitles = swapNext(_tabTitles, uuid);
        _fragments = swapNext(_fragments, uuid);
    }
    public void delFragment(String uuid) {
        int index = 0;
        int i = 0;
        for (LinkedHashMap.Entry<String, String> entity : _tabTitles.entrySet()) {
            if (Objects.equals(entity.getValue(), uuid)) {
                index = i;
            }
            i++;
        }
        _tabTitles.remove(uuid);
        _fragments.remove(uuid);
        mFragmentHashCodes.remove(index);
    }
    public void clearFragment() {
        _tabTitles.clear();
        _fragments.clear();
        mFragmentHashCodes.clear();
    }

    public <K, V> LinkedHashMap<K, V> swapPre(LinkedHashMap<K, V> map, K sourceKey) {
        LinkedHashMap<K, V> ret = new LinkedHashMap<K, V>();
        // 找到目标key的位置
        ArrayList<K> keys = new ArrayList<>(map.keySet());
        int index = keys.indexOf(sourceKey);

        // 如果目标key不存在，或者它已经是第一个元素，则无法交换
        if (index <= 0 || index >= keys.size()) {
            return map;
        }

        // 获取交换值
        V sourceValue = map.get(sourceKey);
        K preKey = keys.get(index - 1);
        V preValue = map.get(preKey);

        // 重新赋值
        for (int i = 0; i < map.size(); i++) {
            if (i == index - 1) {
                ret.put(sourceKey, sourceValue);
            } else if (i == index) {
                ret.put(preKey, preValue);
            } else {
                K iKey = keys.get(i);
                V iValue = map.get(iKey);
                ret.put(iKey, iValue);
            }
        }

        return ret;
    }
    public <K, V> LinkedHashMap<K, V> swapNext(LinkedHashMap<K, V> map, K sourceKey) {
        LinkedHashMap<K, V> ret = new LinkedHashMap<K, V>();
        // 找到目标key的位置
        ArrayList<K> keys = new ArrayList<>(map.keySet());
        int index = keys.indexOf(sourceKey);

        // 如果目标key不存在，或者它已经是第一个元素，则无法交换
        if (index < 0 || index >= keys.size() - 1) {
            return map;
        }

        // 获取交换值
        V sourceValue = map.get(sourceKey);
        K nextKey = keys.get(index + 1);
        V nextValue = map.get(nextKey);

        // 重新赋值
        for (int i = 0; i < map.size(); i++) {
            if (i == index) {
                ret.put(nextKey, nextValue);
            } else if (i == index + 1) {
                ret.put(sourceKey, sourceValue);
            } else {
                K iKey = keys.get(i);
                V iValue = map.get(iKey);
                ret.put(iKey, iValue);
            }
        }

        return ret;
    }

    private CanvasFragment getFragmentAtPosition(int position) {
        if (_fragments == null || position < 0 || position >= _fragments.size()) {
            return null; // 位置无效或map为空
        }
        Iterator<LinkedHashMap.Entry<String, CanvasFragment>> iterator = _fragments.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            LinkedHashMap.Entry<String, CanvasFragment> entry = iterator.next();
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
        Iterator<LinkedHashMap.Entry<String, String>> iterator = _tabTitles.entrySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            LinkedHashMap.Entry<String, String> entry = iterator.next();
            if (index == position) {
                return entry.getValue(); // 返回找到的Fragment
            }
            index++;
        }

        return null; // 如果遍历完map还没有返回，说明位置无效
    }
}
