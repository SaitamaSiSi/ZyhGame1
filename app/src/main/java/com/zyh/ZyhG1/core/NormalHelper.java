package com.zyh.ZyhG1.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.zyh.ZyhG1.model.BaseObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.List;

public class NormalHelper {
    public static String GetCurrentTime() {
        LocalDateTime now = LocalDateTime.now();

        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }

    public static void SaveBitmapWithCustomName(Bitmap bitmap, Context context) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "OverlayPicture" + NormalHelper.GetCurrentTime() + ".png");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        ContentResolver contentResolver = context.getContentResolver();
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream outputStream = null;
            if (imageUri != null) {
                outputStream = contentResolver.openOutputStream(imageUri);
            }
            if (outputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
                MediaScannerConnection.scanFile(context, new String[]{imageUri.toString()}, null, null);
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public static List<BaseObject> CloneListWithSerialization(List<BaseObject> originalList) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(originalList);

        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return (List<BaseObject>) ois.readObject();
    }
}
