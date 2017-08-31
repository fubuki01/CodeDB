package Ship;

import java.awt.Graphics;

import Shells.Shells;
import mygame.SeaMap;

public class Cruiser extends Ship {

	{
		maingunatk=10;
	    specialweaponatk=0;
	    specialweaponnum=0;
	    specialactionnum=0;
		XSPEED = 6;
    	YSPEED = 4;
    	Width = 50;
    	Height = 25;
    	hp = 70;
	}
    public Cruiser(SeaMap sm, boolean isplayer, int x, int y) {
    	super(sm, isplayer, x, y);
    }
    
	@Override
	protected void drawPT(Graphics g) {
		// TODO Auto-generated method stub
		int tx = x+Width/3;
		int tx1 = x+Width*2/3;
		int ty = y+Height/2;
		int t = (int)(Width/2/1.4);
		switch(ptDir) {
        case L:
            g.drawLine(tx, ty, tx-Width/2, ty);
            g.drawLine(tx1, ty, tx1-Width/2, ty);
            break;
        case LU:
            g.drawLine(tx, ty, tx-t, ty-t);
            g.drawLine(tx1, ty, tx1-t, ty-t);
            break;
        case U:
            g.drawLine(tx, ty, tx, ty-Width/2);
            g.drawLine(tx1, ty, tx1, ty-Width/2);
            break;
        case RU:
            g.drawLine(tx, ty, tx+t, ty-t);
            g.drawLine(tx1, ty, tx1+t, ty-t);
            break;
        case R:
            g.drawLine(tx, ty, tx+Width/2, ty);
            g.drawLine(tx1, ty, tx1+Width/2, ty);
            break;
        case RD:
            g.drawLine(tx, ty, tx+t, ty+t);
            g.drawLine(tx1, ty, tx1+t, ty+t);
            break;
        case D:
            g.drawLine(tx, ty, tx, ty+Width/2);
            g.drawLine(tx1, ty, tx1, ty+Width/2);
            break;
        case LD:
            g.drawLine(tx, ty, tx-t, ty+t);
            g.drawLine(tx1, ty, tx1-t, ty+t);
            break;
        case STOP:
        	break;
        }
	}

	@Override
	public void fire() {
		// TODO Auto-generated method stub
		if(!isalive)
			return;
		int fx = x+Width/3;
		int fx1 = x+Width*2/3;
		int fy = y+Height/2;
		seamap.shells.add(new Shells(fx, fy, maingunatk, seamap, ptDir, 0, isplayer));
		seamap.shells.add(new Shells(fx1, fy, maingunatk, seamap, ptDir, 0, isplayer));
	}

	@Override
	public void specialfire() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void specialaction() {
		// TODO Auto-generated method stub
		
	}

}
