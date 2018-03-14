package com.mbfw.service.assets;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;


@Service("SuppliesUseService")
public class SuppliesUseService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	public List<PageData> findStoreHouse(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("SuppliesUseMapper.listAllSuppliesUse", pd);
	}
	/**
	 * 根据ID来进行显示对应的领用信息，进行编辑
	 * @param pd
	 * @throws Exception 
	 */
//	public PageData deleteLingYongById(PageData pd) throws Exception {
//		return (PageData) dao.findForObject("SuppliesUseMapper.deleteLingYong" , pd);
//		
//	}
	
	//（新增耗材领用页面保存）
			@SuppressWarnings("unused")
			public void addLyData(PageData pd) throws Exception {
				dao.save("SuppliesUseMapper.addly", pd);
			   int count = Integer.parseInt(pd.getString("quantity"));
			   PageData pds = (PageData) dao.findForObject("SuppliesUseMapper.searchly", pd);
			  if (pds!=null) {
//				 int inventory_quantity = (int) pds.get("inventory_quantity");
				  int inventory_quantity = (int) pds.get("inventory_quantity");
				  inventory_quantity = inventory_quantity - count;
				  pd.put("quantity", inventory_quantity+"");
				  dao.update("SuppliesUseMapper.updateLY", pd);
			}
			}
	/**
	 * 根据userId来进行删除用户信息
	 * @param pd
	 * @throws Exception 
	 */
	public void deleteById(PageData pd) throws Exception {
		dao.delete("SuppliesUseMapper.deletely", pd);		
	}
	//批量删除
			public void deleteLyData(Integer[] Allot_ids) throws Exception {
				dao.delete("SuppliesUseMapper.delLy", Allot_ids);
			}
//	
//	//批量删除
//		public void deleteLyData(Integer[] ids) throws Exception {
//			dao.delete("SuppliesUseMapper.delLy", ids);
//		}
//		编辑
		public PageData EditLyData(PageData pd) throws Exception {
			return (PageData) dao.findForObject("SuppliesUseMapper.editLy", pd);
		}
		
		//更新
		public void updateLy(PageData pd) throws Exception {
					dao.update("SuppliesUseMapper.updateLy" , pd);			
				}
   
		//分页显示数据条数
		public Integer findTotalLyDataNumber() throws Exception {		
			return (Integer) dao.findForObject("SuppliesUseMapper.findLyTotalNumber", null);
		}

		//显示分页所有数据
		@SuppressWarnings("unchecked")
		public List<PageData> listLyGetPageApprover(PageOption page) throws Exception{	
			return (List<PageData>) dao.findForList("SuppliesUseMapper.LyListPage" ,page);
		}

		// 查询检索姓名内容的数据总条数		
		public Integer findNumberLyBySearchName(PageOption page) throws Exception {
			return (Integer) dao.findForObject("SuppliesUseMapper.findLySearchNameNumber", page);
		}
		
		// 根据检索内容，查询数据库，返回所有的的数据信息		 
		public List<PageData> listLySearchNameApprover(PageOption page) throws Exception {
			return (List<PageData>) dao.findForList("SuppliesUseMapper.findLyTotalSearchInfo", page);
		}
		//显示耗材编码
		public List<PageData> find_suppliesUse(PageData pd) throws Exception{
			return  (List<PageData>) dao.findForList("SuppliesUseMapper.find_suppliesUse", pd);
		}
		// 根据耗材编码填写耗材信息
		public PageData find_product_to_add_supplies(String id) throws Exception{
			return (PageData) dao.findForObject("SuppliesUseMapper.find_product_to_add_supplies", id);
		}
}
