package com.zyh.ZyhG1.ui.AndroidStudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.compose.foundation.gestures.Orientation;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.zyh.ZyhG1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Fragment3 extends Fragment {
    private Context _context;
    private final int TAKE_PHOTO = 1;
    private final int FROM_ALBUM = 2;
    private Uri _imageUri;
    private File _outputImage;
    private ImageView _imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        _context = view.getContext();

        _imageView = view.findViewById(R.id.f3_image_view);

        Button takePhotoBtn = view.findViewById(R.id.f3_btn_take_photo);
        takePhotoBtn.setOnClickListener((v) -> {
            try {
                // 创建File对象，用于存储拍照后的图片 /sdcard/Android/data/<package name>/cache
                _outputImage = new File(_context.getExternalCacheDir(), "output_image.jpg");
                if (_outputImage.exists()) {
                    _outputImage.delete();
                }
                _outputImage.createNewFile();
                /* 解包，因为当前环境下永远为true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    _imageUri = FileProvider.getUriForFile(_context, "com.zyh.ZyhG1.fileprovider", _outputImage);
                } else {
                    Uri.fromFile(_outputImage);
                }
                **/
                _imageUri = FileProvider.getUriForFile(_context, "com.zyh.ZyhG1.fileprovider", _outputImage);
                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri);
                // TODO 方法已弃用
                startActivityForResult(intent, TAKE_PHOTO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button fromBtn = view.findViewById(R.id.f3_btn_from_album);
        fromBtn.setOnClickListener((v) -> {
            // 打开文件选择器
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // 指定只显示图片
            intent.setType("image/*");
            // TODO 方法已弃用
            startActivityForResult(intent, FROM_ALBUM);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    // 将拍摄的照片显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(_context.getContentResolver().openInputStream(_imageUri));
                        _imageView.setImageBitmap(rotateIfRequired(bitmap));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Bitmap bitmap = getBitmapFromUri(data.getData());
                    if (bitmap != null) {
                        _imageView.setImageBitmap(bitmap);
                    }
                }
                break;
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return BitmapFactory.decodeFileDescriptor(_context.getContentResolver().openFileDescriptor(uri, "r").getFileDescriptor());
        }
        catch (Exception e) {
            return null;
        }
    }

    private Bitmap rotateIfRequired(Bitmap bitmap) {
        try {
            ExifInterface exif = new ExifInterface(_outputImage.getPath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateBitmap(bitmap, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateBitmap(bitmap, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateBitmap(bitmap, 270);
                default:
                    return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(),
                matrix, true);
        bitmap.recycle(); // 回收不需要的对象
        return rotatedBitmap;
    }
}
