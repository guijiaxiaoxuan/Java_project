package gjxx;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//自定义窗口类，创建对象，显示一个主窗口
public class MainFrame extends JFrame {
    //定义一个常量来存储图片的路径
    private static final String IMAGESPATH = "stone-maze/src/image/";
    //定义一个二维数组,用来定义图片色块(4x4)
    private int[][] imageArray = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    private int[][] winArray = {
            {1,2,3,4},
            {5,6,7,8},
            {9,10,11,12},
            {13,14,15,0}
    };
    private int x;
    private int y;
    private int conut;

    public MainFrame(){
        //1.调用一个初始化方法，初始化窗口大小
        initFrame();
        //4.先打乱数字色块的顺序，在打乱顺序后，再展示图片色块
        initRandomArray();
        //2.初始化界面图片,展示数字色块
        initImage();
        //3.初始化界面菜单，系统退出，重启游戏
        initMenu();
        //5.给当前窗口绑定上下左右按键事件
        intKeyPressEvent();
        this.setVisible(true);

    }

    private void intKeyPressEvent() {
        //给当前窗口绑定上下左右按键事件
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                //获取当前按键的编号
                int keyCode = e.getKeyCode();
                //判断按键编号
                switch (keyCode){
                    case KeyEvent.VK_W,KeyEvent.VK_UP:
                        System.out.println("上");
                        switchAndMove(Dierction.UP);
                        //上
                        break;
                    case KeyEvent.VK_DOWN,KeyEvent.VK_S:
                        //下
                        System.out.println("下");
                        switchAndMove(Dierction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT,KeyEvent.VK_A:
                        System.out.println("左");
                        //左
                        switchAndMove(Dierction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT,KeyEvent.VK_D:
                        System.out.println("右");
                        switchAndMove(Dierction.RIGHT);
                        //右
                        break;
                }
            }
        });
    }
    private void switchAndMove(Dierction dierction){
    //判断图片方向，在控制方向后，移动图片
        switch (dierction){
            case Dierction.UP:
                //上交换的条件小于3
                if(x < imageArray.length-1){
                    //当前空白位置:x y
                    //需要被交换的位置:x+1,y
                    System.out.println(1);
                    int temp = imageArray[x][y];
                    imageArray[x][y] = imageArray[x+1][y];
                    imageArray[x+1][y] = temp;
                    //更新空白块的位置
                    x++;
                    conut++;
                }
                break;
            case Dierction.DOWN:
                if(x > 0){
                    int temp = imageArray[x][y];
                    System.out.println(2);
                    imageArray[x][y] = imageArray[x-1][y];
                    imageArray[x-1][y] = temp;
                    x--;
                    conut++;
                }
                break;
            case Dierction.LEFT:
                if(y < imageArray.length-1 ){
                    int temp = imageArray[x][y];
                    System.out.println(3);
                    imageArray[x][y] = imageArray[x][y+1];
                    imageArray[x][y+1] = temp;
                    y++;
                    conut++;
                }
                break;
            case Dierction.RIGHT:
                if(y > 0) {
                    int temp = imageArray[x][y];
                    System.out.println(4);
                    imageArray[x][y] = imageArray[x][y-1];
                    imageArray[x][y-1] = temp;
                    y--;
                    conut++;
                }
                break;
            default:{
                System.out.println("无效按键");
            }
            }
            initImage();
        }


    private void initRandomArray() {

       for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                int i1 = (int)(Math.random()* imageArray.length); //0-3  0 1 2 3
                int j1 = (int)(Math.random()* imageArray.length); //0-3  0 1 2 3
                int i2 = (int)(Math.random()* imageArray.length); //0-3 0 1 2 3
                int j2 = (int)(Math.random()* imageArray.length); //0-3 0 1 2 3
                int temp = imageArray[i1][j1];
                imageArray[i1][j1] = imageArray[i2][j2];
                imageArray[i2][j2] = temp;
            }
        }
        //打乱二维数组中的元素顺序,算法是以0为空白块,向他周围随机移动元素
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
               //1.在空白块在0,0的位置-0,3的位置
            }

        }
        //获取空白块的位置
        OUT:
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                if (imageArray[i][j] == 0){
                    x = i;
                    y = j;
                    break OUT;//跳出循环
                }
            }
        }
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("系统");
        JMenuItem jMenuItem = new JMenuItem("系统退出");
        menu.add(jMenuItem);
        jMenuItem.addActionListener(e -> {
            dispose();
        });
        JMenuItem restartjiemenu = new JMenuItem("重启游戏");
        menu.add(restartjiemenu);
        restartjiemenu.addActionListener(e -> {
            //重新初始化窗口
            conut = 0;
            initRandomArray();
            initImage();
        });
        menuBar.add(menu);
        this.setJMenuBar(menuBar); //设置菜单到窗口




    }

    private void initImage() {
        //先清空窗口上的全部图层
        this.getContentPane().removeAll();
        //判断是不是赢了，弹出win.png

        //创建一个步数标签
        JLabel countLabel = new JLabel("步数："+conut);
        countLabel.setBounds(10,10,100,20);
        this.add(countLabel);

        if (isWin()){
            JLabel winLabel = new JLabel(new ImageIcon(IMAGESPATH+"win.png"));
            winLabel.setBounds(124,230,266,88);
            this.add(winLabel);
            //游戏胜利,不准移动了
            for (int i = 0; i < imageArray.length; i++) {
                for (int j = 0; j < imageArray[i].length; j++) {
                    imageArray[i][j] = 0;
                }
            }
        }

        //1.展示一个行列矩阵的图片色块依次铺满窗口(4x4)
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                String imageName = imageArray[i][j] + ".png";
                //2.创建一个图片对象
                JLabel label = new JLabel();
                //3.设置图片路径
                label.setIcon(new ImageIcon(IMAGESPATH+ imageName));
                //4.设置图片位置
                label.setBounds(22 + j*100, 80 + i*100,100,100);
                //5.将图片添加到窗口中
                this.add(label);
            }
        }
        //6.设置窗口的背景图片
        JLabel bgLabel = new JLabel(new ImageIcon(IMAGESPATH+"background.png"));
        bgLabel.setBounds(0,20,450,484);
        this.add(bgLabel);
    //刷新窗口
        this.repaint();
    }

    private boolean isWin() {
        for (int i = 0; i < imageArray.length; i++) {
            for (int j = 0; j < imageArray[i].length; j++) {
                if (imageArray[i][j] != winArray[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private void initFrame() {
            //设置窗口的标题
            this.setTitle("石子迷宫 V1.0 ZJF");
            //设置窗口的宽高
            this.setSize(475,575);
            //设置窗口的位置
            this.setLocation(200,200);
            //设置窗口是否可见
            this.setLayout(null);
            //设置窗口的关闭方式


            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }


