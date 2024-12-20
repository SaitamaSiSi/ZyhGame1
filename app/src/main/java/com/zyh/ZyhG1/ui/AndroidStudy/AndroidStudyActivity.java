package com.zyh.ZyhG1.ui.AndroidStudy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.adapter.SimpleFragmentAdapter;
import com.zyh.ZyhG1.ui.BaseActivity;

public class AndroidStudyActivity extends BaseActivity {

    public static class TimeChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Time has changed", Toast.LENGTH_LONG).show();
        }
    }

    String msg = "Android AndroidStudyActivity: ";
    ViewPager2 _viewPager2;
    TabLayout _tabLayout;
    SimpleFragmentAdapter _adapter;
    TimeChangeReceiver _timeChangeReceiver = null;

    /** 当活动第一次被创建时调用 */
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.d(msg, "The onCreate() event");
        setContentView(R.layout.android_stuty);

        boolean extraData = getIntent().getBooleanExtra("HiddenActionBar", false);

        initTab();
        // initBroadCast();
    }

    public void initTab() {
        _viewPager2 = findViewById(R.id.as_viewPager2);
        _tabLayout = findViewById(R.id.as_tabLayout);
        // 创建FragmentStateAdapter的实例
        _adapter = new SimpleFragmentAdapter(this);
        _viewPager2.setAdapter(_adapter);

        // 将TabLayout与ViewPager2关联起来
        new TabLayoutMediator(_tabLayout, _viewPager2, (tab, position) -> {
            // 配置标签的标题或图标等
            tab.setText(_adapter.getPageTitle(position));
            tab.setIcon(_adapter.getPageIcon(position));
        }).attach();

        // 设置TabLayout的OnTabSelectedListener
        _tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                _viewPager2.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 可以在这里处理标签未选中的情况（如果需要的话）
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 可以在这里处理标签重新选中的情况（如果需要的话）
            }
        });

        // 设置ViewPager2的页面变化监听器
        _viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 可以在这里处理页面滚动的情况（如果需要的话）
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                // _tabLayout.selectTab(_tabLayout.getTabAt(position));
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int newState) {
                // 可以在这里处理页面滚动状态变化的情况（如果需要的话）
                super.onPageScrollStateChanged(newState);
            }
        });

        // 初始选中第一个标签（可选）
        if (_tabLayout.getTabCount() > 0) {
            _tabLayout.selectTab(_tabLayout.getTabAt(0));
        }

        _viewPager2.setOffscreenPageLimit(1);
        /*RecyclerView rv = (RecyclerView)_viewPager2.getChildAt(0);
        rv.setPadding(60, 0, 60, 0);
        rv.setClipToPadding(false);*/
    }

    public void initBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        _timeChangeReceiver = new TimeChangeReceiver();
        registerReceiver(_timeChangeReceiver, intentFilter);
    }

    public void sendBroadcast(View view) {
        Intent intent = new Intent("com.zyh.ZyhG1.MY_BROADCAST");
        intent.setPackage(getPackageName());
        intent.putExtra("BroadcastInfo", "This is broadcast info");
        // sendBroadcast(intent);
        sendOrderedBroadcast(intent, null);
    }

    /* 退出按钮的点击事件*/
    public void quit(View view) {
        Log.d(msg, "The quit() event");
        Intent intent = new Intent(AndroidStudyActivity.this, MainActivity.class);
        intent.putExtra("return", msg + "返回的数据");
        setResult(RESULT_OK, intent);
        AndroidStudyActivity.this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (_timeChangeReceiver != null) {
            unregisterReceiver(_timeChangeReceiver);
        }
    }
}
