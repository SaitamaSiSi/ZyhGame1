package com.zyh.ZyhG1.ui.AndroidStudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import androidx.exifinterface.media.ExifInterface;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.zyh.ZyhG1.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Fragment3 extends Fragment {
    private Context _context;
    private final int TAKE_PHOTO = 1;
    private final int FROM_ALBUM = 2;
    private Uri _imageUri;
    private File _outputImage;
    private ImageView _imageView;
    private MediaPlayer _mediaPlayer = new MediaPlayer();
    private VideoView _videoView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        _context = view.getContext();

        initPicFunc(view);

        initMp3Func(view);

        initVideoFunc(view);

        return view;
    }

    private void initPicFunc(View view) {
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
    }

    private void initMediaPlayer() {
        try {
            AssetFileDescriptor fd = _context.getAssets().openFd("伍佰-晚风.mp3");
            _mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            _mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initMp3Func(View view) {
        initMediaPlayer();
        /*
        Button loadBtn = view.findViewById(R.id.f3_btn_load_mp3);
        loadBtn.setOnClickListener((v) -> {
            _mediaPlayer.reset();
            initMediaPlayer();
        });
        **/

        Button playBtn = view.findViewById(R.id.f3_btn_play_mp3);
        playBtn.setOnClickListener((v) -> {
            if (!_mediaPlayer.isPlaying()) {
                _mediaPlayer.start(); // 开始播放
            }
        });
        Button pauseBtn = view.findViewById(R.id.f3_btn_pause_mp3);
        pauseBtn.setOnClickListener((v) -> {
            if (_mediaPlayer.isPlaying()) {
                _mediaPlayer.pause(); // 暂停播放
            }
        });
        Button stopBtn = view.findViewById(R.id.f3_btn_stop_mp3);
        stopBtn.setOnClickListener((v) -> {
            if (_mediaPlayer.isPlaying()) {
                _mediaPlayer.reset(); // 停止播放
                initMediaPlayer();
            }
        });
    }

    private void initVideoFunc(View view) {
        _videoView = view.findViewById(R.id.f3_video_view);

        String videoUrl = "android.resource://" +
                _context.getPackageName() +
                "/" + R.raw.video;
        Uri uri = Uri.parse(videoUrl);
        _videoView.setVideoURI(uri);
        /*
        Button loadBtn = view.findViewById(R.id.f3_btn_load_video);
        loadBtn.setOnClickListener((v) -> {
            _videoView.suspend();
            Uri uri = Uri.parse("android.resource://" + _context.getPackageName() + "/" + R.raw.video);
            _videoView.setVideoURI(uri);
        });
        _videoView.requestFocus();
        _videoView.start();
        _videoView.setOnPreparedListener(mp -> mp.setOnInfoListener((mp1, what, extra) -> {
            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                _videoView.setBackgroundColor(Color.TRANSPARENT);
            }
            return true;
        }));
        **/

        Button playBtn = view.findViewById(R.id.f3_btn_play_video);
        playBtn.setOnClickListener((v) -> {
            if (!_videoView.isPlaying()) {
                _videoView.start(); // 开始播放
            }
        });
        Button pauseBtn = view.findViewById(R.id.f3_btn_pause_video);
        pauseBtn.setOnClickListener((v) -> {
            if (_videoView.isPlaying()) {
                _videoView.pause(); // 暂停播放
            }
        });
        Button resumeBtn = view.findViewById(R.id.f3_btn_resume_video);
        resumeBtn.setOnClickListener((v) -> {
            if (_videoView.isPlaying()) {
                _videoView.resume(); // 重新播放
            }
        });
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
            return BitmapFactory.decodeFileDescriptor(Objects.requireNonNull(_context.getContentResolver().openFileDescriptor(uri, "r")).getFileDescriptor());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        _mediaPlayer.stop();
        _mediaPlayer.release();
        _videoView.suspend();
    }
}
