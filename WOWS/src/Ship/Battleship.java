package Ship;

import java.awt.Graphics;

import Shells.Shells;
import mygame.SeaMap;

public class Battleship extends Ship {

	{
		maingunatk=15;
	    specialweaponatk=50;
	    specialweaponnum=15;
	    specialactionnum=3;
		XSPEED = 3;
    	YSPEED = 1;
    	Width = 90;
    	Height = 40;
    	hp = 100;
	}
    public Battleship(SeaMap sm, boolean isplayer, int x, int y) {
    	super(sm, isplayer, x, y);
    }
    
	@Override
	protected void drawPT(Graphics g) {
		// TODO Auto-generated method stub
		int tx = x+Width/4;
		int tx1 = x+Width/2;
		int tx2 = x+Width*3/4;
		int ty = y+Height/2;
		int t = (int)(Width/2/1.4);
		switch(ptDir) {
        case L:
            g.drawLine(tx, ty, tx-Width/2, ty);
            g.drawLine(tx1, ty, tx1-Width/2, ty);
            g.drawLine(tx1, ty, tx2-Width/2, ty);
            break;
        case LU:
            g.drawLine(tx, ty, tx-t, ty-t);
            g.drawLine(tx1, ty, tx1-t, ty-t);
            g.drawLine(tx2, ty, tx2-t, ty-t);
            break;
        case U:
            g.drawLine(tx, ty, tx, ty-Width/2);
            g.drawLine(tx1, ty, tx1, ty-Width/2);
            g.drawLine(tx2, ty, tx2, ty-Width/2);
            break;
        case RU:
            g.drawLine(tx, ty, tx+t, ty-t);
            g.drawLine(tx1, ty, tx1+t, ty-t);
            g.drawLine(tx2, ty, tx2+t, ty-t);
            break;
        case R:
            g.drawLine(tx, ty, tx+Width/2, ty);
            g.drawLine(tx1, ty, tx1+Width/2, ty);
            g.drawLine(tx2, ty, tx2+Width/2, ty);
            break;
        case RD:
            g.drawLine(tx, ty, tx+t, ty+t);
            g.drawLine(tx1, ty, tx1+t, ty+t);
            g.drawLine(tx2, ty, tx2+t, ty+t);
            break;
        case D:
            g.drawLine(tx, ty, tx, ty+Width/2);
            g.drawLine(tx1, ty, tx1, ty+Width/2);
            g.drawLine(tx2, ty, tx2, ty+Width/2);
            break;
        case LD:
            g.drawLine(tx, ty, tx-t, ty+t);
            g.drawLine(tx1, ty, tx1-t, ty+t);
            g.drawLine(tx2, ty, tx2-t, ty+t);
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
		int fx = x+Width/4;
		int fx1 = x+Width/2;
		int fx2 = x+Width*3/4;
		int fy = y+Height/2;
		seamap.shells.add(new Shells(fx, fy, maingunatk, seamap, ptDir, 0, isplayer));
		seamap.shells.add(new Shells(fx1, fy, maingunatk, seamap, ptDir, 0, isplayer));
		seamap.shells.add(new Shells(fx2, fy, maingunatk, seamap, ptDir, 0, isplayer));
	}

	@Override
	public void specialfire() {
		// TODO Auto-generated method stub
		if(!isalive)
			return;
		if(specialweaponnum<=0)
			return;
		specialweaponnum--;
		int fx = x+Width/2;
		int fx1 = x+Width/4;
		int fx2 = x+Width*3/4;
		int fy = y+Height/2;
		int fy1 = y+Height/4;
		int fy2 = y+Height*3/4;
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.D, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.L, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.LD, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.LU, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.R, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.RD, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.RU, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, Direction.U, 1, isplayer));
		
		seamap.shells.add(new Shells(fx1, fy, specialweaponatk, seamap, Direction.LD, 1, isplayer));
		seamap.shells.add(new Shells(fx1, fy, specialweaponatk, seamap, Direction.LU, 1, isplayer));
		seamap.shells.add(new Shells(fx2, fy, specialweaponatk, seamap, Direction.RD, 1, isplayer));
		seamap.shells.add(new Shells(fx2, fy, specialweaponatk, seamap, Direction.RU, 1, isplayer));
		
		seamap.shells.add(new Shells(fx, fy1, specialweaponatk, seamap, Direction.R, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy1, specialweaponatk, seamap, Direction.L, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy2, specialweaponatk, seamap, Direction.R, 1, isplayer));
		seamap.shells.add(new Shells(fx, fy2, specialweaponatk, seamap, Direction.L, 1, isplayer));
	}

	@Override
	public void specialaction() {
		// TODO Auto-generated method stub
		if(specialactionnum<=0)
			return;
		specialactionnum--;
		if(hp<100)
			hp+=30;
		if(hp>100)
			hp=100;
	}

}
