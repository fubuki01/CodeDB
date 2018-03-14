<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  taglib  uri="http://java.sun.com/jsp/jstl/functions"   prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">

<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目立项增加界面</title>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>

<style type="text/css">
	.project_apply_add td{
		vertical-align: middle;	
	}
</style>

</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<form action="" method="post" name="projectprocessform" id="projectprocessform" >
					<!-- 隐藏项目的ID -->
					<input type="hidden" name="detail_ProcessId" id="detail_ProcessId" value="${currentProjectContent.projectprocess_Id}">
					<input type="hidden" name="projectprocess_Id" id="projectprocess_Id" value="${currentProjectContent.projectprocess_Id}">
					<input type="hidden" name="description_Id" id="description_Id" value="${currentProjectContent.projectprocess_Id}">
					<input type="hidden" name="project_Id" id="project_Id" value="${currentProjectContent.project_Id}">
					<!-- 隐藏审批项目的类型，比如项目立项，报废申请，还是耗材申请和资产调入与申请 -->
					<input type="hidden" name="process_Type" id="process_Type" value="${currentProjectContent.process_Type}">
					<table id="table_report" class="table table-striped table-bordered table-hover">
						<thead>
							<c:if test="${currentProjectContent.process_Type == '项目立项'}"> <!-- 如果是项目立项那么就显示下面的这段内容 -->
							<tr>
								<th>项目名称</th>
								<td>
									<input type="text" id="apply_name" name="apply_name"
										value="${currentProjectContent.apply_name}" class="col-xs-10 col-sm-5" disabled="disabled">								
								</td>

								<th>申请公司</th>
								<td>
									<input type="text" id="apply_company" name="apply_company" disabled="disabled"
									value="${currentProjectContent.apply_company}" class="col-xs-10 col-sm-5">
								</td>

								<th>申请部门</th>
								<td>
									<input type="text" id="apply_dept" name="apply_dept" disabled="disabled"
									value="${currentProjectContent.apply_dept}" class="col-xs-10 col-sm-5">
								</td>
							</tr>
							<tr>
								<th>申请人</th>
								<td>
									<input type="text" id="apply_person" name="apply_person" disabled="disabled"
									value="${currentProjectContent.apply_person}" class="col-xs-10 col-sm-5">
								</td>

								<th>申请时间</th>
								<td>
									<input type="date" id="apply_time" name="apply_time" disabled="disabled"
									value="${currentProjectContent.apply_time}" class="form-control hasDatepicker"></td>

								<th>申请流程名称</th>
								<td>
									<input type="text" id="apply_procedure" name="apply_procedure" disabled="disabled"
									value="${currentProjectContent.apply_procedure}" class="form-control hasDatepicker">
								</td>
							</tr>
							<tr>
								<th>设备名称</th>
								<td><input type="text" id="device_name" name="device_name" disabled="disabled"
									value="${currentProjectContent.device_name}" class="col-xs-10 col-sm-5"></td>

								<th>型号</th>
								<td>
									<input type="text" id="device_model" name="device_model"  disabled="disabled"
									value="${currentProjectContent.device_model}" class="col-xs-10 col-sm-5"></td>

								<th>市场价格(元)</th>
								<td>
									<input type="text" id="device_price" name="device_price" disabled="disabled"
									value="${currentProjectContent.device_price}" class="col-xs-10 col-sm-5"></td>
							</tr>
							<tr>
								<th>数量(个)</th>
								<td>
									<input type="text" id="device_number" name="device_number"  disabled="disabled"
									value="${currentProjectContent.device_number}" class="col-xs-10 col-sm-5">
								</td>

								<th>用途</th>
								<td><input type="text" id="device_purpose" disabled="disabled"
									name="device_purpose" value="${currentProjectContent.device_purpose}" 
									class="col-xs-10 col-sm-5"></td>

								<th>预计使用年限(年)</th>
								<td><input type="text" id="device_use_years" disabled="disabled"
									name="device_use_years" value="${currentProjectContent.device_use_years}" 
									class="col-xs-10 col-sm-5"></td>							
							</tr>

							<tr>
								<th>查看申请报告</th>
								<td>
								<!-- 判断是否有文件 -->
								<c:choose>
									<c:when test="${currentProjectContent.apply_report_address != null || currentProjectContent.apply_report_address != ''}">
										<span>${fn:split(currentProjectContent.apply_report_address,"@")[1]}</span>							
										<button type="button" class="btn btn-small btn-success" onclick="downFile('${currentProjectContent.apply_report_address}');">下载</button>														
									</c:when>
									<c:otherwise>
										<span><font style="color: red;font-size: 16px;">当前项目没有上传文件</font></span>																	
									</c:otherwise>
								</c:choose>							
 								</td>

								<th>申请原因</th>
								<td colspan="3">
								<textarea id="apply_reason" name="apply_reason" rows="4" cols="50" 
									style="width: 98%" disabled="disabled">
									${currentProjectContent.apply_reason}
								</textarea>
								</td>																					
							</tr>
						</c:if>
						
						<!-- 如果是耗材申请那么就显示下面的这段内容 -->
						<c:if test="${currentProjectContent.process_Type == '耗材申请'}"> 
							<tr>
								<th>耗材编码</th>
								<td>
									<input type="text" id="supplies_model" name="supplies_model"
										value="${currentProjectContent.supplies_model}" class="col-xs-10 col-sm-5" disabled="disabled">								
								</td>

								<th>耗材名称</th>
								<td>
									<input type="text" id="supplies_name" name="supplies_name" disabled="disabled"
									value="${currentProjectContent.supplies_name}" class="col-xs-10 col-sm-5">
								</td>

								<th>实际数量</th>
								<td>
									<input type="text" id="quantity" name="quantity" disabled="disabled"
									value="${currentProjectContent.quantity}" class="col-xs-10 col-sm-5">
								</td>
							</tr>
							<tr>
								<th>用途</th>
								<td>
									<input type="text" id="supplies_use" name="supplies_use" disabled="disabled"
									value="${currentProjectContent.supplies_use}" class="col-xs-10 col-sm-5">
								</td>

								<th>品牌</th>
								<td>
									<input type="text" id="brand" name="brand" disabled="disabled"
									value="${currentProjectContent.brand}" class="form-control hasDatepicker"></td>

								<th>市场报价</th>
								<td>
									<input type="text" id="Market_quotes" name="Market_quotes" disabled="disabled"
									value="${currentProjectContent.Market_quotes}" class="form-control hasDatepicker">
								</td>
							</tr>
							<tr>
								<th>耗材类型</th>
								<td><input type="text" id="supplies_type" name="supplies_type" disabled="disabled"
									value="${currentProjectContent.supplies_type}" class="col-xs-10 col-sm-5"></td>

								<th>报价依据</th>
								<td>
									<input type="text" id="quote_basis" name="quote_basis"  disabled="disabled"
									value="${currentProjectContent.quote_basis}" class="col-xs-10 col-sm-5"></td>

								<th>资金来源</th>
								<td>
									<input type="text" id="sour_of_funds" name="sour_of_funds" disabled="disabled"
									value="${currentProjectContent.sour_of_funds}" class="col-xs-10 col-sm-5"></td>
							</tr>
							<tr>
								<th>供应商</th>
								<td>
									<input type="text" id="supplier" name="supplier"  disabled="disabled"
									value="${currentProjectContent.supplier}" class="col-xs-10 col-sm-5">
								</td>
								
								<th>经办人</th>
								<td>
									<input type="text" id="manager" name="manager"  disabled="disabled"
									value="${currentProjectContent.manager}" class="col-xs-10 col-sm-5">
								</td>

								<th>备注</th>
								<td><input type="text" id="remarks" name="remarks" disabled="disabled"
									 value="${currentProjectContent.remarks}" 
									class="col-xs-10 col-sm-5"></td>							
							</tr>
							<tr>
								<th>申请公司</th>
								<td>
									<input type="text" id="company_apply" name="company_apply"  disabled="disabled"
									value="${currentProjectContent.company_apply}" class="col-xs-10 col-sm-5">
								</td>
								
								<th>申请部门</th>
								<td>
									<input type="text" id="applicant_sector" name="applicant_sector"  disabled="disabled"
									value="${currentProjectContent.applicant_sector}" class="col-xs-10 col-sm-5">
								</td>
								
								<th>申请时间</th>
								<td>
									<input type="text" id="time_apply" name="time_apply"  disabled="disabled"
									value="${currentProjectContent.time_apply}" class="col-xs-10 col-sm-5">
								</td>						
							</tr>
						</c:if>
						
						<!-- 如果是报废申请的审批 -->
						<c:if test="${currentProjectContent.process_Type == '报废申请'}">
						   	<tr>
								<th>资产编码</th>
								<td><input type="text" id="asset_code" name="asset_code" disabled="disabled"
									value="${currentProjectContent.asset_code}" class="col-xs-10 col-sm-5"></td>

								<th>使用部门</th>
								<td>
									<input type="text" id="asset_use_dept" name="asset_use_dept"  disabled="disabled"
									value="${currentProjectContent.asset_use_dept}" class="col-xs-10 col-sm-5"></td>

								<th>使用人</th>
								<td>
									<input type="text" id="asset_user" name="asset_user" disabled="disabled"
									value="${currentProjectContent.asset_user}" class="col-xs-10 col-sm-5"></td>
							</tr>
							
							<tr>
								<th>资产配置</th>
								<td><input type="text" id="asset_detail_config" name="asset_detail_config" disabled="disabled"
									value="${currentProjectContent.asset_detail_config}" class="col-xs-10 col-sm-5"></td>

								<th>资产用途</th>
								<td>
									<input type="text" id="asset_use" name="asset_use"  disabled="disabled"
									value="${currentProjectContent.asset_use}" class="col-xs-10 col-sm-5"></td>

								<th>报废原因</th>
								<td>
									<input type="text" id="abandon_reason" name="abandon_reason" disabled="disabled"
									value="${currentProjectContent.abandon_reason}" class="col-xs-10 col-sm-5"></td>
							</tr>
							
							<tr>
								<th>报废时间</th>
								<td><input type="text" id="abandon_time" name="abandon_time" disabled="disabled"
									value="${currentProjectContent.abandon_time}" class="col-xs-10 col-sm-5"></td>

								<th>资产状态</th>
								<td>
									<input type="text" id="asset_status" name="asset_status"  disabled="disabled"
									value="${currentProjectContent.asset_status}" class="col-xs-10 col-sm-5"></td>

								<th>报废处理意见</th>
								<td>
									<input type="text" id="abandon_idea" name="abandon_idea" disabled="disabled"
									value="${currentProjectContent.abandon_idea}" class="col-xs-10 col-sm-5"></td>
							</tr>
							
							<tr>
								<th>办理人</th>
								<td><input type="text" id="abandon_manager" name="abandon_manager" disabled="disabled"
									value="${currentProjectContent.abandon_manager}" class="col-xs-10 col-sm-5"></td>
									
								<th>审批流程</th>
								<td><input type="text" id="abandon_approve" name="abandon_approve" disabled="disabled"
									value="${currentProjectContent.abandon_approve}" class="col-xs-10 col-sm-5"></td>

								<th>备注：</th>
								<td>
									<input type="text" id="abandon_remark" name="abandon_remark"  disabled="disabled"
									value="${currentProjectContent.abandon_remark}" class="col-xs-10 col-sm-5"></td>								
							</tr>
						</c:if>	
						
						<!-- 处理资产调入与申请 -->
						<c:if test="${currentProjectContent.process_Type == '资产调入与借出'}">
							<tr>							
								<th>资产编码</th>
								<td>
								<input type="text" id="asset_code" name="asset_code"  disabled="disabled"
									value="${currentProjectContent.asset_code}" class="col-xs-10 col-sm-5">
								</td>
								<th>资产调入申请单名</th>
								<td>
								<input type="text" id="allot_name" name="allot_name"  disabled="disabled"
									value="${currentProjectContent.allot_name}" class="col-xs-10 col-sm-5">
								</td>	
								<th>银行名称</th>
								<td>
								<input type="text" id="bank_name" name="bank_name"  disabled="disabled"
									value="${currentProjectContent.bank_name}" class="col-xs-10 col-sm-5">
								</td>
							</tr>
							
							<tr>															
								<th>调拨时间</th>
								<td>
								<input type="text" id="allot_time" name="allot_time"  disabled="disabled"
									value="${currentProjectContent.allot_time}" class="col-xs-10 col-sm-5">
								</td>
								<th>调入部门</th>
								<td>
								<input type="text" id="it_department" name="it_department"  disabled="disabled"
									value="${currentProjectContent.it_department}" class="col-xs-10 col-sm-5">
								</td>
								
								<th>调出部门</th>
								<td>
								<input type="text" id="ot_department" name="ot_department"  disabled="disabled"
									value="${currentProjectContent.ot_department}" class="col-xs-10 col-sm-5">
								</td>									
							</tr>
							
							<tr>
								<th>资产原管理（使用）人</th>
								<td>
								<input type="text" id="asset_user" name="asset_user"  disabled="disabled"
									value="${currentProjectContent.asset_user}" class="col-xs-10 col-sm-5">
								</td>
								<th>资产接收（使用）人</th>
								<td>
								<input type="text" id="asset_receiver" name="asset_receiver"  disabled="disabled"
									value="${currentProjectContent.asset_receiver}" class="col-xs-10 col-sm-5">
								</td>
								
								<th>审批流程</th>
								<td>
								<input type="text" id="apply_procedure" name="apply_procedure"  disabled="disabled"
									value="${currentProjectContent.apply_procedure}" class="col-xs-10 col-sm-5">
								</td>									
							</tr>	
							
							<tr>
								<th>调拨原因</th>
								<td colspan="6">
									<textarea id="allot_reason" name="allot_reason" rows="4" cols="50" 
									style="width: 98%" disabled="disabled">
									${currentProjectContent.allot_reason}
								</textarea>
								</td>
							</tr>						
						</c:if>
										
							<tr>
								<th>审批意见</th>
								<td colspan="6">
									<textarea rows="5" cols="" placeholder="请输入审批意见" style="width:98%;" name="detail_ApproverContent" id="detail_ApproverContent"></textarea>
								</td>
							</tr>	

							<tr>
								<td style="text-align: center;" colspan="6">
									<button type="button" class="btn  btn-success" onclick="agreeOption();" style="margin: 20px;">
										<i class="ace-icon fa fa-check bigger-110"></i> 审批通过
									</button>
									<button class="btn  btn-primary" type="button"
										onClick="disagreeOption();" style="margin: 20px;">
										<i class="ace-icon fa fa-check bigger-110"></i> 审批不通过
									</button>
									
									<button class="btn  btn-danger" type="button"
										onClick="cancelOption();" style="margin: 20px;">
										<i class="ace-icon fa fa-check bigger-110"></i> 返回
									</button>
								</td>
							</tr>

							<tr>
								<th>当前状态</th>	
 									<td colspan="6">
 									<textarea  rows="1" cols="80" style="width: 98%;font-size:16px;color: red;"   readonly="readonly" >1、请填写对审批项目审批意见2、请进行对本项目的审批操作-同意或者不同意</textarea>
 								</td>	
							</tr>
						</thead>
					</table>
				</form>
			</div>
		</div>
	</div>
	
	
	 
	<script type="text/javascript">
		$(top.hangge());
		
		//点击取消按钮，返回项目流程主界面
		function cancelOption() {
			window.location="${pageContext.request.contextPath}/asset/atp_approvalprojectlist";
		}

		//点击同意按钮的操作
		function agreeOption(){
			if($('#detail_ApproverContent').val()=="" || $('#detail_ApproverContent').val()==null){			
				$("#detail_ApproverContent").tips({
					side:3,
		            msg:'请填写审批意见',
		            bg:'#AE81FF',
		            time:2
		        });			
				$("#detail_ApproverContent").focus();
				return false;
			}

			//规则都符合之后进行判断是否是已经完成的过程跳转到后台(ajax)
			var detail_ProcessId =$('#detail_ProcessId').val() ; //获取当前项目过程ID
			var process_Type = $('#process_Type').val();//获取当前项目过程的类型
			var project_Id = $('#project_Id').val();//获取当前审批项目的ID
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/atp_editjugdecurrentproject',
				async:false,
				data:{"detail_ProcessId":detail_ProcessId ,"projectprocess_Id":detail_ProcessId,"process_Type":process_Type,"project_Id":project_Id},
				type:'POST',
				success:function(data){
					if(data.finishstatus == "nofinished"){  //没有审核完成的过程，则跳转处理
						if(data.aleradoption == "no"){  //没有进行操作过
							$('#projectprocessform').attr({action:"asset/atp_editprojectapprovalagree"});
							$('#projectprocessform').submit();
						}
						else if(data.aleradoption == "yes"){ //已经操作过了一次同意或者不同意
							alert("当前项目过程您已经审批过了，请不要重复审批!");
						}
					}
					else{
						alert("当前项目过程已经完成审核，无法进行审核操作，请返回");
					}
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"			
			});	
		}
		
		//点击不同意按钮的操作
		function disagreeOption(){
			if($('#detail_ApproverContent').val()=="" || $('#detail_ApproverContent').val()==null){			
				$("#detail_ApproverContent").tips({
					side:3,
		            msg:'请填写审批意见',
		            bg:'#AE81FF',
		            time:2
		        });			
				$("#detail_ApproverContent").focus();
				return false;
			}			
			//规则都符合之后进行判断是否是已经完成的过程跳转到后台(ajax)
			var detail_ProcessId =$('#detail_ProcessId').val() ; //获取当前项目过程ID
			var process_Type = $('#process_Type').val();//获取当前项目过程的类型
			var project_Id = $('#project_Id').val();//获取当前审批项目的ID
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/atp_editjugdecurrentproject',
				async:false,
				data:{"detail_ProcessId":detail_ProcessId ,"projectprocess_Id":detail_ProcessId,"process_Type":process_Type,"project_Id":project_Id},
				type:'POST',
				success:function(data){
					if(data.finishstatus == "nofinished"){  //没用审核完成的过程，则跳转处理
						if(data.aleradoption == "no"){  //没有进行操作过
							$('#projectprocessform').attr({action:"asset/atp_editprojectapprovaldisagree"});
							$('#projectprocessform').submit();
						}
						else if(data.aleradoption == "yes"){ //已经操作过了一次同意或者不同意
							alert("当前项目过程您已经审批过了，请不要重复审批!");
						}				
					}
					else{
						alert("当前项目过程已经完成审核，无法进行审核操作，请返回");
					}
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"			
			});		
		}
		
		//点击下载文件的处理
		function downFile(fileurl){
			//先判断路径是否为空
			if(fileurl == null || fileurl == "" || ("").equels(fileurl)){
				alert("当前没有文件可以下载！");
			}
			else{
			//注意这里要进行编码处理一下，否则会出现乱码问题
			window.location ='${pageContext.request.contextPath}/asset/atp_downloadfile?fileurl='+encodeURI(encodeURI(fileurl));
			}
		}
	</script>
</body>
</html>

