package com.mbfw.controller.assets;

import java.io.File;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.MertialClassify;
import com.mbfw.service.assets.MertialClassifyService;
import com.mbfw.util.ClassExcelRead;
import com.mbfw.util.Const;
import com.mbfw.util.FileDownload;
import com.mbfw.util.FileUpload;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

import net.sf.json.JSONArray;

/**
 * 耗材资产自定义类别Controller
 * 
 * @author Wyd
 *
 */
@Controller
@RequestMapping(value = "/asset")
public class MertialClassifyController extends BaseController {
	@Autowired
	private MertialClassifyService mertialClassifyService;

	// 自定义耗材类别
	@RequestMapping(value = "/setMertialClass")
	public ModelAndView setMertialClass() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//获得增删改查的具体权限
		Map<String,String> QX = getHC();
		mv.addObject("QX", QX);
		List<PageData> mertialClassify = mertialClassifyService.listAllPoint(pd);
		String treeNodesJson = JSONArray.fromObject(mertialClassify).toString();
		mv.addObject("addPoint", this.getPageData().getString("addPoint"));// 传送增加的结果
		mv.addObject("delectresult", this.getPageData().getString("delectResult"));// 传送删除的结果
		mv.addObject("addChildPoint", this.getPageData().getString("addChildPoint"));// 传送增加子级的结果
		mv.addObject("zTreeNodes", treeNodesJson);
		mv.setViewName("system/atype_set/atp_setMertialAccessts");
		return mv;
	}

	// 添加节点
	@RequestMapping(value = "/addMertialPoint")
	public String addPoint() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String message = "";
		//判断节点是否存在
		PageData pdf = mertialClassifyService.findByClass(pd);
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
			List<PageData> pds = mertialClassifyService.listAllParentPoint(pd);
			int length = pds.size() + 1;
			// 如果长度小于99让其添加
			if (length < 1000) {
				for (PageData pageData : pds) {
					String orderIndex = pageData.getString("orderSort");
					if(li.contains(orderIndex)){
						li.remove(orderIndex);
					}
				}
				String order = li.get(0);
				pd.put("orderSort", order);
				mertialClassifyService.addPoint(pd);
				message = "success";
			} else {
				message = "fail";
			}
		}
		
		return "redirect:/asset/setMertialClass?addPoint=" + message;
		/*
		 * mv.setViewName("system/atype_set/atp_setAccessts"); return mv;
		 */
	}

	// 添加孩子节点
	@RequestMapping(value = "/addMertialChildPoint")
	public String addChildPoint(MertialClassify mertialClassify) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//创建传递消息的对象
		String message = ""; 
		pd.put("mertialClassify", mertialClassify);
		//判断节点是否存在
		PageData pdf = mertialClassifyService.findByMertialName(pd);
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
			
			// 查询父节点对应的所有孩子节点
			List<PageData> pds = mertialClassifyService.listChildPoint(pd);
			int length = pds.size() + 1;
			// 判断父节点下的孩子节点数量是否超过限制
			if (length < 1000) {
				for (PageData pageData : pds) {
					String orderIndex = pageData.getString("orderSort");
					if(li.contains(orderIndex)){
						li.remove(orderIndex);
					}
				}
				String order = li.get(0);
				pd.put("orderSort", order);
				// 查询父节点的序列号
				PageData singPd = mertialClassifyService.getParentPoint(pd);
				String parentSort = singPd.getString("orderSort");
				// 设置parentSort
				pd.put("parentSort", parentSort);
				// 设置coding
				String coding = parentSort + "-" + order;
				pd.put("coding", coding);
				mertialClassifyService.addChildPoint(pd);
				message = "success";
			} else {
				// 超过数量限制 创建失败
				message = "fail";
			}

		}
		return "redirect:/asset/setMertialClass?addChildPoint=" + message;
		/*
		 * mv.setViewName("system/atype_set/atp_setAccessts"); return mv;
		 */
	}

	// 删除节点
	@RequestMapping(value = "/delectMertialPoint")
	public String delectPoint() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();;
		boolean b = false;
		String message = "";
		pd.put("name", URLDecoder.decode(pd.getString("names"), "utf-8"));
		String parent = pd.getString("parent");
		if(parent.equals("null")){
			b = mertialClassifyService.SearchPointByFu(pd);
		}else{
			b = mertialClassifyService.SearchPointByZi(pd);
		}
		if (!b) {
			mertialClassifyService.DelectPoint(pd);
			message = "success";
		} else {
			message = "fail";
		}
		return "redirect:/asset/setMertialClass?delectResult=" + message;
		/*
		 * mv.setViewName("system/atype_set/atp_setAccessts"); return mv;
		 */
	}
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/uploadMertialClassExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/atype_set/uploadMertialClassExcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downMertialClassExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
//		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "耗材类别导入模板.xls", "耗材类别导入模板.xls");
		FileDownload.fileDownload(response, PathUtil.getPath("0")+File.separator+"耗材类别导入模板.xls", "耗材类别导入模板.xls");
	}
	
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value = "/readMertialClassExcel")
	public ModelAndView readMertialClassExcel(@RequestParam(value = "excel", required = false) MultipartFile file) throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		String mg = "";
		if (null != file && !file.isEmpty()) {
//			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE; // 文件上传路径
			String filePath = PathUtil.getPath("0"); // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "mertialClass"); // 执行上传

			List<PageData> listPd = (List) ClassExcelRead.mertialClassReadExcel(filePath, fileName, 2, 0, 0); // 执行读EXCEL操作,读出的数据导入List 1:从第2行开始；0:从第A列开始；0:第0个sheet
			
			//判断Excl是否符合规范传过来了内容
			if(listPd == null){
				mv.addObject("msg", "error");
				mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
				return mv;
			}
			//符合规范则进行后面的操作
			
			/* 存入数据库操作====================================== */
			pd.put("open", "false"); // 数据库的一些字段 暂时没有使用
			pd.put("isuse", "false"); // 数据库的一些字段 暂时没有使用
		

			/**
			 * var0 :编号 var1 :资产类别 var2 :资产名称......等等
			 * 这个var就是readExcel方法返回对象中在方法中进行封装好的信息，也就是对应的信息
			 */
			for (int i = 0; i < listPd.size(); i++) {
				//判断资产类别是否为空，如果为空，后面的就不进行添加，直接提示用户
				if(listPd.get(i).getString("var1") == null || listPd.get(i).getString("var1") == ""){					
					mv.addObject("msg", "error");
					mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
					return mv;
				}else if(listPd.get(i).getString("var2") == null || listPd.get(i).getString("var2") == ""){
					pd.put("name", listPd.get(i).getString("var1"));//存储资产类别
					mg = addParent(pd);
					if(mg.equals("exist")){//查询资产类别是否存在
						continue;
					}else if(mg.equals("fail")){
						mv.addObject("msg", "toolong");
						mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
						return mv;
					}
				}else {
					pd.remove("name");
					pd.put("name", listPd.get(i).getString("var1"));//存储资产类别
					PageData ac = mertialClassifyService.findByClass(pd);//判断资产类别是否存在
					pd.remove("name");//清除父的名字
					pd.put("name", listPd.get(i).getString("var2"));//存储资产名称
					if(ac!=null){//父存在
						//存在获取对应的资产编码
						int number = (int) ac.get("number");
						//判断对应的资产名称是否存在
						pd.put("pId", number);//加入父编码
						mg = addChild(pd);
						
						if(mg.equals("exist")){//父子都存在
							continue;
						}else if(mg=="fail"){
							mv.addObject("msg", "toolong");
							mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
							return mv;
						}
						
					}else{//父子都不存在
						pd.remove("name");
						pd.remove("pId");
						pd.put("name", listPd.get(i).getString("var1"));
						mg = addParent(pd);
						if(mg=="fail"){
							mv.addObject("msg", "toolong");
							mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
							return mv;
						}
						//获取父编码
						PageData acp = mertialClassifyService.findByClass(pd);
						int pnm = (int) acp.get("number");
						pd.remove("name");
						pd.put("name", listPd.get(i).getString("var2"));
						pd.put("pId", pnm);
						mg = addChild(pd);
						if(mg=="fail"){
							mv.addObject("msg", "toolong");
							mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
							return mv;
						}
					}
				}
				
			}
			/* 存入数据库操作====================================== */
			mv.addObject("msg", "success");
		}
		mv.addObject("msg", "success");
		mv.setViewName("system/atype_set/uploadMertialClassExcelResult");
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
		PageData pdf = mertialClassifyService.findByClass(pd);
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
			List<PageData> pds = mertialClassifyService.listAllParentPoint(pd);
			int length = pds.size() + 1;
			// 如果长度小于99让其添加
			if (length < 1000) {
				for (PageData pageData : pds) {
					String orderIndex = pageData.getString("orderSort");
					if(li.contains(orderIndex)){
						li.remove(orderIndex);
					}
				}
				String order = li.get(0);
				pd.put("orderSort", order);
				mertialClassifyService.addPoint(pd);
				message = "success";
			} else {
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
		PageData pdf = mertialClassifyService.findByMertialName(pd);
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
			
			// 查询父节点对应的所有孩子节点
			List<PageData> pds = mertialClassifyService.listChildPoint(pd);
			int length = pds.size() + 1;
			// 判断父节点下的孩子节点数量是否超过限制
			if (length < 1000) {
				for (PageData pageData : pds) {
					String orderIndex = pageData.getString("orderSort");
					if(li.contains(orderIndex)){
						li.remove(orderIndex);
					}
				}
				String order = li.get(0);
				pd.put("orderSort", order);
				// 查询父节点的序列号
				PageData singPd = mertialClassifyService.getParentPoint(pd);
				String parentSort = singPd.getString("orderSort");
				// 设置parentSort
				pd.put("parentSort", parentSort);
				// 设置coding
				String coding = parentSort + "-" + order;
				pd.put("coding", coding);
				mertialClassifyService.addChildPoint(pd);
				message = "success";
			} else {
				// 超过数量限制 创建失败
				message = "fail";
			}

		}
		return message;
	}
	
	
	
	/* ===============================权限================================== */
	public Map<String, String> getHC() {
		Subject currentUser = SecurityUtils.getSubject(); // shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
}
