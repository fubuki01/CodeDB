
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>





<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<%@ include file="../admin/top.jsp"%> 
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
		<!-- 内容居中显示 -->
		<style type="text/css">
		.mytable  tr td {
			text-align: center;
		}
		
		.mytable tr td label {
			text-align: center;
		}
		.mytable tr td input {
			text-align: center;
		}
		</style>
		
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		if($("#asset_code").val()==""){
			 $("#asset_code").tips({
				side:3,
	            msg:'请输入资产编码',
	            bg:'#AE81FF',
	            time:2
	        }); 
			
			$("#asset_code").focus();
			return false;
		}
		
		
		if($("#abandon_reason").val()==""){
			
			$("#abandon_reason").tips({
				side:3,
	            msg:'请输入报废原因',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#abandon_reason").focus();
			return false;
		}else if($.trim($("#abandon_reason").val()) != '无法维修' && $.trim($("#abandon_reason").val()) != '超过使用年限'){
			alert("该资产在 正常使用中 或处于 维修中，无法进行报废");
		}
		
		
		if($("#abandon_time").val()==""){
			
			$("#abandon_time").tips({
				side:3,
	            msg:'输选择报废时间',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#abandon_time").focus();
			$("#abandon_time").val('');
			$("#abandon_time").css("background-color","white");
			return false;
		}
				
			
		if($("#abandon_idea").val()==""){
			
			$("#abandon_idea").tips({
				side:3,
	            msg:'请输入报废处理意见',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#abandon_idea").focus();
			return false;
		}
		
					
		
		if($("#abandon_manager").val()==""){
			
			$("#abandon_manager").tips({
				side:3,
	            msg:'请输入办理人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#abandon_manager").focus();
			return false;
		}
		

		
		else{
		//获取下拉列表中的所选择的流程的名字
		var id = $('#abandon_approve option:selected').attr("id");
		$("#apply_procedure_id").val(id);
						
	    $("#editAbandonForm").submit(); 
		$("#zhongxin").hide();
		$("#zhongxin2").show();   		 
		}
	}
	
</script>
	</head>
	
	<body>
	<form action="asset/save_abandon.do" name="editAbandonForm" id="editAbandonForm" method="post">
		<input type="hidden" name="id" id="id" />	
		<input type="hidden" id="apply_procedure_id" name ="apply_procedure_id"><!-- 隐藏审批流程的ID -->
		
		<input type="hidden" id="asset_status" name="asset_status"><!-- 隐藏点击的资产状态 -->
		<div id="zhongxin" style="margin-left: 10px;margin-right: 10px;margin-top: 15px;">
		<table id="table_report"
							class="text-table table table-striped table-bordered table-hover">
							<tbody class="mytable">
								<!-- 开始循环 -->
								<tr>				
									<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>新增报废表</h2></b></label></td>
								</tr>
								<tr>
									<td><label>资产编码<b style="color: red">*</b></label></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="asset_code"  id="asset_code" data-placeholder="请选择资产编码" onchange="intolibrary(this.value);">											
											<c:forEach items="${assetCodeFind}" var="pf" varStatus="vs">	
												<option></option>										
												<option value="${pf.asset_code}">${pf.asset_code}</option>  										
											</c:forEach>
											</select>									
										</td> 					
									<td><label>资产名称</label></td>
										<td><input type="text" name="asset_name" id="asset_name"  readonly="readonly" placeholder="暂无资产名称信息" ></td>
									<td><label>资产配置</label></td>
										<td><input type="text" name=asset_detail_config id="asset_detail_config" readonly="readonly" placeholder="暂无资产配置信息" ></td>	
								</tr>
								<tr>									
									<td><label>使用公司</label></td>
										<td><input type="text" name="asset_use_company" id="asset_use_company" readonly="readonly" placeholder="暂无资产公司信息" ></td>
									<td><label>使用部门</label></td>
										<td><input type="text" name="asset_use_dept" id="asset_use_dept" readonly="readonly" placeholder="暂无资产部门信息" ></td>
									<td><label>使用人</label></td>
										<td><input type="text" name="asset_user" id="asset_user" readonly="readonly" placeholder="暂无使用人信息" ></td>
								</tr>							
								<tr>
									<td><label>资产用途</label></td>
										<td><input type="text" name="asset_use" id="asset_use" readonly="readonly" placeholder="暂无资产用途信息" ></td>	
									<td><label>报废原因<b style="color: red">*</b></label></td>
										<td><input type="text" name="abandon_reason" id="abandon_reason" placeholder="请输入报废原因"></td>
									<td><label>报废时间<b style="color: red">*</b></label></td>
										<td><input type="text" class="date-picker" placeholder="请选择报废时间" name="abandon_time" id="abandon_time" 
									 data-date-format="yyyy-mm-dd"></td>				
								</tr>
								<tr>									
									<td><label>资产状态</label></td>
									<td><select class="chzn-select" name="current_status" id="current_status" disabled="disabled">
												<option value="报废">报废</option>
										</select></td>
									<td><label>原资产状态</label></td>
										<td><input type="text" name="orig_status" id="orig_status" placeholder="原资产状态" readonly="readonly" ></td>
									<td><label>报废处理意见<b style="color: red">*</b></label></td>
										<td><input type="text" name="abandon_idea" id="abandon_idea" placeholder="请输入报废处理意见"></td>									
								</tr>
								<tr>											
									<td><label>报废审批<b style="color: red">*</b></label></td>
										<td>
											<select class="chzn-select" name="abandon_approve" id="abandon_approve" data-placeholder="请选择审批流程" style="width: 221px;vertical-align: center">
											<c:forEach items="${approvalProcess}" var="info" >
												<option id=${info.process_Id }>${info.process_Name}</option>				
											</c:forEach>
											</select>									
										</td>
									<td><label>审批状态</label></td>
										<td><input type="text" name="approve_status" id="approve_status" placeholder="由审批部门填写" disabled="disabled"></td>	
									<td><label>办理人<b style="color: red">*</b></label></td>
										<td><input type="text" name="abandon_manager" id="abandon_manager" placeholder="请输入办理人姓名"></td>									
								</tr>
								<tr>
									<td><label>备注：</label></td>
									<td colspan="6"><textarea rows="2" cols="50"
											placeholder="请输入备注信息" name="abandon_remark" id="abandon_remark" style="width: 92%"></textarea></td>
								</tr>

								<tr style="height: 40">
									<td style="text-align: center;" colspan="7">
										<a class="btn btn-primary" onclick="save();">提交</a>
										<a class="btn btn-danger"  href="asset/aucs_abandon.do;">取消</a>
									</td>
								</tr>
							</tbody>
						</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->		
		<script type="text/javascript">
		
		function intolibrary(id){
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/find_assetinfo',
				async:false,
				data:{"id":id},
				dataType:'json',
				type:'POST',
				success:function(data){
					$.each(data,function(key, value){
						if(key == "asset_status"){
							if(value== "领用" ){
								$('#asset_status').val('领用');	
								var limy;
								$.each(data,function(key, value){
									if(key == 'asset_name'){
										$('#asset_name').val(value);
									}								
									 if(key == 'asset_use_company'){
										$('#asset_use_company').val(value);
									}
									 if(key == 'asset_use_dept'){
										$('#asset_use_dept').val(value);
									}
									if(key == 'asset_user'){
										$('#asset_user').val(value);
									}
									if(key == 'asset_use'){
										$('#asset_use').val(value);
									}
									if(key == 'asset_detail_config'){
										$('#asset_detail_config').val(value);
									}
									if(key == 'asset_max_years'){
										limy = value;
									}
									if(key == 'asset_purchase_time'){
										var a  = new Array();
										a = value.split("-");
										var date = new Date();
							        	var seperator1 = "-";
							        	var year = date.getFullYear() - a[0];
							        	var month = date.getMonth() + 1 - a[1];
							        	var strDate = date.getDate()- a[2];
							        	if(strDate < 0){
							        		month = month - 1;
							        	}
							        	if(month < 0){
							        		year = year - 1;
							        	}							        	
								        if (month >= 1 && month <= 9) {
								            month = "0" + month;
								       	}							        
							        	if (strDate >= 0 && strDate <= 9) {
							            	strDate = "0" + strDate;
							        	}
										if(year > limy){
											$('#abandon_reason').val("超过使用年限");
										}else{
											$('#abandon_reason').val("正常使用中");
										}
									}
									if(key == 'asset_status'){
										$('#orig_status').val(value);
									}
									if(key == 'id'){
										$('#id').val(value);
									}
								});
							}							
							if(value == "回收"){
								$('#asset_status').val('回收');
								$.each(data,function(key, value){
										if(key == 'asset_name'){
											$('#asset_name').val(value);
										}
										if(key == 'orig_company'){
											$('#asset_use_company').val(value);
										}
										if(key == 'orig_department'){
											$('#asset_use_dept').val(value);
										}
										if(key == 'orig_user'){
											$('#asset_user').val(value);
										}
										if(key == 'asset_use'){
											$('#asset_use').val(value);
										}
										if(key == 'asset_detail_config'){
											$('#asset_detail_config').val(value);
										}
										if(key == 'asset_max_years'){
											limy = value;
										}
										if(key == 'asset_purchase_time'){
											var a  = new Array();
											a = value.split("-");
											var date = new Date();
								        	var seperator1 = "-";
								        	var year = date.getFullYear() - a[0];
								        	var month = date.getMonth() + 1 - a[1];
								        	var strDate = date.getDate()- a[2];
								        	if(strDate < 0){
								        		month = month - 1;
								        	}
								        	if(month < 0){
								        		year = year - 1;
								        	}							        	
									        if (month >= 1 && month <= 9) {
									            month = "0" + month;
									       	}							        
								        	if (strDate >= 0 && strDate <= 9) {
								            	strDate = "0" + strDate;
								        	}
											if(year > limy){
												$('#abandon_reason').val("超过使用年限");
											}else{
												$('#abandon_reason').val("正常使用中");
											}
										}
										if(key == 'asset_status'){
											$('#orig_status').val(value);
										}
										if(key == 'id'){
											$('#id').val(value);
										}	
								});
							}
							if(value == "闲置"){
								$('#asset_status').val('闲置');
								$.each(data,function(key, value){
										if(key == 'asset_name'){
											$('#asset_name').val(value);
										}
										if(key == 'asset_detail_config'){
											$('#asset_detail_config').val(value);
										}
										if(key == 'asset_max_years'){
											limy = value;
										}
										if(key == 'asset_purchase_time'){
											var a  = new Array();
											a = value.split("-");
											var date = new Date();
								        	var seperator1 = "-";
								        	var year = date.getFullYear() - a[0];
								        	var month = date.getMonth() + 1 - a[1];
								        	var strDate = date.getDate()- a[2];
								        	if(strDate < 0){
								        		month = month - 1;
								        	}
								        	if(month < 0){
								        		year = year - 1;
								        	}							        	
									        if (month >= 1 && month <= 9) {
									            month = "0" + month;
									       	}							        
								        	if (strDate >= 0 && strDate <= 9) {
								            	strDate = "0" + strDate;
								        	}
											if(year > limy){
												$('#abandon_reason').val("超过使用年限");
											}else{
												$('#abandon_reason').val("正常使用中");
											}
										}
										if(key == 'asset_status'){
											$('#orig_status').val(value);
										}
										if(key == 'id'){
											$('#id').val(value);
										}	
								});
							}
							if(value == "报修"){
								$('#asset_status').val('报修');
								$.each(data,function(key, value){
										if(key == 'asset_name'){
											$('#asset_name').val(value);
										}
										if(key == 'asset_detail_config'){
											$('#asset_detail_config').val(value);
										}
										if(key == 'asset_status'){
											$('#orig_status').val(value);
										}
										if(key == 'asset_use_company'){
											$('#asset_use_company').val(value);
										}
										if(key == 'asset_use_dept'){
											$('#asset_use_dept').val(value);
										}
										if(key == 'asset_user'){
											$('#asset_user').val(value);
										}
										if(key == 'asset_use'){
											$('#asset_use').val(value);
										}
										if(key == 'maintain_result'){
											$('#abandon_reason').val(value);
										}
										if(key == 'id'){
											$('#id').val(value);
										}	
								});
							}
						}							
						
					});
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				}
			});
		}
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		//获取当前时间，格式YYYY-MM-DD
    	
		</script>
	
</body>
</html>