package com.mbfw.service.assets;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.AssetDepartmentManage;
import com.mbfw.util.PageData;

/** 
* @ClassName: DepartmentService 
* @Description: 部门Service
* @author scw
* @date 2017-09-21
*  
*/
@Service("departmentService")
public class AssetDepartmentManageService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 显示所有的部门的信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public AssetDepartmentManage listAllDepartment(PageData pd) throws Exception {
		return (AssetDepartmentManage)dao.findForObject("AssetDepartment.listAllDepartment",pd);
	}
	
	/**
	 * 
	 * @param pd
	 * @throws Exception 
	 */
	public void saveDepartmentContent(PageData pd) throws Exception {
		dao.save("AssetDepartment.savedepartment", pd);
		
	}
}
