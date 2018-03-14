package com.mbfw.controller.assets;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.Page;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.service.assets.ProviderEditService;
import com.mbfw.util.Const;
import com.mbfw.util.FileDownload;
import com.mbfw.util.FileUpload;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;
import com.mbfw.util.ProviderInfoExcelRead;
@Controller
@RequestMapping(value = "/provider")
public class ProviderController extends BaseController {

	@Resource(name="providerEditService")
	private ProviderEditService providerEditService;
	/**
	 * 显示主页
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/apl_provider_manager")
	public ModelAndView apl_provider_manager() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		List<PageData> providerFind = new ArrayList<PageData>();
		Integer totalnumber = 0;
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
		if(pd.getString("retrieve_content") == "" || pd.getString("retrieve_content") == null){
			// (3)查询数据库中数据的总条数
			totalnumber = providerEditService.find_provider_totalnumber(pd);
			// (4)设置总的数据条数
			page.setTotalResult(totalnumber);
			// (5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit
			// (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage() - 1) * (page.getShowCount()));
			// (6)查询数据库，返回对应条数的数据

			providerFind=providerEditService.find_provider_insert(page);
			// ----------分页结束------------------------------
		}else{
			System.out.println(pd.getString("retrieve_content") + " 2dfg");
			page.setPd(pd);
			totalnumber = providerEditService.find_provider_bykey_totalnumber(page);
			page.setTotalResult(totalnumber);
			page.setCurrentResult((page.getCurrentPage() -1)*(page.getShowCount()));

			providerFind = providerEditService.find_provider_bykey(page);
		}
		String msg =pd.getString("msg") ;
		String provider = pd.getString("provider_name");
		String del_all_provider = pd.getString("provider");
		String delProvider= pd.getString("delProvider");
		String provider_name ="";
		if(provider !=null){
			provider_name= URLDecoder.decode(provider,"utf-8");
			if(msg!=null && msg !="" &&  provider_name !=null && provider_name !=""){
				mv.addObject("msg_provider", msg);
				mv.addObject("provider_name", provider_name);
			}
		}
		if(del_all_provider !=null){//批量删除
			provider_name= URLDecoder.decode(del_all_provider,"utf-8");
			mv.addObject("msg_provider", "del_all_provider");
			mv.addObject("provider_name", provider_name);
		}
		mv.addObject("delProvider", delProvider);
		mv.addObject("providerFind", providerFind);
		String update_result = pd.getString("update_result");
		if(update_result!= null){ // 供应商不能编辑
			mv.addObject("update_result", update_result);
		}
		mv.addObject("page", page);
		mv.setViewName("system/apl_provider/apl_provider_index");

		return mv;
	}

	/**
	 * 
	 * @param 供应商添加
	 * @return
	 */
	@RequestMapping(value="/apl_provider_insert")
	public ModelAndView apl_provider_insert(Page page){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.setViewName("system/apl_provider/provider_insert");
		mv.addObject("msg","save_provider_insert");
		mv.addObject("pd",pd);

		return mv;
	}

	/**
	 * 
	 * 保存新增供应商信息
	 */
	@RequestMapping(value = "/save_provider_insert")
	public ModelAndView save_provider_insert(PrintWriter out) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		providerEditService.save_provider_insert(pd);
		//mv.setViewName("system/apl_provider/provider_insert");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 
	 * @return 供应商能编辑
	 * @throws Exception
	 */
	@RequestMapping(value = "/apl_provider_edit")
	public ModelAndView apl_provider_edit() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		if(providerEditService.find_provider_code_product(pd) > 0){//有用，不能名称编辑
			mv.addObject("provider_no_edit", "no");
		}else{
			mv.addObject("provider_yes_edit", "yes");
		}
		pd=providerEditService.find_providerByPId(pd);
		mv.setViewName("system/apl_provider/provider_edit");
		mv.addObject("pd",pd);
		mv.addObject("msg", "provider_edit");
		return mv;
	}
/**
 * 
 * 保存x修改后供应商信息
 */
@RequestMapping(value = "/provider_edit")
public String provider_edit() throws Exception {
	PageData pd = new PageData();
	pd = this.getPageData();
	providerEditService.edit_provider(pd);
	return "redirect:/provider/apl_provider_manager";
}

/**
 * 通过id删除供应商 delete_provider
 * @throws Exception 
 */
@RequestMapping(value = "/delete_provider")
public String delete_provider(){
	PageData pd = new PageData();
	try {
		pd = this.getPageData();
		if(providerEditService.find_provider_code_product(pd) > 0){
			String msg = "hadprovider_code";
			String provider_name = URLDecoder.decode(pd.getString("provider_name"),"utf-8");
			String aa = URLEncoder.encode(provider_name, "UTF-8");
			return "redirect:/provider/apl_provider_manager?msg="+msg+"&provider_name="+URLEncoder.encode(aa, "UTF-8");//provider_name.toString();
		}
		if(pd.getString("flag").equals("del")){
			providerEditService.delete_provider(pd);
		}
	} catch (Exception e) {
		logger.error(e.toString(), e);
	}
	return "redirect:/provider/apl_provider_manager?delProvider=success";
}

/**
 * 批量删除供应商  delete_all_provider
 */
@RequestMapping(value = "/delete_all_provider")
@ResponseBody
public PageData delete_all_provider() {
	PageData pd = new PageData();
	try {
		pd = this.getPageData();
		String provider_code ="";
		Boolean flag=false;
		StringBuffer provider_name=new StringBuffer();
		StringBuffer del_provider=new StringBuffer();
		String del_all_provider = pd.getString("del_all_provider");
		if (null != del_all_provider && !"".equals(del_all_provider)) {
			String array_del_all_provider[] = del_all_provider.split(",");
			for(int i=0;i<array_del_all_provider.length;i++ ){
				String string = array_del_all_provider[i];
				provider_code= string.split("@")[0];
				pd.put("provider_code", provider_code);
				Integer p =providerEditService.find_provider_code_product(pd);
				if(providerEditService.find_provider_code_product(pd) >0  ){
					provider_name.append(string.split("@")[1]+",");
					flag=true;
				}else{
					del_provider.append(provider_code+",");
				}
			}
			if(flag){
				pd.put("provider", provider_name);
			}

			String[] delp =del_provider.toString().split(","); /// 没有使用供应商可以删除
			if(delp.length > 0){
				providerEditService.delete_all_provider(delp);
				pd.put("msg", "success");
			}
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

//导入数据

/**
 * 下载资产信息导入模版
 */
@RequestMapping(value = "/downAssetExcel")
public void downAssetExcel(HttpServletResponse response) throws Exception {
	FileDownload.fileDownload(response, PathUtil.getPath("0")+ File.separator + "ProviderInfo.xls", "ProviderInfo.xls");

}
/**
 * 打开上传EXCEL页面
 */
@RequestMapping(value = "/goUploadExcel")
public ModelAndView goUploadExcel() throws Exception {
	ModelAndView mv = this.getModelAndView();
	mv.setViewName("system/apl_provider/uploadexcel");
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
		String filePath = PathUtil.getPath("0"); // 文件上传路径
		String fileName = FileUpload.fileUp(file, filePath, "ProviderInfo"); // 执行上传
		List<PageData> listpd =(List) ProviderInfoExcelRead.providerInfoReadExcel(filePath, fileName, 2, 0, 0);
		if(listpd == null){
			mv.addObject("msg","error");
			mv.setViewName("system/apl_provider/uploadexcelresult");
			return mv ;
		}

		//存入数据库操作====================================== */
		/**
		 * var0 :编号  var1:供应商名称 var2 :供应商地址......等等
		 * 这个var就是readExcel方法返回对象中在方法中进行封装好的信息，也就是对应的信息
		 */
		for(int i=0;i<listpd.size(); i++){
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
					&&(listpd.get(i).getString("var11") == null || listpd.get(i).getString("var11") == "")){
				continue;
				/*}else if((listpd.get(i).getString("var1") == null || listpd.get(i).getString("var1") == "")
					||(listpd.get(i).getString("var2") == null || listpd.get(i).getString("var2") == "")
					||(listpd.get(i).getString("var3") == null || listpd.get(i).getString("var3") == "")){
				mv.addObject("msg","error_provider");
				mv.setViewName("system/apl_provider/uploadexcelresult");
				return mv ;*/
			}else{
				if(listpd.get(i).getString("var1")==null || listpd.get(i).getString("var1").equals("")){
					pd.put("provider_name", "不祥");
				}else{
					pd.put("provider_name", listpd.get(i).getString("var1"));
				}
				
				if(listpd.get(i).getString("var1")==null || listpd.get(i).getString("var1").equals("")){
					pd.put("provider_name", "不祥");
				}else{
					pd.put("provider_name", listpd.get(i).getString("var1"));
				}
				
				if(listpd.get(i).getString("var2")==null || listpd.get(i).getString("var2").equals("")){
					pd.put("provider_address", "不祥");
				}else{
					pd.put("provider_address", listpd.get(i).getString("var2"));
				}
				if(listpd.get(i).getString("var3")==null || listpd.get(i).getString("var3").equals("")){
					pd.put("provider_tel", "");
				}else{
					pd.put("provider_tel", listpd.get(i).getString("var3"));
				}
				
				if(listpd.get(i).getString("var4")==null || listpd.get(i).getString("var4").equals("")){
					pd.put("provider_fax", "");
				}else{
					pd.put("provider_fax", listpd.get(i).getString("var4"));
				}
				
				if(listpd.get(i).getString("var5")==null || listpd.get(i).getString("var5").equals("")){
					pd.put("provider_email", "");
				}else{
					pd.put("provider_email", listpd.get(i).getString("var5"));
				}
				
				if(listpd.get(i).getString("var6")==null || listpd.get(i).getString("var6").equals("")){
					pd.put("provider_conn_person", "");
				}else{
					pd.put("provider_conn_person", listpd.get(i).getString("var6"));
				}
				if(listpd.get(i).getString("var7")==null || listpd.get(i).getString("var7").equals("")){
					pd.put("provider_conn_tel", "");
				}else{
					pd.put("provider_conn_tel", listpd.get(i).getString("var7"));
				}
				if(listpd.get(i).getString("var8")==null || listpd.get(i).getString("var8").equals("")){
					pd.put("products_quality_standard", "");
				}else{
					pd.put("products_quality_standard", listpd.get(i).getString("var8"));
				}
				if(listpd.get(i).getString("var9")==null || listpd.get(i).getString("var9").equals("")){
					pd.put("provider_aptitude", "");
				}else{
					pd.put("provider_aptitude", listpd.get(i).getString("var9"));
				}
				if(listpd.get(i).getString("var10")==null || listpd.get(i).getString("var10").equals("")){
					pd.put("provider_comment", "");
				}else{
					pd.put("provider_comment", listpd.get(i).getString("var10"));
				}
				if(listpd.get(i).getString("var11")==null || listpd.get(i).getString("var11").equals("")){
					pd.put("provider_note", "");
				}else{
					pd.put("provider_note", listpd.get(i).getString("var11"));
				}
				if(providerEditService.isRepeat(pd)> 0){
					continue;
				}
				if(listpd.get(i).getString("var0")!=null && listpd.get(i).getString("var0")!=""){
					providerEditService.save_provider_insert(pd);
					mv.addObject("msg", "success");
				}
			}
		}
		/* 存入数据库操作====================================== */
	}
	mv.setViewName("system/apl_provider/uploadexcelresult");
	return mv;
}

}




















