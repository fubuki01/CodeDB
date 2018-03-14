package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.assets.SuppliesApplication;
import com.mbfw.entity.assets.SuppliesStore;
import com.mbfw.util.PageData;


@Service("SuppliesApplicationService")
public class SuppliesApplicationService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
//	插入数据库数据
	public List<PageData> findApplicationHouse(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SuppliesApplicationMapper.listAllSuppliesApplication", pd);
	}
	
	//保存数据
		public void saveSq(PageData pd) throws Exception {
			dao.save("SuppliesApplicationMapper.addSq", pd);
		}
		//批量删除
		public void deleteSqData(Integer[] Allot_ids) throws Exception {
			dao.delete("SuppliesApplicationMapper.deleteSq", Allot_ids);
		}
		/**
		 * 根据userId来进行删除用户信息
		 * @param pd
		 * @throws Exception 
		 */
		public void deleteSq(PageData pd) throws Exception {
			dao.delete("SuppliesApplicationMapper.delSq", pd);		
		}
		
//		编辑
		public PageData EditSqData(PageData pd) throws Exception {
			return (PageData) dao.findForObject("SuppliesApplicationMapper.editSq", pd);
		}
		
		//更新
		public void updateSq(PageData pd) throws Exception {
					dao.update("SuppliesApplicationMapper.updateSq" , pd);			
				}
		

		//分页显示数据条数
				public Integer findTotalSqDataNumber() throws Exception {		
					return (Integer) dao.findForObject("SuppliesApplicationMapper.findSqTotalNumber", null);
				}

				//显示分页所有数据
				@SuppressWarnings("unchecked")
				public List<PageData> listSqGetPageApprover(PageOption page) throws Exception{	
					return (List<PageData>) dao.findForList("SuppliesApplicationMapper.SqListPage" ,page);
				}

				// 查询检索姓名内容的数据总条数		
				public Integer findNumberSqBySearchName(PageOption page) throws Exception {
					return (Integer) dao.findForObject("SuppliesApplicationMapper.findSqSearchNameNumber", page);
				}
				
				// 根据检索内容，查询数据库，返回所有的的数据信息		 
				public List<PageData> listSqSearchNameApprover(PageOption page) throws Exception {
					return (List<PageData>) dao.findForList("SuppliesApplicationMapper.findSqTotalSearchInfo", page);
				}
				//显示耗材编码
				public List<PageData> find_SuppliesApplication(PageData pd) throws Exception{
					return  (List<PageData>) dao.findForList("SuppliesApplicationMapper.find_SuppliesApplication", pd);
				}
				// 根据耗材编码填写耗材信息
				public PageData find_product_to_add_supplies(String id) throws Exception{
					return (PageData) dao.findForObject("SuppliesApplicationMapper.find_product_to_add_supplies", id);
				}
}
