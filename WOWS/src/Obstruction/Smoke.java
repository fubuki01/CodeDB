package Obstruction;

import java.awt.Color;
import java.awt.Graphics;

import mygame.SeaMap;

public class Smoke extends Obstruction {
	
	private int step;
	private boolean isexist;
	private SeaMap seamap;
	
	public Smoke(int x, int y , SeaMap seamap) {
		
		super(x,y,70,50);
		color = Color.LIGHT_GRAY;
		issmoke = true;
		step=10;
		this.seamap=seamap;
		
		isexist=true;
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					isexist=false;
				}
			}
		}).start();
		
	}
	
	@Override
	public void ondraw(Graphics  g) {
		if(!isexist)
		{
			seamap.obstruction.remove(this);
			return;
		}
		
		Color c = g.getColor();    //���浱ǰ������ɫ
        g.setColor(color);    //���û�����ɫ
        if(step>0)
        {
        	g.fillOval(x, y, (int)(w*((float)1/step)), (int)(h*((float)1/step)) );
        	step--;
        }    	
        else
        	g.fillOval(x, y, w, h);
        g.setColor(c);
	}
	
}
