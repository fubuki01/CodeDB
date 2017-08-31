package Obstruction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import Controller.Controller_Ondraw;

public class Obstruction implements Controller_Ondraw {
	
		protected int x,y,w,h;
		public boolean issmoke = false;
		protected Color color = Color.GREEN;
		
		public Obstruction(int x, int y, int w, int h) {
	        this.x = x;
	        this.y = y;
	        this.w = w;
	        this.h = h;
	    }

		public Rectangle getRect() {
			return new Rectangle(x,y,w,h);
		}
		
		@Override
		public void ondraw(Graphics  g) {
			Color c = g.getColor();    //保存当前画笔颜色
	        g.setColor(color);    //设置画笔颜色
	        g.fillOval(x, y, w, h);
	        g.setColor(c);
		}
}
