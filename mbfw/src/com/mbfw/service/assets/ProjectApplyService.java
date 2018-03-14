package com.mbfw.service.assets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.ProjectApply;
import com.mbfw.util.PageData;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 项目立项
 * @author Wyd
 *
 */
//@Service("projectApply")
@Service
public class ProjectApplyService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 提交项目立项
	 * @param pd
	 * @throws Exception
	 */
	public void saveProjectApply(ProjectApply pd) throws Exception {
		dao.save("ProjectApplyMapper.addProject", pd);
	}
	
	/**
	 * 列出项目立项列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllProject(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.listAllProject", page);
	}
	
	
	/**
	 * 删除选中的项目
	 * @param s
	 * @throws Exception
	 */
	public void deleteProject(int[] s) throws Exception {
		dao.delete("ProjectApplyMapper.deleteProject", s);
	}
	
	/**
	 * 查询要要删除的选中的项目
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> searchProject(int[] s) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.searchProject", s);
	}
	
	/**
	 * 查询要修改的单一选中项目
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public PageData searchModifyProject(int s) throws Exception {
		return (PageData) dao.findForObject("ProjectApplyMapper.searchModifyProject", s);
	}
	
	/**
	 * 更新修改的项目
	 * @param pd
	 * @throws Exception
	 */
	public void updateProject(ProjectApply pd) throws Exception {
		dao.update("ProjectApplyMapper.updateProject", pd);
	}
	
	/**
	 * 更新请求打回项目的状态
	 * @param s
	 * @throws Exception
	 */
	public void updateReturnProject(int[] s) throws Exception {
		dao.findForList("ProjectApplyMapper.updateReturnProject", s);
	}
	
	/**
	 * 显示所有项目共有多少条数据条数
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotalProjectNumber() throws Exception {		
		return (Integer) dao.findForObject("ProjectApplyMapper.findTotalProjectNumber", null);
	}
	
	
	/**
	 * 查询检索内容的数据总条数
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public Integer findNumberBySearch(String s) throws Exception {
		return (Integer) dao.findForObject("ProjectApplyMapper.findSearchNumber", s);
	}
	
	/**
	 * 关键字查询
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> keySearchProject(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.keySearchProject", page);
	}
	
	/**
	 * 查询机构信息表构造成json数据
	 */
	public String institutionInfo() throws Exception {
		// JSON格式数据解析对象
        JSONObject jo = new JSONObject();
        //构造Map和List 用于存放对应的公司和下级公司或部门
        Map<String,List<String>> js = new HashMap<String,List<String>>();
        List<String> superior = new ArrayList<String>();//存放key
        //从数据库中查询机构的所有条目信息
		List<PageData> pds =  (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInfo","");
		//循环鉴别每一个条目
		for (PageData pageData : pds) {
			String superior_name = pageData.getString("superior_organization_name");
			if((superior_name!=null)
					&&(!superior_name.equals(""))){
				
				if(!superior.contains(superior_name)){
					superior.add(superior_name);
					List<String> lower = new ArrayList<String>();
					for (PageData pd : pds) {
						if(pd.getString("superior_organization_name").equals(superior_name)){
							lower.add(pd.getString("organization_abbreviation"));
						}
					}
					js.put(superior_name, lower);
				}
				 
			}
		}
		
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		superior.clear();
		return json.toString();
	}
	
	
	/**
	 * 给张越写的 如果支行下面存在部门则总行下面不包含此支行
	 * 查询机构信息表构造成json数据
	 */
	public String institutionInfoSpecial() throws Exception {
		// JSON格式数据解析对象
        JSONObject jo = new JSONObject();
        //构造Map和List 用于存放对应的公司和下级公司或部门
        Map<String,List<String>> js = new HashMap<String,List<String>>();
        List<String> superior = new ArrayList<String>();//存放key
        //从数据库中查询机构的所有条目信息
		List<PageData> pds =  (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInfo","");
		//存放上级ID
		List<Integer> sois = new ArrayList<Integer>(); 
		//查找上级ID 存放到List数组sois
		for (PageData pageData : pds) {
			Integer soi = (Integer) pageData.get("superior_organizational_identification");
			if(!sois.contains(soi)){
				sois.add(soi);
			}
		}
		//循环鉴别每一个条目
		for (PageData pageData : pds) {
			String superior_name = pageData.getString("superior_organization_name");
			if((superior_name!=null)
					&&(!superior_name.equals(""))){
				
				if(!superior.contains(superior_name)){
					superior.add(superior_name);
					List<String> lower = new ArrayList<String>();
					for (PageData pd : pds) {
						if(pd.getString("superior_organization_name").equals(superior_name)){
							if(!sois.contains((Integer)pd.get("organizational_identification"))){
								lower.add(pd.getString("organization_abbreviation"));
							}
						}
					}
					js.put(superior_name, lower);
				}
				 
			}
		}
		
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		superior.clear();
		return json.toString();
	}
	
	
	/**
	 * 不一定使用 暂时先这样
	 * 查询机构信息表构造成json数据 权限为2
	 */
	public String institutionInfoPermission2(String company,String dept) throws Exception {
		// JSON格式数据解析对象
        JSONObject jo = new JSONObject();
        //构造Map和List 用于存放对应的公司和下级公司或部门
        Map<String,List<String>> js = new HashMap<String,List<String>>();
        List<String> superior = new ArrayList<String>();//存放key
        superior.add(company);
        List<String> lowerStart = new ArrayList<String>();
        lowerStart.add(dept);
        //存放所在公司及部门
        js.put(company, lowerStart);
        //从数据库中查询机构的所有条目信息
		List<PageData> pds =  (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInfo","");
		//循环鉴别每一个条目
		for (PageData pageData : pds) {
			String superior_name = pageData.getString("superior_organization_name");
			if((superior_name!=null)
					&&(!superior_name.equals(""))&&(superior_name.equals(dept))){
				
				if(!superior.contains(superior_name)){
					superior.add(superior_name);
					List<String> lower = new ArrayList<String>();
					for (PageData pd : pds) {
						if(pd.getString("superior_organization_name").equals(superior_name)){
							lower.add(pd.getString("organization_abbreviation"));
						}
					}
					js.put(superior_name, lower);
				}
				 
			}
		}
		
		JSONArray json = JSONArray.fromObject(js);
		js.clear();
		superior.clear();
		return json.toString();
	}
	
	/**
	 * 查询审批流程 增加项目的时候进行选择
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findApproveProcess() throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.findApproveProcess", null);
	}
	
	/**
	 * 列出项目立项列表--权限为2 支行或部门管理员
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPermission2Project(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.listPermission2Project", page);
	}
	
	
	/**
	 * 列出项目立项列表--权限为2 支行或部门管理员的条数
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public Integer listPermission2ProjectTotal(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ProjectApplyMapper.listPermission2ProjectTotal", page);
	}
	
	/**
	 * 列出项目立项列表--权限为3 普通员工
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPermission3Project(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.listPermission3Project", page);
	}
	
	/**
	 * 列出项目立项列表--权限为3 普通员工的条数
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public Integer listPermission3ProjectTotal(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ProjectApplyMapper.listPermission3ProjectTotal", page);
	}
	
	/**
	 * 关键字查询--权限为2对应的部门
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> keySearchPermission2Project(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.keySearchPermission2Project", page);
	}
	
	/**
	 * 关键字查询--权限为2对应的部门的条数
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public Integer keySearchPermission2ProjectTotal(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ProjectApplyMapper.keySearchPermission2ProjectTotal", page);
	}
	
	
	/**
	 * 关键字查询--权限为3对应的部门
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> keySearchPermission3Project(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.keySearchPermission3Project", page);
	}
	
	/**
	 * 关键字查询--权限为3对应的部门的条数
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public Integer keySearchPermission3ProjectTotal(PageOption page) throws Exception {
		return (Integer) dao.findForObject("ProjectApplyMapper.keySearchPermission3ProjectTotal", page);
	}
	
	/**
	 * 删除选中的项目同时删除流程表里面对应的的内容
	 * @param s
	 * @throws Exception
	 */
	public void deleteProcessProject(int[] s) throws Exception {
		dao.delete("ProjectApplyMapper.deleteProcessProject", s);
	}
	
	
	/**
	 * 查询项目的详细审批信息
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> checkApprovalProject(int s) throws Exception {
		return (List<PageData>) dao.findForList("ProjectApplyMapper.checkApprovalProject", s);
	}
	
}
