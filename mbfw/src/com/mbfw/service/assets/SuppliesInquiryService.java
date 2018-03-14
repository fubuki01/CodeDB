package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesInquiry;
import com.mbfw.entity.assets.SuppliesStore;
import com.mbfw.util.PageData;
@Service("SuppliesInquiryService")
public class SuppliesInquiryService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
//	插入数据库数据
	public List<PageData> findStoreHouse(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SuppliesInquiryMapper.listAllSuppliesInquiry", pd);
	}

		
		//显示分页所有数据
				public List<PageData> listPdGetPageApprover(PageOption page) throws Exception{	
					return (List<PageData>) dao.findForList("SuppliesInquiryMapper.InListPage" ,page);
				}
		
		//分页显示数据条数
				public Integer findTotalInDataNumber() throws Exception {		
					return (Integer) dao.findForObject("SuppliesInquiryMapper.findTotalInNumber", null);
				}
				
				// 查询检索姓名内容的数据总条数		
				public Integer findNumberInBySearchName(PageOption page) throws Exception {
					return (Integer) dao.findForObject("SuppliesInquiryMapper.findInSearchNameNumber", page);
				}
				
				// 根据检索内容，查询数据库，返回所有的的数据信息		 
				public List<PageData> listInSearchNameApprover(PageOption page) throws Exception {
					return (List<PageData>) dao.findForList("SuppliesInquiryMapper.findInTotalSearchInfo", page);
				}
				//显示下下拉框中的时间
				public List<PageData> find_suppliesinquiry_time(PageData pd) throws Exception{
					return  (List<PageData>) dao.findForList("SuppliesInquiryMapper.find_suppliesinquiry_time", pd);
				}
				
				// 查询检索姓名内容的数据总条数		
				public Integer findNumberbyItemNumber(PageData pd) throws Exception {
					return (Integer) dao.findForObject("SuppliesInquiryMapper.findNumberbyItemNumber", pd);
				}
				
				public List<PageData> findNumberbyItem(PageOption page) throws Exception{
					return  (List<PageData>) dao.findForList("SuppliesInquiryMapper.findNumberbyItem", page);
				}
				
				//移动端查询
				public List<PageData> findSuppliesByDefault(PageData pd) throws Exception{
					return  (List<PageData>) dao.findForList("SuppliesInquiryMapper.findSuppliesByDefault", pd);
				}
				//显示下拉框中的耗材编码
				@SuppressWarnings("unchecked")
				public List<PageData> find_suppliesStore(PageData pd) throws Exception{
					return  (List<PageData>) dao.findForList("SuppliesInquiryMapper.find_suppliesStore", pd);
				}
	}

