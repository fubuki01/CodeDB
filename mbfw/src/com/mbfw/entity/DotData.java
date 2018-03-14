package com.mbfw.entity;
/**
 * 统计图表需要的点数据，如柱形图，折线图需要的就是点数据
 */
import java.math.BigDecimal;
import java.util.List;

public class DotData {
	private String name;
	private List<BigDecimal> data;
	
	public DotData(String name,List<BigDecimal> data){
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public List<BigDecimal> getData() {
		return data;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setData(List<BigDecimal> data) {
		this.data = data;
	}
}
