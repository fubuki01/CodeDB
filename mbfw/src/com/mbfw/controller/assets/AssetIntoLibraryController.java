package com.mbfw.controller.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.AssetInfo;
import com.mbfw.entity.assets.AssetIntolibraryApply;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetIntolibraryApplyService;
import com.mbfw.service.assets.AssetPurchaseBillService;
import com.mbfw.service.assets.AssetPurchaserApplyService;
import com.mbfw.service.assets.ProductsInfoService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;
import com.mbfw.util.ZxingUtil;

@Controller
@RequestMapping(value = "/asset")
public class AssetIntoLibraryController extends BaseController{
	@Resource(name="assetIntolibraryApplyService")
	private AssetIntolibraryApplyService assetIntolibraryApplyService; //入库但服务

	@Resource(name = "assetPurchaserApplyService")
	private AssetPurchaserApplyService assetPurchaserApplyService; // 采购申请服务

	@Autowired
	private AssetPurchaseBillService assetPurchaseBillService; // 采购单服务

	@Autowired
	private ProductsInfoService productsInfoService;

	/**
	 * 入库申请页面 包括查询
	 */
	@RequestMapping(value = "/apl_ruku_apply")
	public ModelAndView apl_ruku_apply() throws Exception {
		ModelAndView mv = this.getModelAndView();
		Integer totalnumber = 0;
		List<PageData> into_library_apply = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();

		PageOption page = new PageOption(8, 1); // 默认初始化一进来显示就是每页显示8条，当前页面为1

		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字

		pd.put("apply_person", apply_person);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);

		page.setPd(pd);

		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}

		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){
			totalnumber= assetIntolibraryApplyService.find_into_libraray_bill_totalnumber(pd);// (4)设置总的数据条数

			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			into_library_apply=assetIntolibraryApplyService.find_into_library_apply(page);
		}else{
			//page.setPd(pd);
			totalnumber =  assetIntolibraryApplyService.find_into_libraray_bill_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			into_library_apply=assetIntolibraryApplyService.find_into_libraray_bill_bykey(page);
		}
		
		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("into_library_apply", into_library_apply);
		mv.addObject("delectresult", this.getPageData().getString("deleteResult"));// 传送删除项目的结果
		mv.setViewName("system/ainto_libraries/into_apply");
		mv.addObject("page", page);
		return mv;
	}
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	/**
	 * 
	 * 填写入库单
	 * @throws Exception 
	 */
	@RequestMapping(value = "/apl_into_apply")
	public ModelAndView apl_into_apply() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字

		pd.put("apply_person", apply_person);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);

		//page.setPd(pd);
		/// 查询采购单
		String finish_purchase_bill_info = assetPurchaseBillService.finish_purchase_bill_info(pd);
		mv.addObject("finish_purchase_bill_info", finish_purchase_bill_info);

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("into_time", formatter.format(currentTime));

		mv.setViewName("system/ainto_libraries/into_library_apply");
		mv.addObject("msg", "save_into_library_apply");
		mv.addObject("flag", "saveI");
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 
	 *保存填写入库单
	 * @throws Exception 
	 */
	@RequestMapping(value = "/save_into_library_apply")
	public ModelAndView save_into_library_apply() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		Boolean save_result =false ;

		String dateString ="";
		String purchase_code = pd.getString("into_purchase_bill");
		if(pd.getString("flag").equals("saveI")){
			// 获取该类编码的数量
			Integer product_count_flag = assetIntolibraryApplyService.find_product_count_by_class(pd);

			pd.put("product_count_flag", product_count_flag);
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			dateString = formatter.format(currentTime);
			pd.put("into_code", dateString);
			pd.put("device_code", pd.getString("device_code"));
			assetIntolibraryApplyService.save_into_library_apply(pd);
			save_result = true ;
		}else{
			assetIntolibraryApplyService.edit_intolibrary_bill(pd);
			mv.addObject("msg", "success");
		}

		if(save_result){
			assetPurchaseBillService.update_finish_purchase_bill(purchase_code);
		}
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 编辑入库单
	 * @throws Exception 
	 */
	@RequestMapping(value = "/edit_into_library_apply")
	public ModelAndView edit_into_library_apply() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字

		pd.put("apply_person", apply_person);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);
		// json封装
		String finish_purchase_bill_info = assetPurchaseBillService.finish_purchase_bill_info(pd);
		mv.addObject("finish_purchase_bill_info", finish_purchase_bill_info);

		pd = assetIntolibraryApplyService.find_into_libraray_bill_by_id(pd);
		mv.addObject("purchase_code", pd.getString("into_purchase_bill"));

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("into_time", formatter.format(currentTime));

		mv.setViewName("system/ainto_libraries/into_library_apply");
		mv.addObject("msg", "save_into_library_apply");
		mv.addObject("ila",pd);
		mv.addObject("flag", "editI");

		return mv;
	}

	/**
	 * 删除入库单
	 */
	@RequestMapping(value = "/delete_into_library_bill")
	public void delete_into_library_bill(PrintWriter out){
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			assetIntolibraryApplyService.delete_intolibrary_bill(pd);
			//修改采购单状态
		//	pd.put("purchase_code", pd.getString("purchase_code"));
			assetIntolibraryApplyService.update_status_purchase(pd); // 更新采购单状态
			out.write("success");
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 批量删除入库单 delete_all_intolibrary_bill
	 */
	@RequestMapping(value = "/delete_all_into_library_bill")
	@ResponseBody
	public PageData delete_all_into_library_bill() {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String del_into_library_bill = pd.getString("del_into_library_bill");
			StringBuffer str_id =new StringBuffer();
			if (null != del_into_library_bill && !"".equals(del_into_library_bill)) {
				String array_del_purchase_bill[] = del_into_library_bill.split(",");
				for (String string : array_del_purchase_bill) {
					String []str1 = string.split("@");
					str_id.append(str1[0]+",");
					pd.put("purchase_code", str1[1]);
					assetIntolibraryApplyService.update_status_purchase(pd); // 更新采购单状态
				}
				String []str2 = str_id.toString().split(",");
				assetIntolibraryApplyService.delete_all_intolibrary_bill(str2); // 删除操作
				pd.put("msg", "success");
			} else {
				pd.put("msg", "no");
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return pd;
	}

	/**
	 * 填写资产信息
	 */
	@RequestMapping(value = "/apl_asset_into_house")
	public ModelAndView apl_asset_into_house() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字

		pd.put("apply_person", apply_person);
		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);

		mv.addObject("select_into_code",assetIntolibraryApplyService.find_into_library_for_asset(pd));
		
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送增加项目的结果
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		
		mv.setViewName("system/ainto_libraries/asset_into_house");
		mv.addObject("msg", "save_asset_into_house");
		mv.addObject("pd", pd);
		mv.addObject("flag", "saveAI");
		return mv;
	}

	/**
	 * 资产封装成json，用ajax请求
	 */
	@RequestMapping(value = "/fill_asset")	

	public @ResponseBody PageData fill_asset(Page page) throws Exception {
		PageData pd = this.getPageData();
		//获取入库单的唯一id号
		String id = pd.getString("id");
		PageData fill_asset_info = assetIntolibraryApplyService.find_asset_into_libraray(id);
		return fill_asset_info;
	}

	/**
	 * 保存资产入库单
	 */
	@RequestMapping(value = "/save_asset_into_house")
	public String save_asset_into_house(@RequestParam(value = "asset_img", required = false) MultipartFile [] file,
			AssetInfo asset_info) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();

		Integer product_total=Integer.parseInt(asset_info.getProduct_total());
		int ac=0;
		DecimalFormat deft = new DecimalFormat("00000");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String asset_purchase_time= df.format(new Date());
		asset_info.setAsset_purchase_time(asset_purchase_time);

		ac=product_total+1;
		String acode = deft.format(ac);
		asset_info.setAsset_code(asset_info.getProduct_code()+"-"+acode);
		asset_info.setAsset_status("闲置");
		//保存图片
		String filePath="";
		String filePathConst = PathUtil.getPath("2")+File.separator;//.getClasspath() + Const.FILEPATHFILE ;
		StringBuffer img_file= new StringBuffer();
		for (MultipartFile multipartFile : file) {
			if (!multipartFile.getOriginalFilename().equals("")) {
				String time = System.currentTimeMillis()+"@"+multipartFile.getOriginalFilename();
				filePath = filePathConst+time; // 文件上传路径
				FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(filePath));
				img_file.append(time+"#");
			}
		}
		if(!img_file.equals("")){
			asset_info.setAsset_image(img_file.toString());
		}
		//结束
		//生成二维码
		/*String asset_name= asset_info.getAsset_name();
		String qrcode = PathUtil.getPath("1")+ File.separator+acode+asset_name+".png";
		ZxingUtil.createQRCode(asset_info.getProduct_code()+"-"+acode, new File(qrcode));
		asset_info.setAsset_qr_code(acode+asset_name+".png");*/
		/// 结束
		assetIntolibraryApplyService.save_asset_into_house(asset_info);

		System.out.println(ac);
		String into_code = asset_info.getAsset_into_bill();//pd.getString("asset_into_bill");

		// 更新该产品类的信息

		pd.put("product_total", ac);
		pd.put("product_code", asset_info.getProduct_code());
		pd.put("asset_into_bill", into_code);
		productsInfoService.updateProductByProductCode(pd);
		PageData pageData = assetIntolibraryApplyService.find_into_library_apply_by_into_code(pd);

		if(ac-Integer.parseInt(pageData.get("product_count_flag").toString()) ==Integer.parseInt(pageData.get("into_count").toString()) ){
			//更新入库单，表示入库单完成
			assetIntolibraryApplyService.update_into_library_for_asset(into_code);

			//更新采购单，表示采购单完成
			assetPurchaseBillService.update_finish_purchase_bill_after_registerasset(into_code);

			//更新项目立项的状态
			assetPurchaserApplyService.update_apply_project_after_registerasset(into_code);
		}else{
			//更新入库单，表示入库单完成
			assetIntolibraryApplyService.updating_into_library_for_asset(into_code);

			//更新采购单，表示采购单完成
			assetPurchaseBillService.updating_finish_purchase_bill_after_registerasset(into_code);

			//更新项目立项的状态
			assetPurchaserApplyService.updating_apply_project_after_registerasset(into_code);
		}
		return "redirect:/asset/arda_add_asset_index?saveResult=success";
	}




}
