package com.mbfw.service.assets;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.InstitutionalInfo;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.ProjectApply;
import com.mbfw.util.PageData;

/**
 * 机构信息管理Service
 * @author Wyd
 *
 */
@Service("InstitutionalInfo")
public class InstitutionInfoManageService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	/**
	 * 显示所有机构信息共有多少条数据条数
	 * @return
	 * @throws Exception 
	 */
	public Integer findTotaInstitutionNumber() throws Exception {		
		return (Integer) dao.findForObject("InstitutionInfoMapper.findTotaInstitutionNumber", null);
	}
	
	/**
	 * 列出机构信息的列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllInstitution(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInstitution", page);
	}
	

	public List<String> findOrgAbbr() throws Exception {
		return (List<String>) dao.findForList("InstitutionInfoMapper.findOrgAbbr",null);
	}
	
	
	/**
	 * 提交保存机构信息
	 * @param pd
	 * @throws Exception
	 */
	public void saveInstitution(InstitutionalInfo pd) throws Exception {
		dao.save("InstitutionInfoMapper.addInstitution", pd);
	}
	
	/**
	 * 查询机构信息
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public PageData searchInstitution(int s) throws Exception {
		return (PageData) dao.findForObject("InstitutionInfoMapper.searchInstitution", s);
	}
	
	/**
	 * 更新修改的机构
	 * @param pd
	 * @throws Exception
	 */
	public void updateInstitution(InstitutionalInfo pd) throws Exception {
		dao.update("InstitutionInfoMapper.updateInstitution", pd);
	}
	
	/**
	 * 查询要要删除的选中的机构
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> searchDelectInstitution(int[] s) throws Exception {
		return (List<PageData>) dao.findForList("InstitutionInfoMapper.searchDelectInstitution", s);
	}
	
	/**
	 * 删除选中的条目
	 * @param s
	 * @throws Exception
	 */
	public void deleteInstitution(int[] s) throws Exception {
		dao.delete("InstitutionInfoMapper.deleteInstitution", s);
	}
	
	/**
	 * 关键字查询对应的条数
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public Integer keySearchInstitutionNumber(String s) throws Exception {
		return (Integer) dao.findForObject("InstitutionInfoMapper.keySearchIntitutionNumber", s);
	}
	
	
	/**
	 * 关键字查询
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public List<PageData> keySearchInstitution(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("InstitutionInfoMapper.keySearchIntitution", page);
	}
	
	
	/**
	 * 如果权限为1获取总行和支行的所有分支机构 不包含部门
	 * @return
	 * @throws Exception 
	 */
	public List<String> institution1Info() throws Exception{
		//存放组织简称
		List<String> organization_abbreviation = new ArrayList<String>();
		organization_abbreviation.clear();
		organization_abbreviation.add("慈利农商银行");
		//从数据库中查询机构的所有条目信息
		List<PageData> pds =  (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInfo","");
		for (PageData pageData : pds) {
			String bon = pageData.getString("business_organization_nature");
			if(bon.equals("有贷款城区支行")||bon.equals("有贷款农村支行")){
				String oa = pageData.getString("organization_abbreviation");
				organization_abbreviation.add(oa);
			}
		}
		//返回list数组
		return organization_abbreviation;
	}
	
	/**
	 * 暂时不用
	 * 如果权限为2获取支行及其下面的分支机构
	 * @return
	 * @throws Exception
	 */
	public List<String> institution2Info(String name) throws Exception{
		//存放组织简称
		List<String> organization_abbreviation = new ArrayList<String>();
		organization_abbreviation.clear();
		//先把自己加上
		organization_abbreviation.add(name);
		//从数据库中查询机构的所有条目信息
		List<PageData> pds =  (List<PageData>) dao.findForList("InstitutionInfoMapper.listAllInfo","");
		for (PageData pageData : pds) {
			//获取每条的上级机构名称
			String son = pageData.getString("superior_organization_name");
			if(name.equals(son)){
				String oa = pageData.getString("organization_abbreviation");
				organization_abbreviation.add(oa);
			}
		}
		//返回list数组
		return organization_abbreviation;
	}
	
	/**
	 * 查询机构的编号
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public PageData searchInstitutionCode(String s) throws Exception {
		return (PageData) dao.findForObject("InstitutionInfoMapper.searchInstitutionCode", s);
	}
	
	/**
	 * 根据部门列出权限2的机构
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listPermission2Institution(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("InstitutionInfoMapper.listPermission2Institution", page);
	}
	
	/**
	 * 根据部门列出权限2的机构的条数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Integer listPermission2InstitutionTotal(PageOption page) throws Exception {
		return (Integer) dao.findForObject("InstitutionInfoMapper.listPermission2InstitutionTotal", page);
	}
	
	/**
	 * 根据部门列出权限2的检索机构信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> keySearchPermission2Institution(PageOption page) throws Exception {
		return (List<PageData>) dao.findForList("InstitutionInfoMapper.keySearchPermission2Institution", page);
	}
	
	/**
	 * 根据部门查询权限2的检索机构的条数
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public Integer keySearchPermission2InstitutionTotal(PageOption page) throws Exception {
		return (Integer) dao.findForObject("InstitutionInfoMapper.keySearchPermission2InstitutionTotal", page);
	}
	

/**
	 * 查询机构是否正在被使用
	 * @param s
	 * @throws Exception
	 */
	public List<String> judgeInstitutionUse(int[] s) throws Exception {
		List<String> ls = new ArrayList<String>();
		List<String> mapperName = new ArrayList<String>();
		mapperName.add("InstitutionInfoMapper.judge_sys_user");
		mapperName.add("InstitutionInfoMapper.judge_aproject_manager");
		mapperName.add("InstitutionInfoMapper.judge_asset_info");
		mapperName.add("InstitutionInfoMapper.judge_asset_issue");
		mapperName.add("InstitutionInfoMapper.judge_asset_abandon");
		mapperName.add("InstitutionInfoMapper.judge_asset_get");
		mapperName.add("InstitutionInfoMapper.judge_asset_alter");
		mapperName.add("InstitutionInfoMapper.judge_asset_allot");
		mapperName.add("InstitutionInfoMapper.judge_report_repair");
		mapperName.add("InstitutionInfoMapper.judge_supplies_application");
		mapperName.add("InstitutionInfoMapper.judge_supplies_stored");
		mapperName.add("InstitutionInfoMapper.judge_supplies_use");
		for (int i : s) {
			if(i!=0){
				PageData pd = (PageData)dao.findForObject("InstitutionInfoMapper.searchInstitution", i);
				String name = pd.getString("organization_abbreviation");
				for (String string : mapperName) {
					int count = judgeUtil(string, pd);
					System.out.println(string+"  "+count);
					if(count!=0){
						ls.add(name);
					}
				}
			}
		}
		return ls;
	}
	
	public int judgeUtil(String s,PageData pd) throws Exception{
		int number = (Integer)dao.findForObject(s, pd);
		if(number!=0){
			return number;
		}
		return 0;
	}
	//根据自己所在组织名称查询所有下级组织
	public List<String> findBranch(PageData pd) throws Exception{
		return (List<String>) dao.findForList("InstitutionInfoMapper.findBranch", pd);
		
	}
	
	public List<String> findOrgByType(PageData pd) throws Exception{
		return (List<String>) dao.findForList("InstitutionInfoMapper.findOrgByType", pd);
	}
}
