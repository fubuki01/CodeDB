package Ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import Controller.Controller_Fire;
import Controller.Controller_Move;
import Controller.Controller_Ondraw;
import Obstruction.Obstruction;
import mygame.SeaMap;
import mygame.SeaMap.GameStatus;

public abstract class Ship implements Controller_Fire,Controller_Move,Controller_Ondraw {

	protected int Width;
	protected int Height;
	public int hp;
    private Color color=Color.yellow;
    protected boolean isplayer;
    public boolean isalive=true;
    
    protected int maingunatk;
    protected int specialweaponatk;
    public int specialweaponnum;
    public int specialactionnum;
    
	protected int x, y;//ս������
    private int oldX, oldY;    //ս����һ������
    public int XSPEED;    //�����ƶ��ٶ�
    public int YSPEED;    //�����ƶ��ٶ�
    
    public enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};    //���ĸ�����ֵ�ϳɰ˸�������ƶ�
    protected Direction dir = Direction.STOP;    //�ƶ�����
    protected Direction ptDir = Direction.D;    //���������� 
    private boolean sL=false, sU=false, sR=false, sD=false;    //�ĸ���������ƶ�
    private boolean fL=false, fU=false, fR=false, fD=false;    //�ĸ������������
    
    private boolean keyup=true;
    
    protected SeaMap seamap;
    
    private Thread aithread;
    private Thread aigunthread;
    
    public Ship(SeaMap seamap, boolean isplayer, int x, int y) {
    	this.seamap = seamap;
    	this.isplayer = isplayer;
    	this.x = x;
    	this.y = y;
    	if(!isplayer)
    	{
    		aithread = new Thread(new AIThread());
    		aigunthread = new Thread(new AIgunThread());
    		aithread.start();
    		aigunthread.start();
    		color=Color.red;
    	}
    		
    }
    
    public void die() {
    	aithread.interrupt();
		aigunthread.interrupt();
    	isalive=false;
		seamap.enemys.remove(this);
    }
    
    @Override
    public void ondraw(Graphics g) {
    	/*�����������Ӷ�Ӧ������ɾ��*/
        if(hp<=0)
        {
        	if(isplayer)
        	{
        		SeaMap.gamestatus = GameStatus.over;
        		seamap.overgame();
        	}
        	else
        		die();
            return;
        }
        Color c = g.getColor();    //���浱ǰ������ɫ
        /*��ս��*/
        g.setColor(color);    //���û�����ɫ
        g.fillOval(x, y, Width, Height);
        /*����Ͳ*/
        g.setColor(Color.black);
        drawPT(g);
        g.setColor(c);    //��ԭ������ɫ
        move();		//�����ƶ�������ٶȸ���λ��
    }
    
    abstract protected void drawPT(Graphics g);
    
    @Override
    public void move() {	//����λ��
        /*��¼��һ����λ��*/
        oldX = x;
        oldY = y;
        
        switch(dir){
        case L:
            x-=XSPEED;
            break;
        case LU:
            x-=XSPEED;
            y-=YSPEED;
            break;
        case U:
            y-=YSPEED;
            break;
        case RU:
            x+=XSPEED;
            y-=YSPEED;
            break;
        case R:
            x+=XSPEED;
            break;
        case RD:
            x+=XSPEED;
            y+=YSPEED;
            break;
        case D:
            y+=YSPEED;
            break;
        case LD:
            x-=XSPEED;
            y+=YSPEED;
            break;
        case STOP:
            break;
        }
        /*�ж�Խ��*/
        if(x < 5)
        	x = 5;
        if(y < 25)
        	y = 25;
        if(x+Width > seamap.GameWidth-5)
        	x = seamap.GameWidth-Width-5;
        if(y+Height > seamap.GameHeight-5)
        	y = seamap.GameHeight-Height-5;
        /*�ж���ײ*/
        if(isimpact())
        {
        	x=oldX;
        	y=oldY;
        }
    }
    
    public Rectangle getRect(){
        return new Rectangle(x,y,Width,Height);
    }
    
    private boolean isimpact(){ 
    	
    	if(SeaMap.gamestatus == GameStatus.start || SeaMap.gamestatus == GameStatus.over)
    		return true;
    	
    	if(!this.isalive)
    		return true;
    	
        for(Ship ship : seamap.enemys)
        {
        	if(this!=ship && ship.isalive && this.getRect().intersects(ship.getRect()))
	        	return true;
        }
        
        if(this!=seamap.myship && seamap.myship.isalive && this.getRect().intersects(seamap.myship.getRect()))
        	return true;
        
        for(Obstruction obs : seamap.obstruction)
        {
        	if(!obs.issmoke && this.getRect().intersects(obs.getRect()))
        		return true;
        }
        
        return false;
    }
    
    @Override
    public abstract void fire();
    @Override
    public abstract void specialfire();
    @Override
    public abstract void specialaction();
    
    /*���̼���������*/
    public void KeyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch(key)
        {
	        case KeyEvent.VK_UP:
	            sU=true;
	            break;
	        case KeyEvent.VK_DOWN:
	            sD=true;
	            break;
	        case KeyEvent.VK_RIGHT:
	            sR=true;
	            break;
	        case KeyEvent.VK_LEFT:
	            sL=true;
	            break;
	        case KeyEvent.VK_W:
	            fU=true;
	            break;
	        case KeyEvent.VK_S:
	            fD=true;
	            break;
	        case KeyEvent.VK_D:
	            fR=true;
	            break;
	        case KeyEvent.VK_A:
	            fL=true;
	            break;
	        case KeyEvent.VK_SPACE:
	        	if(keyup)
	        	{
	        		fire();
	        		keyup=false;
	        	}
	            break;
	        case KeyEvent.VK_Q:
	        	if(keyup)
	        	{
	        		specialfire();
	        		keyup=false;
	        	}
	            break;
	        case KeyEvent.VK_F:
	        	if(keyup)
	        	{
	        		specialaction();
	        		keyup=false;
	        	}
	            break;
        }
        locateDirection();
    }
    
    /*���̼�����̧��*/
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        switch(key)
        {
	        case KeyEvent.VK_UP:
	            sU=false;
	            break;
	        case KeyEvent.VK_DOWN:
	            sD=false;
	            break;
	        case KeyEvent.VK_RIGHT:
	            sR=false;
	            break;
	        case KeyEvent.VK_LEFT:
	            sL=false;
	            break;
	        case KeyEvent.VK_W:
	            fU=false;
	            break;
	        case KeyEvent.VK_S:
	            fD=false;
	            break;
	        case KeyEvent.VK_D:
	            fR=false;
	            break;
	        case KeyEvent.VK_A:
	            fL=false;
	            break;
	        case KeyEvent.VK_SPACE:
	        	keyup=true;
	            break;
	        case KeyEvent.VK_Q:
	        	keyup=true;
	            break;
	        case KeyEvent.VK_F:
	        	keyup=true;
	            break;
        }
        locateDirection();    //�ϳɷ���
    }
    
    /*�ϳ��ƶ����������������*/
    private void locateDirection(){
    	
        if(sL&&!sU&&!sR&&!sD) dir=Direction.L;
        else if(sL&&sU&&!sR&&!sD) dir=Direction.LU;
        else if(!sL&&sU&&!sR&&!sD) dir=Direction.U;
        else if(!sL&&sU&&sR&&!sD) dir=Direction.RU;
        else if(!sL&&!sU&&sR&&!sD) dir=Direction.R;
        else if(!sL&&!sU&&sR&&sD) dir=Direction.RD;
        else if(!sL&&!sU&&!sR&&sD) dir=Direction.D;
        else if(sL&&!sU&&!sR&&sD) dir=Direction.LD;
        else if(!sL&&!sU&&!sR&&!sD) dir=Direction.STOP;
        
        if(fL&&!fU&&!fR&&!fD) ptDir=Direction.L;
        else if(fL&&fU&&!fR&&!fD) ptDir=Direction.LU;
        else if(!fL&&fU&&!fR&&!fD) ptDir=Direction.U;
        else if(!fL&&fU&&fR&&!fD) ptDir=Direction.RU;
        else if(!fL&&!fU&&fR&&!fD) ptDir=Direction.R;
        else if(!fL&&!fU&&fR&&fD) ptDir=Direction.RD;
        else if(!fL&&!fU&&!fR&&fD) ptDir=Direction.D;
        else if(fL&&!fU&&!fR&&fD) ptDir=Direction.LD;
    }
    
    private Direction getRelativeDir()
    {
    	int rx=seamap.myship.x-x;
    	int ry=y-seamap.myship.y;
    	int angle=0;
    	if(rx==0)
    	{
    		if(ry>0)
    			return Direction.U;
    		else
    			return Direction.D;
    	}
    	if(ry==0)
    	{
    		if(rx>0)
    			return Direction.R;
    		else
    			return Direction.L;
    	}
    	if(ry>0&&rx>0)
    	{
    		angle = (int) (Math.atan((float)(ry/rx))/Math.PI*180);
    		if(angle<15)
    			return Direction.R;
    		else if(angle<75)
    			return Direction.RU;
    		else
    			return Direction.U;
    	}
    	if(ry>0&&rx<0)
    	{
    		angle = (int) (Math.atan((float)(ry/-rx))/Math.PI*180);
    		if(angle<15)
    			return Direction.L;
    		else if(angle<75)
    			return Direction.LU;
    		else
    			return Direction.U;
    	}
    	if(ry<0&&rx<0)
    	{
    		angle = (int) (Math.atan((float)(ry/rx))/Math.PI*180);
    		if(angle<15)
    			return Direction.L;
    		else if(angle<75)
    			return Direction.LD;
    		else
    			return Direction.D;
    	}
    	if(ry<0&&rx>0)
    	{
    		angle = (int) (Math.atan((float)(-ry/rx))/Math.PI*180);
    		if(angle<15)
    			return Direction.R;
    		else if(angle<75)
    			return Direction.RD;
    		else
    			return Direction.D;
    	}
    	return dir;
    }
    
    public class AIThread implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
        	Direction[] dirs = Direction.values();
            while(SeaMap.gamestatus!=GameStatus.over && isalive)
            {
            	Ship.this.dir = dirs[(int)(8*Math.random())];
            	try {
            	    Thread.sleep(500+(int)(1500*Math.random()));
            	} catch(InterruptedException ex) {
            	    Thread.currentThread().interrupt();
            	}
            }
        }
    }
    
    public class AIgunThread implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(SeaMap.gamestatus!=GameStatus.over && isalive)
            {
            	if(hp<=hp/2)
            		specialaction();
            	try {
            	    Thread.sleep(500+(int)(1000*Math.random()));
            	} catch(InterruptedException ex) {
            	    Thread.currentThread().interrupt();
            	}
            	Ship.this.ptDir = getRelativeDir();
            	if(Math.random()>0.35)
            		 fire();
            	else
            		 specialfire();
            }
        }
    }
    
}
