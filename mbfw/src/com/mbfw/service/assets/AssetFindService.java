package com.mbfw.service.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetRAR;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetFindService")
public class AssetFindService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> find_asset_find(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList(
				"AssetFindMapper.find_asset_info", page);
	}

	/**
	 * 查询总条数
	 * 
	 * @throws Exception
	 */
	public Integer findTotalNumber(PageData pd) throws Exception {

		return (Integer) dao.findForObject("AssetFindMapper.findTotalNumber",
				pd);
	}

	/**
	 * 条件查询对应的条目数量
	 * 
	 * @throws Exception
	 */
	public Integer findConditionNumber(PageData pd) throws Exception {
		
		return (Integer) dao.findForObject("AssetFindMapper.findConditionNumber",
				pd);
	}
	
	/**
	 * 条件查询对应的条目
	 */
	public List<PageData> findConditionItem(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList(
				"AssetFindMapper.findConditionItem", page);
	}

	
	/**
	 * 查询机构信息表构造成json数据
	 */
	public String findAssetInfo() throws Exception {
		// JSON格式数据解析对象
		JSONObject jo = new JSONObject();
		// 构造Map和List 用于存放对应的公司和下级公司或部门
		Map<String, List<String>> js = new HashMap<String, List<String>>();
		List<String> superior = new ArrayList<String>();// 存放key
		// 从数据库中查询机构的所有条目信息
		List<PageData> pds = (List<PageData>) dao.findForList(
				"AssetFindMapper.find_asset_info", null);
		// 循环鉴别每一个条目
		List<String> lower = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_class");
			if (!lower.contains(asset_class)) {
				lower.add(asset_class);
			}
		}
		js.put("资产类别", lower);
		
		
		List<String> lower1 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_code");
			if (!(lower1.contains(asset_class))) {
				lower1.add(asset_class);
			}
		}
	
		js.put("资产编码", lower1);
	
		
		List<String> lower2 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_name");
			if (!(lower2.contains(asset_class))) {
				lower2.add(asset_class);
			}
		}
		js.put("资产名称", lower2);
		
		List<String> lower3 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_use_dept");
			if (!lower3.contains(asset_class)) {
				lower3.add(asset_class);
			}
		}
		js.put("使用部门", lower3);
		
		
		List<String> lower9 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_use_company");
			if (!lower9.contains(asset_class)) {
				lower9.add(asset_class);
			}
		}
		js.put("使用公司", lower9);
		
		
		List<String> lower4 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_status");
			if (!lower4.contains(asset_class)) {
				lower4.add(asset_class);
			}
		}
		
		
		js.put("状态", lower4);
		
		List<String> lower5 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_brand");
			if (!lower5.contains(asset_class)) {
				lower5.add(asset_class);
			}
		}
		js.put("品牌", lower5);
		
		List<String> lower6 = new ArrayList<String>();
		for (PageData pd : pds) {

			String asset_class = pd.get("asset_price")+"";
			if (!lower6.contains(asset_class)) {
				lower6.add(asset_class);
			}
		}
		js.put("价格", lower6);
		
		List<String> lower7 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_into_bill");
			if (!lower7.contains(asset_class)) {
				lower7.add(asset_class);
			}
		}
		js.put("入库单号", lower7);
		
		List<String> lower8 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_user");
			if (!lower8.contains(asset_class)) {
				lower8.add(asset_class);
			}
		}
		js.put("使用人", lower8);
		
		
		/*
		List<String> lower10 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_class");
			if (!lower10.contains(asset_class)) {
				lower10.add(asset_class);
			}
		}
		js.put("资产类别", lower10);
		
		List<String> lower11 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_class");
			if (!lower11.contains(asset_class)) {
				lower11.add(asset_class);
			}
		}
		js.put("资产类别", lower11);
		
		List<String> lower12 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_class");
			if (!lower12.contains(asset_class)) {
				lower12.add(asset_class);
			}
		}
		js.put("资产类别", lower12);
		
		List<String> lower13 = new ArrayList<String>();
		for (PageData pd : pds) {
			String asset_class = pd.getString("asset_class");
			if (!lower13.contains(asset_class)) {
				lower13.add(asset_class);
			}
		}
		js.put("资产类别", lower13);
		*/
		
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		lower.clear();
		lower1.clear();
		lower2.clear();
		superior.clear();
		return json.toString();
	}

	/**
	 * 查询所有的资产信息（当无检索条件的时候）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAllAssetInfo(PageOption page) throws Exception {
	
		return (List<PageData>) dao.findForList("AssetFindMapper.assetinfoListPage", page);
	}
	
	public List<PageData> findBetterAssetInfo(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList(
				"AssetFindMapper.findByDefault", page);
	}
	/**
	 * 查询所有的资产信息（高级查询）
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findGaoJi(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList(
				"AssetFindMapper.findGaoJi", page);
	}
	
	
	public Integer findGaoJiNumber(PageData pd) throws Exception {
		return (Integer) dao.findForObject(
				"AssetFindMapper.findGaoJiNumber", pd);
	}
	//单独查询显示数据
	public AssetInfo listAllotById(PageData pd) throws Exception {
		return (AssetInfo) dao.findForObject("AssetFindMapper.listAssetInfo",pd);
	}
}
