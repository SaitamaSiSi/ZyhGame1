package com.zyh.ZyhG1.model;

public class BaseObject {
    public String _uuid = "";
    public int _order = 0;
    public int _x = 10;
    public int _y = 10;
    public int _w = 50;
    public int _h = 50;
    public int _angle = 0;
    public float _scale = 1;
    public float GetX() {
        return _scale * _x;
    }
    public float GetY() {
        return _scale * _y;
    }
    public float GetW() {
        return _scale * _w;
    }
    public float GetH() {
        return _scale * _h;
    }
    public float GetAngle() { return _angle; }
}
