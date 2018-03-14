package com.mbfw.controller.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetRAR;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetRARService;
import com.mbfw.service.assets.AssetRegisterService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;

@Controller
@RequestMapping(value="/mobile/repair")
public class RepairController extends BaseController{
	/**
	 * 报修
	 */
	@Resource(name="assetRARService")
	private AssetRARService assetRARService;
	@Resource(name="assetRegisterService")
	private AssetRegisterService assetRegisterService;
	
	@RequestMapping(value = "/report", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageData repair_report() throws Exception {

		PageData map = new PageData();
		PageData pd = this.getPageData();
		String report_code = pd.getString("report_code");
		String op_code = "no";
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		List<AssetInfo> list=assetRegisterService.findByAssetCode(pd);
		
		//判断与回显操作
		if(list.isEmpty()){
			//无此资产编码
			map.put("result", "找不到该资产编码。");
		}
		else{
			AssetInfo asset = list.get(0);
			
			if(asset.getAsset_user()==null) {
				//该资产无人使用，且已经报修过了（重复报修）
				if(asset.getAsset_status().equals("报修")){	
					PageData query = new PageData();
					query.put("asset_code", pd.getString("asset_code"));
					query.put("maintain_result", "无法维修");
					Boolean beyondrepair = assetRARService.mobile_maintain_query(query)!=null ? true : false;
					
					if(beyondrepair){
						//该资产已经无法维修了
						map.put("result", "该资产已无法维修，请申请报废。");
					}
					else{
						//该资产可以维修，覆盖上次报修记录
						map.put("result", "该资产已报修，重复提交覆盖上次报修记录。");
						map.put("asset_user", "");
						map.put("asset_use_company", "");
						map.put("asset_use_dept", "");
						map.put("asset_status", asset.getAsset_status());
						map.put("asset_name", asset.getAsset_name());
						op_code = "updata";
					}
				}
				else{
					//该资产无人使用，还没重复报修，插入报修记录
					map.put("result", "该资产还未领用，使用人和部门不填即可。");
					map.put("asset_user", "");
					map.put("asset_use_company", "");
					map.put("asset_use_dept", "");
					map.put("asset_status", asset.getAsset_status());
					map.put("asset_name", asset.getAsset_name());
					op_code = "insert";
				}
			}
			else if(asset.getAsset_user().equals(user.getNAME())
					&& asset.getAsset_use_dept().equals(user.getOrganization_name())
					&& asset.getAsset_use_company().equals(user.getSuperior_organization_name())){
				//该资产为此用户资产，该用户拥有报修权限
				if(asset.getAsset_status().equals("报废")){
					//该资产已报废
					map.put("result", "请勿报修已报废的资产。");
				}
				else if(asset.getAsset_status().equals("报修")){
					//该资产已经报修过了，重复报修
					PageData query = new PageData();
					query.put("asset_code", pd.getString("asset_code"));
					query.put("maintain_result", "无法维修");
					Boolean beyondrepair = assetRARService.mobile_maintain_query(query)!=null ? true : false;
					
					if(beyondrepair){
						//该资产已经无法维修了
						map.put("result", "该资产已无法维修，请申请报废。");
					}
					else{
						//该资产可以维修，覆盖上次报修记录
						map.put("result", "该资产已报修，重复提交覆盖上次报修记录。");
						map.put("asset_user", asset.getAsset_user());
						map.put("asset_use_company", asset.getAsset_use_company());
						map.put("asset_use_dept", asset.getAsset_use_dept());
						map.put("asset_status", asset.getAsset_status());
						map.put("asset_name", asset.getAsset_name());
						op_code = "updata";
					}
					
				}
				else if(asset.getAsset_status().equals("领用")){
					//该资产为此用户资产，还没重复报修，插入报修记录
					map.put("result", "可以报修");
					map.put("asset_user", asset.getAsset_user());
					map.put("asset_use_company", asset.getAsset_use_company());
					map.put("asset_use_dept", asset.getAsset_use_dept());
					map.put("asset_status", asset.getAsset_status());
					map.put("asset_name", asset.getAsset_name());
					op_code = "insert";
				}
			}
			else {
				//此用户无该资产报修权限
				map.put("result", "无该资产报修权限。");
			}
		}
		
		//报修操作
		if(report_code.equals("report")){
			if(op_code.equals("updata")){
				//重复报修更新覆盖
				assetRARService.mobile_updateRAR(pd);
				map.put("result", "报修成功：已覆盖之前的报修表。");
			}
			else if(op_code.equals("insert")){
				//未重复报修插入记录
				PageData tmp = new PageData();
				tmp.put("asset_code", pd.getString("asset_code"));
				tmp.put("asset_status", "报修");
				assetRegisterService.editAssetStatus(tmp);
				assetRARService.saveRAR(pd);
				map.put("result", "报修成功！");
			}
			else if(op_code.equals("no")){
				//未通过判断，报修失败
				map.put("result", "报修失败：请填写正确表单信息。");
			}
		}
		
		return map;
	}
	
	/**
	 * 维修
	 */
	@RequestMapping(value = "/maintain", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object maintain() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		
		PageData query = new PageData();
		query.put("asset_code", pd.getString("asset_code"));
		query.put("maintain_result", "未维修");
		PageData report = assetRARService.mobile_maintain_query(query);
		String report_code = pd.getString("report_code");
		boolean flag=true;
		
		if(report==null){
			map.put("result", "资产编码不存在，或该资产不处于报修状态");
			flag = false;
		}
		else {
			map.put("result", "ok");
			map.put("asset_name", report.getString("asset_name"));
			map.put("asset_user", report.getString("asset_person"));
		}
		
		if(flag && report_code.equals("maintain")){
			assetRARService.mobile_maintain(pd);
			map.put("result", "维修成功！");
			if(pd.getString("maintain_result").equals("已维修")){
				PageData tmp = new PageData();
				tmp.put("asset_code", pd.getString("asset_code"));
				tmp.put("asset_status", report.getString("pre_asset_status"));
				assetRegisterService.editAssetStatus(tmp);
			}
		}
		
		return map;
	}
	
}
