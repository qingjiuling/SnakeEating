package Snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Snake implements Serializable {
    public static final int rows=20;//总行数
    public static final int cols=35;//总列数
    public static final String up="u";
    public static final String down="d";
    public static final String left="l";
    public static final String right="r";
    public static boolean isLive=true;//初始设蛇是活着的
    public static boolean isWin=false;//是否胜利
    int cnt=1;
    Node head;//蛇的头部
    Node tail;//蛇的头部
    Egg egg;

    Snake(Egg egg){
    head = new Node(7,18,left);
    tail = head;
    //再加两个点使得初始3个结点
    addHead();
    addHead();
    this.egg=egg;
    }

    class Node implements Serializable{
        int row,col;
        String dir;
        Node next,pre;
        Node(int row,int col,String dir){
            this.row=row;
            this.col=col;
            this.dir=dir;
        }
        //这个是用来画单个结点的方法，后面会被画蛇的方法调用
        public void draw(Graphics g){
            g.fillOval(col*20,row*20,20,20);
        }
    }

    //这是用来画蛇的方法
    public void draw(Graphics g){
        //蛇头设为红色
        g.setColor(Color.red);
        for(Node n=head;n!=null;n=n.next){
            n.draw(g);
            //蛇身为黑色
            g.setColor(Color.black);
        }
    }

    //用来获取键盘输入的方法
    //每个case下第一个if是键盘读取的键和当前前进方向反向就无效。
    public void keyboard(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                if(head.dir.equals(down)){
                    break;
                }
                head.dir=up;
                break;
            case KeyEvent.VK_DOWN:
                if(head.dir.equals(up)){
                    break;
                }
                head.dir=down;
                break;
            case KeyEvent.VK_LEFT:
                if(head.dir.equals(right)){
                    break;
                }
                head.dir=left;
                break;
            case KeyEvent.VK_RIGHT:
                if(head.dir.equals(left)){
                    break;
                }
                head.dir=right;
                break;
            default:
                break;
        }
    }

    //根据当前前进的方向直接加头部
    public void addHead(){
        Node node = null;
        switch (head.dir){
            case "l":
                node = new Node(head.row,head.col-1,head.dir);
                break;
            case "r":
                node = new Node(head.row,head.col+1,head.dir);
                break;
            case "u":
                node = new Node(head.row-1,head.col,head.dir);
                break;
            case "d":
                node = new Node(head.row+1,head.col,head.dir);
                break;
            default:
                break;
        }
        node.next=head;
        head.pre=node;
        head=node;
    }

    //删除尾部
    public void deleteTail(){
        tail.pre.next=null;
        tail=tail.pre;
    }

    //这是贪吃蛇的移动方法，在没吃到蛋时加头减尾，吃到了就只加头
    public void move(){
        addHead();
        if(new Rectangle(head.col*20,head.row*20,20,20).intersects(egg.getCoordinate())){
            //吃到蛋了就重新生成，同时不减尾部
            egg.generateEgg();
            //生成后要检查是否是出现在蛇的身上，如果出现的话得重新生成
            while (true){
                boolean flag2=false;
                for(Node n = head;n!=null;n=n.next){
                    if(new Rectangle(n.col*20,n.row*20,20,20).intersects(egg.getCoordinate())){
                        egg.generateEgg();
                    }
                    else {
                        flag2=true;
                    }
                }
                if(flag2)break;
            }
            //记录净增长的次数
            cnt++;
            if(cnt==cols*rows)isWin=true;
        }else{
            deleteTail();
        }
        ifAlive();//每走一步都要判断是否还活着
    }

    //判断蛇是否还存活
    public void ifAlive(){
        //撞到边界死
        if(head.row<0 || head.row>rows-1 || head.col<0 ||head.col>cols){
            isLive=false;
        }
        //咬到自己死
        for(Node n=head.next;n!=null;n=n.next){
            if(n.col==head.col && n.row==head.row){
                isLive=false;
            }
        }
    }
}
