package com.mbfw.service.assets;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mbfw.dao.DaoSupport;
//import com.mbfw.entity.assets.AssetCount;
import com.mbfw.entity.assets.AssetGetManage;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.util.PageData;

@Service("assetRegisterService")
public class AssetRegisterService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	// 保存资产登记
	public void  save_used(AssetInfo pd) throws Exception{
		dao.save("AssetRegisterMapper.save_used", pd);
	}

	/**
	 * 
	 * @param pd 查找全部资产按分页显示
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUsedData(PageOption pd) throws Exception {
		return (List<PageData>) dao.findForList("AssetRegisterMapper.list_used", pd);
	}

	/**
	 * 
	 * @param pd 查找全部的资产，并统计总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_register_asset_totalnumber(PageData pd) throws Exception{
		return (Integer) dao.findForObject("AssetRegisterMapper.find_register_asset_totalnumber", pd);
	}

	// 查找满足关键字总条数
	public Integer find_register_asset_bykey_totalnumber(PageOption pd) throws Exception{
		return (Integer) dao.findForObject("AssetRegisterMapper.find_register_asset_bykey_totalnumber", pd);
	}

	//查询不同的资产状态的总价格
	public BigDecimal findTotalPriceByStatus(PageData pd) throws Exception{
		BigDecimal total =  (BigDecimal) dao.findForObject("AssetRegisterMapper.findTotalPriceByStatus", pd);
		return total == null ? new BigDecimal(0) : total;
	}

	//根据类别查询不同资产类别的总价格
	public BigDecimal findTotalPriceByClass(PageData pd) throws Exception{
		BigDecimal total =  (BigDecimal) dao.findForObject("AssetRegisterMapper.findTotalPriceByClass", pd);
		return total == null ? new BigDecimal(0) : total;
	}

	//查找满足关键字的记录
	public List<PageData> find_register_asset_bykey(PageOption pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetRegisterMapper.find_register_asset_bykey", pd);
	}

	public  void deleteData(PageData pd) throws Exception {
		dao.delete("AssetRegisterMapper.deleteData", pd);
	}

	/**
	 * 
	 * @param pd 删除登记资产
	 * @throws Exception
	 */
	public  void deleteAllData(String[] pd) throws Exception {
		dao.delete("AssetRegisterMapper.deleteAlldata", pd);
	}

	/**
	 * 通过id查找
	 */
	public AssetInfo findObjectById(PageData pd) throws Exception {
		return (AssetInfo) dao.findForObject("AssetRegisterMapper.findObjectById", pd);
	}

	/**
	 * 编辑角色
	 */
	public void edit(AssetInfo pd) throws Exception {
		dao.update("AssetRegisterMapper.edit", pd);
	}

	/**
	 * 移动端缺省查找
	 */
	public List<AssetInfo> findByDefault(PageData pd) throws Exception {
		return (List<AssetInfo>) dao.findForList("AssetRegisterMapper.findByDefault", pd);
	}

	/**
	 * 资产编码查找
	 */
	public List<AssetInfo> findByAssetCode(PageData pd) throws Exception {
		return (List<AssetInfo>) dao.findForList("AssetRegisterMapper.findByAssetCode", pd);
	}

	/**
	 * 按资产编码查找图片(包括实物图片和二维码图片)
	 */
	public PageData findImg(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AssetRegisterMapper.findImg", pd);
	}
	
	/**
	 * 资产统计:根据复杂条件查询资产信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<AssetInfo> findAssetInfoByCond(PageData pd) throws Exception{
		return (List<AssetInfo>) dao.findForList("AssetRegisterMapper.findAssetInfoByCond",pd);
	}

	/**
	 * 资产状态修改
	 */
	public void editAssetStatus(PageData pd) throws Exception {
		dao.update("AssetRegisterMapper.editAssetStatus", pd);
	}
	/**
	 * 资产状态修改(JY,勿删)
	 */
	public void editAssetStatus2(PageData pd) throws Exception {
		dao.update("AssetRegisterMapper.editAssetStatus2", pd);
	}
	/**
	 * 资产编码查找（JY，勿删）
	 */
	public String findAssetCode(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetRegisterMapper.findAssetCode", pd);
	}
	/**
	 * 使用人查找（JY，勿删）
	 */
	public String findAssetUser(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetRegisterMapper.findAssetUser", pd);
	}
	/**
	 * 资产名称查找（JY，勿删）
	 */
	public String findAssetName(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetRegisterMapper.findAssetName", pd);
	}
	/**
	 * 公司名称查找（JY，勿删）
	 */
	public String findAssetGongsi(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetRegisterMapper.findAssetGongsi", pd);
	}
	/**
	 * 部门名称查找（JY，勿删）
	 */
	public String findAssetDept(PageData pd) throws Exception {
		return (String) dao.findForObject("AssetRegisterMapper.findAssetDept", pd);
	}

	/******** 资产入库登记  ***********/

	/**
	 * @param page 查询资产信息
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_index(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetRegisterMapper.find_asset_index", page);
	}
	/**
	 * @param pd 查询总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_index_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetRegisterMapper.find_asset_index_totalnumber", page);
	}
	/**
	 * @param pd 更新资产信息
	 * @throws Exception
	 */
	public void update_asset_register(AssetInfo pd) throws Exception{
		dao.update("AssetRegisterMapper.update_asset_register", pd);
	}
	/**
	 * @param page  按关键字查询总条数
	 * @return
	 * @throws Exception
	 */
	public Integer find_asset_index_bykey_totalnumber(PageOption page) throws Exception{
		return (Integer) dao.findForObject("AssetRegisterMapper.find_asset_index_bykey_totalnumber", page);
	}
	/**
	 * @param page 按关键字查询
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_asset_index_bykey(PageOption page) throws Exception{
		return (List<PageData>) dao.findForList("AssetRegisterMapper.find_asset_index_bykey", page);
	}
	/**************结束 
	 * @throws Exception ************/

	//===****** 保存导入数据
	public void save_import_data(PageData pd) throws Exception{
		dao.save("AssetRegisterMapper.save_import_data", pd);
	}

	// 导入数据是判断是否有重复的 lss
	public List<PageData> find_asset_ishas_repeat(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetRegisterMapper.find_asset_ishas_repeat", pd);
	}
	
	/**
	 * 
	 * @param pd 查找是否有相同的sn如果是则不添加到资产库里
	 * @return
	 * @throws Exception
	 */
	public List<PageData> find_bySN(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("AssetRegisterMapper.find_bySN", pd);
	}
	
}
