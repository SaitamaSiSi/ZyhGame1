package com.zyh.ZyhG1.model;

public class Msg {
    public String _content;
    public int _type;
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    public Msg(String content, int type) {
        _content = content;
        _type = type;
    }
}
