package com.example.a1407232261.test0215;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 1407232261 on 2020/4/9.
 */

public class DrawView extends View {
    //设置两个球的坐标x,y
    float B_x=40;
    float B_y=100;
    float R_x=(float)(Math.random()*460+140);
    float R_y=(float)(Math.random()*800+50);
    int R_Radius;
//设置两只画笔
    Paint B=new Paint();
    Paint R=new Paint();
    public DrawView(Context context) {
        super(context);
    }
    //创建画布
    public  void  onDraw(Canvas canvas){
        super.onDraw(canvas);
        B.setColor(Color.BLUE);
        R.setColor(Color.RED);
        //设置所要绘制图形
        canvas.drawCircle(B_x,B_y,20,B);
        canvas.drawCircle(R_x,R_y,R_Radius,R);
    }
    //处理触发
    public boolean onTouchEvent(MotionEvent event){
       if(event.getX()<(B_x+R_Radius)&&event.getX()>(B_x-R_Radius)&&event.getY()<(B_y+R_Radius)&&event.getY()>(B_y-R_Radius)){
           B_x=event.getX();
           B_y=event.getY();
       }

        BallActivity.ball.xy.setText("当前蓝球的坐标是"+(int)B_x+","+(int)B_y);
        //重新绘图
        if(B_x<R_x+R_Radius&&B_x>R_x-R_Radius&&B_y<R_y+R_Radius&&B_y>R_y-R_Radius){
        BallActivity.ball.show(BallActivity.ball);

    }
        invalidate();

        return true;
    }
}
