package com.mbfw.controller.assets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//import java.util.List;

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
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetClassifyService;
import com.mbfw.service.assets.AssetGetService;
import com.mbfw.service.assets.AssetRegisterService;
import com.mbfw.service.assets.ProductsInfoService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.service.assets.ProviderEditService;
import com.mbfw.util.AssetRegisterExcelRead;
import com.mbfw.util.Const;
import com.mbfw.util.DateUtil;
import com.mbfw.util.FileDownload;
import com.mbfw.util.FileUpload;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;
import com.mbfw.util.ProductInfoExcelRead;
import com.mbfw.util.ZxingUtil;

import net.sf.json.JSONArray;


@Controller
@RequestMapping(value = "/asset")
public class AssertRegisterController extends BaseController{

	@Resource(name = "assetRegisterService")
	private AssetRegisterService assetRegisterService; // 登记
	@Autowired
	private ProductsInfoService productsInfoService; // 产品
	@Autowired
	private ProjectApplyService projectApplyService; // 部门

	@Resource(name = "assetGetService")
	private AssetGetService assetGetService; // 领用

	@Resource(name="providerEditService") // 供应商
	private ProviderEditService providerEditService;

	@Resource(name="AssetClassify") // 供应商
	private AssetClassifyService assetClassifyService;


	/**
	 * 
	 * @return // 资产登记入库
	 * @throws Exception
	 */
	@RequestMapping(value = "/arda_assertRegister")
	public ModelAndView arda_assertRegister() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd =new PageData();
		pd = this.getPageData();

		Integer totalnumber = 0;
		List<PageData> register_asset = new ArrayList<PageData>();

		// ----------分页操作开始----------
		PageOption page = new PageOption(8, 1);
		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字


		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);
		pd.put("apply_person", apply_person);

		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}

		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){
			// (3)查询数据库中数据的总条数
			totalnumber = assetRegisterService.find_register_asset_totalnumber(pd);

			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			page.setPd(pd);
			register_asset = assetRegisterService.findUsedData(page);	
			// ----------分页结束------------------------------
		}else{
			page.setPd(pd);
			totalnumber = assetRegisterService.find_register_asset_bykey_totalnumber(page);
			System.out.println(totalnumber);
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			register_asset = assetRegisterService.find_register_asset_bykey(page);	
		}

		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("asset_register", register_asset);

		mv.addObject("page", page);
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送增加项目的结果
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果

		mv.setViewName("system/asset_register/assertRegister");

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
	 * 填写资产信息
	 */
	@RequestMapping(value = "/arda_asset_into_house")
	public ModelAndView arda_asset_into_house() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		List<PageData> product_find = productsInfoService.findProductByClass(pd); // 查找产品
		//查询公司和对应的部门生成json 供二级联动适应
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String register_person = user.getNAME();
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		if(permission==1){
			//查询公司和对应的部门生成json 供二级联动适应
			String info = projectApplyService.institutionInfo();
			JSONArray js = JSONArray.fromObject(info);
			mv.addObject("institutionInfo", js);
		}
		if(permission==2){
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//查询公司和对应的部门生成json 供二级联动适应
			String info = projectApplyService.institutionInfoPermission2(apply_company,apply_dept);
			JSONArray js = JSONArray.fromObject(info);
			mv.addObject("institutionInfo", js);
		}
		if(permission==3){
			//获取用户名字
			String apply_person = user.getNAME();
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//如果权限为3申请人为用户的名字 上级名称 所在部门
			mv.addObject("apply_person", apply_person);
			mv.addObject("apply_company", apply_company);
			mv.addObject("apply_dept", apply_dept);
		}

		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限

		mv.addObject("product_find",product_find);
		mv.setViewName("system/asset_register/asset_into_house");
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("asset_purchase_time", formatter.format(currentTime));

		mv.addObject("register_person", register_person);
		mv.addObject("msg", "arda_save_asset_into_house");
		mv.addObject("permission", permission);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 资产封装成json，用ajax请求
	 */
	@RequestMapping(value = "/arda_fill_asset")	

	public @ResponseBody PageData arda_fill_asset(Page page) throws Exception {
		PageData pd = this.getPageData();
		//获取入库单的唯一id号
		Integer id = Integer.parseInt(pd.getString("id"));
		PageData fill_asset_info = productsInfoService.find_product_to_add_asset(id);
		System.out.println(fill_asset_info);
		return fill_asset_info;
	}

	/**
	 * 保存资产入库单
	 */
	@RequestMapping(value = "/arda_save_asset_into_house")
	public String arda_save_asset_into_house(@RequestParam(value = "asset_img", required = false) MultipartFile file[],
			AssetInfo asset_info) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Integer product_total=Integer.parseInt(asset_info.getProduct_total());
		String asset_name= asset_info.getAsset_name_select().split("@")[0];
		Integer id = Integer.parseInt(asset_info.getAsset_name_select().split("@")[1]);

		asset_info.setAsset_name(asset_name);
		int ac=0;
		DecimalFormat deft = new DecimalFormat("00000");
		ac=product_total+1;
		String acode = asset_info.getProduct_code()+"-"+deft.format(ac);
		asset_info.setAsset_code(acode);
		//保存图片
		String filePath="";
		String filePathConst = PathUtil.getPath("2")+File.separator;//.getClasspath() + Const.FILEPATHFILE ;
		StringBuffer img_file= new StringBuffer();
		for (MultipartFile multipartFile : file) {
			
			if (!multipartFile.getOriginalFilename().equals("")) {
				String time = System.currentTimeMillis()+"@"+(int)(Math.random() *1000+1)+"."+multipartFile.getOriginalFilename().split("[.]")[multipartFile.getOriginalFilename().split("[.]").length-1];
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
		/*String qrcode = PathUtil.getPath("1")+ File.separator+acode+asset_name+".png";
		ZxingUtil.createQRCode(acode, new File(qrcode));
		asset_info.setAsset_qr_code(qrcode);*/
		/// 结束

		asset_info.setAsset_status("领用");
		assetRegisterService.save_used(asset_info); // 登记资产入库

		// 登记到领用表
		pd.put("asset_code", acode);
		pd.put("asset_use_company", asset_info.getAsset_use_company());
		pd.put("asset_use_dept", asset_info.getAsset_use_dept());
		pd.put("asset_user", asset_info.getAsset_user());
		pd.put("get_time", asset_info.getAsset_purchase_time());
		pd.put("get_manager", asset_info.getAsset_manager());

		assetGetService.saveData(pd);
		// 结束

		// 更新到产品表
		pd.put("product_total", ac);
		//pd.put("id", id);
		pd.put("product_code", asset_info.getProduct_code());
		productsInfoService.updateProductByProductCode(pd);

		return "redirect:/asset/arda_assertRegister?saveResult=success";
	}

	@RequestMapping(value = "/goAddU")
	public ModelAndView goAddU() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.setViewName("system/asset_register/asset_into_house");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);


		return mv;
	}

	// 单个删除
	@RequestMapping(value = "/arda_deleteRegisterData")
	public void arda_deleteRegisterData(PrintWriter out) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		assetRegisterService.deleteData(pd);
		out.write("success");
		out.close();
	}

	// 批量删除
	@RequestMapping(value = "/arda_deleteAllRegisterData")
	@ResponseBody
	public PageData arda_deleteAllRegisterData() {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String Allot_ids = pd.getString("del_register_asset");
			String ArrayAllot_ids[] = Allot_ids.split(",");

			assetRegisterService.deleteAllData(ArrayAllot_ids);
			pd.put("msg", "success");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			pd.put("msg", "no");
		} finally {
			logAfter(logger);
		}
		return pd;
	}

	/**
	 * 去编辑页面,对资产进行编辑
	 */
	@RequestMapping(value = "/arda_toEdit")
	public ModelAndView arda_toEdit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//查询公司和对应的部门生成json 供二级联动适应
		// 通过session获取用户的权限和部门
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		int permission = user.getUser_Permission();// 用户的权限（部门权限）
		if(permission==1){
			//查询公司和对应的部门生成json 供二级联动适应
			String info = projectApplyService.institutionInfo();
			JSONArray js = JSONArray.fromObject(info);
			mv.addObject("institutionInfo", js);
		}
		if(permission==2){
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//查询公司和对应的部门生成json 供二级联动适应
			String info = projectApplyService.institutionInfoPermission2(apply_company,apply_dept);
			JSONArray js = JSONArray.fromObject(info);
			mv.addObject("institutionInfo", js);
		}
		if(permission==3){
			//获取用户名字
			String apply_person = user.getNAME();
			String apply_company = user.getSuperior_organization_name();//上级组织简称
			String apply_dept = user.getOrganization_name();// 所在公司名称
			//如果权限为3申请人为用户的名字 上级名称 所在部门
			mv.addObject("apply_person", apply_person);
			mv.addObject("apply_company", apply_company);
			mv.addObject("apply_dept", apply_dept);
		}

		mv.addObject("permission", permission);

		AssetInfo agm =assetRegisterService.findObjectById(pd);

		String gongsi=agm.getAsset_use_company();
		String bumen =agm.getAsset_use_dept();

		mv.addObject("agm", agm);
		mv.addObject("gongsi",gongsi);
		mv.addObject("bumen",bumen);
		mv.addObject("productFind", productsInfoService.findProductByClass(pd));
		mv.addObject("msg","arda_edit");

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		mv.addObject("asset_purchase_time", formatter.format(currentTime));

		mv.setViewName("system/asset_register/registerTable");
		return mv;
	}

	/**
	 * 保存编辑
	 */
	@RequestMapping(value = "/arda_edit")
	public String arda_edit(@RequestParam(value = "asset_img", required = false) MultipartFile file[],
			AssetInfo asset_info) throws Exception {
		//保存图片
		String filePath="";
		String filePathConst = PathUtil.getPath("2")+File.separator;//.getClasspath() + Const.FILEPATHFILE ;
		StringBuffer img_file= new StringBuffer();
		for (MultipartFile multipartFile : file) {
			if (!multipartFile.getOriginalFilename().equals("")) {
				String time = System.currentTimeMillis()+"@"+(int)(Math.random() *1000+1)+"."+multipartFile.getOriginalFilename().split("[.]")[multipartFile.getOriginalFilename().split("[.]").length-1];
				filePath = filePathConst+time; // 文件上传路径
				FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(filePath));
				img_file.append(time+"#");
			}
		}
		if(!img_file.equals("")){
			asset_info.setAsset_image(img_file.toString());
		}
		/*//判断供应商的添加
		String provider_name = asset_info.getAsset_provider();
		if(provider_name != "不祥" &&provider_name !=null){ // 供应商被修改应该插入供应商表
			PageData pd = new PageData();
			pd.put("provider_name", provider_name);
			pd.put("provider_address", "不祥");
			pd.put("provider_tel", "不祥");
			pd.put("provider_conn_person", "不祥");
			pd.put("provider_conn_tel", "不祥");
			pd.put("provider_aptitude", "不祥");
			pd.put("products_quality_standard", "不祥");
			pd.put("provider_comment", "不祥");
			pd.put("provider_note", "修改资产信息时添加，添加时间是:"+DateUtil.getTime());
			providerEditService.save_provider_insert(pd);
		}*/
		
		assetRegisterService.edit(asset_info);

		return "redirect:/asset/arda_assertRegister";
	}

	/****************************** 采购时的资产登记入库 *************************/

	// 资产登记显示主页
	@RequestMapping(value = "/arda_add_asset_index")
	public ModelAndView arda_add_asset_index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd =new PageData();
		pd = this.getPageData();

		Integer totalnumber = 0;
		List<PageData> register_asset = new ArrayList<PageData>();

		PageOption page = new PageOption(8, 1); 
		// 获取session
		Subject currentInstitution = SecurityUtils.getSubject();
		Session session = currentInstitution.getSession();
		User user = (User) session.getAttribute("sessionUser");
		String superior_organization_name = user.getSuperior_organization_name();
		Integer user_Permission =  user.getUser_Permission();
		String organization_name = user.getOrganization_name();
		String apply_person = user.getNAME();//获取用户名字


		pd.put("superior_organization_name", superior_organization_name);
		pd.put("user_Permission", user_Permission);
		pd.put("organization_name", organization_name);
		pd.put("apply_person", apply_person);

		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}

		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){
			page.setPd(pd);
			totalnumber = assetRegisterService.find_asset_index_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			register_asset = assetRegisterService.find_asset_index(page);	
		}else{
			page.setPd(pd);
			totalnumber = assetRegisterService.find_asset_index_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			register_asset = assetRegisterService.find_asset_index_bykey(page);
		}

		Map<String,String> 	QX = getHC();
		//把权限传到jsp页面
		mv.addObject("QX", QX);//增删改查权限
		mv.addObject("asset_register", register_asset);
		mv.addObject("page", page);
		mv.addObject("updateresult", this.getPageData().getString("updateResult"));// 传送增加项目的结果
		mv.addObject("saveresult", this.getPageData().getString("saveResult"));// 传送增加项目的结果
		mv.setViewName("system/ainto_libraries/into_house");

		return mv;
	}

	// 修改页面
	@RequestMapping(value = "/arda_update_asset")
	public ModelAndView arda_update_asset() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		AssetInfo agm =assetRegisterService.findObjectById(pd);

		mv.setViewName("system/ainto_libraries/asset_into_house");
		mv.addObject("agm", agm);
		mv.addObject("flag", "editAI");
		mv.addObject("msg","arda_save_update_asset");

		return mv;
	}

	//保存修改
	@RequestMapping(value = "/arda_save_update_asset")
	public String arda_save_update_asset(@RequestParam(value = "asset_img", required = false) MultipartFile file[],
			AssetInfo asset_info) throws Exception {
		//保存图片
		String filePath="";
		String filePathConst = PathUtil.getPath("2")+File.separator;//.getClasspath() + Const.FILEPATHFILE ;
		StringBuffer img_file= new StringBuffer();
		for (MultipartFile multipartFile : file) {
			if (!multipartFile.getOriginalFilename().equals("")) {
				String time = System.currentTimeMillis()+"@"+(int)(Math.random() *1000+1)+"."+multipartFile.getOriginalFilename().split("[.]")[multipartFile.getOriginalFilename().split("[.]").length-1];
				filePath = filePathConst+time; // 文件上传路径
				FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(filePath));
				img_file.append(time+"#");
			}
		}
		if(!img_file.equals("")){
			asset_info.setAsset_image(img_file.toString());
		}

		assetRegisterService.update_asset_register(asset_info);
		return "redirect:/asset/arda_add_asset_index?updateResult=success";
	}

	//导入数据

	/**
	 * 下载资产信息导入模版
	 */
	@RequestMapping(value = "/arda_downAssetExcel")
	public void downAssetExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getPath("0")+ File.separator + "AssetInfo.xls", "AssetInfo.xls");

	}
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/arda_goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/asset_register/uploadexcel");
		return mv;
	}

	/**
	 * 
	 * @param file 导入数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/arda_readExcel")
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//PageData providerPd = new PageData(); // 供应商pd
		PageData productPd = new PageData(); // 产品pd
		//	PageData classPd = new PageData(); // 类别pd

		if(null != file && !file.isEmpty()){
			String filePath = PathUtil.getPath("0"); // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "AssetInfo"); // 执行上传

			List<PageData> listpd =(List) AssetRegisterExcelRead.assetRegisterReadExcel(filePath, fileName, 2, 0, 0);
			//从数据表中查找资产类别
			Map<String,Map<String,String>> assetClassInfo = productsInfoService.getAssetClass();
			//从数据库中查找供应商信息
			Map<String,String> providerInfo = providerEditService.getProvider();

			if(listpd == null){
				mv.addObject("msg","error");
				mv.setViewName("system/asset_register/uploadexcelresult");
				return mv ;
			}
			// 符合要求进行 操作入库
			for(int  i=0;i<listpd.size();i++){
				if((listpd.get(i).getString("var1") == null || listpd.get(i).getString("var1") == "")
						&&(listpd.get(i).getString("var2") == null || listpd.get(i).getString("var2") == "")
						&&(listpd.get(i).getString("var3") == null || listpd.get(i).getString("var3") == "")
						&&(listpd.get(i).getString("var4") == null || listpd.get(i).getString("var4") == "")
						&&(listpd.get(i).getString("var5") == null || listpd.get(i).getString("var5") == "")
						&&(listpd.get(i).getString("var6") == null || listpd.get(i).getString("var6") == "")
						&&(listpd.get(i).getString("var7") == null || listpd.get(i).getString("var7") == "")
						&&(listpd.get(i).getString("var8") == null || listpd.get(i).getString("var8") == "")
						&&(listpd.get(i).getString("var9") == null || listpd.get(i).getString("var9") == "")
						&&(listpd.get(i).getString("var10") == null || listpd.get(i).getString("var10") == "")
						&&(listpd.get(i).getString("var11") == null || listpd.get(i).getString("var11") == "")
						&&(listpd.get(i).getString("var12") == null || listpd.get(i).getString("var12") == "")
						&&(listpd.get(i).getString("var13") == null || listpd.get(i).getString("var13") == "")
						&&(listpd.get(i).getString("var14") == null || listpd.get(i).getString("var14") == "")
						&&(listpd.get(i).getString("var15") == null || listpd.get(i).getString("var15") == "")
						&&(listpd.get(i).getString("var16") == null || listpd.get(i).getString("var16") == "")
						&&(listpd.get(i).getString("var17") == null || listpd.get(i).getString("var17") == "")){
					continue;
				}else if((listpd.get(i).getString("var1") == null || listpd.get(i).getString("var1") == "")
						||(listpd.get(i).getString("var2") == null || listpd.get(i).getString("var2") == "")
						||(listpd.get(i).getString("var3") == null || listpd.get(i).getString("var3") == "")
						||(listpd.get(i).getString("var4") == null || listpd.get(i).getString("var4") == "")
						||(listpd.get(i).getString("var14") == null || listpd.get(i).getString("var14") == "")){
					mv.addObject("msg","error_asset");
					mv.setViewName("system/asset_register/uploadexcelresult");
					return mv ;
				}else{
					String asset_sn =listpd.get(i).getString("var15");
					if(!asset_sn.equals("")){
						pd.put("asset_sn", asset_sn);
						if(assetRegisterService.find_bySN(pd)!= null){
							continue;
						}
					}
					String code = "";
					String assetCode= "";
					String assetName = listpd.get(i).getString("var2");//资产名称
					String assetClass = listpd.get(i).getString("var1");//资产类别
					String provider = listpd.get(i).getString("var9");//供应商

					pd.put("product_class", assetClass);
					pd.put("product_name", assetName);
					pd.put("provider_name", provider);
					PageData hasPd = new PageData();
					hasPd = productsInfoService.find_class_name(pd);
					DecimalFormat adeft = new DecimalFormat("00000");
					if( hasPd !=null){ // 资产编码前12位在产品表已经有，且满足要求
						pd.put("asset_class", assetClass);
						pd.put("asset_name", assetName);
						pd.put("asset_status", listpd.get(i).getString("var3"));
						pd.put("asset_unit", listpd.get(i).getString("var4"));
						String asset_use_company =listpd.get(i).getString("var5");
						if(asset_use_company == null || asset_use_company.equals("")){
							asset_use_company="";
						}
						String asset_use_dept =listpd.get(i).getString("var6");
						if(asset_use_dept == null || asset_use_dept.equals("")){
							asset_use_dept="";
						}
						
						pd.put("asset_use_company", asset_use_company);
						pd.put("asset_use_dept", asset_use_company);
						if(listpd.get(i).getString("var7") == null || listpd.get(i).getString("var7").equals("")){
							pd.put("asset_price", 0.0);
						}else{
							pd.put("asset_price", listpd.get(i).getString("var7"));
						}
						
						String asset_use =listpd.get(i).getString("var8");
						if(asset_use == null || asset_use.equals("")){
							asset_use="";
						}
						String asset_provider =listpd.get(i).getString("var9");
						if(asset_provider == null || asset_provider.equals("")){
							asset_provider="";
						}
						String asset_storehouse =listpd.get(i).getString("var10");
						if(asset_storehouse == null || asset_storehouse.equals("")){
							asset_storehouse="";
						}
						String asset_brand =listpd.get(i).getString("var11");
						if(asset_brand == null || asset_brand.equals("")){
							asset_brand="";
						}
						String asset_user =listpd.get(i).getString("var12");
						if(asset_user == null || asset_user.equals("")){
							asset_user="";
						}
						String asset_max_years =listpd.get(i).getString("var13");
						if(asset_max_years == null || asset_max_years.equals("")){
							asset_max_years="";
						}
						String asset_standard_model =listpd.get(i).getString("var14");
						if(asset_standard_model == null || asset_standard_model.equals("")){
							asset_standard_model="";
						}
						
						if(asset_sn == null || asset_sn.equals("")){
							asset_sn="";
						}
						String asset_detail_config =listpd.get(i).getString("var16");
						if(asset_detail_config == null || asset_detail_config.equals("")){
							asset_detail_config="";
						}
						String asset_nod =listpd.get(i).getString("var17");
						if(asset_nod == null || asset_nod.equals("")){
							asset_nod="";
						}
						pd.put("asset_use", asset_use);
						pd.put("asset_provider", asset_provider);
						pd.put("asset_storehouse", asset_storehouse);
						pd.put("asset_brand", asset_brand);
						pd.put("asset_user", asset_user);
						pd.put("asset_max_years", asset_max_years);
						pd.put("asset_standard_model", asset_standard_model);
						pd.put("asset_sn", asset_sn);
						pd.put("asset_detail_config", asset_detail_config);
						pd.put("asset_nod",asset_nod);

						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						pd.put("asset_purchase_time", formatter.format(currentTime));

						// 获取session
						Subject currentInstitution = SecurityUtils.getSubject();
						Session session = currentInstitution.getSession();
						User user = (User) session.getAttribute("sessionUser");
						String apply_person = user.getNAME();//获取用户名字
						pd.put("asset_manager", apply_person);

						hasPd = productsInfoService.find_class_name(pd);
						Integer product_total = Integer.parseInt(hasPd.get("product_total").toString()) + 1;
						String acode = hasPd.getString("product_code")+"-"+adeft.format(product_total);
						pd.put("asset_code", acode);
						//生成二维码
/*						String qrcode = PathUtil.getPath("1")+ File.separator+acode+listpd.get(i).getString("var2")+".png";
						ZxingUtil.createQRCode(acode, new File(qrcode));
						pd.put("asset_qr_code", acode+listpd.get(i).getString("var2")+".png");
*/						//保存到数据库中
						assetRegisterService.save_import_data(pd);
						pd.put("product_code", hasPd.getString("product_code"));
						pd.put("product_total", product_total);
						productsInfoService.updateProductByProductCode(pd);
					}else{
						if(assetClassInfo.containsKey(assetClass)){//查找资产类别是否存在
							if(assetClassInfo.get(assetClass).containsKey(assetName)){//查找资产名称是否存在
								assetCode = assetClassInfo.get(assetClass).get(assetName);
							}else{//资产名称不存在
								PageData p = new PageData();
								p.put("name", assetClass);
								PageData ac = assetClassifyService.findByClass(p);
								//存在获取对应的资产编码
								int number = (int) ac.get("number");
								//判断对应的资产名称是否存在
								p.remove("name");
								p.put("name", assetName);
								p.put("pId", number);//加入父编码
								addChild(p);
								assetCode = productsInfoService.getCode(p);
								assetClassInfo.get(assetClass).put(assetName, assetCode);

							}
						}else{//资产类别不存在
							PageData pdt = new PageData();
							pdt.put("name", assetClass);
							addParent(pdt);
							//获取父编码
							PageData acp = assetClassifyService.findByClass(pdt);
							pdt.remove("name");
							int pnm = (int) acp.get("number");
							pdt.put("name", assetName);
							pdt.put("pId", pnm);
							addChild(pdt);
							assetCode = productsInfoService.getCode(pdt);
							Map<String,String> tm = new HashMap<>();
							tm.put(assetName, assetCode);
							assetClassInfo.put(assetClass, tm);
						}
						// 供应商编码处理
						DecimalFormat deft = new DecimalFormat("000");
						if(provider!=null&&!provider.equals("")&&provider!=""){ // 判断供应商是够存在
							String providerCode = providerInfo.get(provider);
							if(providerCode!=null){
								code = assetCode+"-"+providerCode;
								pd.put("provider_code",providerCode);
							}else{
								// 插入此供应商 save_provider_insert
								pd.put("provider_name", provider);
								pd.put("provider_address", "不祥");
								pd.put("provider_tel", "");
								pd.put("provider_conn_person", "不祥");
								pd.put("provider_conn_tel", "");
								pd.put("provider_aptitude", "一级");
								pd.put("products_quality_standard", "");
								pd.put("provider_comment", "");
								pd.put("provider_note", "导入产品是的供应商，可后面完善供应商信息，导入时间是:"+DateUtil.getTime());
								providerEditService.save_provider_insert(pd);

								//查找刚才添加的供应商
								Integer provider_code = providerEditService.get_code_byproduct(pd);
								//封装到供应商中
								providerInfo.put(provider, deft.format(provider_code));
								pd.put("provider_code", provider_code); // 添加供应商代码,是产品的外键
								code = assetCode+"-"+deft.format(provider_code);
							}

						}else{
							code = assetCode+"-"+"000";
							pd.remove("provider_name");
							pd.remove("provider_code");
							pd.put("provider_code", "000");
							pd.put("provider_name", "不祥");
						}
						// 以上已经完成前9位编码 000-000-000
						//添加到产品表
						pd.put("product_code", code);
						String price = listpd.get(i).getString("var7");
						if(price == null || price== "" ){
							pd.put("product_price", "0.0");
						}else{
							pd.put("product_price", price);
						}
						if(productsInfoService.get_product_total(pd) == null){ // 产品没有则插入
							
							pd.put("product_type", listpd.get(i).getString("var14"));
							pd.put("product_unit", listpd.get(i).getString("var4"));
							pd.put("product_others", "");
							pd.put("product_note", "");
							pd.put("product_total", 1);
							pd.put("product_flag", "固定资产");
							productsInfoService.saveProduct(pd);// 保存到产品数据库
						}


						//已有资产添加到数据库中
						pd.put("asset_class", assetClass);
						pd.put("asset_name", assetName);
						pd.put("asset_status", listpd.get(i).getString("var3"));
						pd.put("asset_unit", listpd.get(i).getString("var4"));
						String asset_use_company =listpd.get(i).getString("var5");
						if(asset_use_company == null || asset_use_company.equals("")){
							asset_use_company="";
						}
						String asset_use_dept =listpd.get(i).getString("var6");
						if(asset_use_dept == null || asset_use_dept.equals("")){
							asset_use_dept="";
						}
						pd.put("asset_use_company", asset_use_company);
						pd.put("asset_use_dept", asset_use_dept);
						if(price == null || price.equals("")){
							pd.put("asset_price", "0.0");
						}else{
							pd.put("asset_price", price);
						}
						pd.put("asset_use", listpd.get(i).getString("var8"));
						if(provider ==null || provider.equals("")){
							pd.put("asset_provider", "不祥");
						}else{
							pd.put("asset_provider", provider);
						}
						
						String asset_storehouse =listpd.get(i).getString("var10");
						if(asset_storehouse == null || asset_storehouse.equals("")){
							asset_storehouse="";
						}
						String asset_brand =listpd.get(i).getString("var11");
						if(asset_brand == null || asset_brand.equals("")){
							asset_brand="";
						}
						String asset_user =listpd.get(i).getString("var12");
						if(asset_user == null || asset_user.equals("")){
							asset_user="";
						}
						String asset_max_years =listpd.get(i).getString("var13");
						if(asset_max_years == null || asset_max_years.equals("")){
							asset_max_years="";
						}
						String asset_standard_model =listpd.get(i).getString("var14");
						if(asset_standard_model == null || asset_standard_model.equals("")){
							asset_standard_model="";
						}
						//String asset_sn =listpd.get(i).getString("var15");
						if(asset_sn == null || asset_sn.equals("")){
							asset_sn="";
						}
						String asset_detail_config =listpd.get(i).getString("var16");
						if(asset_detail_config == null || asset_detail_config.equals("")){
							asset_detail_config="";
						}
						String asset_nod =listpd.get(i).getString("var17");
						if(asset_nod == null || asset_nod.equals("")){
							asset_nod="";
						}
						
						pd.put("asset_storehouse", asset_storehouse);
						pd.put("asset_brand", asset_brand);
						pd.put("asset_user", asset_user);
						pd.put("asset_max_years", asset_max_years);
						pd.put("asset_standard_model", asset_standard_model);
						pd.put("asset_sn", asset_sn);
						pd.put("asset_detail_config", asset_detail_config);
						pd.put("asset_nod",asset_nod);

						Date currentTime = new Date();
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						pd.put("asset_purchase_time", formatter.format(currentTime));

						// 获取session
						Subject currentInstitution = SecurityUtils.getSubject();
						Session session = currentInstitution.getSession();
						User user = (User) session.getAttribute("sessionUser");
						String apply_person = user.getNAME();//获取用户名字
						pd.put("asset_manager", apply_person);

						productPd = productsInfoService.get_product_total(pd); // 查找满足的供应商
						Integer product_total = Integer.parseInt(productPd.get("product_total").toString()) + 1;

						String acode = code+"-"+adeft.format(product_total);
						pd.put("asset_code", acode);
						//生成二维码
						/*String qrcode = PathUtil.getPath("1")+ File.separator+acode+assetName+".png";
						ZxingUtil.createQRCode(acode, new File(qrcode));
						pd.put("asset_qr_code", acode+assetName+".png");*/
						//保存到数据库中
						assetRegisterService.save_import_data(pd);
						pd.remove("product_total");
						pd.put("product_total", product_total);
						productsInfoService.updateProductByProductCode(pd);

					}
				}
			}

		}
		mv.addObject("msg", "success");
		mv.setViewName("system/asset_register/uploadexcelresult");
		return mv;
	}

	/**
	 * 封装添加父节点的方法
	 * 只用于导入Excel
	 * @param pd
	 * @throws Exception 
	 */
	private String addParent(PageData pd) throws Exception {
		String message = "";
		//判断节点是否存在
		PageData pdf = assetClassifyService.findByClass(pd);
		if(pdf!=null){
			message = "exist";
		}else{
			List<String> li = new ArrayList<String>();
			//创建数组防止删除之后出现冲突
			DecimalFormat deft = new DecimalFormat("000");
			for(int i=1;i<1000;i++){
				String index = deft.format(i);
				li.add(index);
			}
			List<PageData> pds = assetClassifyService.listAllParentPoint(pd);
			int length = pds.size()+1;

			//如果长度小于99让其添加
			if(length<1000){
				for (PageData pageData : pds) {
					String orderIndex = pageData.getString("orderSort");
					if(li.contains(orderIndex)){
						li.remove(orderIndex);
					}
				}
				String order = li.get(0);
				pd.put("orderSort", order);
				assetClassifyService.addPoint(pd);
				message = "success";
			}else{
				message = "fail";
			}
		}
		return message;
	}


	/**
	 * 封装添加子节点的方法
	 * 只用于导入Excel
	 * @param pd
	 * @throws Exception 
	 */
	private String addChild(PageData pd) throws Exception {
		//创建传递消息的对象
		String message = ""; 
		//判断节点是否存在
		PageData pdf = assetClassifyService.findByAssetName(pd);
		if(pdf!=null){
			message = "exist";
		}else{
			List<String> li = new ArrayList<String>();
			//创建数组防止删除之后出现冲突
			DecimalFormat deft = new DecimalFormat("000");
			for(int i=1;i<1000;i++){
				String index = deft.format(i);
				li.add(index);
			}
			//查询父节点对应的所有孩子节点
			List<PageData> pds = assetClassifyService.listChildPoint(pd);
			int length = pds.size()+1;

			//判断父节点下的孩子节点数量是否超过限制
			if(length<1000){
				for (PageData pageData : pds) {
					String orderIndex = pageData.getString("orderSort");
					if(li.contains(orderIndex)){
						li.remove(orderIndex);
					}
				}
				String order = li.get(0);
				pd.put("orderSort", order);
				//查询父节点的序列号
				PageData singPd = assetClassifyService.getParentPoint(pd);
				String parentSort = singPd.getString("orderSort");
				//设置parentSort
				pd.put("parentSort", parentSort);
				//设置coding
				String coding = parentSort+"-"+order;
				pd.put("coding", coding);
				assetClassifyService.addChildPoint(pd);
				message = "success";
			}else{
				//超过数量限制 创建失败
				message = "fail";
			}
		}
		return message;
	}

}
