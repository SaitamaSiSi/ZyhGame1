package com.zyh.ZyhG1.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AnotherBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String extraData = intent.getStringExtra("BroadcastInfo");
        Toast.makeText(context, "Received 【" + extraData + "】 in AnotherBroadcastReceiver", Toast.LENGTH_SHORT).show();
    }
}
