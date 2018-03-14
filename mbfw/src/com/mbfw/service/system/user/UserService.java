package com.mbfw.service.system.user;

import java.util.List;

import javax.annotation.Resource;
import org.apache.poi.ss.formula.eval.UnaryMinusEval;
import org.springframework.stereotype.Service;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.Page;
import com.mbfw.entity.system.User;
import com.mbfw.util.PageData;

@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// ======================================================================================

	/*
	 * 通过id获取数据
	 */
	public PageData findByUiId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUiId", pd);
	}

	/*
	 * 通过loginname获取数据
	 */
	public PageData findByUId(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUId", pd);
	}

	/*
	 * 通过邮箱获取数据
	 */
	public PageData findByUE(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUE", pd);
	}

	/*
	 * 通过编号获取数据
	 */
	public PageData findByUN(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.findByUN", pd);
	}

	/*
	 * 保存用户
	 */
	public void saveU(PageData pd) throws Exception {
		dao.save("UserXMapper.saveU", pd);
	}

	/*
	 * 修改用户
	 */
	public void editU(PageData pd) throws Exception {
		dao.update("UserXMapper.editU", pd);
	}

	/*
	 * 换皮肤
	 */
	public void setSKIN(PageData pd) throws Exception {
		dao.update("UserXMapper.setSKIN", pd);
	}

	/*
	 * 删除用户
	 */
	public void deleteU(PageData pd) throws Exception {
		dao.delete("UserXMapper.deleteU", pd);
	}

	/*
	 * 批量删除用户
	 */
	public void deleteAllU(String[] USER_IDS) throws Exception {
		dao.delete("UserXMapper.deleteAllU", USER_IDS);
	}

	/*
	 * 用户列表(用户组)
	 */
	public List<PageData> listPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userlistPage", page);
	}

	/*
	 * 用户列表(全部)
	 */
	public List<PageData> listAllUser(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.listAllUser", pd);
	}

	/*
	 * 用户列表(供应商用户)
	 */
	public List<PageData> listGPdPageUser(Page page) throws Exception {
		return (List<PageData>) dao.findForList("UserXMapper.userGlistPage", page);
	}

	/*
	 * 保存用户IP
	 */
	public void saveIP(PageData pd) throws Exception {
		dao.update("UserXMapper.saveIP", pd);
	}

	/*
	 * 登录判断
	 */
	public PageData getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData) dao.findForObject("UserXMapper.getUserInfo", pd);
	}

	/*
	 * 跟新登录时间
	 */
	public void updateLastLogin(PageData pd) throws Exception {
		dao.update("UserXMapper.updateLastLogin", pd);
	}

	/*
	 * 通过id获取数据
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception {
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
	}

	
	/**
	 * 添加用户为审批人
	 * @param pd
	 */
	public void saveApproverUser(PageData pd) {
		dao.saveApproverDao("UserXMapper.saveApprover",pd);		
	}
	
	/**
	 * 判断该用户是否已经在审核人员表中
	 * @throws Exception 
	 * 
	 */
	public PageData findByUIdApprover(String useid) throws Exception{
		PageData pageData =  (PageData) dao.findForObject("UserXMapper.findByUiIdApprover",useid);
		return pageData;
	}
	
	/**
	 * 通userId来查找是否有数据
	 * @param uString
	 * @return
	 * @throws Exception 
	 */
	public PageData findByUIdOne(String uString) throws Exception{
		return (PageData) dao.findForObject("UserXMapper.findByUiIdUser", uString);
	}

	/**
	 * 获取所有的部门权限的信息，从数据库中user_departmentauthority获取
	 * @return
	 * @throws Exception 
	 */
	public List<PageData> findAllDepartmentAuthority() throws Exception {	
		return (List<PageData>) dao.findForList("UserXMapper.findAllDepartmentAuthority", null);
	}

	/**
	 * 查询institutional_information表中，部门的简称和是否属于支行信息
	 * @return
	 * @throws Exception 
	 */
	public List<String> findAllInstitutionalInformation(String str) throws Exception {		
		if("总行".equals(str)){
			return (List<String>) dao.findForList("UserXMapper.findAllInstitutionalSuper", null);
		}
		else if("支行".equals(str)){
			return (List<String>) dao.findForList("UserXMapper.findAllInstitutionalCurrent", null);	
		}
		return null;
	}
}
