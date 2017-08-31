package Ship;

import java.awt.Graphics;

import Obstruction.Smoke;
import Shells.Shells;
import mygame.SeaMap;

public class Destroyer extends Ship {
	
	private boolean issmoking=false;
	{ 
		maingunatk=5;
	    specialweaponatk=30;
	    specialweaponnum=10;
	    specialactionnum=2;
		XSPEED = 8;
		YSPEED = 6;
		Width = 35;
		Height = 15;
		hp = 25;
	}
	
    public Destroyer(SeaMap sm, boolean isplayer, int x, int y) {
    	super(sm,isplayer,x,y);
    }
    
	@Override
	protected void drawPT(Graphics g) {
		// TODO Auto-generated method stub
		int tx = x+Width/2;
		int ty = y+Height/2;
		int t = (int)(Width/2/1.4);
		switch(ptDir) {
        case L:
            g.drawLine(tx, ty, tx-Width/2, ty);
            break;
        case LU:
            g.drawLine(tx, ty, tx-t, ty-t);
            break;
        case U:
            g.drawLine(tx, ty, tx, ty-Width/2);
            break;
        case RU:
            g.drawLine(tx, ty, tx+t, ty-t);
            break;
        case R:
            g.drawLine(tx, ty, tx+Width/2, ty);
            break;
        case RD:
            g.drawLine(tx, ty, tx+t, ty+t);
            break;
        case D:
            g.drawLine(tx, ty, tx, ty+Width/2);
            break;
        case LD:
            g.drawLine(tx, ty, tx-t, ty+t);
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
		int fx = x+Width/2;
		int fy = y+Height/2;
		seamap.shells.add(new Shells(fx, fy, maingunatk, seamap, ptDir, 0, isplayer));
	}

	@Override
	public void specialfire() {
		// TODO Auto-generated method stub
		if(!isalive)
			return;
		if(specialweaponnum<=0)
			return;
		if(!(ptDir==Direction.D||ptDir==Direction.U))
			return;
		specialweaponnum--;
		int fx = x+Width/2;
		int fx1 = x;
		int fx2 = x+Width;
		int fy = y+Height/2;
		seamap.shells.add(new Shells(fx, fy, specialweaponatk, seamap, ptDir, 2, isplayer));
		seamap.shells.add(new Shells(fx1, fy, specialweaponatk, seamap, ptDir, 2, isplayer));
		seamap.shells.add(new Shells(fx2, fy, specialweaponatk, seamap, ptDir, 2, isplayer));
	}

	@Override
	public void specialaction() {
		// TODO Auto-generated method stub
		if(issmoking)
			return;
		if(specialactionnum<=0)
			return;
		specialactionnum--;
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				int i=30;
				issmoking=true;
				while(i-->0)
				{
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					finally {
						seamap.obstruction.add(new Smoke(x, y, seamap));
					}
				}
				issmoking=false;
			}
		}).start();
	}
	
}
