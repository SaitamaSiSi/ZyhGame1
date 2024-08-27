package com.zyh.ZyhG1.ui.Canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zyh.ZyhG1.model.BaseObject;
import com.zyh.ZyhG1.model.ImgObject;
import com.zyh.ZyhG1.model.TextObject;

import java.util.LinkedHashMap;

public class CanvasBaseView extends View {
    private static final String TAG = "CanvasBaseView";
    //默认属性
    private int defaultWidth;
    private int defaultHeight;
    private Paint defaultPaint;
    private TextObject textObj = null;
    private ImgObject imgObj = null;

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
        defaultWidth = 160;
        defaultHeight = 160;
        defaultPaint = new Paint();
        defaultPaint.setTextSize(48); // 设置文本大小
        defaultPaint.setColor(Color.RED);//画笔颜色
        //defaultPaint.setStyle(Paint.Style.STROKE);//描边模式
        defaultPaint.setStrokeWidth(1f);//设置画笔粗细度
        defaultPaint.setAntiAlias(true);//抗锯齿
        defaultPaint.setDither(true);//防抖动
        //设置是否为粗体文字
        //defaultPaint.setFakeBoldText(true);
        //设置下划线
        //defaultPaint.setUnderlineText(true);
        //设置带有删除线效果
        //defaultPaint.setStrikeThruText(true);
        //defaultPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        //defaultPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        //defaultPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 没有必要再让 view 自己测量一遍了，浪费资源
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 指定期望的 size
        int width = resolveSize(defaultWidth, widthMeasureSpec);
        int height = resolveSize(defaultHeight, heightMeasureSpec);
        // 设置大小
        //setMeasuredDimension(width, height);
        setMeasuredDimension(width, width);
    }

    public void SetText(TextObject text)
    {
        textObj = text;
        invalidate(); // 刷新
    }
    public void SetImg(ImgObject img)
    {
        imgObj = img;
        invalidate(); // 刷新
    }


    public void Resume()
    {
        textObj = null;
        imgObj = null;
        invalidate(); // 刷新
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (imgObj != null) {
            drawBitmap(canvas, imgObj);
        }
        if (textObj != null) {
            drawText(canvas, textObj);
        }
    }



    private float getCharWidth(char character)
    {
        // 创建一个临时的字符串，只包含要测量的字符
        String charStr = String.valueOf(character);

        // 使用measureText测量字符的宽度
        return defaultPaint.measureText(charStr);
    }

    private LinkedHashMap<String, Float> calculateText(String text, int totalW)
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

    private void drawText(Canvas canvas, TextObject text)
    {
        defaultPaint.setColor(Color.RED);
        defaultPaint.setTextSize(text._fontSize);
        int screenW = getWidth(); // 获取屏幕宽度（以px为单位）
        int screenH = getHeight();
        int order = 0;
        int AMEND_LENGTH = 8; // 垂直修正

        LinkedHashMap<String, Float> hashMap = calculateText(text._text, screenW);
        int count = hashMap.size();
        if (text._vertical == BaseObject.VerticalType.BOTTOM) {
            if (text._horizontal == BaseObject.HorizontalType.LEFT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), 0, screenH - (text._fontSize + text._lineSpace) * (count - 1 - order) - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            } else if (text._horizontal == BaseObject.HorizontalType.RIGHT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), screenW - entity.getValue(), screenH - (text._fontSize + text._lineSpace) * (count - 1 - order) - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            } else if (text._horizontal == BaseObject.HorizontalType.CENTER) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), (screenW - entity.getValue()) / 2, screenH - (text._fontSize + text._lineSpace) * (count - 1 - order) - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            }
        }
        else if (text._vertical == BaseObject.VerticalType.TOP) {
            if (text._horizontal == BaseObject.HorizontalType.LEFT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), 0, text._fontSize * (order + 1) + text._lineSpace * order - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            } else if (text._horizontal == BaseObject.HorizontalType.RIGHT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), screenW - entity.getValue(), text._fontSize * (order + 1) + text._lineSpace * order - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            } else if (text._horizontal == BaseObject.HorizontalType.CENTER) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), (screenW - entity.getValue()) / 2, text._fontSize * (order + 1) + text._lineSpace * order - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            }
        }
        else if (text._vertical == BaseObject.VerticalType.MIDDLE) {
            if (text._horizontal == BaseObject.HorizontalType.LEFT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), 0, (float) (screenH - (text._fontSize + text._lineSpace) * (count - 1 - order)) / 2 - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            } else if (text._horizontal == BaseObject.HorizontalType.RIGHT) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), screenW - entity.getValue(), (float) (screenH - (text._fontSize + text._lineSpace) * (count - 1 - order)) / 2 - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            } else if (text._horizontal == BaseObject.HorizontalType.CENTER) {
                for (LinkedHashMap.Entry<String, Float> entity : hashMap.entrySet()) {
                    canvas.drawText(entity.getKey(), (screenW - entity.getValue()) / 2, (float) (screenH - (text._fontSize + text._lineSpace) * (count - 1 - order)) / 2 - AMEND_LENGTH, defaultPaint);
                    order++;
                }
            }
        }
    }

    private void drawBitmap(Canvas canvas, ImgObject img)
    {
        int screenW = getWidth(); // 获取屏幕宽度（以px为单位）
        int screenH = getHeight();

        RectF rectf = new RectF(0, 0, screenW, screenH);
        canvas.drawBitmap(img._bitmap, null, rectf, defaultPaint);
    }

    protected void drawPoint(Canvas canvas)
    {
        //画点
        defaultPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("画点：", 10, 390, defaultPaint);
        canvas.drawPoint(60, 390, defaultPaint);//画一个点
        canvas.drawPoints(new float[]{60,400,65,400,70,400}, defaultPaint);//画多个点
    }

    protected void drawBessel(Canvas canvas)
    {
        //画贝塞尔曲线
        canvas.drawText("画贝塞尔曲线:", 10, 310, defaultPaint);
        defaultPaint.reset();
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setColor(Color.GREEN);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(150, 310, 170, 400); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, defaultPaint);//画出贝塞尔曲线
    }

    protected void drawRect(Canvas canvas)
    {
        canvas.drawText("画矩形：", 10, 80, defaultPaint);
        defaultPaint.setColor(Color.GRAY);// 设置灰色
        defaultPaint.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(60, 60, 80, 80, defaultPaint);// 正方形
        canvas.drawRect(60, 90, 160, 100, defaultPaint);// 长方形

        //画圆角矩形
        defaultPaint.setStyle(Paint.Style.FILL);//充满
        defaultPaint.setColor(Color.LTGRAY);
        defaultPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        canvas.drawText("画圆角矩形:", 10, 260, defaultPaint);
        RectF oval3 = new RectF(80, 260, 200, 300);// 设置个新的长方形
        canvas.drawRoundRect(oval3, 20, 15, defaultPaint);//第二个参数是x半径，第三个参数是y半径
    }

    protected void drawPolygon(Canvas canvas)
    {
        // 你可以绘制很多任意多边形，比如下面画六连形
        defaultPaint.reset();//重置
        defaultPaint.setColor(Color.LTGRAY);
        defaultPaint.setStyle(Paint.Style.STROKE);//设置空心
        Path path1=new Path();
        path1.moveTo(180, 200);
        path1.lineTo(200, 200);
        path1.lineTo(210, 210);
        path1.lineTo(200, 220);
        path1.lineTo(180, 220);
        path1.lineTo(170, 210);
        path1.close();//封闭
        canvas.drawPath(path1, defaultPaint);
    }

    protected void drawTriangle(Canvas canvas)
    {
        canvas.drawText("画三角形：", 10, 200, defaultPaint);
        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(80, 200);// 此点为多边形的起点
        path.lineTo(120, 250);
        path.lineTo(80, 250);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, defaultPaint);
    }

    protected void drawEllipse(Canvas canvas)
    {
        canvas.drawText("画扇形和椭圆:", 10, 120, defaultPaint);

        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[] { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
                        Color.LTGRAY }, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。
        defaultPaint.setShader(mShader);
        // defaultPaint.setColor(Color.BLUE);
        RectF oval2 = new RectF(60, 100, 200, 240);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, 200, 130, true, defaultPaint);
        // 画弧，第一个参数是RectF：该类是第二个参数是角度的开始，第三个参数是多少度，第四个参数是真的时候画扇形，是假的时候画弧线
        //画椭圆，把oval改一下
        oval2.set(210,100,250,130);
        canvas.drawOval(oval2, defaultPaint);
    }

    protected void drawLine(Canvas canvas)
    {
        canvas.drawText("画线及弧线：", 10, 60, defaultPaint);
        defaultPaint.setColor(Color.GREEN);// 设置绿色
        canvas.drawLine(60, 40, 100, 40, defaultPaint);// 画线
        canvas.drawLine(110, 40, 190, 80, defaultPaint);// 斜线
        //画笑脸弧线
        defaultPaint.setStyle(Paint.Style.STROKE);//设置空心
        RectF oval1=new RectF(150,20,180,40);
        canvas.drawArc(oval1, 180, 180, false, defaultPaint);//小弧形
        oval1.set(190, 20, 220, 40);
        canvas.drawArc(oval1, 180, 180, false, defaultPaint);//小弧形
        oval1.set(160, 30, 210, 60);
        canvas.drawArc(oval1, 0, 180, false, defaultPaint);//小弧形
    }

    protected void drawCircle(Canvas canvas)
    {
        canvas.drawText("画圆：", 10, 20, defaultPaint);// 画文本
        canvas.drawCircle(60, 20, 10, defaultPaint);// 小圆
        defaultPaint.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除
        canvas.drawCircle(120, 20, 20, defaultPaint);// 大圆
    }

}