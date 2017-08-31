package mygame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import Exploded.Exploded;
import Obstruction.Obstruction;
import Shells.Shells;
import Ship.Battleship;
import Ship.Cruiser;
import Ship.Destroyer;
import Ship.Ship;

public class SeaMap extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static final SeaMap seamap = new SeaMap();
	private SeaMap() {
	}
	
	public final int GameWidth = 1200;
	public final int GameHeight = 700;
	
	public Ship myship;
	public ArrayList<Ship> enemys = new ArrayList<Ship>();
	public ArrayList<Shells> shells = new ArrayList<Shells>();
	public ArrayList<Obstruction> obstruction = new ArrayList<Obstruction>();
    public ArrayList<Exploded> exploded = new ArrayList<Exploded>();
    
	private Image offScreenImage;
	private Graphics gImage;
	
	public enum GameStatus {start, run, over};
	public static GameStatus gamestatus = GameStatus.start;
	private boolean iswin = false;
	
	public static SeaMap getMap() {
		return seamap;
	}
	
	public void newgame() {
        // TODO Auto-generated method stub
		this.setLocation(0, 0);    //窗口初始坐标点
        this.setSize(GameWidth, GameHeight);        //窗口初始大小
        this.setTitle("WOWS");    //窗口名称
		this.addKeyListener(new KeyMoniton());    //设置键盘监听
        this.setVisible(true);    //设置窗口显现
        this.setResizable(false);    //设置窗口不可改变大小
        this.getContentPane().setBackground(new Color(113,191,234) );    //设置窗口背景颜色
        
        enemys.add(new Battleship(seamap,true,500,350));
    	enemys.add(new Cruiser(seamap,true,600,350));
    	enemys.add(new Destroyer(seamap,true,670,350));
    	
    	obstruction.add(new Obstruction(450,150,200,40));
    	obstruction.add(new Obstruction(250,450,100,20));
    	obstruction.add(new Obstruction(600,200,40,250));
    	obstruction.add(new Obstruction(150,3000,30,230));
    	obstruction.add(new Obstruction(550,550,100,20));
        
        new Thread(new PaintThread()).start();    //开始运行PaintThread类run
    }
	
	public void overgame() {
		myship = null;
		for(int i=0; i<enemys.size(); i++)
			enemys.get(i).die();
		enemys.clear();
		shells.clear();
		exploded.clear();
		enemys.add(new Battleship(seamap,true,500,350));
    	enemys.add(new Cruiser(seamap,true,600,350));
    	enemys.add(new Destroyer(seamap,true,670,350));
	}

    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
    	
    	// 在重绘函数中实现双缓冲机制
    	if(offScreenImage==null)
    		offScreenImage = this.createImage(GameWidth, GameHeight);
    	// 获得截取图片的画布
    	gImage = offScreenImage.getGraphics();
    	// 调用父类的重绘方法
    	super.paint(gImage);
    	//绘制缓冲图像
        ondraw(gImage);
        //将缓冲图像绘制到界面上
        g.drawImage(offScreenImage, 0, 0, null);
    }
    
    private void ondraw(Graphics gImage) {
    	
    	if(gamestatus==GameStatus.run)
        {
    		if(enemys.isEmpty())
    		{
    			iswin=true;
    			gamestatus=GameStatus.over;
    			overgame();
    		}
        	gImage.setFont(new Font("宋体",Font.BOLD, 15));
        	gImage.drawString("生命值："+myship.hp, 1100, 650);
        	gImage.drawString("特殊武器："+myship.specialweaponnum, 1100, 670);
        	gImage.drawString("特殊装备："+myship.specialactionnum, 1100, 690);
        	myship.ondraw(gImage);
            for(int i=0; i<enemys.size(); i++)
            	enemys.get(i).ondraw(gImage);
            for(int i=0; i<shells.size(); i++)
            	shells.get(i).ondraw(gImage);
            for(int i=0; i<exploded.size(); i++)
            	exploded.get(i).ondraw(gImage);
            for(int i=0; i<obstruction.size(); i++)
            	obstruction.get(i).ondraw(gImage);
        }
        else if(gamestatus==GameStatus.start)
        {
        	gImage.setFont(new Font("宋体",Font.BOLD, 30));
        	gImage.drawString("WOWS", 550, 200);
        	gImage.setFont(new Font("宋体",Font.BOLD, 15));
        	gImage.drawString("请选择战舰：1，战列舰；2，巡洋舰；3，驱逐舰；", 400, 300);
        	enemys.get(0).ondraw(gImage);
            enemys.get(1).ondraw(gImage);
            enemys.get(2).ondraw(gImage);
        }
        else
        {
        	gImage.setFont(new Font("宋体",Font.BOLD, 30));
        	if(iswin)
        		gImage.drawString("You win !", 500, 200);
        	else
        		gImage.drawString("You die !", 500, 200);
        	gImage.setFont(new Font("宋体",Font.BOLD, 15));
        	gImage.drawString("按R键再开游戏", 515, 300);
        }
    }
    
    private class PaintThread implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(true)
            {
                repaint();    //repaint会自己调用update和paint
                try {
                    Thread.sleep(50);    //每隔50毫秒刷新画面一次
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /*键盘响应*/
    private class KeyMoniton extends KeyAdapter {
    	
        /*摁下键盘响应*/
        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            super.keyPressed(e);
            if(gamestatus==GameStatus.start)
            {
            	int key = e.getKeyCode();
            	enemys.clear();
            	switch(key)
            	{
            		case KeyEvent.VK_1:
            	        myship = new Battleship(seamap,true,900,650);
            	        enemys.add(new Battleship(seamap,false,100,50));
                    	enemys.add(new Cruiser(seamap,false,100,100));
                    	enemys.add(new Cruiser(seamap,false,150,100));
                    	enemys.add(new Destroyer(seamap,false,100,150));
                    	enemys.add(new Destroyer(seamap,false,150,150));
                    	enemys.add(new Destroyer(seamap,false,200,150));
                    	gamestatus = GameStatus.run;
                    	SeaMap.this.iswin = false;
            	        break;
            		case KeyEvent.VK_2:
            	        myship = new Cruiser(seamap,true,900,650);
            	        enemys.add(new Battleship(seamap,false,100,50));
                    	enemys.add(new Cruiser(seamap,false,100,100));
                    	enemys.add(new Cruiser(seamap,false,150,100));
                    	enemys.add(new Destroyer(seamap,false,100,150));
                    	enemys.add(new Destroyer(seamap,false,150,150));
                    	enemys.add(new Destroyer(seamap,false,200,150));
                    	gamestatus = GameStatus.run;
                    	SeaMap.this.iswin = false;
            	        break;
            		case KeyEvent.VK_3:
            	        myship = new Destroyer(seamap,true,900,650);
            	        enemys.add(new Battleship(seamap,false,100,50));
                    	enemys.add(new Cruiser(seamap,false,100,100));
                    	enemys.add(new Cruiser(seamap,false,150,100));
                    	enemys.add(new Destroyer(seamap,false,100,150));
                    	enemys.add(new Destroyer(seamap,false,150,150));
                    	enemys.add(new Destroyer(seamap,false,200,150));
                    	gamestatus = GameStatus.run;
                    	SeaMap.this.iswin = false;
            	        break;
            	}
            	
            }
            else if(gamestatus==GameStatus.run)
            	myship.KeyPressed(e);
            else
            {
            	int key = e.getKeyCode();
            	if(key==KeyEvent.VK_R)
            		gamestatus=GameStatus.start;
            }
        }
        /*抬起键盘响应*/
        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            super.keyReleased(e);
            if(gamestatus==GameStatus.run)
            	myship.keyReleased(e);
        }  
    }
} 