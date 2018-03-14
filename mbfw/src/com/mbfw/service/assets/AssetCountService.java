package com.mbfw.service.assets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetCountService")
public class AssetCountService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public BigDecimal findTotalPrice(PageData pd) throws Exception {
		return (BigDecimal) dao.findForObject("AssetCountMapper.findTotalPrice", pd);
	}
}
