<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
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
		$("#asset_use_company").attr("disabled",false);
		$("#asset_use_dept").attr("disabled",false);
		$("#abandon_approve").attr("disabled",false);
		
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
		
		if($("#asset_use_dept").val()=="" ){
			
			$("#asset_use_dept").tips({
				side:3,
	            msg:'请选择领用部门',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#asset_use_dept").focus();
			return false;
		}
		
		if($("#asset_user").val()==""){
			
			$("#asset_user").tips({
				side:3,
	            msg:'请输入领用人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#asset_user").focus();
			return false;
		}
		
		if($("#asset_use").val()==""){
			
			$("#asset_use").tips({
				side:3,
	            msg:'请输入资产用途',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#asset_use").focus();
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
				

		if($("#asset_status").val()==""){
			
			$("#asset_status").tips({
				side:3,
	            msg:'请输入资产状态',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#asset_status").focus();
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
		
	    $("#editAbandonForm").submit(); 
		$("#zhongxin").hide();
		$("#zhongxin2").show();   		 
		}
	}
	
	
</script>
	</head>
	<body>
	<form action="asset/edit_abandon.do?" name="editAbandonForm" id="editAbandonForm"
		method="post" >
	
		<input type="hidden" id="apply_procedure_id" name ="apply_procedure_id"><!-- 隐藏审批流程的ID -->
		<input type="hidden" name="id" id="sssd" value="${aam.id}"
		 />
		<div id="zhongxin" style="margin-left: 10px;margin-right: 10px;margin-top: 15px;">
			<table id="table_report"
				class="text-table table table-striped table-bordered table-hover">
						<tbody class="mytable">
								<tr>
									<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>修改报废表</h2></b></label></td>
								</tr>
								<tr>
									<td><label>资产编码</label></td>
										<td><input type="text" name="asset_code" id="asset_code" readonly="readonly" value="${aam.asset_code}" placeholder="请输入资产编码" disabled="disabled"></td>
									<td><label>资产名称</label></td>
										<td><input type="text" name="asset_name" id="asset_name" readonly="readonly" value="${aam.asset_name}" placeholder="暂无资产名称"></td>
									<td><label>资产配置</label></td>
										<td><input type="text" name="asset_detail_config" id="asset_detail_config" readonly="readonly" value="${aam.asset_detail_config}" placeholder="暂无资产配置情况" ></td>
								</tr>
								<tr>
									<td><label>使用公司</label></td>
									<td style="vertical-align: top;">
										<select class="chzn-select" data-placeholder="请选择使用公司" disabled="true"
											name="asset_use_company" id="asset_use_company" onchange="select_company();">
											<option></option>
										</select></td>
									<td><label>使用部门</label></td>
									<td style="vertical-align: top;">
										<select class="chzn-select" name="asset_use_dept" id="asset_use_dept"  data-placeholder="请选择下级机构"
											disabled="true" style="width: 221px; vertical-align: center;">
											<option></option>															
										</select>
									</td>
									<td><label>使用人</label></td>
										<td><input type="text" name="asset_user" id="asset_user" readonly="readonly" value="${aam.asset_user}" placeholder="暂无使用人信息" ></td>
								</tr>
								<tr>
									<td><label>资产用途</label></td>
										<td><input type="text" name="asset_use" id="asset_use" placeholder="暂无具体资产用途" readonly="readonly" value="${aam.asset_use}" ></td>	
									<td><label>报废原因<b style="color: red">*</b></label></td>
										<td><input type="text" name="abandon_reason" id="abandon_reason" value="${aam.abandon_reason}" placeholder="请输入报废原因"></td>	
									<td><label>报废时间<b style="color: red">*</b></label></td>
										<td>
											<input name="abandon_time" id="abandon_time"  value="${aam.abandon_time}" type="date" />								
										</td>			
								</tr>
								<tr>
									<td><label>报废处理意见<b style="color: red">*</b></label></td>
										<td><input type="text" name="abandon_idea" id="abandon_idea" value="${aam.abandon_idea}" placeholder="请输入报废处理意见"></td>		
									<td><label>审批流程</label></td>
									<td><select class="chzn-select" name="abandon_approve" id="abandon_approve" disabled="true" >
										<c:forEach items="${approvalProcess}" var="process">
											<option id=${process.process_Id } <c:if test="${aam.abandon_approve == process.process_Name}">selected</c:if>>${process.process_Name}</option>
										</c:forEach></select>
									<td><label>审批状态</label></td>
										<td><input type="text" name="approve_status" id="approve_status" value="${aam.approve_status}" placeholder="由审批部门填写" readonly="readonly"></td>	
								</tr>
								<tr>									
									<td><label>资产状态</label></td>
									<td><select class="chzn-select" name="current_status" id="current_status" disabled="disabled">
												<option value="报废">报废</option>
										</select></td>		
									<td><label>办理人<b style="color: red">*</b></label></td>
									<td><input type="text" name="abandon_manager" id="abandon_manager" value="${aam.abandon_manager}" placeholder="请输入办理人姓名"></td>	
									<td colspan="2"></td>								
								</tr>
								
								<tr>
									<td><label>备注：</label></td>
									<td colspan="6"><textarea rows="2" cols="50"
											placeholder="请输入备注信息" name="abandon_remark" id="abandon_remark" style="width: 92%">${aam.abandon_remark}</textarea></td>
								</tr>

								<tr style="height: 40">
									<td style="text-align: center;" colspan="7">
										<a class="btn btn-primary" onclick="save();">提交</a>
										<a class="btn btn-danger" href="asset/aucs_abandon.do;">取消</a>
									</td>
								</tr>
						</tbody>
			</table>
		</div>
		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div> 
	</form>


	<!-- 引入 -->
 	 <script type="text/javascript">
		window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>  
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<!-- 	<script type="text/javascript" src="static/js/jquery-1.9.1.min.js"></script>
 -->	
	<!-- 下拉框 -->

	<script type="text/javascript">
	$(top.hangge());
	var zn = '${institutionInfo}';
	var jsons = JSON.parse(zn)
	//开始进入 初始化公司下拉框
	$.each(jsons,function(key, value){
		$.each(value,function(key, value){
 			var option = document.createElement("option");
        	option.innerHTML = key+"";
        	if(key=='${aam.asset_use_company }'){
            	option.selected='selected';
            }
        	$("#asset_use_company").append(option);
 		});
	}); 
	
	
	$.each(jsons,function(key, value){
			$.each(value,function(key, value){
				if(key=='${aam.asset_use_company }'){
					for (var i=0;i<value.length;i++) {
						var te = value[i];
						var option = document.createElement("option");
						if(te=='${aam.asset_use_dept }'){
							option.selected='selected';
						}
				        option.innerHTML = te+"";
				        $("#asset_use_dept").append(option); 
			        }
				}
				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
				$("#asset_use_dept").trigger("liszt:updated");
	 		});
		}); 

	//申请公司的点击change事件
	function select_company() {
		var apply_company = document.getElementById("asset_use_company");
	    var apply_dept = document.getElementById("asset_use_dept");
		var options = apply_company.options;
		var company_name = options[apply_company.selectedIndex].innerHTML;
		apply_dept.length = 1; //清除以前的的信息
		$.each(jsons,function(key, value){
				$.each(value,function(key, value){
					if(key==company_name){
						for (var i=0;i<value.length;i++) {
							var te = value[i];
							var option = document.createElement("option");
					        option.innerHTML = te+"";
					        $("#asset_use_dept").append(option); 
				        }
					}
					/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
					$("#asset_use_dept").trigger("liszt:updated");
		 		});
			}); 
		
	}
		$(function() {

			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});
	</script>

</body>
</html>