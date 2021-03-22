package Snake;

import javax.swing.*;

public class MyFrame extends JFrame {
    public static int cnnt=0;//这个用来控制点开始游戏按钮之前蛇不会移动
    MyPanel panel = new MyPanel();
    MyButton button = new MyButton(panel);
    MyFrame(){
        System.out.println(Thread.currentThread());
        this.setBounds(400,100,700,500);//设置窗口程序位置和大小
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭事件的
        this.setResizable(false);//窗口程序不可调整大小
        this.add(panel);
        this.add(button);
        //设置焦点(选中)
        panel.setFocusable(true);
        panel.requestFocus();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
