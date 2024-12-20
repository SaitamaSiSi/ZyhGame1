package com.zyh.ZyhG1;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyh.ZyhG1.network.AppService;
import com.zyh.ZyhG1.ui.AiConversation.AiConversationActivity;
import com.zyh.ZyhG1.ui.AndroidStudy.NotificationActivity;
import com.zyh.ZyhG1.ui.AndroidStudy.RunningPermissionActivity;
import com.zyh.ZyhG1.ui.AndroidStudy.ThreadActivity;
import com.zyh.ZyhG1.ui.BaseActivity;
import com.zyh.ZyhG1.ui.PtGame.PtGameActivity;
import com.zyh.ZyhG1.ui.Material.MaterialActivity;
import com.zyh.ZyhG1.ui.Canvas.CanvasActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {
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
        switch (current_select) {
            case "拼图游戏":
                Intent intent1 = new Intent(MainActivity.this, PtGameActivity.class);
                startActivity(intent1);
                break;
            case "Android学习":
                //Intent intent2 = new Intent(MainActivity.this, AndroidStudyActivity.class);
                Intent intent2 = new Intent("com.zyh.ZyhG1.ACTION_asStudy");
                intent2.addCategory("com.zyh.ZyhG1.CATEGORY_asStudy");
                intent2.putExtra("HiddenActionBar", true);
                // TODO 方法已弃用
                startActivityForResult(intent2, 1);
                break;
            case "应用通知":
                Intent intent3 = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent3);
                break;
            case "运行时申请权限":
                Intent intent4 = new Intent(MainActivity.this, RunningPermissionActivity.class);
                startActivity(intent4);
                break;
            case "AI问答":
                Intent intent5 = new Intent(MainActivity.this, AiConversationActivity.class);
                startActivity(intent5);
                break;
            case "多线程":
                Intent intent6 = new Intent(MainActivity.this, ThreadActivity.class);
                startActivity(intent6);
                break;
            case "Material Design":
                Intent intent7 = new Intent(MainActivity.this, MaterialActivity.class);
                startActivity(intent7);
                break;
            case "Canvas":
                Intent intent8 = new Intent(MainActivity.this, CanvasActivity.class);
                startActivity(intent8);
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
                    // 跳转页面关闭返回的信息
                    String retStr = data.getStringExtra("return");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onRequestClick(View view) {
        ProgressBar progressBar = findViewById(R.id.main_process);
        progressBar.setVisibility(View.VISIBLE);
        EditText editText = findViewById(R.id.main_edit_text);
        String url = editText.getText().toString();

        Retrofit retofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AppService appService = retofit.create(AppService.class);
        appService.getData("").enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        String responseData = response.body().string();
                        runOnUiThread(() -> {
                            TextView textView = findViewById(R.id.main_msg);
                            textView.setText(responseData);
                            progressBar.setVisibility(View.INVISIBLE);
                        });
                    }
                } catch (IOException e) {
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
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<ResponseBody> call, @NonNull Throwable throwable) {
                runOnUiThread(() -> {
                    TextView textView = findViewById(R.id.main_msg);
                    textView.setText(throwable.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                });
            }
        });
    }

    public void onQuitClick(View view) {
        MainActivity.this.finish();
    }

    /** 当活动即将可见时调用 */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /** 当活动可见时调用 */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /** 当其他活动获得焦点时调用 */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /** 当活动不再可见时调用 */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /** 当活动将被销毁时调用 */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
        }
    }
}