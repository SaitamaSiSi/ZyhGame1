package com.zyh.ZyhG1.model;

public class BaseObject {
    public enum HorizontalType {
        LEFT,
        CENTER,
        RIGHT
    }
    public enum VerticalType {
        TOP,
        MIDDLE,
        BOTTOM
    }

    public HorizontalType _horizontal;
    public VerticalType _vertical;
}
