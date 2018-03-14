package com.mbfw.service.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

import net.sf.ehcache.search.aggregator.Count;
import net.sf.json.JSONArray;

@Service
public class ProductsInfoService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private AssetClassifyService assetClassifyService ;

	@Autowired
	private MertialClassifyService mertialClassifyService;

	/**
	 * 
	 * @param pd 保存产品信息
	 * @throws Exception
	 */
	public void saveProduct(PageData pd) throws Exception{
		dao.save("ProductsInfoMapper.saveProduct", pd);
	}
	
	/**
	 * 
	 * @param pd 插叙编码是否重复
	 * @throws Exception
	 */
	public Integer searchProductCode(String code) throws Exception{
		return(Integer) dao.findForObject("ProductsInfoMapper.searchProductCode", code);
	}

	/**
	 * 
	 * @param page 查询全部的id
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findProduct(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("ProductsInfoMapper.findProduct", page);
	}

	/**
	 * 
	 * @param pd 查找固定产品
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findProductByClass(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ProductsInfoMapper.findProductByClass", pd);
	}
	/**
	 * 
	 * @param pd 统计总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_productInfo_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("ProductsInfoMapper.find_productInfo_totalnumber", pd);
	}

	/**
	 * 
	 * @param pd 通过id查询产品
	 * @return
	 * @throws Exception
	 */
	public PageData findProductById(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProductsInfoMapper.findProductById", pd);
	}

	/**
	 * 通过provider_code 查找产品
	 */
	public PageData findProductByprovider_code(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProductsInfoMapper.findProductByprovider_code", pd);
	}

	/**
	 * 
	 * @param pd 通过关键字查询
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findProductByKey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("ProductsInfoMapper.findProductByKey", page);
	}

	/**
	 * 
	 * @param page 根据关键字获得总条数
	 * @return
	 * @throws Exception
	 */
	public Integer findProductByKey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("ProductsInfoMapper.findProductByKey_totalnumber", page);
	}

	/**
	 * 
	 * @param pd 据id更新产品信息
	 * @throws Exception
	 */
	public void updateProduct(PageData pd) throws Exception{
		dao.update("ProductsInfoMapper.updateProduct", pd);
	}

	/**
	 * 
	 * @param pd 据id删除产品信息
	 * @throws Exception
	 */
	public void deleteProductById(PageData pd) throws Exception{
		dao.delete("ProductsInfoMapper.deleteProductById", pd);
	}

	/**
	 * 
	 * @param pd 据id批量删除产品信息
	 * @throws Exception
	 */
	public void deleteAllProductById(String [] array_del_all_product) throws Exception{
		dao.delete("ProductsInfoMapper.deleteAllProductById", array_del_all_product);
	}

	/// 封装，固定产品类别，名称
	public String fixed_product_info(PageData pd) throws Exception{
		// JSON格式数据解析对象
		// JSONObject jo = new JSONObject();
		//构造Map和List 用于存放对应的公司和下级公司或部门
		Map<String,List<String>> js = new HashMap<String,List<String>>();
		//从数据库中查询类别的所有条目信息
		List<PageData> pds= assetClassifyService.listAllPoint(pd);
		//循环鉴别每一个条目
		for(PageData pageData :pds){
			String pId = pageData.getString("pId");
			String orderSort = pageData.getString("orderSort");
			if(pId.equals("0")){
				String parentName = pageData.getString("name");
				List<String> childClass = new ArrayList<String>();
				for (PageData pageData2 : pds) {
					String parentSort = pageData2.getString("parentSort");
					if(orderSort.equals(parentSort)){
						String name = pageData2.getString("name");
						String name_coding = name +"@" +pageData2.getString("coding");
						childClass.add(name_coding);
					}
				}
				js.put(parentName, childClass);
			}
		}
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		return json.toString();

	}

	/// 封装，耗材产品类别，名称
	public String used_product_info(PageData pd) throws Exception{
		// JSON格式数据解析对象
		// JSONObject jo = new JSONObject();
		//构造Map和List 用于存放对应的公司和下级公司或部门
		Map<String,List<String>> js = new HashMap<String,List<String>>();
		//从数据库中查询类别的所有条目信息
		List<PageData> pds= mertialClassifyService.find_used_class(pd);
		//循环鉴别每一个条目
		for(PageData pageData :pds){
			String pId = pageData.getString("pId");
			String orderSort = pageData.getString("orderSort");
			if(pId.equals("0")){
				String parentName = pageData.getString("name");
				List<String> childClass = new ArrayList<String>();
				for (PageData pageData2 : pds) {
					String parentSort = pageData2.getString("parentSort");
					if(orderSort.equals(parentSort)){
						String name = pageData2.getString("name");
						String name_coding = name +"@" +pageData2.getString("coding");
						childClass.add(name_coding);
					}
				}
				js.put(parentName, childClass);
			}
		}
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		return json.toString();

	}
	//更新产品信息，根据资产编码 
	public void updateProductByProductCode(PageData pd) throws Exception{
		dao.update("ProductsInfoMapper.updateProductByProductCode", pd);
	}

	// 根据id填写资产登记
	public PageData find_product_to_add_asset(Integer id) throws Exception{
		return (PageData) dao.findForObject("ProductsInfoMapper.find_product_to_add_asset", id);
	}
	
	/**
	 * 从资产类别表中获取数据进行封装
	 * @return
	 * @throws Exception
	 */
	public Map<String,Map<String,String>> getAssetClass() throws Exception {
		Map<String,Map<String,String>> assetClass = new HashMap<>();//存放产品类别
		assetClass.clear();
		List<PageData> pds = (List<PageData>) dao.findForList("ProductsInfoMapper.getAssetClass", null);
		for (PageData pageData : pds) {
			String pId = pageData.getString("pId");
			if(pId.equals("0")){
				String name = pageData.getString("name");
				String orderSort = pageData.getString("orderSort");
				Map<String,String> assetName = new HashMap<>();//产品名称 产品编号
				for (PageData pageData2 : pds) {
					String zPId = pageData2.getString("pId");
					if(!zPId.equals("0")){
						String parentSort = pageData2.getString("parentSort");
						if(parentSort.equals(orderSort)){
							String ziName = pageData2.getString("name");
							String coding = pageData2.getString("coding");
							assetName.put(ziName, coding);
						}else{
							continue;
						}
					}else{
						continue;
					}
					
				}
				assetClass.put(name, assetName);
			}
		}
		return assetClass;
	}

	public String getCode(PageData p) throws Exception {
		PageData pd = (PageData) dao.findForObject("ProductsInfoMapper.getCode", p);
		String code = pd.getString("coding");
		return code;
	}
	
	//===*********导入资产入库 lss 
	/**
	 * 
	 * @param pd查找匹配 产品类别，名称，供应商 肯定唯一
	 * @return
	 * @throws Exception
	 */
	public PageData find_class_name(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProductsInfoMapper.find_class_name", pd);
	}
	/**
	 * 
	 * @param pd根据product_code 查找
	 * @return
	 * @throws Exception
	 */
	public PageData get_product_total(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProductsInfoMapper.get_product_total", pd);
	}
	//==***********
	
	/**
	 * 
	 * @param pd 删除产品信息，判断资产表里有没有已用该产品
	 * @return
	 * @throws Exception
	 */
	public Integer get_asset_ncp(PageData pd) throws Exception{
		return (Integer) dao.findForObject("ProductsInfoMapper.get_asset_ncp", pd);
	}
	/**
	 * 
	 * @param pd 判断项目立项是否使用该产品
	 * @return
	 * @throws Exception
	 */
	public Integer get_product_code_aproject(PageData pd) throws Exception{
		return (Integer) dao.findForObject("ProductsInfoMapper.get_product_code_aproject", pd);
	}

}
