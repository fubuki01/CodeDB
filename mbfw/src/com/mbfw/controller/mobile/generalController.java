package com.mbfw.controller.mobile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssetCheckService;
import com.mbfw.service.assets.AssetRegisterService;
import com.mbfw.service.assets.ProjectApplyService;
import com.mbfw.util.Const;
import com.mbfw.util.PageData;
import com.mbfw.util.PathUtil;

@Controller
@RequestMapping(value="/mobile/general")
public class generalController extends BaseController{
	/**
	 * 银行和部门信息的二级联动数据获取
	 */
	@Resource(name="projectApplyService")
	private ProjectApplyService projectApplyService;
	@RequestMapping(value = "/two_level_linkage", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object two_level_linkage() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String info = projectApplyService.institutionInfoSpecial();
		map.put("result", info);
		return map;
	}
	
	/**
	 * ajax图片异步获取
	 */
	@Resource(name="assetRegisterService")
	private AssetRegisterService assetRegisterService;
	@RequestMapping(value = "/ajax_img", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object ajax_img() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		
		PageData img = assetRegisterService.findImg(pd);
		if(img==null || !img.containsKey("asset_image"))
			map.put("result", "noimg");
		else{
			String path = PathUtil.getPath("2")+"/"+img.getString("asset_image").split("#")[0];
			map.put("result", img2str(new File(path)));
		}
		
		return map;
	}
	//将图片按base64编码转换为字符串
	private String img2str(File file) {
		byte[] data=null;
		FileInputStream fin=null;
		ByteArrayOutputStream bout=null;
		try{
			fin=new FileInputStream(file);
			bout=new ByteArrayOutputStream((int)file.length());
			byte[] buffer=new byte[1024];
			//用于表示读取的位置
			int len=-1;
			//开始读取文件
			while((len=fin.read(buffer))!=-1) {
				//从buffer的第0位置开始，读取至第len位置，结果写入bout
				bout.write(buffer,0,len);
			}
			data=bout.toByteArray();
			fin.close();
			bout.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return (new sun.misc.BASE64Encoder().encode(data));
	}
	
	/**
	 * 资产类别数据获取
	 */
	
	/**
	 * 盘点单获取
	 */
	@Resource(name="assetCheckService")
	private AssetCheckService assetCheckService;
	@RequestMapping(value = "/check_name", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Object check_name() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		PageData pd = this.getPageData();
		String resultjson = "[";
		User user = (User)SecurityUtils.getSubject().getSession().getAttribute(Const.SESSION_USER);
		
		int permission = user.getUser_Permission();
		if(permission == 1)
			pd.put("organization_name", "");
		else if(permission ==  2)
			pd.put("organization_name", user.getOrganization_name());
		else{
			resultjson = "[{\"value\":\"无\",\"text\":\"无\"},";
			pd.put("organization_name", "-1");
		}
		
		List<String> list = assetCheckService.mobile_getCheckName(pd);
		
		if(list!=null)
			for(String name : list)
				resultjson += "{\"value\":\""+name+"\",\"text\":\""+name+"\"},";
		
		resultjson = resultjson.substring(0, resultjson.length()-1)+"]";
		
		map.put("result", resultjson);
		return map;
	}
}
