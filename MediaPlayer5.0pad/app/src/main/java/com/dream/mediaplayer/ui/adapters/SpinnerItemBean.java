package com.dream.mediaplayer.ui.adapters;

public class SpinnerItemBean {
	/**
	 */
	private String name;
	
	/**
	 */
	private boolean isSelected;
	
	public SpinnerItemBean(String name, boolean isSelected) {
		this.name = name;
		this.isSelected = isSelected;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getIsSelected() {
		return isSelected;
	}
	
	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
