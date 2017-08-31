package Exploded;

import java.awt.Color;
import java.awt.Graphics;

import mygame.SeaMap;

public class Exploded {

	int x,y;    //��ըλ��
    private boolean live = true;    //��ը�Ƿ����
    int step = 0;    //��ըʱ�����
    int [] diameter = new int[] {5,15,25,35,40,45,50,55,50,45,40,35,30,25,20,15,10,5};//��ը��Χ
    
    private SeaMap seamap;
    public Exploded(int x, int y, SeaMap seamap) {    
        super();
        this.x = x;
        this.y = y;
        this.seamap = seamap;
    }
    
    /*����ը*/
    public void ondraw(Graphics g){
        if(!live) return;    //�����ը����״̬��������
        /*�����ըʱ�������ը�����ڲ��ڼ�����ɾ��*/
        if(step == diameter.length){
            live = false;    //��ը����
            step = 0;    //����ʱ���0
            seamap.exploded.remove(this);    //������ɾ��
            return;
        }
        /*����ը*/
        Color c = g.getColor();
        g.setColor(Color.orange);
        g.fillOval(x, y, diameter[step], diameter[step]);
        g.setColor(c);
        
        step++;
    }
}
