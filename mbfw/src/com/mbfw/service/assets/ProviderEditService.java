package com.mbfw.service.assets;

import java.text.DecimalFormat;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("providerEditService")
public class ProviderEditService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	
	 // 增加供应商
	public void save_provider_insert(PageData pd) throws Exception{
		dao.save("ProviderMapper.save_provider_insert", pd);
	}
	
	 //* 查询供应商,分页
	public List<PageData> find_provider_insert(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProviderMapper.find_provider_insert", page);
	}
	public List<PageData> find_provider(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("ProviderMapper.find_provider", pd);
	}
	
	 //* 通过provider_code 查找供应商
	public PageData findProviderByprovider_code(PageData pd) throws Exception{
		
		return (PageData) dao.findForObject("findProviderByprovider_code", pd);
	}
	
	 //* @param pd 查询总条数
	public Integer find_provider_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("ProviderMapper.find_provider_totalnumber", pd);
	}
	
	// 根据关键字查询总条数
	public Integer find_provider_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("ProviderMapper.find_provider_bykey_totalnumber", page);
	}
	//根据关键字查询
	public List<PageData> find_provider_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("ProviderMapper.find_provider_bykey", page);
	}
	
	// 编辑供应商
	public void edit_provider(PageData pd) throws Exception{
		dao.update("ProviderMapper.edit_provider", pd);
	}
	
	// @param pd 通过id 查询供应商
	public PageData find_providerByPId(PageData pd) throws Exception{
		return (PageData) dao.findForObject("ProviderMapper.find_providerByPId", pd);
	}
	
	 // @param pd 通过id 删除供应商
	public void delete_provider(PageData pd) throws Exception{
		dao.delete("ProviderMapper.delete_provider", pd);
	}
	
	//批量删除供应商
	public void delete_all_provider(String [] all_provider) throws Exception{
		dao.delete("ProviderMapper.delete_all_provider", all_provider);
	}
	
	//封装供应商成json 暂时不用
	public String provider_code_name(PageOption page) throws Exception{
		 //构造Map和List 用于存放对应的供应商代码和名称
        Map<String,String> js = new HashMap<String,String>();
        //从数据库中查询机构的所有条目信息
        List<PageData> pds = find_provider_insert(page);
        for (PageData pageData : pds) {
			String provider_code =  pageData.get("provider_code").toString();
			String provider_name = pageData.getString("provider_name");
			js.put(provider_code,provider_name);
		}
        JSONArray json = JSONArray.fromObject(js);
		js.clear();
		return json.toString();
	}
	
	public Integer isRepeat(PageData pd) throws Exception{
		return (Integer) dao.findForObject("ProviderMapper.isRepeat", pd);
	}
	
	
	/**
	 * 
	 * @return 封装供应商
	 * @throws Exception
	 */
	public Map<String,String> getProvider() throws Exception{
		Map<String,String> providerName = new HashMap<>();
		List<PageData> pds = (List<PageData>) dao.findForList("ProviderMapper.getProvider", null);
		DecimalFormat deft = new DecimalFormat("000");
		for (PageData pageData : pds) {
			int pc = (int) pageData.get("provider_code");
			String providerCode = deft.format(pc);
			String name = pageData.getString("provider_name");
			providerName.put(name, providerCode);
		}
		return providerName;
	}
	
	/**
	 * 
	 * @param pd 获得供应商代码
	 * @return
	 * @throws Exception
	 */
	public Integer get_code_byproduct(PageData pd) throws Exception{
		return (Integer) dao.findForObject("ProviderMapper.get_code_byproduct", pd);
	}
	
	/**
	 * 
	 * @param pd 删除，如果产品表有已经使用的供应商，则该供应商不能删除
	 * @return
	 * @throws Exception
	 */
	public Integer find_provider_code_product(PageData pd) throws Exception{
		return (Integer)dao.findForObject("ProviderMapper.find_provider_code_product", pd);
	}
	
}
