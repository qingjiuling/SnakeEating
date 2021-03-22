package Snake;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MyButton extends JPanel {
    public static boolean isMove=false;//控制一开始蛇不动
    MyPanel panel;
    MyButton(MyPanel panel){
        System.out.println(Thread.currentThread()+" button");
        this.panel=panel;
        this.setBounds(0,400,700,100);
        JButton jb1 = new JButton("开始游戏");
        JButton jb2 = new JButton("结束游戏");
        JButton jb3 = new JButton("保存游戏");
        JButton jb4 = new JButton("恢复游戏");
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.snakeThread.stopThread();
                Egg egg = new Egg();
                panel.egg = egg;
                panel.snake=new Snake(egg);
                Snake.isLive=true;
                isMove = true;
                MyPanel.SnakeThread st = panel.new SnakeThread();
                panel.snakeThread=st;
                st.start();

                panel.setFocusable(true);
                panel.requestFocus();
            }
        });
        jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Snake.isLive=false;
            }
        });
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    isMove = false;
                    FileOutputStream fileStream = new FileOutputStream("myGame.ser");
                    ObjectOutputStream os = new ObjectOutputStream(fileStream);
                    os.writeObject(panel.snake);
                    os.writeObject(panel.egg);
                    os.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        jb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fileStream = new FileInputStream("myGame.ser");
                    ObjectInputStream is = new ObjectInputStream(fileStream);
                    panel.snakeThread.stopThread();
                    panel.snake = (Snake) is.readObject();
                    panel.egg = (Egg) is.readObject();
                    Snake.isLive=true;
                    isMove = true;
                    MyPanel.SnakeThread st = panel.new SnakeThread();
                    panel.snakeThread=st;
                    st.start();

                    panel.setFocusable(true);
                    panel.requestFocus();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
