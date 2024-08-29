package com.zyh.ZyhG1.ui.Canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyh.ZyhG1.model.BaseObject;
import com.zyh.ZyhG1.model.ImgObject;
import com.zyh.ZyhG1.model.TextObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class CanvasBaseView extends View {
    private int defaultColor = Color.BLACK;
    private float canvasScale = 1;
    private float bordScale;
    private Paint canvasPaint;
    private Paint textPaint;
    private Paint borderPaint;
    private final ArrayList<BaseObject> objList = new ArrayList<>();

    public CanvasBaseView(Context context) {
        super(context);
        init();
    }

    public CanvasBaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        canvasPaint = new Paint();

        textPaint = new Paint();
        //textPaint.setStyle(Paint.Style.STROKE);//描边模式
        textPaint.setStrokeWidth(1f);//设置画笔粗细度
        textPaint.setAntiAlias(true);//抗锯齿
        textPaint.setDither(true);//防抖动
        //设置是否为粗体文字
        //textPaint.setFakeBoldText(true);
        //设置下划线
        //textPaint.setUnderlineText(true);
        //设置带有删除线效果
        //textPaint.setStrikeThruText(true);
        //textPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        //textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        //textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE); // 设置为边框样式
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 没有必要再让 view 自己测量一遍了，浪费资源
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 指定期望的 size
        //默认属性
        int defaultWidth = 160;
        int width = resolveSize(defaultWidth, widthMeasureSpec);
        int defaultHeight = 160;
        int height = resolveSize(defaultHeight, heightMeasureSpec);
        // 设置大小
        //setMeasuredDimension(width, height);
        bordScale = (float) width / defaultHeight;
        setMeasuredDimension(width, (int)(width / canvasScale));
    }

    public void AddText(TextObject text)
    {
        text._scale = bordScale;
        objList.add(text);
        invalidate(); // 刷新
    }

    public void AddImg(ImgObject img)
    {
        img._scale = bordScale;
        objList.add(img);
        invalidate(); // 刷新
    }

    public void DelObj(String uuid) {
        boolean finded = false;
        int i = 0;
        for (i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                finded = true;
                break;
            }
        }
        if (finded) {
            objList.remove(i);
            invalidate(); // 刷新
        }
    }

    public void ChangeX(String uuid, int x)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                obj._x = x;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeY(String uuid, int y)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                obj._y = y;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeW(String uuid, int w)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                obj._w = w;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeH(String uuid, int h)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                obj._h = h;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeAngle(String uuid, int angle)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                obj._angle = angle;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeFontSize(String uuid, int fontSize)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._fontSize = fontSize;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeFontColor(String uuid, String fontColor)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._fontColor = fontColor;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeBorderWidth(String uuid, int borderWidth)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._borderWidth = borderWidth;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeRadius(String uuid, int radius)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._radius = radius;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeLineSpace(String uuid, int lineSpace)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._lineSpace = lineSpace;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeHorizontal(String uuid, TextObject.HorizontalType type)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._horizontal = type;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeVertical(String uuid, TextObject.VerticalType type)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._vertical = type;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeText(String uuid, String text)
    {
        boolean changed = false;
        for (int i = 0; i < objList.size(); i++) {
            BaseObject obj = objList.get(i);
            if (Objects.equals(uuid, obj._uuid)) {
                TextObject textObj = (TextObject) obj;
                textObj._text = text;
                changed = true;
                break;
            }
        }
        if (changed) {
            invalidate(); // 刷新
        }
    }

    public void ChangeCanvasColor(String color) {
        defaultColor = TextObject.GetColor(color);
        invalidate(); // 刷新
    }

    public void ChangeCanvasScale(float scale) {
        canvasScale = scale;
        requestLayout();
    }

    public void Resume()
    {
        objList.clear();
        invalidate(); // 刷新
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvasPaint.setColor(defaultColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), canvasPaint);

        for (BaseObject obj : objList) {
            if (obj instanceof ImgObject) {
                ImgObject img = (ImgObject) obj;
                drawBitmap(canvas, img);
            } else if (obj instanceof TextObject) {
                TextObject text = (TextObject) obj;
                drawText(canvas, text);
            }
        }
    }

    private void drawBitmap(Canvas canvas, ImgObject img)
    {
        canvas.save();

        Matrix matrix = new Matrix();
        // 创建一个旋转矩阵
        matrix.setRotate(img.GetAngle(), img.GetX(), img.GetY());
        // 将矩阵应用到画布上
        canvas.setMatrix(matrix);

        RectF rectf = new RectF(img.GetX(), img.GetY(), img.GetX() + img.GetW(), img.GetY() + img.GetH());
        canvas.drawBitmap(img._bitmap, null, rectf, textPaint);

        canvas.restore();
    }

    private void drawText(Canvas canvas, TextObject text)
    {
        float x = text.GetX();
        float y = text.GetY();
        float w = text.GetW();
        float h = text.GetH();
        float fontSize = text.GetFontSize();
        float lineSpace = text.GetLineSpace();
        float radius = text.GetRadius();
        float borderWidth = text.GetBorderWidth();
        int fontColor = text.GetFontColor();
        int borderColor = text.GetBorderColor();
        textPaint.setColor(fontColor);
        textPaint.setTextSize(fontSize);

        canvas.save();

        Matrix matrix = new Matrix();
        // 创建一个旋转矩阵
        matrix.setRotate(text.GetAngle(), text.GetX(), text.GetY());
        // 将矩阵应用到画布上
        canvas.setMatrix(matrix);

        LinkedHashMap<String, Float> hashMap = calculateText(text._text, w);
        int index = 0;
        for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
            float horizontalFix = 0;
            if (text._horizontal == TextObject.HorizontalType.RIGHT) {
                horizontalFix = w - entity.getValue();
            } else if (text._horizontal == TextObject.HorizontalType.CENTER) {
                horizontalFix = (w - entity.getValue()) / 2;
            }

            //先用画笔测量文字
            Rect bounds = new Rect(0, 0, (int)w, (int)fontSize);
            textPaint.getTextBounds(entity.getKey(), 0, entity.getKey().length() - 1, bounds);
            //获取 FontMetrics对象
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            // 计算中线跟基线的差值
            float dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            //计算baseLineY
            float baseLineY = fontSize / 2 + dy;
            // 计算偏差量
            float fixedHeight = fontSize - baseLineY;

            float restHeight = h - index * fontSize + (index == 0 ? 0 : index - 1) * lineSpace;
            if ((index == 0) && restHeight >= fontSize) {
                canvas.drawText(entity.getKey(), x + horizontalFix, y + fontSize - fixedHeight, textPaint);
            } else if ((index != 0) && restHeight >= (fontSize + lineSpace)) {
                canvas.drawText(entity.getKey(), x + horizontalFix, y + fontSize * (index + 1) + lineSpace * index - fixedHeight, textPaint);
            }
            index++;
        }

        if (borderWidth > 0) {
            borderPaint.setColor(borderColor); // 边框颜色
            borderPaint.setStrokeWidth(borderWidth); // 设置边框宽度
            // 确定矩形的边界
            RectF rect = new RectF(x, y, x + w, y + h);
            // 绘制边框矩形
            canvas.drawRoundRect(rect, radius, radius, borderPaint);
        }

        canvas.restore();
    }

    private float getCharWidth(char character)
    {
        // 创建一个临时的字符串，只包含要测量的字符
        String charStr = String.valueOf(character);

        // 使用measureText测量字符的宽度
        return textPaint.measureText(charStr);
    }

    private LinkedHashMap<String, Float> calculateText(String text, float totalW)
    {
        LinkedHashMap<String, Float> hashMap = new LinkedHashMap<>();

        float number = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (char ch : text.toCharArray()) {
            float chW = getCharWidth(ch);
            if (number + chW > totalW)
            {
                hashMap.put(stringBuilder.toString(), number);
                number = 0;
                stringBuilder.setLength(0);
            }
            number += chW;
            stringBuilder.append(ch);
        }
        if (number != 0)
        {
            hashMap.put(stringBuilder.toString(), number);
        }

        return hashMap;
    }

    private void drawTextOld(Canvas canvas, TextObject text)
    {
        float fontSize = bordScale * text._fontSize;
        float lineSpace = bordScale * text._lineSpace;
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(fontSize);
        int screenW = getWidth(); // 获取屏幕宽度（以px为单位）
        int screenH = getHeight();
        int order = 0;
        int AMEND_LENGTH = 8; // 垂直修正

        LinkedHashMap<String, Float> hashMap = calculateText(text._text, screenW);
        int count = hashMap.size();

        if (text._vertical == TextObject.VerticalType.BOTTOM) {
            if (text._horizontal == TextObject.HorizontalType.LEFT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), 0, screenH - (fontSize + lineSpace) * (count - 1 - order) - AMEND_LENGTH, textPaint);
                    order++;
                }
            } else if (text._horizontal == TextObject.HorizontalType.RIGHT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), screenW - entity.getValue(), screenH - (fontSize + lineSpace) * (count - 1 - order) - AMEND_LENGTH, textPaint);
                    order++;
                }
            } else if (text._horizontal == TextObject.HorizontalType.CENTER) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), (screenW - entity.getValue()) / 2, screenH - (fontSize + lineSpace) * (count - 1 - order) - AMEND_LENGTH, textPaint);
                    order++;
                }
            }
        }
        else if (text._vertical == TextObject.VerticalType.TOP) {
            if (text._horizontal == TextObject.HorizontalType.LEFT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), 0, fontSize * (order + 1) + lineSpace * order - AMEND_LENGTH, textPaint);
                    order++;
                }
            } else if (text._horizontal == TextObject.HorizontalType.RIGHT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), screenW - entity.getValue(), fontSize * (order + 1) + lineSpace * order - AMEND_LENGTH, textPaint);
                    order++;
                }
            } else if (text._horizontal == TextObject.HorizontalType.CENTER) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), (screenW - entity.getValue()) / 2, fontSize * (order + 1) + lineSpace * order - AMEND_LENGTH, textPaint);
                    order++;
                }
            }
        }
        else if (text._vertical == TextObject.VerticalType.MIDDLE) {
            float space = (screenH - fontSize * count - lineSpace * (count - 1)) / 2;
            if (text._horizontal == TextObject.HorizontalType.LEFT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), 0, space + fontSize * (order + 1) + lineSpace * order - AMEND_LENGTH, textPaint);
                    order++;
                }
            } else if (text._horizontal == TextObject.HorizontalType.RIGHT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), screenW - entity.getValue(), space + fontSize * (order + 1) + lineSpace * order - AMEND_LENGTH, textPaint);
                    order++;
                }
            } else if (text._horizontal == TextObject.HorizontalType.CENTER) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), (screenW - entity.getValue()) / 2, space + fontSize * (order + 1) + lineSpace * order - AMEND_LENGTH, textPaint);
                    order++;
                }
            }
        }
    }

    protected void drawPoint(Canvas canvas)
    {
        //画点
        textPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, 390, textPaint);
        canvas.drawPoint(60, 390, textPaint);//画一个点
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, textPaint);//画多个点
    }

    protected void drawBessel(Canvas canvas)
    {
        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, textPaint);
        textPaint.reset();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.GREEN);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, textPaint);//画出贝塞尔曲线
    }

    protected void drawRect(Canvas canvas)
    {
        canvas.drawText("画矩形：", 10, 80, textPaint);
        textPaint.setColor(Color.GRAY);// 设置灰色
        textPaint.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(60, 60, 80, 80, textPaint);// 正方形
        canvas.drawRect(60, 90, 160, 100, textPaint);// 长方形

        //画圆角矩形
        textPaint.setStyle(Paint.Style.FILL);//充满
        textPaint.setColor(Color.LTGRAY);
        textPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, textPaint);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, textPaint);//第二个参数是x半径，第三个参数是y半径
    }

    protected void drawPolygon(Canvas canvas)
    {
        // 你可以绘制很多任意多边形，比如下面画六连形
        textPaint.reset();//重置
        textPaint.setColor(Color.LTGRAY);
        textPaint.setStyle(Paint.Style.STROKE);//设置空心
        Path path1=new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, textPaint);
    }

    protected void drawTriangle(Canvas canvas)
    {
        canvas.drawText("画三角形：", 10, 200, textPaint);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, textPaint);
    }

    protected void drawEllipse(Canvas canvas)
    {
        canvas.drawText("画扇形和椭圆:", 10, 120, textPaint);

        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        textPaint.setShader(mShader);
        // textPaint.setColor(Color.BLUE);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, textPaint);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        //画椭圆，把oval改一下
        oval2.set(210,100,250,130);
        canvas.drawOval(oval2, textPaint);
    }

    protected void drawLine(Canvas canvas)
    {
        canvas.drawText("画线及弧线：", 10, 60, textPaint);
        textPaint.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(60, 40, 100, 40, textPaint);// 画线
        canvas.drawLine(110, 40, 190, 80, textPaint);// 斜线
        //画笑脸弧线
        textPaint.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1=new RectF(150,20,180,40);
        canvas.drawArc(oval1, 180, 180, false, textPaint);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, textPaint);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, textPaint);//小弧形
    }

    protected void drawCircle(Canvas canvas)
    {
        canvas.drawText("画圆：", 10, 20, textPaint);// 画文本
        canvas.drawCircle(60, 20, 10, textPaint);// 小圆
        textPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除
        canvas.drawCircle(120, 20, 20, textPaint);// 大圆
    }

}