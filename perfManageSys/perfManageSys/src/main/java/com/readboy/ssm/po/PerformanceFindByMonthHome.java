package com.readboy.ssm.po;

import java.util.List;

public class PerformanceFindByMonthHome{
	private PerformanceGzhz performanceGzhz;
	private List<PerformanceGwjxgzMxCustom> performanceGwjxgzMxCustomList;
	public PerformanceGzhz getPerformanceGzhz() {
		return performanceGzhz;
	}
	public List<PerformanceGwjxgzMxCustom> getPerformanceGwjxgzMxCustomList() {
		return performanceGwjxgzMxCustomList;
	}
	public void setPerformanceGzhz(PerformanceGzhz performanceGzhz) {
		this.performanceGzhz = performanceGzhz;
	}
	public void setPerformanceGwjxgzMxCustomList(
			List<PerformanceGwjxgzMxCustom> performanceGwjxgzMxCustomList) {
		this.performanceGwjxgzMxCustomList = performanceGwjxgzMxCustomList;
	}
}
