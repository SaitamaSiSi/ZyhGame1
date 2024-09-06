package com.zyh.ZyhG1.components;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;

import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.zyh.ZyhG1.R;

import java.util.Objects;

public class ColorPickerDialog {
    private Dialog mDialog = null;
    private final Context mContext;
    private final com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener mConfirmClickListener;
    private final com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener mCancleClickListener;
    private final int mInitColor;
    private final String mTitle;
    private EditText mColorEdit;

    private ColorPickerDialog(Context context, int color, String title,
                              com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener confirmClickListener,
                              com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener cancleClickListener) {
        this.mContext = context;
        this.mInitColor = color;
        this.mTitle = title;
        this.mConfirmClickListener = confirmClickListener;
        this.mCancleClickListener = cancleClickListener;
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.layout_color_picker);
        Objects.requireNonNull(mDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        //mDialog.getWindow().setBackgroundDrawableResource();

        TextView mTitleView = mDialog.findViewById(R.id.color_picker_title);
        if (Objects.equals(mTitle, "")) {
            mTitleView.setVisibility(View.GONE);
        } else {
            mTitleView.setLineSpacing(5.0f, 1.0f);
            mTitleView.setText(mTitle);
        }

        mColorEdit = mDialog.findViewById(R.id.color_value);
        mColorEdit.setText("#" + Integer.toHexString(mInitColor).toUpperCase());
        com.skydoves.colorpickerview.ColorPickerView mColorPicker = mDialog.findViewById(R.id.color_picker_view);
        mColorPicker.setInitialColor(mInitColor);
        mColorPicker.setColorListener(new ColorEnvelopeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                String value = "#" + envelope.getHexCode().substring(2);
                mColorEdit.setText(value);
                mColorEdit.setSelection(value.length());
            }
        });

        AppCompatTextView tvConfifm = mDialog.findViewById(R.id.color_confirm);
        tvConfifm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = mColorEdit.getText().toString();
                if (!value.startsWith("#") || value.length() != 7) {
                    Toast.makeText(mContext, "颜色格式不正确，请重新选择或输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mConfirmClickListener != null) {
                    mConfirmClickListener.onClick(mColorEdit.getText().toString());
                }
                close();
            }
        });
        AppCompatTextView tvCancel = mDialog.findViewById(R.id.color_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCancleClickListener != null) {
                    mCancleClickListener.onClick("#" + Integer.toHexString(mInitColor).toUpperCase());
                }
                close();
            }
        });
        mDialog.show();
    }

    public void show() {
        if (mDialog != null) {
            mDialog.show();
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public static class Builder {
        private Context mContext;
        private int mInitColor;
        private String mTitle;
        private com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener mConfirmClickListener;
        private com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener mCancleClickListener;

        public com.zyh.ZyhG1.components.ColorPickerDialog.Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public com.zyh.ZyhG1.components.ColorPickerDialog.Builder setColor(int color) {
            mInitColor = color;
            return this;
        }

        public com.zyh.ZyhG1.components.ColorPickerDialog.Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public com.zyh.ZyhG1.components.ColorPickerDialog.Builder setConfirmClickListener(com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener confirmClickListener) {
            mConfirmClickListener = confirmClickListener;
            return this;
        }

        public com.zyh.ZyhG1.components.ColorPickerDialog.Builder setCancleClickListener(com.zyh.ZyhG1.components.ColorPickerDialog.OnClickListener cancleClickListener) {
            mCancleClickListener = cancleClickListener;
            return this;
        }


        public com.zyh.ZyhG1.components.ColorPickerDialog create() {
            return new com.zyh.ZyhG1.components.ColorPickerDialog(mContext, mInitColor, mTitle, mConfirmClickListener, mCancleClickListener);
        }

    }

    public interface OnClickListener {
        void onClick(String value);
    }
}
