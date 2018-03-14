package com.mbfw.entity.assets;

import com.mbfw.util.PageData;

/**
 * 
 * @author scw
 * 2017-09-19
 * funciton：处理对于界面中的分页部分的管理
 *
 */
public class PageOption {
	private int showCount; // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage; // 当前页
	private int currentResult; // 当前记录起始索引
	private PageData pd ;      //封装一个PageData对象，主要是为了能够方便进行检索功能
	
	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}

	public PageOption(int showCount , int currentPage){
		this.showCount = showCount;
		this.currentPage = currentPage;
	}
		
	public int getShowCount() {
		return showCount;
	}
	
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	
	public int getTotalPage() {
		if (totalResult % showCount == 0)
			totalPage = totalResult / showCount;
		else
			totalPage = totalResult / showCount + 1;
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalResult() {
		return totalResult;
	}
	
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	
	public int getCurrentPage() {
		if (currentPage <= 0)
			currentPage = 1;
		if (currentPage > getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	//获取当前页的数据条的索引
	public int getCurrentResult() {
		currentResult = (getCurrentPage() - 1) * getShowCount();
		if (currentResult < 0)
			currentResult = 0;
		return currentResult;
	}
	
	
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	
	
}
