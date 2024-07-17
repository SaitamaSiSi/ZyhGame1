package com.zyh.ZyhG1.components;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ulit.ConvertHelper;

public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button titleBack = findViewById(R.id.title_back);
        if (titleBack != null) {
            titleBack.setOnClickListener(view -> {
                Activity activity = ConvertHelper.GetActivity(context);
                if (activity != null) {
                    activity.finish();
                }
            });
        }
        Button titleEdit = findViewById(R.id.title_edit);
        if (titleEdit != null) {
            titleEdit.setOnClickListener(view -> {
                Toast.makeText(context, "点击Edit按钮", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
