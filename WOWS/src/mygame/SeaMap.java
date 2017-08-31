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
		this.setLocation(0, 0);    //���ڳ�ʼ�����
        this.setSize(GameWidth, GameHeight);        //���ڳ�ʼ��С
        this.setTitle("WOWS");    //��������
		this.addKeyListener(new KeyMoniton());    //���ü��̼���
        this.setVisible(true);    //���ô�������
        this.setResizable(false);    //���ô��ڲ��ɸı��С
        this.getContentPane().setBackground(new Color(113,191,234) );    //���ô��ڱ�����ɫ
        
        enemys.add(new Battleship(seamap,true,500,350));
    	enemys.add(new Cruiser(seamap,true,600,350));
    	enemys.add(new Destroyer(seamap,true,670,350));
    	
    	obstruction.add(new Obstruction(450,150,200,40));
    	obstruction.add(new Obstruction(250,450,100,20));
    	obstruction.add(new Obstruction(600,200,40,250));
    	obstruction.add(new Obstruction(150,3000,30,230));
    	obstruction.add(new Obstruction(550,550,100,20));
        
        new Thread(new PaintThread()).start();    //��ʼ����PaintThread��run
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
    	
    	// ���ػ溯����ʵ��˫�������
    	if(offScreenImage==null)
    		offScreenImage = this.createImage(GameWidth, GameHeight);
    	// ��ý�ȡͼƬ�Ļ���
    	gImage = offScreenImage.getGraphics();
    	// ���ø�����ػ淽��
    	super.paint(gImage);
    	//���ƻ���ͼ��
        ondraw(gImage);
        //������ͼ����Ƶ�������
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
        	gImage.setFont(new Font("����",Font.BOLD, 15));
        	gImage.drawString("����ֵ��"+myship.hp, 1100, 650);
        	gImage.drawString("����������"+myship.specialweaponnum, 1100, 670);
        	gImage.drawString("����װ����"+myship.specialactionnum, 1100, 690);
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
        	gImage.setFont(new Font("����",Font.BOLD, 30));
        	gImage.drawString("WOWS", 550, 200);
        	gImage.setFont(new Font("����",Font.BOLD, 15));
        	gImage.drawString("��ѡ��ս����1��ս�н���2��Ѳ�󽢣�3�����𽢣�", 400, 300);
        	enemys.get(0).ondraw(gImage);
            enemys.get(1).ondraw(gImage);
            enemys.get(2).ondraw(gImage);
        }
        else
        {
        	gImage.setFont(new Font("����",Font.BOLD, 30));
        	if(iswin)
        		gImage.drawString("You win !", 500, 200);
        	else
        		gImage.drawString("You die !", 500, 200);
        	gImage.setFont(new Font("����",Font.BOLD, 15));
        	gImage.drawString("��R���ٿ���Ϸ", 515, 300);
        }
    }
    
    private class PaintThread implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(true)
            {
                repaint();    //repaint���Լ�����update��paint
                try {
                    Thread.sleep(50);    //ÿ��50����ˢ�»���һ��
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /*������Ӧ*/
    private class KeyMoniton extends KeyAdapter {
    	
        /*���¼�����Ӧ*/
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
        /*̧�������Ӧ*/
        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            super.keyReleased(e);
            if(gamestatus==GameStatus.run)
            	myship.keyReleased(e);
        }  
    }
} 