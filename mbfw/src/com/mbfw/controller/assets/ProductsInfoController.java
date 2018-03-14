package com.mbfw.controller.assets;

import java.io.File;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.service.assets.AssetClassifyService;
import com.mbfw.service.assets.ProductsInfoService;
import com.mbfw.service.assets.ProviderEditService;
import com.mbfw.util.Const;
import com.mbfw.util.DateUtil;
import com.mbfw.util.FileDownload;
import com.mbfw.util.FileUpload;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;
import com.mbfw.util.ProductInfoExcelRead;
import com.mbfw.util.ProviderInfoExcelRead;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/product")
public class ProductsInfoController extends BaseController {
	@Resource(name="productsInfoService") // 产品
	private ProductsInfoService productsInfoService;

	@Resource(name="providerEditService") // 供应商
	private ProviderEditService providerEditService;

	@Resource(name="AssetClassify") // 供应商
	private AssetClassifyService assetClassifyService;

	/**
	 * 显示产品信息主页
	 * @param page product/apl_product_index.do
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/apl_product_index")
	public ModelAndView apl_product_index() throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<PageData> productFind =  new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();
		// ----------分页操作开始----------
		PageOption page = new PageOption(8, 1); // 默认初始化一进来显示就是每页显示5条，当前页面为1
		// (1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if (pd.getString("currentPage") != null) {
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		// (2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if (pd.getString("showCount") != null) {
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		if(pd.getString("retrieve_content") == null ||pd.getString("retrieve_content") =="" ){
			// (3)查询数据库中数据的总条数
			Integer totalnumber = productsInfoService.find_productInfo_totalnumber(pd);
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据
			productFind=productsInfoService.findProduct(page);
		}else{
			//(3)获取传送过来的进行检索的审核人员的姓名
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = productsInfoService.findProductByKey_totalnumber(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(8)查询数据库，返回对应检索姓名的数据
			productFind = productsInfoService.findProductByKey(page);
		}
		// ----------分页结束------------------------------ 
		mv.addObject("delFaile", pd.getString("delFaile"));
		mv.addObject("delResult", pd.getString("delResult"));
		String del_all_product = pd.getString("product");
		if(del_all_product !=null){

			del_all_product= URLDecoder.decode(del_all_product,"utf-8");
			mv.addObject("msg_product", "del_all_product");
			mv.addObject("product_name", del_all_product);
		}
		String codeRepeat= pd.getString("codeRepeat");// 判断产品是否重复
		if(codeRepeat!= null){
			mv.addObject("codeRepeat", codeRepeat);
		}
		String saveProduct= pd.getString("saveProduct");// 填加成功
		if(saveProduct!= null){
			mv.addObject("saveProduct", saveProduct);
		}
		String updateProduct= pd.getString("updateProduct");// 修改成功
		if(updateProduct!= null){
			mv.addObject("updateProduct", updateProduct);
		}
		String others = pd.getString("others");
		if(others !=null && !others.equals("")){
			mv.addObject("others", others);
		}
		mv.addObject("productFind", productFind);
		mv.addObject("page", page);
		mv.setViewName("system/apl_product/apl_product_index");

		return mv;
	}

	/**
	 *  产品新增页面
	 * @return
	 * @throws Exception 
	 */
	// 选择是固定还是耗材
	@RequestMapping(value="/apl_product_insert")
	public ModelAndView  apl_product_insert() throws Exception {
		ModelAndView mv = new ModelAndView();
		List<PageData> providerFind = new ArrayList<PageData>();
		PageData pd = new PageData();
		pd = this.getPageData();

		//---------固定资产类别
		String class_name =productsInfoService.fixed_product_info(pd); // 获取产品类别 JSon
		JSONArray js2 = JSONArray.fromObject(class_name);
		//-----------------

		//---------耗材资产类别

		String used_name = productsInfoService.used_product_info(pd);
		JSONArray js3 = JSONArray.fromObject(used_name);
		//-----------------

		// 封装资产编码

		providerFind=providerEditService.find_provider(pd);

		mv.addObject("providerFind", providerFind);
		mv.addObject("class_name",js2); // 封装json类别
		mv.addObject("used_class_name",js3); // 封装json类别
		// 查找供应商

		mv.setViewName("system/apl_product/apl_product_insert");
		mv.addObject("msg", "save_product_insert");

		mv.addObject("flag","saveP");
		mv.addObject("providerFind", providerFind);
		mv.setViewName("system/apl_product/apl_product_insert");
		return mv;
	}



	/**
	 * 产品保存页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/save_product_insert")
	public String save_product_insert() throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String str2=pd.getString("provider");
		String provider_code="";
		if(str2 !=null && !str2.equals("")){
			String[] provider =str2.split(":");
			provider_code = provider[0];
			String provider_name = provider[1];
			pd.put("provider_code", provider_code);
			pd.put("provider_name", provider_name);
		}
		
		String str3 = pd.getString("select_product");
		String product_code = "";
		DecimalFormat deft = new DecimalFormat("000");
		if(str3 !=null && !str3.equals("")){
			String product_name =str3.split("@")[0]; // 获取产品名称
			pd.put("product_name", product_name);
			String coding = pd.getString("select_product").split("@")[1]; // 获取产品编码
			
			//格式化输出

			

			String provider_coding = deft.format(Integer.parseInt(provider_code));
			product_code = coding +"-"+ provider_coding;

			pd.put("product_code", product_code);// 获得产品编码
		}
		
		if(pd.getString("flag").equals("saveP")){
			int count = productsInfoService.searchProductCode(product_code);//判断产品是否存在，存在则不能添加
			if(count==0){
				productsInfoService.saveProduct(pd);
				return "redirect:/product/apl_product_index?saveProduct=success";
			}else {
				return "redirect:/product/apl_product_index?codeRepeat=true";
			}

		}else{
			String provider2 = pd.getString("provider_name");
			if(provider2 !=null && !provider2.equals("")){
				pd.remove("provider_name");
				pd.put("provider_name", provider2);
				//插入供应商
				pd.put("provider_address", "不祥");
				pd.put("provider_tel", "不祥");
				pd.put("provider_conn_person", "不祥");
				pd.put("provider_conn_tel", "不祥");
				pd.put("provider_aptitude", "不祥");
				pd.put("products_quality_standard", "不祥");
				pd.put("provider_comment", "不祥");
				pd.put("provider_note", "修改产品时添加的供应商，添加时间是:"+DateUtil.getTime());
				providerEditService.save_provider_insert(pd);
				//查找刚才添加的供应商编号
				Integer providerCoding = providerEditService.get_code_byproduct(pd);
				String pcode = pd.getString("pcode");
				String [] str = pcode.split("-");
				String pcoding =str[0]+"-"+str[1]+"-"+deft.format(providerCoding);
				pd.remove("product_code");
				pd.put("product_code", pcoding);// 生成编码
				pd.put("provider_code", String.valueOf(providerCoding));// 为产品添加供应商
				//修改资产表留在后面用 
			}
			productsInfoService.updateProduct(pd);
			return "redirect:/product/apl_product_index?updateProduct=success";
		}
	}

	/**
	 *  
	 * @return 修改产品信息
	 * @throws Exception
	 */
	@RequestMapping(value="/apl_product_update")
	public ModelAndView apl_product_update() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd =new PageData();
		pd = this.getPageData();

		PageData pdProduct = productsInfoService.findProductById(pd); // 查找对应产品的信息
		String pc= pdProduct.getString("product_class");
		String pn = pdProduct.getString("product_name");
		String class_flag = pdProduct.getString("product_flag");

		mv.addObject("pc", pc);
		mv.addObject("pn",pn);
		mv.addObject("class_flag",class_flag);
		
		//---------固定资产类别
		String class_name =productsInfoService.fixed_product_info(pd); // 获取产品类别 JSon
		JSONArray js2 = JSONArray.fromObject(class_name);
		//-----------------
		//---------耗材资产类别
		String used_name = productsInfoService.used_product_info(pd);
		JSONArray js3 = JSONArray.fromObject(used_name);
		//-----------------

		String onlyedit = this.getPageData().getString("onlyedit");
		if(onlyedit!=null && !onlyedit.equals("")){//能修改供应商，其他配置，备注。其他的不能修改
			mv.addObject("onlyedit", onlyedit);
		}
		String others = pd.getString("others");
		if(others !=null && others.equals(others)){ // 判断采购单是否使用该产品
			if(productsInfoService.get_product_code_aproject(pd)>0){ // 被其他使用，不能修改
				List<PageData> productFind =  new ArrayList<PageData>();
				PageOption page = new PageOption(8, 1); // 默认初始化一进来显示就是每页显示5条，当前页面为1
				if (pd.getString("currentPage") != null) {
					page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
				}
				if (pd.getString("showCount") != null) {
					page.setShowCount(Integer.parseInt(pd.getString("showCount")));
				}
				
				Integer totalnumber = productsInfoService.find_productInfo_totalnumber(pd);
				page.setTotalResult(totalnumber);
				page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
				productFind=productsInfoService.findProduct(page);
				
				mv.addObject("others", others);
				mv.addObject("productFind", productFind);
				mv.addObject("page", page);
				mv.setViewName("system/apl_product/apl_product_index");

				return mv;
			}
		}
		
		List<PageData> providerFind=providerEditService.find_provider(pd);//查找全部供应商
		mv.addObject("providerFind", providerFind);
		
		mv.addObject("class_name",js2); // 封装json类别
		mv.addObject("used_class_name",js3); // 封装json类别
		mv.setViewName("system/apl_product/apl_product_update");
		mv.addObject("msg", "save_product_insert");
		mv.addObject("pd", pdProduct);
		mv.addObject("flag", "updateP");

		return mv;
	}

	/**
	 * 
	 * @param out 根据id删除产品信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/delete_product")
	public String delete_product() throws Exception{
		PageData pd =new PageData();
		pd =this.getPageData();
		//String stre= pd.getString("product");
		String [] str = URLDecoder.decode(pd.getString("product"),"utf-8").split("@");
		if(str.length == 3){
			pd.put("asset_name", str[0]);
			pd.put("asset_class", str[1]);
			pd.put("asset_provider", str[2]);
		}else{
			pd.put("asset_name", str[0]);
			pd.put("asset_class", str[1]);
			pd.put("asset_provider", "");
		}
		if(productsInfoService.get_asset_ncp(pd) > 0 || productsInfoService.get_product_code_aproject(pd)>0){ // 资产信息有引用不能删除
			return "redirect:/product/apl_product_index?delFaile=faile";
		}else{
			productsInfoService.deleteProductById(pd);
			return "redirect:/product/apl_product_index?delResult=success";
		}
	}

	/**
	 * 批量删除产品
	 */
	@RequestMapping(value = "/delete_all_product")
	@ResponseBody
	public PageData delete_all_purchase_bill() {
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String del_all_product = pd.getString("del_all_product");
			StringBuffer product_name=new StringBuffer();
			StringBuffer del_product=new StringBuffer();
			Boolean flag=false;
			if (null != del_all_product && !"".equals(del_all_product)) {
				String array_del_all_product[] = del_all_product.split(",");
				for (String string : array_del_all_product) {
					String [] str =string.split("@");
					if(str.length == 5){
						pd.put("asset_name", str[1]);
						pd.put("asset_class", str[2]);
						pd.put("asset_provider", str[3]);
					}else{
						pd.put("asset_name", str[1]);
						pd.put("asset_class", str[2]);
						pd.put("asset_provider", "");
					}
					pd.put("product_code", str[4]);
					if(productsInfoService.get_asset_ncp(pd) > 0 || productsInfoService.get_product_code_aproject(pd)>0){ // 资产信息有引用不能删除
						product_name.append(str[1]+",");
						flag=true;
					}else{
						del_product.append(str[0]+",");
					}
				}
				if(flag){
					pd.put("product", product_name.toString());
				}
				String[] str1= del_product.toString().split(",");
				if(str1!=null && !str1.equals("")&&str1.length>0)
					productsInfoService.deleteAllProductById(str1);
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

	// 产品信息导入

	/**
	 * 下载资产信息导入模版
	 */
	@RequestMapping(value = "/downAssetExcel")
	public void downAssetExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getPath("0")+ File.separator + "ProductInfo.xls", "ProductInfo.xls");

	}
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/apl_product/uploadexcel");
		return mv;
	}

	/**
	 * 从EXCEL导入到数据库
	 * @throws Exception 
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();

		if(null != file && !file.isEmpty()){
			String filePath = PathUtil.getClasspath()+Const.FILEPATHFILE ;
			String fileName= FileUpload.fileUp(file, filePath, "ProviderInfo");

			List<PageData> listpd =(List) ProductInfoExcelRead.productInfoReadExcel(filePath, fileName, 2, 0, 0);
			if(listpd == null){
				mv.addObject("msg","error");
				mv.setViewName("system/apl_provider/uploadexcelresult");
				return mv ;
			}

			//从数据表中查找资产类别
			Map<String,Map<String,String>> assetClassInfo = productsInfoService.getAssetClass();
			//从数据库中查找供应商信息
			Map<String,String> providerInfo = providerEditService.getProvider();

			//存入数据库操作====================================== */
			/**
			 * var0 :编号  var1:供应商名称 var2 :供应商地址......等等
			 * 这个var就是readExcel方法返回对象中在方法中进行封装好的信息，也就是对应的信息
			 */
			for(int i=0;i<listpd.size(); i++){
				if(	(listpd.get(i).getString("var1") == null || listpd.get(i).getString("var1") == "")
						&&(listpd.get(i).getString("var2") == null || listpd.get(i).getString("var2") == "")
						&&(listpd.get(i).getString("var3") == null || listpd.get(i).getString("var3") == "")
						&&(listpd.get(i).getString("var4") == null || listpd.get(i).getString("var4") == "")
						&&(listpd.get(i).getString("var5") == null || listpd.get(i).getString("var5") == "")
						&&(listpd.get(i).getString("var6") == null || listpd.get(i).getString("var6") == "")
						&&(listpd.get(i).getString("var7") == null || listpd.get(i).getString("var7") == "")){
					continue;
				}else if((listpd.get(i).getString("var1") == null || listpd.get(i).getString("var1") == "")
						||(listpd.get(i).getString("var2") == null || listpd.get(i).getString("var2") == "")
						||(listpd.get(i).getString("var5") == null || listpd.get(i).getString("var5") == "")){
					mv.addObject("msg","error_product");
					mv.setViewName("system/apl_product/uploadexcelresult");
					return mv ;
				}else{
					String code = "";
					String assetCode= "";
					String assetName = listpd.get(i).getString("var1");//资产名称
					String assetClass = listpd.get(i).getString("var2");//资产类别
					String provider = listpd.get(i).getString("var3");//供应商
					//如果资产表有要添加产品则该条记录不加入
					if(provider!= null || provider !=""){
						pd.put("product_class", listpd.get(i).getString("var2"));
						pd.put("product_name", listpd.get(i).getString("var1"));
						pd.put("provider_name", listpd.get(i).getString("var3"));
						if(productsInfoService.find_class_name(pd) != null){
							continue;
						}
					}


					String units = listpd.get(i).getString("var5");//计量单位
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

					if(!provider.equals("")&&provider!=""&&provider!=null){ // 判断供应商是够存在
						String providerCode = providerInfo.get(provider);
						if(providerCode!=null){
							code = assetCode+"-"+providerCode;
							pd.put("provider_code",providerCode);
						}else{
							// 插入此供应商 save_provider_insert
							pd.put("provider_name", provider);
							pd.put("provider_address", "不祥");
							pd.put("provider_tel", "");
							pd.put("provider_conn_person", "");
							pd.put("provider_conn_tel", "");
							pd.put("provider_aptitude", "一级");
							pd.put("products_quality_standard", "");
							pd.put("provider_comment", "");
							pd.put("provider_note", "导入产品是的供应商，可后面完善供应商信息，导入时间是:"+DateUtil.getTime());
							providerEditService.save_provider_insert(pd);

							//查找刚才添加的供应商
							Integer provider_code = providerEditService.get_code_byproduct(pd);
							//封装到供应商中
							DecimalFormat deft = new DecimalFormat("000");
							providerInfo.put(provider, deft.format(provider_code));
							pd.put("provider_code", provider_code); // 添加供应商代码
							code = assetCode+"-"+deft.format(provider_code);
						}

					}else{
						code = assetCode+"-"+"000";
						pd.put("provider_name", "不祥");
						pd.remove("provider_code");
						pd.put("provider_code", "000");
					}
					pd.put("product_code", code);
					pd.put("product_type", "不祥");
					pd.put("product_unit", units);
					String price = listpd.get(i).getString("var4");
					if(price == null || price.equals("") ){
						pd.put("product_price", "0.0");
					}else{
						pd.put("product_price", price);
					}
					if(listpd.get(i).getString("var6") == null || listpd.get(i).getString("var6").equals("") ){
						pd.put("product_others", "");
					}else{
						pd.put("product_others", listpd.get(i).getString("var6"));
					}
					if(listpd.get(i).getString("var7") == null || listpd.get(i).getString("var7").equals("") ){
						pd.put("product_note", "");
					}else{
						pd.put("product_note", listpd.get(i).getString("var7"));
					}
					
					pd.put("product_total", 0);
					pd.put("product_flag", "固定资产");
					// 保存到产品数据库
					productsInfoService.saveProduct(pd);
				}
			}

		}
		mv.addObject("msg", "success");
		mv.setViewName("system/apl_product/uploadexcelresult");
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
