package Shells;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Controller.Controller_Move;
import Controller.Controller_Ondraw;
import Exploded.Exploded;
import Obstruction.Obstruction;
import Ship.Ship;
import Ship.Ship.Direction;
import mygame.SeaMap;

public class Shells implements Controller_Move,Controller_Ondraw {

	private Color c=Color.RED;
    private int x,y;
    private int type;
    //主炮属性
    private int atk = 10;
    private int XSPEED = 20;
    private int YSPEED = 20;
    private int Width = 10;
    private int Height = 10;
    
    private SeaMap seamap = null;
    private boolean islive = true;
    private boolean isplayer;
    Direction dir;
    
    public Shells(int x, int y, int atk, SeaMap seamap, Direction ptDir, int type, boolean isplayer) {
    	
    	this.x = x;
    	this.y = y;
    	this.atk = atk;
    	this.seamap = seamap;
    	this.dir = ptDir;
    	this.isplayer = isplayer;
    	this.type = type;
    	if(isplayer)
    		c=Color.yellow;
    	if(type==1)
    	{
    		//副炮属性
    		Width = 5;
    		Height = 5;
    	}
    	else if(type==2)
    	{
    		//鱼雷属性
    		Height=25;
    		XSPEED = 10;
    	    YSPEED = 10;
    	}
    }
    
    @Override
    public void ondraw(Graphics g) {
        /*如果死亡将其从对应集合中删除*/
        if(!islive)
        {
        	seamap.shells.remove(this);
            return;
        }
        Color d = g.getColor();    //保存当前画笔颜色
        g.setColor(c);    //设置画笔颜色
        g.fillOval(x, y, Width, Height);    
        g.setColor(d);    //还原画笔颜色
        move();    //移动
    }
    
    @Override
    public void move(){
        /*向开炮方向移动*/
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
        /*判断命中情况,命中就爆炸*/
        if(ishit())
        	seamap.exploded.add(new Exploded(x,y,seamap));
        /*判断越界情况*/
        if(x<0||y<0||x>seamap.GameWidth||y>seamap.GameHeight)
            islive = false;
    }
    
    public Rectangle getRect(){
        return new Rectangle(x,y,Width,Height);
    }
    
    private boolean ishit() {
    	if(!this.islive)
    		return false;
    	
        for(Ship ship : seamap.enemys)
        {
        	if(this.isplayer && ship.isalive && this.getRect().intersects(ship.getRect()))
        	{
        		islive=false;
        		ship.hp-=this.atk;
	        	return true;
        	}
        }
        
        if(!this.isplayer && seamap.myship.isalive && this.getRect().intersects(seamap.myship.getRect()))
        {
        	islive=false;
    		seamap.myship.hp-=this.atk;
        	return true;
        }
        	
        for(Obstruction obs : seamap.obstruction)
        {
        	if(this.type!=2 && this.getRect().intersects(obs.getRect()))
        	{
        		islive=false;
        		return true;
        	}
        	else if(this.type==2 && !obs.issmoke && this.getRect().intersects(obs.getRect()))
        	{
        		islive=false;
        		return true;
        	}
        }
        return false;
    }
}
