package Snake;

import java.awt.*;
import java.io.Serializable;

public class Egg implements Serializable {
    //初始化食物的位置
    int row=10,col=10;


    public void generateEgg(){
        //随机生成,不生成在蛇身上的限制条件在Snake类里
        row =(int)(Math.random()*18);
        col =(int)(Math.random()*33);
    }

    //用来画食物的方法,食物画成矩形
    public void draw(Graphics g){
        g.setColor(Color.orange);
        g.fillRect(col*20,row*20,20,20);
    }

    //返回食物的坐标

    public Rectangle getCoordinate(){
        return new Rectangle(col*20,row*20,20,20);
    }
}
