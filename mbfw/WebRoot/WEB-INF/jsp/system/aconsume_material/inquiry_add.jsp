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

	</head>
<body>
	<form action="asset/savaLy.do" name="saveLy" id="saveLy" method="post">
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>耗材编码<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select name=""supplies_model"" id=""supplies_model"" class="chzn-select"  data-placeholder="请选择耗材编码" style="width: 221px;vertical-align: center" onchange="add_supplies(this.value);">
						<option></option>
<%-- 						<c:forEach items="${product_code_used}" var="pp"> --%>
<%-- 						<option value="${pp.product_code}">${pp.product_code}</option>  --%>
<%-- 						</c:forEach>			 --%>
					</select>
				</td> 
				<td><label>耗材名称<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_name" id="supplies_name" ></td>
			</tr>
			<tr>
			    <td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quantity" id="quantity" ></td>
		 		<td><label>领用人<b style="color: red">*</b></label></td>
				<td><input type="text" name="leader" id="leader"  ></td>
			</tr>
			<tr>
				<td><label>领用部门<b style="color: red">*</b></label></td>
				<td><input type="text" name="department" id="department"  ></td>
				<td><label>领用公司<b style="color: red">*</b></label></td>
				<td><input type="text" name="company" id="company"  ></td>
			</tr>
			<tr>
				<td><label>发放人<b style="color: red">*</b></label></td>
			    <td><input type="text" name="release_people" id="release_people"></td>

				<td><label>领用时间<b style="color: red">*</b></label></td>
			    <td>
					<input class="span10 date-picker" name="time" id="time"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="领用时间(必填)" title="领用时间"/>
				</td>
			</tr>
			
			<tr>
				<td><label>备注：</label></td>
				<td colspan="4"><textarea rows="3" cols="40"placeholder="备注:" style="width: 87%" name="remarks" id="remarks" ></textarea></td>
			</tr>

			 <tr style="height: 40">
				<td style="text-align: center;" colspan="4">
				  <a class="btn btn-mini btn-primary" onclick="save();">提交</a>
				  <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
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
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});



$(document).ready(function(){
if($("#id").val()!=""){
	$("#loginname").attr("readonly","readonly");
	$("#loginname").css("color","gray");
}
});

function save(){
		//这里还要写对其中填写的内容进行判断是否合法
		var issuccess = true;
		if($("#supplies_model").val() == "" || $("#supplies_model").val() == null){
			alert("耗材编码不能为空！");
			issuccess = false;
			
		}	
		
		if($("#supplies_name").val() == "" || $("#supplies_name").val() == null){
			alert("耗材名称不能为空！");	
			issuccess = false
		}
		if($("#quantity").val() == "" || $("#quantity").val() == null){
			alert("实际数量不能为空！");	
			issuccess = false
		}
		if($("#leader").val() == "" || $("#leader").val() == null){
			alert("领用人不能为空！");
			issuccess = false;
		}
		if($("#department").val() == "" || $("#department").val() == null){
			alert("领用部门不能为空！");
			issuccess = false;
		}
		if($("#company").val() == "" || $("#company").val() == null){
			alert("领用公司不能为空！");
			issuccess = false;
		}
		if($("#release_people").val() == "" || $("#release_people").val() == null){
			alert("发放人不能为空！");
			issuccess = false;
		}
		
		 if($("#time").val() == "" || $("#time").val() == null){
				alert("领用时间不能为空！");
				issuccess = false;
			}
		
		
		//如果合法，则跳转到更新界面
		if(issuccess ){
		 	$('#saveLy').submit(); 
		 	$('#zhongxin').hide(); 
		 	$('#zhongxin2').show(); 
		 	
		}
	}


//入库选择下拉框到后台请求对应入库单内容
function add_supplies(id){
  
	$.ajax({
		url:'${pageContext.request.contextPath}/asset/fill_suppliesApplication',
		async:false,
		data:{"id":id},
		type:'POST',
		success:function(data){
			$('#supplies_name').val(data.product_name);
			$('#Market_quotes').val(data.product_price);
// 			$('#asset_unit').val(data.product_unit);
// 			$('#asset_provider').val(data.provider_name);
// 			$('#asset_standard_model').val(data.asset_standard_model);
			$('#quantity').val(data.product_total);
// 			$('#product_code').val(data.product_code);
		},
		error:function(){
			alert("网络出现问题，请稍后再试");
		},
		dataType:"json"
	});
}
		
		
		</script>
		
		
		
		
		
		
		
	</body>
</html>