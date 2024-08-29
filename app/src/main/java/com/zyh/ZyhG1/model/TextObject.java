package com.zyh.ZyhG1.model;

import android.graphics.Color;

import java.util.Objects;

public class TextObject extends BaseObject {
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

    public HorizontalType _horizontal = HorizontalType.LEFT;
    public VerticalType _vertical = VerticalType.TOP;
    public String _text = "文本内容";
    public int _fontSize = 25;
    public int _lineSpace = 0;
    public int _radius = 25;
    public int _borderWidth = 1;
    public String _fontColor = "红";
    public String _borderColor = "蓝";

    public float GetFontSize() {
        return _scale * _fontSize;
    }
    public float GetLineSpace() {
        return _scale * _lineSpace;
    }
    public float GetRadius() { return _radius; }
    public float GetBorderWidth() { return _scale * _borderWidth; }
    public int GetFontColor() {
        if (Objects.equals(_fontColor, "")) {
            return Color.RED;
        } else {
            return GetColor(_fontColor);
        }
    }
    public int GetBorderColor() {
        if (Objects.equals(_borderColor, "")) {
            return Color.BLUE;
        } else {
            return GetColor(_borderColor);
        }
    }

    public static int GetColor(String color) {
        switch (color) {
            case "黑":
            default:
                return Color.BLACK;
            case "白":
                return Color.WHITE;
            case "红":
                return Color.RED;
            case "黄":
                return Color.YELLOW;
            case "绿":
                return Color.GREEN;
            case "蓝":
                return Color.BLUE;
        }
    }
}