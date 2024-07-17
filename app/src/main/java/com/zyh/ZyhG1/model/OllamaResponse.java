package com.zyh.ZyhG1.model;

import java.util.ArrayList;

public class OllamaResponse {
    public String model;
    public String created_at;
    public String response;
    public boolean done;
    public String done_reason;
    public ArrayList<String> context = new ArrayList<>();
    public long total_duration;
    public long load_duration;
    public int prompt_eval_count;
    public long prompt_eval_duration;
    public int eval_count;
    public long eval_duration;
}
