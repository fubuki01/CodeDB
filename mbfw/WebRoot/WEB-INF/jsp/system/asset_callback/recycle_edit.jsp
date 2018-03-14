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
		
		$("#orig_company").attr("disabled",false);
		$("#orig_department").attr("disabled",false);
		
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
		
		if($("#orig_company").val()=="" ){
			
			$("#orig_company").tips({
				side:3,
	            msg:'请选择原使用部门',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#orig_company").focus();
			return false;
		}
		
		if($("#orig_department").val()=="" ){
			
			$("#orig_department").tips({
				side:3,
	            msg:'请选择原使用部门',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#orig_department").focus();
			return false;
		}
		
		if($("#orig_user").val()==""){
			
			$("#orig_user").tips({
				side:3,
	            msg:'请输入原使用人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#orig_user").focus();
			return false;
		}
		
		
		if($("#recycle_reason").val()==""){
			
			$("#recycle_reason").tips({
				side:3,
	            msg:'请输入回收原因',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#recycle_reason").focus();
			return false;
		}
		
		if($("#recycle_time").val()==""){
			
			$("#recycle_time").tips({
				side:3,
	            msg:'输选择回收时间',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#recycle_time").focus();
			$("#recycle_time").val('');
			$("#recycle_time").css("background-color","white");
			return false;
		}
		
		
		if($("#recycleman").val()==""){
			
			$("#recycleman").tips({
				side:3,
	            msg:'请输入回收人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#recycleman").focus();
			return false;
		}
		
		if($("#recycle_manager").val()==""){
			
			$("#recycle_manager").tips({
				side:3,
	            msg:'请输入办理人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#recycle_manager").focus();
			return false;
		}
			
		else{			
	    $("#editRecycleForm").submit(); 
		$("#zhongxin").hide();
		$("#zhongxin2").show();   
		 
		} 
	}
			
	
</script>
	</head>
	<body>
	<form action="asset/edit_recycle.do?" name="editRecycleForm" id="editRecycleForm"
		method="post" >
		<input type="hidden" name="id" id="Id" value="${arm.id}"
		 />
		<div id="zhongxin" style="margin-left: 10px;margin-right: 10px;margin-top: 15px;">
			<table id="table_report"
				class="text-table table table-striped table-bordered table-hover">
						<tbody class="mytable">
								<tr>
									<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>修改回收表</h2></b></label></td>
								</tr>
								<tr>
									<td><label>资产编码<b style="color: red">*</b></label></td>
										<td><input type="text" placeholder="请输入资产编码" name="asset_code" id="asset_code" value="${arm.asset_code}"  readonly="readonly" ></td>
									<td><label>资产名称<b style="color: red">*</b></label></td>
										<td><input type="text" placeholder="请输入资产名称" name="asset_name" id="asset_name" value="${arm.asset_name}"  readonly="readonly" ></td>		
									<td><label>原使用人<b style="color: red">*</b></label></td>
										<td><input type="text" placeholder="请输入领原用人" name="orig_user" id="orig_user" readonly="readonly" value="${arm.orig_user}"></td>											
								<tr>
									<td><label>原使用公司</label></td>
									<td style="vertical-align: top;">
										<select class="chzn-select" data-placeholder="请选择使用公司" disabled="true"
											name="orig_company" id="orig_company" onchange="select_company();" >
											<option></option>
										</select></td>
									<td><label>原使用部门</label></td>
									<td style="vertical-align: top;">
										<select class="chzn-select" name="orig_department" id="orig_department"  data-placeholder="请选择下级机构"
											disabled="true" style="width: 221px; vertical-align: center;">
											<option></option>															
										</select>
									</td>
									<td><label>回收原因<b style="color: red">*</b></label></td>
										<td><input type="text" placeholder="请输入回收原因" name="recycle_reason" id="recycle_reason" value="${arm.recycle_reason}"></td>																								
								</tr>
								<tr>
									<td><label>回收时间<b style="color: red">*</b></label></td>
									<td>
										<input type="date" name="recycle_time" id="recycle_time"  value="${arm.recycle_time}" />								
									</td>
									<td><label>回收人<b style="color: red">*</b></label></td>
										<td><input type="text" placeholder="请输入回收人" name="recycleman" id="recycleman" value="${arm.recycleman}"></td>	
									<td><label>资产状态<b style="color: red">*</b></label></td>
									<td><select class="chzn-select" name="asset_status" id="asset_status" data-placeholder="请选择资产状态" disabled="disabled">
												<option value="回收">回收</option>
										</select></td>																																
								</tr>
								
								<tr>											
									<td><label>办理人<b style="color: red">*</b></label></td>
										<td><input type="text" name="recycle_manager" id="recycle_manager" value="${arm.recycle_manager}" placeholder="请输入办理人姓名"></td>
									<td colspan="4"></td>									
								</tr>
								
								<tr>
									<td><label>备注：</label></td>
									<td colspan="5"><textarea rows="2" cols="50"
											placeholder="请输入备注信息" name="recycle_remark" id="recycle_remark" style="width: 92%">${arm.recycle_remark}</textarea></td>
								</tr>

								<tr style="height: 40">
									<td colspan="7" style="text-align:center">
										<a class="btn btn-primary" onclick="save();">提交</a>
										<a class="btn btn-danger" href="asset/aucs_recycle.do;">取消</a>
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
        	if(key=='${arm.orig_company }'){
            	option.selected='selected';
            }
        	$("#orig_company").append(option);
 		});
	}); 
	
	
	$.each(jsons,function(key, value){
			$.each(value,function(key, value){
				if(key=='${arm.orig_company }'){
					for (var i=0;i<value.length;i++) {
						var te = value[i];
						var option = document.createElement("option");
						if(te=='${arm.orig_department }'){
							option.selected='selected';
						}
				        option.innerHTML = te+"";
				        $("#orig_department").append(option); 
			        }
				}
				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
				$("#orig_department").trigger("liszt:updated");
	 		});
		}); 

	//申请公司的点击change事件
	function select_company() {
		var apply_company = document.getElementById("orig_company");
	    var apply_dept = document.getElementById("orig_department");
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
					        $("#orig_department").append(option); 
				        }
					}
					/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
					$("#orig_department").trigger("liszt:updated");
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