package Exploded;

import java.awt.Color;
import java.awt.Graphics;

import mygame.SeaMap;

public class Exploded {

	int x,y;    //爆炸位置
    private boolean live = true;    //爆炸是否存在
    int step = 0;    //爆炸时间控制
    int [] diameter = new int[] {5,15,25,35,40,45,50,55,50,45,40,35,30,25,20,15,10,5};//爆炸范围
    
    private SeaMap seamap;
    public Exploded(int x, int y, SeaMap seamap) {    
        super();
        this.x = x;
        this.y = y;
        this.seamap = seamap;
    }
    
    /*画爆炸*/
    public void ondraw(Graphics g){
        if(!live) return;    //如果爆炸死亡状态不画结束
        /*如果爆炸时间结束爆炸不存在并在集合中删除*/
        if(step == diameter.length){
            live = false;    //爆炸死亡
            step = 0;    //步骤时间归0
            seamap.exploded.remove(this);    //集合中删除
            return;
        }
        /*画爆炸*/
        Color c = g.getColor();
        g.setColor(Color.orange);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);
        
        step++;
    }
}
