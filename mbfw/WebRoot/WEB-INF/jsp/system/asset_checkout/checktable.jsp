
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
			
	var result="";	
			
	//进行数据输入的提交处理
	function save(){
		if($("#check_name").val()==""){
			 $("#check_name").tips({
				side:3,
	            msg:'请输入盘点单名称',
	            bg:'#AE81FF',
	            time:2
	        }); 
			
			$("#check_name").focus();
			return false;
		}
		
		if(result == "error"){
			 $("#check_name").tips({
				side:3,
	            msg:'请重新输入盘点单名称',
	            bg:'#AE81FF',
	            time:2
	        }); 
			result = "ok"
			$("#check_name").focus();
			return false;
		}

		if($("#build_name").val()==""){
			 $("#build_name").tips({
				side:3,
	            msg:'请输入创建人',
	            bg:'#AE81FF',
	            time:2
	        }); 
			
			$("#build_name").focus();
			return false;
		}
		
		if($("#build_time").val()==""){
			 $("#build_time").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        }); 
			
			$("#build_time").focus();
			return false;
		}
		
		if($("#check_status").val()==""){
			
			$("#check_status").tips({
				side:3,
	            msg:'请输入盘点产状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#check_status").focus();
			return false;
		}
		
		
		else{
		
	    $("#addCheckForm").submit(); 
		$("#zhongxin").hide();
		$("#zhongxin2").show();   		 
		} 
	}
	
		</script>
	</head>


	<body>		
		<form action="asset/save_Check.do" name="addCheckForm" id="addCheckForm" method="post">
		<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
		<div id="zhongxin" style="margin-left: 50px;margin-right: 1000px;margin-top: 30px;">
			<table id="table_report"
				class="text-table table table-striped table-bordered table-hover">
				<tbody class="mytable">
					<!-- 开始循环 -->
						<tr>				
							<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>新增盘点单</h2></b></label></td>
						</tr>
						<tr>
							<td><label>盘点单名称<b style="color: red">*</b></label></td>
								<td colspan="5"><input type="text" name="check_name" id="check_name" onblur="isexist(this.value);" 
									title="请勿输入已存在的名称" style="width: 92%" placeholder="请输入盘点单名称">								
							</td>															
						</tr>
						<tr>
							<td><label>创建人<b style="color: red">*</b></label></td>
								<td><input type="text" placeholder="请输入创建人" name="build_name" id="build_name" style="width: 92%""></td>
						</tr>
						<tr>
							<td><label>创建时间<b style="color: red">*</b></label></td>
								<td><input type="text" class="date-picker" placeholder="请选择创建时间" name="build_time" id="build_time" 
									style="width: 92%" data-date-format="yyyy-mm-dd"></td>
						</tr>
						<tr>															
							<td><label>购买时间</label></td>
								<td colspan="12"><input type="text" class="date-picker" placeholder="请选择购买时间" title="设置起始时间" name="start_purchase_time" id="start_purchase_time" 
									value="${pd.start_purchase_time}" style="width: 220px; vertical-align: center;" data-date-format="yyyy-mm-dd">
								——
								<input type="text" class="date-picker" placeholder="请选择购买时间" title="设置终止时间" name="end_purchase_time" id="end_purchase_time" 
									value="${pd.end_purchase_time}" style="width: 220px; vertical-align: center;" data-date-format="yyyy-mm-dd"></td>	
						</tr>
						<tr>
							<td><label>资产名称</label></td>
							<td>
								<select class="chzn-select" name="asset_name" id="asset_name"  data-placeholder="请选择资产名称" 
									multiple="multiple" style="width: 500px; vertical-align: center;" >
									<option></option>
									<c:forEach items="${asset_name}" var="pf">											
											<option value="${pf}" <c:if test="${pd.asset_name == pf}">selected</c:if>>${pf }</option>  										
									</c:forEach>
								</select>	
							</td>
						</tr>
						<tr>
							<td><label>使用公司</label></td>
									<td style="vertical-align: top;">
										<select class="chzn-select" data-placeholder="请选择使用公司" style="width: 500px; vertical-align: center;"
											name="asset_use_company" id="asset_use_company" onchange="select_company();">
											<option></option>
										</select></td>
						</tr>
						<tr>
							<td><label>使用部门</label></td>
									<td style="vertical-align: top;">
										<select class="chzn-select" name="asset_use_dept" id="asset_use_dept"  data-placeholder="请选择下级机构"
											multiple="multiple" style="width: 500px; vertical-align: center;">
											<option></option>															
										</select>
									</td>																							
						</tr>
						<tr>							
							<td><label>规格</label></td>
								<td><select class="chzn-select" multiple="multiple" name="asset_standard_model" id="asset_standard_model" data-placeholder="请选择规格" 
									style="width: 500px; vertical-align: center;" >
										<option ></option>
									<c:forEach items="${asset_standard_model}" var="pf" >											
											<option value="${pf }" <c:if test="${pd.asset_standard_model == pf }">selected</c:if>>${pf }</option>  										
									</c:forEach>
								</select></td>
						</tr>
						<tr>
							<td><label>资产类别</label></td>
							<td><select class="chzn-select" name="asset_class" id="asset_class" multiple="multiple" data-placeholder="选择资产类别" 
								style="width: 500px; vertical-align: center;" >
									<option ></option>
									<c:forEach items="${asset_class}" var="pf" >											
											<option value="${pf }" <c:if test="${pd.asset_class == pf }">selected</c:if>>${pf }</option>  										
									</c:forEach>
								</select></td>
						</tr>
						<tr>						
							<td><label>资产状态</label></td>
							<td>
								<select class="chzn-select" name="asset_status" id="asset_status" multiple="multiple" data-placeholder="选择资产状态" 
									style="width: 500px; vertical-align: center;" >
									<option ></option>
									<c:forEach items="${asset_status}" var="pf" varStatus="vs">											
											<option value="${pf }" <c:if test="${pd.asset_status == pf }">selected</c:if>>${pf }</option>  										
									</c:forEach>
								</select></td>
						</tr>
						<tr>
							<td><label>备注：</label></td>
								<td colspan="5"><textarea rows="2" cols="50"
									placeholder="请输入备注信息" name="check_remark" id="check_remark" style="width: 92%"></textarea></td>
						</tr>						
						<tr style="height: 40">
							<td colspan="7" style="text-align:center">
								<a class="btn btn-primary" onclick="save();">提交盘点结果</a>
								<a class="btn btn-danger" href="asset/check_list.do;">取消</a>
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
		
		$(top.hangge());
		var zn = '${institutionInfo}';
		var jsons = JSON.parse(zn) 
		//开始进入 初始化公司下拉框
		$.each(jsons,function(key, value){
			$.each(value,function(key, value){
	 			var option = document.createElement("option");
	        	option.innerHTML = key+"";
	        	$("#asset_use_company").append(option);
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
		
		function isexist(check_name){
			$.ajax({
				url:'${pageContext.request.contextPath}/asset/find_checkname',
				async:false,
				data:{"check_name":check_name},
				type:'POST',
				success:function(data){
					if("ok" != data.result){
						alert("该名称已存在，请重新输入！");
						result = data.result;
					}
				},
				error:function(){
					alert("网络出现问题，请稍后再试");
				},
				dataType:"json"
			});
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker();
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
		});
		
		</script>	
		</body>
		
</html>