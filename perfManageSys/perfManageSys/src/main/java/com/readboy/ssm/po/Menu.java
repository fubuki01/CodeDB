package com.readboy.ssm.po;

/**
 * 手机主界面菜单选项，对应数据库表 apk_menu
 * @author tan
 * @date 2017-7-20
 */
public class Menu {
	private int id;			//菜单id，自增长
	private String name;	//菜单名称	
	private int show;		//是否显示
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getShow() {
		return show;
	}
	public void setShow(int show) {
		this.show = show;
	}
	
}
