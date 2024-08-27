package com.zyh.ZyhG1.ui.Canvas;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.zyh.ZyhG1.MainActivity;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.model.BaseObject;
import com.zyh.ZyhG1.model.ImgObject;
import com.zyh.ZyhG1.model.TextObject;
import com.zyh.ZyhG1.singleton.ActivityCollector;
import com.zyh.ZyhG1.ui.BaseActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class CanvasActivity extends BaseActivity {
    private CanvasBaseView m_view;
    private ImageView m_imgView;
    private EditText m_editText;
    private final int TO_DIR = 1;
    private final int FROM_ALBUM = 2;
    private TextObject m_text = new TextObject();
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.canvas_demo);

        m_view = findViewById(R.id.canvas_demo_view);
        m_imgView = findViewById(R.id.canvas_demo_save_img);
        m_editText = findViewById(R.id.canvas_input);

        Spinner vSpinner = findViewById(R.id.canvas_demo_v_spinner);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> vAdapter = ArrayAdapter.createFromResource(this,
                R.array.VerticalType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        vAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        vSpinner.setAdapter(vAdapter);
        vSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理选项被选中的逻辑
                switch(position) {
                    case 0:
                        m_text._vertical = BaseObject.VerticalType.TOP;
                        break;
                    case 1:
                        m_text._vertical = BaseObject.VerticalType.MIDDLE;
                        break;
                    case 2:
                        m_text._vertical = BaseObject.VerticalType.BOTTOM;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        Spinner hSpinner = findViewById(R.id.canvas_demo_h_spinner);
        // 创建一个包含选项的数组适配器
        ArrayAdapter<CharSequence> hAdapter = ArrayAdapter.createFromResource(this,
                R.array.HorizontalType, android.R.layout.simple_spinner_item);
        // 设置下拉选项框的样式
        hAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将适配器设置到Spinner中
        hSpinner.setAdapter(hAdapter);
        hSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理选项被选中的逻辑
                switch(position) {
                    case 0:
                        m_text._horizontal = BaseObject.HorizontalType.LEFT;
                        break;
                    case 1:
                        m_text._horizontal = BaseObject.HorizontalType.CENTER;
                        break;
                    case 2:
                        m_text._horizontal = BaseObject.HorizontalType.RIGHT;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        NumberPicker numberPicker = findViewById(R.id.canvas_demo_font_size);
        // 设置数字选择器的范围
        numberPicker.setMinValue(0); // 最小值
        numberPicker.setMaxValue(999); // 最大值
        numberPicker.setValue(48); // 初始值

        // 设置增量
        numberPicker.setWrapSelectorWheel(false); // 如果为true，选择器会在到达最大值后回到最小值

        // 监听选择变化
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                // 当值变化时执行的操作
                // 例如：更新UI或保存选择的值
                m_text._fontSize = newVal;
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
                    m_imgView.setImageBitmap(scaledBitmap);

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
                    // 设置缩放后的Bitmap到ImageView
                    m_imgView.setImageBitmap(scaledBitmap);
                    */

                    Uri uri = data.getData();
                    saveBitmapWithCustomName(uri, scaledBitmap, "OverlayPicture.png");
                }
                break;
            case FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bitmap bitmap = getBitmapFromUri(data.getData());
                    if (bitmap != null) {
                        ImgObject img = new ImgObject();
                        //img._bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.canvasbg);
                        img._bitmap = bitmap;
                        m_view.SetImg(img);
                    }
                }
                break;
        }
    }

    private void saveBitmapWithCustomName(Uri directoryUri, Bitmap bitmap, String fileName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        ContentResolver contentResolver = getContentResolver();
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream outputStream = contentResolver.openOutputStream(imageUri);
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
                MediaScannerConnection.scanFile(this, new String[]{imageUri.toString()}, null, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    public void choose(View view) {
        // 打开文件选择器
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // 指定只显示图片
        intent.setType("image/*");
        // TODO 方法已弃用
        startActivityForResult(intent, FROM_ALBUM);
    }

    public void resume(View view) {
        m_view.Resume();
    }

    public void submit(View view) {
        m_text._text = m_editText.getText().toString();
        m_text._lineSpace = 5;
        m_view.SetText(m_text);
    }

    public void save(View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, TO_DIR);
    }

    public void quit(View view) {
        Intent intent = new Intent(CanvasActivity.this, MainActivity.class);
        setResult(RESULT_OK, intent);
        CanvasActivity.this.finish();
        //ActivityCollector.finishAll();
    }
}


