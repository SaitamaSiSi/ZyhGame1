package com.zyh.ZyhG1.ui.AiConversation;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.model.Msg;
import com.zyh.ZyhG1.adapter.MsgAdapter;
import com.zyh.ZyhG1.model.OllamaRequest;
import com.zyh.ZyhG1.model.OllamaResponse;
import com.zyh.ZyhG1.network.AppService;
import com.zyh.ZyhG1.network.OkHttpHelper;
import com.zyh.ZyhG1.network.RequestHelper;
import com.zyh.ZyhG1.singleton.OllamaServiceCreator;
import com.zyh.ZyhG1.ui.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AiConversationActivity extends BaseActivity {
    String msg = "Android AiConversationActivity: ";

    private static final int Type_Disable = 0;
    private static final int Type_Enable = 1;
    private final RequestHelper _requestHelper = new RequestHelper();
    private final OkHttpHelper _okHttpHelper = new OkHttpHelper();
    private final ArrayList<Msg> _msgList = new ArrayList<>();
    private MsgAdapter _adapter = null;
    RecyclerView _recyclerView;
    EditText _inputText;
    Button _send;
    ProgressBar _processBar;
    TextView _textView;

    /** 当活动第一次被创建时调用 */
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Log.d(msg, "The onCreate() event");
        setContentView(R.layout.ai_conversation);

        init();
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
        _adapter = new MsgAdapter(_msgList);
        _recyclerView.setAdapter(_adapter);
        _send = findViewById(R.id.ai_send);
        _processBar = findViewById(R.id.ai_process);
        _textView = findViewById(R.id.ai_process_text);
    }

    private void init() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        _msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SEND);
        _msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        _msgList.add(msg3);

        _recyclerView = findViewById(R.id.ai_recyclerView);
        _inputText = findViewById(R.id.ai_inputText);
    }

    public void OnSendClick(View view) {
        String content = String.valueOf(_inputText.getText());
        if (!content.isEmpty()) {
            SetMsg(Msg.TYPE_SEND, content);
            // 清空输入框中的内容
            _inputText.setText("");
            SetStatus(Type_Disable);

            /* 方法一 网络请求需要在子线程中完成
            new Thread(() -> {
                try {
                    // String retContent = _requestHelper.post("http://192.168.100.198:11434/api/generate", "{\"model\": \"qwen\",\"prompt\": \"" + content + "\",\"stream\": false}");//调用我们写的post方法
                    String retContent = _okHttpHelper.post("http://192.168.100.198:11434/api/generate", "{\"model\": \"qwen\",\"prompt\": \"" + content + "\",\"stream\": false}");

                    if (!retContent.isEmpty()) {
                        Gson gson = new Gson();
                        OllamaResponse ollamaResponse = gson.fromJson(retContent, OllamaResponse.class);
                        if (ollamaResponse != null && !ollamaResponse.response.isEmpty()) {
                            runOnUiThread(() -> {
                                SetMsg(Msg.TYPE_RECEIVED, ollamaResponse.response);
                            });
                        }
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
                finally {
                    runOnUiThread(() -> {
                        SetStatus(Type_Enable);
                    });
                }
            }).start();
            **/

            /* 方法二 OkHttp回调请求
            OkHttpHelper.post("http://192.168.100.198:11434/api/generate", "{\"model\": \"qwen\",\"prompt\": \"" + content + "\",\"stream\": false}", new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    // 异常情况处理
                    runOnUiThread(() -> {
                        SetStatus(Type_Enable);
                        Toast.makeText(AiConversationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    // 得到服务器返回的具体内容
                    if (response.body() != null) {
                        String responseData = response.body().string();
                        if (!responseData.isEmpty()) {
                            Gson gson = new Gson();
                            OllamaResponse ollamaResponse = gson.fromJson(responseData, OllamaResponse.class);
                            if (ollamaResponse != null && !ollamaResponse.response.isEmpty()) {
                                runOnUiThread(() -> {
                                    SetMsg(Msg.TYPE_RECEIVED, ollamaResponse.response);
                                });
                            }
                        }
                    }
                    runOnUiThread(() -> {
                        SetStatus(Type_Enable);
                    });
                }
            });
            **/

            /* 方法三 Retrofit **/
            AppService appService = OllamaServiceCreator.create(AppService.class);
            OllamaRequest requestBody = new OllamaRequest();
            requestBody.model = "qwen";
            requestBody.prompt = content;
            requestBody.stream = false;
            appService.getOllamaData("generate", requestBody).enqueue(new retrofit2.Callback<OllamaResponse>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<OllamaResponse> call, @NonNull retrofit2.Response<OllamaResponse> response) {
                    OllamaResponse ollamaResponse = response.body();
                    if (ollamaResponse != null && !ollamaResponse.response.isEmpty()) {
                        runOnUiThread(() -> {
                            SetMsg(Msg.TYPE_RECEIVED, ollamaResponse.response);
                        });
                    }
                    runOnUiThread(() -> {
                        SetStatus(Type_Enable);
                    });
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<OllamaResponse> call, @NonNull Throwable throwable) {
                    runOnUiThread(() -> {
                        SetStatus(Type_Enable);
                        Toast.makeText(AiConversationActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        }
    }

    public void SetMsg(int type, @NotNull String content) {
        Msg msg = null;
        switch (type) {
            case Msg.TYPE_SEND:
            case Msg.TYPE_RECEIVED:
                msg = new Msg(content, type);
                break;
            default:
                break;
        }
        if (msg != null) {
            _msgList.add(msg);
            // 当有新消息时,刷新RecyclearView中的显示
            _adapter.notifyItemInserted(_msgList.size() - 1);
            // 将RecyclerView定位到最后一行
            _recyclerView.scrollToPosition(_msgList.size() - 1);
        }
    }

    public void SetStatus(int type) {
        switch (type) {
            case Type_Disable:
                _inputText.setEnabled(false);
                _send.setEnabled(false);
                _processBar.setVisibility(ProgressBar.VISIBLE);
                _textView.setVisibility(TextView.VISIBLE);
                break;
            case Type_Enable:
                _inputText.setEnabled(true);
                _send.setEnabled(true);
                _processBar.setVisibility(ProgressBar.GONE);
                _textView.setVisibility(TextView.GONE);
                break;
            default:
                break;
        }
    }

    /* 退出按钮的点击事件*/
    public void quit(View view) {
        AiConversationActivity.this.finish();
    }
}
