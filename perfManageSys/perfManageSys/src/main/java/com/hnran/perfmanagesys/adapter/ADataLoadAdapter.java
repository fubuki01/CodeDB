package com.hnran.perfmanagesys.adapter;

import java.util.List;

import android.widget.BaseAdapter;

public abstract class ADataLoadAdapter extends BaseAdapter{
	public abstract boolean addData(List<?> t);
	public abstract void clearData();
}
