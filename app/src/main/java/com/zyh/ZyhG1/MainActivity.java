package com.zyh.ZyhG1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.zyh.ZyhG1.network.RequestHelper;
import com.zyh.ZyhG1.ui.AiConversation.AiConversationActivity;
import com.zyh.ZyhG1.ui.AndroidStudy.RunningPermissionActivity;
import com.zyh.ZyhG1.ui.BaseActivity;
import com.zyh.ZyhG1.ui.Login.LoginActivity;
import com.zyh.ZyhG1.ui.PtGame.PtGameActivity;

import java.util.Objects;

public class MainActivity extends BaseActivity {
    String activity_title = "Android MainActivity: ";
    // 下拉选择器
    Spinner game_selector;
    // 下拉选项内容
    String[] game_select_options;
    // 当前选择
    String current_select;

    static {
        System.loadLibrary("ZyhG1");
    }

    /** 当活动第一次被创建时调用 */
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        System.out.println("The onCreate() event");
        setContentView(R.layout.activity_main);
        /*if (state != null) {
            String tempData = state.getString("as_temp_data");
            Log.d(activity_title, String.format("tempData is %s", tempData));
        }*/
        // 隐藏ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
    }

    public void initView() {
        game_selector = findViewById(R.id.game_selector);
        game_select_options = getResources().getStringArray(R.array.Games);

        // （可选）设置Spinner选项被选中的监听器
        game_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在这里处理选项被选中的逻辑
                current_select = game_select_options[position];
                // ...
                Log.d(activity_title, "Select => " + current_select);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 在这里处理没有选项被选中的逻辑（如果需要）
            }
        });

        TextView textView = findViewById(R.id.main_msg);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        Button forceBtn = findViewById(R.id.main_force_offline_bt);
        forceBtn.setOnClickListener(view -> {
            Intent intent = new Intent("com.zyh.ZyhG1.FORCE_OFFLINE");
            sendBroadcast(intent);
        });
    }

    /** 由于页面销毁，保存临时数据 **/
    /*@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "Something you just typed";
        outState.putString("as_temp_data", tempData);
    }*/

    public void onOKClick(View view) {
        Log.d(activity_title, "The onOKClick() event");
        switch (current_select) {
            case "拼图游戏":
                Intent intent1 = new Intent(MainActivity.this, PtGameActivity.class);
                intent1.putExtra("HiddenActionBar", true);
                startActivity(intent1);
                break;
            case "Android学习":
                //Intent intent2 = new Intent(MainActivity.this, AndroidStudyActivity.class);
                Intent intent2 = new Intent("com.zyh.ZyhG1.ACTION_asStudy");
                intent2.addCategory("com.zyh.ZyhG1.CATEGORY_asStudy");
                intent2.putExtra("HiddenActionBar", true);
                // TODO 方法被弃用
                startActivityForResult(intent2, 1);
                break;
            case "百度":
                Intent intent3 = new Intent(Intent.ACTION_VIEW);
                intent3.setData(Uri.parse("https://www.baidu.com"));
                startActivity(intent3);
                break;
            case "运行时申请权限":
                Intent intent4 = new Intent(MainActivity.this, RunningPermissionActivity.class);
                intent4.putExtra("HiddenActionBar", true);
                startActivity(intent4);
                break;
            case "AI问答":
                Intent intent5 = new Intent(MainActivity.this, AiConversationActivity.class);
                intent5.putExtra("HiddenActionBar", true);
                startActivity(intent5);
                break;
            case "新游戏":
                Toast.makeText(this, "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Log.d(activity_title, Objects.requireNonNull(data.getStringExtra("return")));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestClick(View view) {
        Log.d(activity_title, "The OnLogin() event");
        ProgressBar progressBar = findViewById(R.id.main_process);
        progressBar.setVisibility(View.VISIBLE);
        EditText editText = findViewById(R.id.main_edit_text);
        String url = editText.getText().toString();
        //网络请求需要在子线程中完成
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                RequestHelper request = new RequestHelper();
                // String res = request.post("https://www.baidu.com", "username=root&password=12345");//调用我们写的post方法
                // String res = request.get("http://192.168.1.149:8200/");//调用我们写的get方法
                String res = request.get(url);
                Log.d(String.valueOf(Log.INFO), res);
                // 在这里你可以将responseBody设置为TextView的文本或其他UI更新操作
                // 但请注意，这需要在主线程上执行
                runOnUiThread(() -> {
                    TextView textView = findViewById(R.id.main_msg);
                    textView.setText(res);
                });
            } catch (InterruptedException e) {
                runOnUiThread(() -> {
                    TextView textView = findViewById(R.id.main_msg);
                    textView.setText(e.getMessage());
                });
            }
            finally {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.INVISIBLE);
                });
            }
        }).start();
    }

    public void onQuitClick(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        setResult(RESULT_OK, intent);
        MainActivity.this.finish();
    }

    /** 当活动即将可见时调用 */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(activity_title, "The onStart() event");
    }

    /** 当活动可见时调用 */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(activity_title, "The onResume() event");
    }

    /** 当其他活动获得焦点时调用 */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(activity_title, "The onPause() event");
    }

    /** 当活动不再可见时调用 */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(activity_title, "The onStop() event");
    }

    /** 当活动将被销毁时调用 */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(activity_title, "The onDestroy() event");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            Log.d(activity_title, "The onWindowFocusChanged() event => hasFocus");
        }
    }
}