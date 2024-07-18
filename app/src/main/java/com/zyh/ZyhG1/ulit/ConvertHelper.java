package com.zyh.ZyhG1.ulit;

import android.app.Activity;
import android.content.Context;

public class ConvertHelper {
    public static Activity GetActivity(Context context) {
        if (context instanceof Activity) {
            // 现在你可以安全地使用 activity 变量了
            return (Activity) context;
        } else {
            // context 不是 Activity 的一个实例
            return null;
        }
    }

    public static <T>T GetConvert(Object obj, Class<T> clazz) {
        if (clazz.isInstance(obj)) {
            return clazz.cast(obj);
        } else {
            return null;
        }
    }

    public static <T extends Number>T method(T param) {
        return param;
    }
}
