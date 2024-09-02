package com.zyh.ZyhG1.model;

public class BaseObject {
    public String _uuid = "";
    public int _x = 10;
    public int _y = 10;
    public int _w = 50;
    public int _h = 50;
    public int _angle = 0;
    public float _scale = 1;
    public float GetX(boolean isOri) {
        if (isOri) {
            return _x;
        } else {
            return _scale * _x;
        }
    }
    public float GetY(boolean isOri) {
        if (isOri) {
            return _y;
        } else {
            return _scale * _y;
        }
    }
    public float GetW(boolean isOri) {
        if (isOri) {
            return _w;
        } else {
            return _scale * _w;
        }
    }
    public float GetH(boolean isOri) {
        if (isOri) {
            return _h;
        } else {
            return _scale * _h;
        }
    }
    public float GetAngle() { return _angle; }
}
