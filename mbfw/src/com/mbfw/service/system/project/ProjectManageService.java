package com.mbfw.service.system.project;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.util.PageData;

@Service("projectManageService")
public class ProjectManageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	// ======================================================================================

	/*
	 * 通过id获取数据
	 */
	public List<PageData> listAllProjectManage(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("ProjectManageMapper.listAllProjectManage", null);
	}
}
