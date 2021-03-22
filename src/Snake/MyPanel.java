package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyPanel extends JPanel {
    ImageIcon image = new ImageIcon("src/Snake/cyan.jpg");//设置背景图片
    Egg egg = new Egg();
    Snake snake = new Snake(egg);
    SnakeThread snakeThread = new SnakeThread();
    MyPanel(){
        MyFrame.cnnt=0;
        this.setBounds(0,0,700,400);
        this.setBackground(Color.BLACK);
        //snakeThread.start();//启动蛇的线程
        //创建键盘监听
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                snake.keyboard(e);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        image.paintIcon(this,g,0,0);
        snake.draw(g);//画蛇
        if(MyFrame.cnnt==0){
            MyFrame.cnnt++;
        }
        else snake.move();//蛇移动
        egg.draw(g);
    }

    class SnakeThread extends Thread{
        //这个flag的目的是为了在不杀死蛇的情况下结束这个线程的工作,开始游戏和恢复游戏时需要用到
        boolean flag=true;
        @Override
        public void run() {
            while (Snake.isLive&&flag){
                if(Snake.isWin){
                    JOptionPane.showMessageDialog(MyPanel.this, "游戏胜利");
                    MyButton.isMove=false;
                }
                try {
                    Thread.sleep(100);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                //线程每次沉睡完毕后蛇还活着且可执行也为正确就执行一次重画
                if(Snake.isLive&&MyButton.isMove)
                repaint();
            }
            //退出循环且flag为真说明蛇死了，游戏结束
            if(flag)JOptionPane.showMessageDialog(MyPanel.this, "游戏结束");
        }

        public void stopThread(){
            flag=false;
        }
    }

}
