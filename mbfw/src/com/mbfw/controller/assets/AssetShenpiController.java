package com.mbfw.controller.assets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mbfw.controller.base.BaseController;
import com.mbfw.entity.assets.AssetProjectProcessDetail;
import com.mbfw.entity.assets.PageOption;
import com.mbfw.entity.system.User;
import com.mbfw.service.assets.AssertShenpiService;
import com.mbfw.service.assets.AssetApproverNodeManageService;
import com.mbfw.util.PageData;

/**
 * 
 * @author scw
 *2017-09-21
 *function：关于审批过程的管理
 */
@Controller
@RequestMapping(value = "/asset")
public class AssetShenpiController extends BaseController {

	@Autowired
	private AssertShenpiService ass;  //处理项目流程的service
	
	@Autowired
	private AssetApproverNodeManageService aanms ; //处理审核节点的service
	
	//项目审批列表
	@RequestMapping(value = "/atp_approvalprojectlist")
	public ModelAndView shenpiList() throws Exception {
		ModelAndView mav  = this.getModelAndView();	
		
		PageData pd = this.getPageData();
		List<PageData> 	listProject = new ArrayList<PageData>();	
		 
		PageOption page = new PageOption(5, 1); //默认初始化一进来显示就是每页显示5条，当前页面为1
		//(1)获取是否有传过来需要显示的页面数，如果有，就设置相应的属性
		if(pd.getString("currentPage") != null){
			page.setCurrentPage(Integer.parseInt(pd.getString("currentPage")));
		}
		//(2)获取是否有传过来每个页面显示的条数，如果有，就设置相应的属性
		if(pd.getString("showCount") != null){
			page.setShowCount(Integer.parseInt(pd.getString("showCount")));
		}
		//------没有进行检索的处理-----
		if(pd.getString("retrieve_content") == null || pd.getString("retrieve_content") == ""){
			//(3)查询数据库中数据的总条数
			Integer totalnumber = ass.findTotalDataNumber();
			//(4)设置总的数据条数
			page.setTotalResult(totalnumber); 
			//(5)设置需要显示的数据的索引,其实这有个公式就是为了分页查询limit (currentPage-1)*showcount,showcount即可
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));  	
			//(6)查询数据库，返回对应条数的数据
			listProject = ass.findListProjectProcess(page);
		}
		//--------进行检索处理
		else{
			//(3)获取传送过来的进行检索的内容(这里要解码，因为如果是中文的话就会发生乱码问题)
			pd.put("retrieve_content", URLDecoder.decode(pd.getString("retrieve_content"), "utf-8"));
			//(4)设置分页中需要进行检索的姓名的内容，便于在分页中根据姓名获取内容
			page.setPd(pd);
			//(5)查询对应审核人员姓名的数据总条数
			Integer totalnumber = ass.findNumberBySearchName(page);
			//(6)设置总的数据条数
			page.setTotalResult(totalnumber);
			//(7)设置需要显示的数据的索引
			page.setCurrentResult((page.getCurrentPage()-1)*(page.getShowCount()));			
			//(8)查询数据库，返回对应检索姓名的数据
			listProject = ass.listSearchNameApprover(page);
		}
		//-----查询对应当前用户存在的审批项目（通过流程ID，来判断是否当前用户处于审批人员，是的话就显示这条数据条目）
		List<PageData> rightProjectData = new ArrayList<PageData>();
		//获取到当前用户的UserId，通过session获取
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute("sessionUser");
		Integer currentUserPermission = user.getUser_Permission();//获取当前用户权限
		if(currentUserPermission == 1){ //如果是总行的权限，那么就直接显示所有的内容即可，不需要只显示自己的内容
			mav.addObject("listProject",listProject);   //返回对应条数的数据
		}
		else{  //如果是非总行的权限，那么就显示对应用户的信息
			String currentUserId = user.getUSER_ID();//获取当前用户的userid
			//循环判断所有的项目，当前用户是否处于所选流程当中
			for(int i = 0 ; i < listProject.size() ; i++){
				List<String> usersID =  ass.findAllRightProjectByUserId((Integer)(listProject.get(i).get("process_ApprovalId")));
				if(usersID.contains(currentUserId)){ //表示存在该用户的信息
					rightProjectData.add(listProject.get(i));
				}
			}
			//重新设置对应的总条数(因为后面筛选了所符合对应用户的数据信息，这样的话总的 条数会发生变化)
			page.setTotalResult(rightProjectData.size());
			mav.addObject("listProject",rightProjectData);   //返回对应条数的数据
		}
				
		mav.addObject("page",page);                  //返回对应的分页的内容
		mav.setViewName("system/aproject_manager/apjm_ProjectProcess_list");		
		return mav;	
		
	}
	/**
	 * 点击审批按钮进行的详细项目进程的显示
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/atp_editcurrentprojectprocess")
	public ModelAndView editCurrentProjectProcessDetail() throws Exception{
		ModelAndView mav = this.getModelAndView();
		PageData pd  = this.getPageData();
		//查询点击的项目流程中的ID对应的项目ID，方便后面进行显示(联表查询)
		PageData current = ass.findCurrentProjectDetail(pd);	
		//根据查询到的内容，获取到对应的数据表中的内容
		PageData currentProjectContent = ass.findDifferentContent(current);
		
		mav.addObject("currentProjectContent",currentProjectContent);
		mav.setViewName("system/aproject_manager/apjm_ProjectProcess_edit");
		return mav;
	}
	
	/**
	 * 当点击同意或者不同意的时候通过ajax首先先进行判断是否当前的过程已经是完成的状态
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/atp_editjugdecurrentproject")
	public PageData editJugdeIfFinishProject() throws Exception{
		PageData pd = this.getPageData();
		//首先判断当前的这个审批过程是否已经是处于完成状态
		String finishStatus  = ass.findCurrentProjectIfFinish(pd);
		if("未完成".equals(finishStatus)){
			pd.put("finishstatus", "nofinished");
			//获取当前审批人的userid(从session中获取)
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(); //获取session对象
			User user = (User) session.getAttribute("sessionUser"); //获取用户对象
			pd.put("detail_ApproverUserID", user.getUSER_ID());
			//查看当前的用户是否已经处理过该项目过程，即判断项目明细表中，是否有该用户的操作信息，如果返回的条目数为0，那么表示还未进行过处理
			Integer currentNodeId = ass.findCurrentNodeId(pd); //获取到当前处理的节点ID（因为可能存在同一个人在两个审批节点中的情况，但是这样其实不是重复审批的，所以需要判断）	
			pd.put("detail_CurrentNodeId", currentNodeId);
			Integer number = ass.findCurrentUserStatus(pd);					
			if(number == 0){  //没有操作过
				pd.put("aleradoption", "no");
			}
			else{ //已经操作过，则不再允许重复操作
				pd.put("aleradoption", "yes");
			}
		}
		else{
			pd.put("finishstatus", "finished");
		}
		return pd;
	}
	
	
	/**
	 * 在进行审核流程的时候，点击的同意按钮
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/atp_editprojectapprovalagree")
	public String editProjectApprovalAgreeOption( ) throws Exception{
		PageData pd = this.getPageData();
		//首先当有人审核了，那么就将项目数据库中的审批状态由已提交变为审核中
		Integer currentProjecttId = Integer.parseInt (pd.getString("project_Id"));
		String currentProcessType = pd.getString("process_Type"); //获取审批的类型
		ass.editProjectManagerStatus(currentProjecttId , currentProcessType);
		
		//(1)将该审核人具体的操作插入到项目流程细节表中
		//1:得到该操作的操作时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		pd.put("detail_ApprovalTime", df.format(new Date())); 
		//2:获取当前审批人的userid(从session中获取)
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(); //获取session对象
		User user = (User) session.getAttribute("sessionUser"); //获取用户对象
		pd.put("detail_ApproverUserID", user.getUSER_ID());
		pd.put("detail_OptionResult", "通过");//加入审批结果
		//3:获取进行操作审批项目的当前处理节点的ID
		Integer currentNodeId = ass.findCurrentNodeId(pd);
		pd.put("detail_CurrentNodeId", currentNodeId);
		//4:将数据插入到项目流程细节表中
		ass.saveProjectProceeDetail(pd);
		
		//(2)查询当前的项目流程中，在项目过程表中，通过的人数，并且+1(因为当前的操作也是通过)
		Integer passNumber = ass.findProjectDescriptionPassNumber(pd) +1;
		//(3)获取当前项目流程，当前审批流程，当前审批节点的所有信息
		PageData approvalProcessInfo = ass.findRelativeInfo(pd);
		//(4)比较当前节点通过人数和当前审批节点需要通过的最小人数的比较
		 Integer nodeMinNumber =  (Integer) approvalProcessInfo.get("node_PassNumber");  //这里千万不要用getstring来转，这会报错
		 if( passNumber < nodeMinNumber){  //处于还是当前流程节点的状态
			pd.put("description_Time", df.format(new Date())); //当前操作的时间
			pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_Name")+"-审批执行中;审批节点-"+approvalProcessInfo.getString("node_Name")+"-不变，通过人数+1"); //当前的项目流程内容
		    //在项目流程描述表中更新数据，主要就是更新当前的内容字段
			ass.saveProjectProcessDescription(pd);
			//更新项目过程表对应的过程信息(这个时候其实就是更新通过的人数字段)
			pd.put("description_Passnumber", passNumber);//当前通过的人数
			ass.editProjectProcess(pd);
		}
		else{ //大于或者相等的话，就判断是否又后续节点，有的话就进入下一个节点，没用的话，就为项目流程终止
			pd.put("description_Time", df.format(new Date())); //当前操作的时间
			//判断是否还有后续节点
			PageData nextApprovalProcessInfo = ass.findIfNextApprovalNode(pd);
			//判断得到的后续信息是否为空，如果为空就表示当前节点已经是流程的最后一个节点
			if(nextApprovalProcessInfo == null){
				pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_Name")+"-执行完毕;审批节点-"+approvalProcessInfo.getString("node_Name")+"-为最后一个节点，该审批过程结束"); //当前的项目流程内容
				//在项目流程描述表中更新数据，主要就是更新当前的内容字段
				ass.saveProjectProcessDescription(pd);	
				//更新项目过程表对应的过程信息(这个时候其实就是更新通过的人数字段,过程完成状态)
				pd.put("description_Passnumber", passNumber);//当前通过的人数
				ass.editProjectProcessStatus(pd);
				//根据项目ID，更新不同表中的流程状态(通过项目审批ID和审批类型)为审批完成并且通过
				ass.editAprojectManagerStatus(currentProjecttId , currentProcessType , "审批完成(通过)");
			}
			else{ //还存在后续节点
				pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_Name")+"-审批执行中;审批节点-"+approvalProcessInfo.getString("node_Name")+"-审批完毕，进入后续节点-"+nextApprovalProcessInfo.getString("node_Name")+"-进行审批，通过人数变为0"); //当前的项目流程内容
				//在项目流程描述表中更新数据，主要就是更新当前的内容字段
				ass.saveProjectProcessDescription(pd);	
				//更新项目过程表对应的过程信息(这个时候其实就是更新通过的人数字段为0,当前的审批节点名字，审批的第几个次序字段)
				nextApprovalProcessInfo.put("current_NodeName", nextApprovalProcessInfo.getString("node_Name")); //修改当前的节点名称
				ass.editProjectProcessCurrentNode(nextApprovalProcessInfo);
			}			
		}
		return "redirect:/asset/atp_approvalprojectlist?editresult=success";
	}
	
	/**
	 * 在进行审核流程的时候，点击的不同意按钮
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/atp_editprojectapprovaldisagree")
	public String editProjectApprovalDisAgreeOption() throws Exception{
		PageData pd = this.getPageData();
		//首先当有人审核了，那么就将项目数据库中的审批状态由已提交变为审核中
		Integer currentProjecttId = Integer.parseInt (pd.getString("project_Id"));
		String currentProcessType = pd.getString("process_Type"); //获取审批的类型
		ass.editProjectManagerStatus(currentProjecttId , currentProcessType);
		
		//(1)将该审核人具体的操作插入到项目流程细节表中
		//1:得到该操作的操作时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		pd.put("detail_ApprovalTime", df.format(new Date())); 
		//2:获取当前审批人的userid(从session中获取)
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(); //获取session对象
		User user = (User) session.getAttribute("sessionUser"); //获取用户对象
		pd.put("detail_ApproverUserID", user.getUSER_ID());
		pd.put("detail_OptionResult", "不通过");//加入审批结果
		//3:获取进行操作审批项目的当前处理节点的ID
		Integer NodeId = ass.findCurrentNodeId(pd);
		pd.put("detail_CurrentNodeId", NodeId);
		//4:将数据插入到项目流程细节表中
		ass.saveProjectProceeDetail(pd);
		//(2)查询当前的项目流程中，在项目过程表中，不通过的人数，并且+1(因为当前的操作也是不通过)
		Integer refuseNumber = ass.findProjectDescriptionRefuseNumber(pd) +1;
		//(3)获取当前项目流程，当前审批流程，当前审批节点的所有信息
		PageData approvalProcessInfo = ass.findRelativeInfo(pd);
		//(4)比较当前节点不通过人数和当前审批节点需要通过的最小人数的比较
		//获取到当前节点最少需要通过的人数
		Integer nodeMinNumber =  (Integer) approvalProcessInfo.get("node_PassNumber");  //这里千万不要用getstring来转，这会报错
		//获取当前节点中一共的总人数
		Integer totalNumber = (Integer) approvalProcessInfo.get("node_TotalNumber");
		//进行比较不通过的人数的情况(二种)
		if(nodeMinNumber <= (totalNumber -refuseNumber )){  //最小通过人数 <= 总人数-拒绝人数，说明还有可能该流程同意，该流程不终止
			//添加信息到项目描述表中
			pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_ApprovalName")+"-审批执行中;审批节点-"+approvalProcessInfo.getString("node_Name")+"-不变，不通过人数+1"); //当前的项目流程内容
			pd.put("description_Time", df.format(new Date())); //当前操作的时间
			//在项目流程描述表中更新数据，主要就是更新当前的内容字段
			ass.saveProjectProcessDescription(pd);
			//更新项目过程表对应的过程信息(这个时候其实就是更新不通过的人数字段)
			pd.put("description_Refusenumber", refuseNumber);//当前不通过的人数
			ass.editProjectProcessRefuseNumber(pd);
		}
		else{ // 最小通过 > 总人数-拒绝人数,这时候就进行判断当前节点是否可以打回，如果不能，则整个流程结束
			//获取当前审批节点是否能够被打回,on为可以打回
			String backResult = approvalProcessInfo.getString("processdetail_Back");
			pd.put("description_Time", df.format(new Date())); //当前操作的时间
			Integer projectId = Integer.parseInt(pd.getString("projectprocess_Id")); //获取项目流程表的序号ID
			if("on".equals(backResult)){ //可以打回
				//(1)判断当前节点顺序是处于第几级
				Integer currentNodeOrder = (Integer) approvalProcessInfo.get("current_NodeOrder");
				if(currentNodeOrder == 1){  //表示的是第一级级别，那么就相当于重新开始
					pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_ApprovalName")+"-由于总人数-不通过人数超过最小通过人数，并且审批节点-"+approvalProcessInfo.getString("node_Name")+"-属于第一级能够打回，则重新进行当前审批节点审批处理"); //当前的项目流程内容
					//在项目流程描述表中插入数据，主要就是添加一个内容
					ass.saveProjectProcessDescription(pd);	
					//根据项目进程ID，更新项目过程表中的通过和不通过的人数信息全部为0，还有审批状态为未完成				
					ass.editCurrentProjectProcessContent(pd);
					//根据项目进程ID，删除所有在项目明细表中的数据
					ass.editDeletProjectProcessDeatil(projectId);	
					//因为又处于第一级审核并且处理审批的人数重新都设置为0了，那么这时候需要将对应项目中的审批状态设置为已提交，这样的话，就可以方便各个项目进行修改
					ass.editAgainDifferenProjectStatus(currentProjecttId , currentProcessType);
				}
				else{  //表示要返回上一级节点重新进行处理
					pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_ApprovalName")+"-由于总人数-不通过人数超过最小通过人数，并且审批节点-"+approvalProcessInfo.getString("node_Name")+"-属于第"+currentNodeOrder+"级能够打回，则重新进行当前审批节点的前一级审批节点处理"); //当前的项目流程内容
					//在项目流程描述表中插入数据，主要就是添加一个数据
					ass.saveProjectProcessDescription(pd);
					//根据当前审批节点的ID，审批人员的userid，删除当前审批节点中审批人员的操作信息
					//获取当前审批ID
					Integer currentNodeId = (Integer) approvalProcessInfo.get("node_Id");
					//查询当前节点对应的UserId信息
					List<String> approverUserIds = aanms.findAllUserIdByNodeId(currentNodeId);
					//删除当前审批节点中对应审批用户的操作信息
					PageData deleteDetail = new PageData();
					deleteDetail.put("detail_ProcessId", projectId) ; //对应的项目流程ID
					for(int i =0 ; i< approverUserIds.size() ; i++){
						deleteDetail.put("detail_ApproverUserID", approverUserIds.get(i)); //审核人员ID				
						ass.editProjectProcessDetailByUserID(deleteDetail);
					}
					//获取上一级的审批顺序
					Integer lastNodeOrder = currentNodeOrder - 1;
					//根据项目流程ID，更新项目流程的审批顺序的字段和通过人数和不通过人数信息，审批是否完成
					ass.editProjectProcessLastOrderInfo(projectId);
					//获取上一级审批的相关的内容信息
					PageData lastPageDate  = ass.findRelativeInfo(pd);
					//根据项目流程ID，更新当前项目过程ID中的审批节点的名字
					PageData updateNodepd = new PageData();
					updateNodepd.put("current_NodeName", lastPageDate.getString("node_Name"));// 获取当前的节点名字
					updateNodepd.put("projectprocess_Id", projectId); //项目过程ID
					ass.editProjectProcessNodeName(updateNodepd);
					//将当前修改了的审批节点中的审批人员的数据从项目明细表中进行删除，因为这个节点的人员之前已经有操作信息
					//获取当前审批ID
					currentNodeId = (Integer) lastPageDate.get("node_Id");
					//查询当前节点对应的UserId信息
					approverUserIds = aanms.findAllUserIdByNodeId(currentNodeId);
					//删除当前审批节点中对应审批用户的操作信息
					deleteDetail.put("detail_ProcessId", projectId) ; //对应的项目流程ID
					for(int i =0 ; i< approverUserIds.size() ; i++){
						deleteDetail.put("detail_ApproverUserID", approverUserIds.get(i)); //审核人员ID				
						ass.editProjectProcessDetailByUserID(deleteDetail);
					}
					//最终完成处理
					//如果返回的上一级是第一级，那么还需要进行下面的一个处理
					if(currentNodeOrder == 2){
					//因为又处于第一级审核并且处理审批的人数重新都设置为0了，那么这时候需要将对应项目中的审批状态设置为已提交，这样的话，就可以方便各个项目进行修改
					ass.editAgainDifferenProjectStatus(currentProjecttId , currentProcessType);
					}
				}			
			}
			else{ //不能够被打回，则将该项目流程直接修改完成状态
				pd.put("description_Content", "当前审批流程-"+approvalProcessInfo.getString("process_ApprovalName")+"-由于总人数-不通过人数超过最小通过人数，并且审批节点-"+approvalProcessInfo.getString("node_Name")+"-无法被打回，则审批过程结束"); //当前的项目流程内容
				//在项目流程描述表中插入数据，主要就是添加内容
				ass.saveProjectProcessDescription(pd);	
				//更新项目过程表对应的过程信息(这个时候其实就是更新不通过的人数字段,过程是否完成状态)
				pd.put("description_Refusenumber",refuseNumber );//当前不通过的人数
				ass.editProjectProcessStatusFinished(pd);
				//根据项目ID，更新不同表中的流程状态(通过项目审批ID和审批类型)
				ass.editAprojectManagerStatus(currentProjecttId , currentProcessType , "审批完成(不通过)");
			}		
		}
		return "redirect:/asset/atp_approvalprojectlist?editresult=success";
	}

	
	/*@RequestMapping(value = "/atp_downloadfile")
	public void downLoadAlreadyFile(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		PageData pd = this.getPageData();
		//获取文件路径
		String urlFile = URLDecoder.decode(pd.getString("fileurl"), "utf-8");
		 //获取输入流  
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(urlFile)));  
        //假如以中文名下载的话  
        String filename = "哈哈";  
        //转码，免得文件名中文乱码  
        filename = URLEncoder.encode(filename,"GBK");  
        //设置文件下载头  
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);    
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型    
        response.setContentType("multipart/form-data");   
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());  
        int len = 0;  
        while((len = bis.read()) != -1){  
            out.write(len);  
            out.flush();  
        }  
        out.close();
        pd.put("result", "success");
        return pd;
      }*/
	
	/**
	 * 处理点击审批操作中的文件下载的处理操作
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/atp_downloadfile")
	public ResponseEntity<byte[]> download() throws IOException {    
		PageData pd = this.getPageData();
		//获取文件路径
		String urlFile = URLDecoder.decode(pd.getString("fileurl"), "utf-8");  
        File file=new File(urlFile);  
        HttpHeaders headers = new HttpHeaders();
        //获取下载名（因为之前通过了@进行分割）
        String fileName = urlFile.split("@")[1];
        fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", fileName);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
                                          headers, HttpStatus.CREATED);    
    }    
	
	/**
	 * 判断当前点击的审批项目，当前用户是否是处于当前节点的审核人员中
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/atp_editjugdepermission")	
	@ResponseBody
	public String editJudgeIfPremission() throws Exception{
		PageData pd = this.getPageData();
		//(1)获取到对应审批流程的数据内容
		pd= ass.findCurrentProjectDetail(pd);
		PageData resultContent = new PageData();
		resultContent.put("processdetail_Nodeorder", (Integer)pd.get("current_NodeOrder"));//次序
		resultContent.put("processdetail_Id", (Integer)pd.get("process_ApprovalId"));//当前流程号id
		//(2)根据当前的审批流程的ID和当前的审批次序，获取到所有的审批人员的userid信息
		List<String> usersId = ass.findAllCurrentNodePeopleInfo(resultContent); 
		//(3)判断当前的用户是否在当前审批节点中
		//获取当前审批人的userid(从session中获取)
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(); //获取session对象
		User user = (User) session.getAttribute("sessionUser"); //获取用户对象
		String currentUserId = user.getUSER_ID(); //获取当前用户的userid 	
		for(int i = 0 ; i< usersId.size() ; i++){  //判断当前用户是否属于当前审批节点中
			if(currentUserId.equals(usersId.get(i))){ //如果用户ID存在于审批人员中		
				return "YES";
			}
		}
		return "NO"; //如果都不包含当前用户，那么返回"NO"
	}
}
