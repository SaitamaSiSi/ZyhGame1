package com.zyh.ZyhG1.ui.Canvas;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.adapter.CanvasFragmentAdapter;
import com.zyh.ZyhG1.core.NormalHelper;
import com.zyh.ZyhG1.core.UUIDGenerator;
import com.zyh.ZyhG1.model.BaseObject;
import com.zyh.ZyhG1.model.ImgObject;
import com.zyh.ZyhG1.model.TextObject;
import com.zyh.ZyhG1.ui.BaseActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CanvasActivity extends BaseActivity {
    private CanvasBaseView m_view;
    private final int TO_DIR = 1;
    private final int FROM_ALBUM = 2;
    ViewPager2 m_viewPager2;
    TabLayout m_tabLayout;
    CanvasFragmentAdapter m_adapter;
    Spinner m_canvas_color;
    String[] m_color_opt;
    Spinner m_canvas_scale;
    String[] m_scale_opt;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.canvas_demo);

        m_view = findViewById(R.id.canvas_demo_view);

        InitViewPager();
    }

    protected void InitViewPager() {
        m_viewPager2 = findViewById(R.id.canvas_viewPager2);
        m_tabLayout = findViewById(R.id.canvas_tabLayout);
        // 创建FragmentStateAdapter的实例
        m_adapter = new CanvasFragmentAdapter(this);
        m_viewPager2.setAdapter(m_adapter);

        // 将TabLayout与ViewPager2关联起来
        new TabLayoutMediator(m_tabLayout, m_viewPager2, (tab, position) -> {
            // 配置标签的标题或图标等
            tab.setText(m_adapter.getPageTitle(position));
        }).attach();

        // 设置TabLayout的OnTabSelectedListener
        m_tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                m_viewPager2.setCurrentItem(tab.getPosition(), true);
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
        m_viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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

        m_viewPager2.setOffscreenPageLimit(1);

        m_canvas_color = findViewById(R.id.canvas_color_spinner);
        m_color_opt = getResources().getStringArray(R.array.ColorType);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
                R.array.ColorType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        m_canvas_color.setAdapter(colorAdapter);
        // 遍历Adapter中的数据
        m_canvas_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_view.ChangeCanvasColor(m_color_opt[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });
        m_canvas_scale = findViewById(R.id.canvas_scale_spinner);
        m_scale_opt = getResources().getStringArray(R.array.ScaleType);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> scaleAdapter = ArrayAdapter.createFromResource(this,
                R.array.ScaleType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        scaleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        m_canvas_scale.setAdapter(scaleAdapter);
        // 遍历Adapter中的数据
        m_canvas_scale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_view.ChangeCanvasScale(Float.parseFloat(m_scale_opt[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TO_DIR:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bitmap bitmap = Bitmap.createBitmap(m_view.getWidth(), m_view.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    m_view.draw(canvas);
                    // 将原始的Bitmap对象缩放到指定的宽度和高度
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 160, 160, true);

                    /*
                    // 创建Bitmap
                    Bitmap bitmap = Bitmap.createBitmap(m_view.getWidth(), m_view.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    m_view.draw(canvas);
                    // 创建一个Matrix对象用于缩放
                    Matrix matrix = new Matrix();
                    matrix.postScale((float) 160 / m_view.getWidth(), (float) 160 / m_view.getHeight());
                    // 创建一个新的Bitmap来存储缩放后的图像
                    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, m_view.getWidth(), m_view.getHeight(), matrix, true);
                    */

                    //Uri uri = data.getData();
                    //saveBitmapWithCustomName(uri, scaledBitmap);
                }
                break;
            case FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bitmap bitmap = getBitmapFromUri(data.getData());
                    if (bitmap != null) {
                        ImgObject img = new ImgObject();
                        img._uuid = UUIDGenerator.generateUUID();
                        img._bitmap = bitmap;
                        m_view.AddImg(img);
                        CanvasFragment fragment = new CanvasFragment();
                        setListener(img, fragment);
                        addFragment(img._uuid, "img", fragment);
                    }
                }
                break;
        }
    }

    private void saveBitmapWithCustomName(Bitmap bitmap) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "OverlayPicture" + NormalHelper.GetCurrentTime() + ".png");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        ContentResolver contentResolver = getContentResolver();
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream outputStream = null;
            if (imageUri != null) {
                outputStream = contentResolver.openOutputStream(imageUri);
            }
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
                MediaScannerConnection.scanFile(this, new String[]{imageUri.toString()}, null, null);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return BitmapFactory.decodeFileDescriptor(Objects.requireNonNull(this.getContentResolver().openFileDescriptor(uri, "r")).getFileDescriptor());
        }
        catch (Exception e) {
            return null;
        }
    }

    public void addImg(View view) {
        // 打开文件选择器
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // 指定只显示图片
        intent.setType("image/*");
        // TODO 方法已弃用
        startActivityForResult(intent, FROM_ALBUM);
    }

    public void addText(View view) {
        TextObject text = new TextObject();
        text._uuid = UUIDGenerator.generateUUID();
        m_view.AddText(text);
        CanvasFragment fragment = new CanvasFragment();
        setListener(text, fragment);
        addFragment(text._uuid, "text", fragment);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListener(BaseObject obj, CanvasFragment fragment) {
        fragment.SetBaseObject(obj);
        fragment.SetXChangeListener((uuid, x) -> {
            // 处理输入框中的数据变化
            m_view.ChangeX(uuid, x);
        });
        fragment.SetYChangeListener((uuid, y) -> {
            // 处理输入框中的数据变化
            m_view.ChangeY(uuid, y);
        });
        fragment.SetWChangeListener((uuid, w) -> {
            // 处理输入框中的数据变化
            m_view.ChangeW(uuid, w);
        });
        fragment.SetHChangeListener((uuid, h) -> {
            // 处理输入框中的数据变化
            m_view.ChangeH(uuid, h);
        });
        fragment.SetAngleChangeListener((uuid, angle) -> {
            // 处理输入框中的数据变化
            m_view.ChangeAngle(uuid, angle);
        });
        fragment.SetUpListener((uuid, msg) -> {
            // 处理输入框中的数据变化
            m_view.DownObj(uuid);
            m_adapter.downFragment(uuid);
            m_adapter.notifyDataSetChanged();
        });
        fragment.SetDownListener((uuid, msg) -> {
            // 处理输入框中的数据变化
            m_view.UpObj(uuid);
            //m_adapter.upFragment(uuid);
            //m_adapter.notifyDataSetChanged();
        });
        fragment.SetDelListener((uuid, msg) -> {
            // 处理输入框中的数据变化
            m_view.DelObj(uuid);
            //m_adapter.delFragment(uuid);
            //m_adapter.notifyDataSetChanged();
        });

        if (obj instanceof TextObject) {
            fragment.SetFontSizeChangeListener((uuid, fontSize) -> {
                // 处理输入框中的数据变化
                m_view.ChangeFontSize(uuid, fontSize);
            });
            fragment.SetFontColorChangeListener((uuid, fontColor) -> {
                // 处理输入框中的数据变化
                m_view.ChangeFontColor(uuid, fontColor);
            });
            fragment.SetBorderColorChangeListener((uuid, fontColor) -> {
                // 处理输入框中的数据变化
                m_view.ChangeBorderColor(uuid, fontColor);
            });
            fragment.SetFontBackColorChangeListener((uuid, fontColor) -> {
                // 处理输入框中的数据变化
                m_view.ChangeFontBackColor(uuid, fontColor);
            });
            fragment.SetBorderWidthChangeListener((uuid, borderWidth) -> {
                // 处理输入框中的数据变化
                m_view.ChangeBorderWidth(uuid, borderWidth);
            });
            fragment.SetRadiusChangeListener((uuid, radius) -> {
                // 处理输入框中的数据变化
                m_view.ChangeRadius(uuid, radius);
            });
            fragment.SetLineSpaceChangeListener((uuid, lineSpace) -> {
                // 处理输入框中的数据变化
                m_view.ChangeLineSpace(uuid, lineSpace);
            });
            fragment.SetHorizontalChangeListener((uuid, horizontalType) -> {
                // 处理输入框中的数据变化
                m_view.ChangeHorizontal(uuid, horizontalType);
            });
            fragment.SetVerticalChangeListener((uuid, verticalType) -> {
                // 处理输入框中的数据变化
                m_view.ChangeVertical(uuid, verticalType);
            });
            fragment.SetTextChangeListener((uuid, text) -> {
                // 处理输入框中的数据变化
                m_view.ChangeText(uuid, text);
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addFragment(String uuid, String title, CanvasFragment fragment) {
        m_adapter.addFragment(uuid, title, fragment);
        m_adapter.notifyDataSetChanged();
        if (m_tabLayout.getTabCount() > 0) {
            m_tabLayout.selectTab(m_tabLayout.getTabAt(m_tabLayout.getTabCount() - 1));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resume(View view) {
        m_view.Resume();
        m_adapter.clearFragment();
        m_adapter.notifyDataSetChanged();
    }

    public void save(View view) {
        Bitmap bitmap = Bitmap.createBitmap(m_view.getWidth(), m_view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        m_view.draw(canvas);
        // 将原始的Bitmap对象缩放到指定的宽度和高度
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 160, 160, true);
        saveBitmapWithCustomName(scaledBitmap);

        /* 自己选择文件夹 */
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        //startActivityForResult(intent, TO_DIR);
    }

    public void quit(View view) {
        Intent intent = new Intent(CanvasActivity.this, MainActivity.class);
        setResult(RESULT_OK, intent);
        CanvasActivity.this.finish();
    }
}


