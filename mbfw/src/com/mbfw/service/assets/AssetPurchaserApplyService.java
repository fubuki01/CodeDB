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
import com.mbfw.entity.assets.ProjectApply;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;

@Service("assetPurchaserApplyService")
public class AssetPurchaserApplyService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private AssetPurchaserApplyService assetPurchaserApplyService; // 项目审批服务
	
	public void save_apurchase_apply(ProjectApply pd)throws Exception{
		dao.save("AssetAProjectManager.save_purchase_apply", pd);
	}

	/**
	 * 获取最大的项目ID
	 * @return
	 * @throws Exception 
	 */
	public Integer findMaxProjectId() throws Exception {
		
		return (Integer) dao.findForObject("AssetAProjectManager.findMaxIdNumber", null);
	}

	/**
	 * 根据流程ID和第一的顺序找到对应的审批节点的名字(联表查询 sys_process_detail 和sys_approvnode )
	 * @param processId
	 * @return 
	 * @throws Exception 
	 */
	public String findProcessNodeName(Integer processId) throws Exception {
		return (String) dao.findForObject("AssetAProjectManager.findProcessNodeName", processId);
		
	}

	/**
	 * 插入数据到项目过程表中（新增）
	 * @param newDataProcess
	 * @throws Exception 
	 */
	public void saveOneProjectProcessInfo(PageData pd) throws Exception {
			dao.save("AssetAProjectManager.saveOneProjectProcessInfo", pd);
	}
	/**
	 * 
	 * @param pd 查询通过审批的条目
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_finish_purchase_bill(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetAProjectManager.find_finish_purchase_bill", pd);
	}
	
	//  封装通过审批的信息 飞虎空调@小米@15-03-02 查找15 有的话就添加
	public String pass_approval_info(PageData pd) throws Exception{
		Map<String,String> js = new HashMap<String,String>();
        List<String> superior = new ArrayList<String>();//存放key
        
        List<PageData> pds =  (List<PageData>) dao.findForList("AssetAProjectManager.find_finish_purchase_bill", pd);
        //循环鉴别每一个条目
        for(PageData pageData : pds){
        	String apply_name = pageData.get("apply_name").toString()+"@"+pageData.get("apply_id").toString();
        	String splits = pageData.getString("device_name")+"@"+pageData.get("device_number").toString()
        	+"@"+pageData.get("device_price").toString();
        	js.put(apply_name, splits);
        	
        }
        
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		superior.clear();
		return json.toString();
	}
	
	/**
	 * 
	 * @param apply_id 更新项目立项状态
	 * @throws Exception
	 */
	public void update_finish_purchase_bill(Integer apply_id) throws Exception{
		dao.update("AssetAProjectManager.update_finish_purchase_bill", apply_id);
	}
	/**
	 * @param into_code  更新项目立项的状态,使之完成
	 * @throws Exception
	 */
	public void update_apply_project_after_registerasset(String into_code) throws Exception{
		dao.update("AssetAProjectManager.update_apply_project_after_registerasset", into_code);
	}
	
	/**
	 * @param into_code  更新项目立项的状态,使之采购中
	 * @throws Exception
	 */
	public void updating_apply_project_after_registerasset(String into_code) throws Exception{
		dao.update("AssetAProjectManager.updating_apply_project_after_registerasset", into_code);
	}
}
