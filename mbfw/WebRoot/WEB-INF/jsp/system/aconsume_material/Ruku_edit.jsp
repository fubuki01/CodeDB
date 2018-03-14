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
	<form action="asset/edit_Rk.do" name="editly" id="editly" method="post">
	    <input type="hidden" name="id" id="Id" value="${eEk.id}"
		 />
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>耗材编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_model" id="supplies_model" value="${eEk.supplies_model}"></td>
				<td><label>耗材名称<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_name" id="supplies_name" value="${eEk.supplies_name}"></td>
			</tr>
			<tr>
			 <td><label>耗材类型<b style="color: red">*</b></label></td>
			 <td><input type="text" name="supplies_type" id="supplies_type" value="${eEk.supplies_type}"></td>
			 <td><label>耗材品牌<b style="color: red">*</b></label></td>
			 <td><input type="text" name="supplies_brand" id="supplies_brand" value="${eEk.supplies_brand}"></td>
			</tr>
			<tr>
			    <td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="actual_amount" id="actual_amount" value="${eEk.actual_amount}"></td>
		 		<td><label>采购价格<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_price" id="purchase_price"  value="${eEk.purchase_price}"></td>
			</tr>
			<tr>
				<td><label>票据<b style="color: red">*</b></label></td>
				<td><input type="text" name="bill" id="bill"  value="${eEk.bill}"></td>
				<td><label>库位<b style="color: red">*</b></label></td>
				<td><input type="text" name="location" id="location"  value="${eEk.location}"></td>
			</tr>
			<tr>
				<td><label>申请人<b style="color: red">*</b></label></td>
			    <td><input type="text" name="applicant" id="applicant"value="${eEk.applicant}"></td>

				<td><label>申请部门<b style="color: red">*</b></label></td>
			    <td colspan="3"><input type="text" name="applicant_sector" id="applicant_sector"  value="${eEk.applicant_sector}" style="width: 221px;"></td>
			</tr>
			<tr>
				<td><label>申请公司<b style="color: red">*</b></label></td>
			    <td><input type="text" name="company_apply" id="company_apply" value="${eEk.company_apply}"></td>

				<td><label>申请时间<b style="color: red">*</b></label></td>
			    <td>
					<input class="span10 date-picker" name="time" id="time"  value="${eEk.time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="申请日期(必填)" title="申请日期"/>
				</td>
			</tr>
			<tr>
			<td><label>耗材年限<b style="color: red">*</b></label></td>
			 <td><input type="text" name="supplies_years" id="supplies_years" value="${eEk.supplies_years}"></td>
				<td><label>备注：</label></td>
				<td colspan="4"><textarea rows="3" cols="40"placeholder="备注:" style="width: 87%" name="remarks"></textarea></td>
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

//保存
function save(){
	if($("#supplies_name").val()==""){
		
		$("#supplies_name").tips({
			side:3,
            msg:'请输入耗材名称',
            bg:'#AE81FF',
            time:2
        });
		
		$("#supplies_name").focus();
		$("#supplies_name").val('');
		$("#supplies_name").css("background-color","white");
		return false;
	}else{
		$("#supplies_name").val($.trim($("#supplies_name").val()));
	}
	
	if($("#supplies_model").val()==""){
		
		$("#supplies_model").tips({
			side:3,
            msg:'请输入耗材编码',
            bg:'#AE81FF',
            time:2
        });
		
		$("#supplies_model").focus();
		$("#supplies_model").val('');
		$("#supplies_model").css("background-color","white");
		return false;
	}else{
		$("#supplies_model").val($.trim($("#supplies_model").val()));
	}
	
	
if($("#supplies_type").val()==""){
		
		$("#supplies_type").tips({
			side:3,
            msg:'请输入耗材类型',
            bg:'#AE81FF',
            time:3
        });
		$("#supplies_type").focus();
		return false;
	}
	
	
if($("#supplies_brand").val()==""){
	
	$("#supplies_brand").tips({
		side:3,
        msg:'请输入耗材品牌',
        bg:'#AE81FF',
        time:3
    });
	$("#supplies_brand").focus();
	return false;
}
	
	//验证价格
	if($("#actual_amount").val()==""){
		
		$("#actual_amount").tips({
			side:3,
            msg:'请输入实际数量',
            bg:'#AE81FF',
            time:3
        });
		$("#actual_amount").focus();
		$("#actual_amount").val('');
		return false;
	}else if($.trim($("#actual_amount").val())!=""){
		if(!ismoney($.trim($("#actual_amount").val()))){
			$("#actual_amount").tips({
				side:3,
	            msg:'输入数量格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#actual_amount").focus();
			return false;
		}
	}
	
	//验证价格
	if($("#purchase_price").val()==""){
		
		$("#purchase_price").tips({
			side:3,
            msg:'请输入实际数量',
            bg:'#AE81FF',
            time:3
        });
		$("#purchase_price").focus();
		$("#purchase_price").val('');
		return false;
	}else if($.trim($("#purchase_price").val())!=""){
		if(!ismoney($.trim($("#purchase_price").val()))){
			$("#purchase_price").tips({
				side:3,
	            msg:'输入采购价格格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#purchase_price").focus();
			return false;
		}
	}
	

	if($("#bill").val()==""){
		
		$("#bill").tips({
			side:3,
            msg:'请输入票据',
            bg:'#AE81FF',
            time:3
        });
		$("#bill").focus();
		return false;
	}
	
	
	if($("#location").val()==""){
		
		$("#location").tips({
			side:3,
            msg:'请输入库位',
            bg:'#AE81FF',
            time:3
        });
		$("#location").focus();
		return false;
	}
	
	if($("#applicant").val()==""){
		
		$("#applicant").tips({
			side:3,
            msg:'请输入申请人',
            bg:'#AE81FF',
            time:3
        });
		$("#applicant").focus();
		$("#applicant").val('');
		return false;
	}
	
if($("#company_apply").val()==""){
		
		$("#company_apply").tips({
			side:3,
            msg:'请输入申请公司',
            bg:'#AE81FF',
            time:3
        });
		$("#company_apply").focus();
		return false;
	}
	
	if($("#applicant_sector").val()==""){
		
		$("#applicant_sector").tips({
			side:3,
            msg:'请输入申请部门',
            bg:'#AE81FF',
            time:2
        });
		
		$("#applicant_sector").focus();
		$("#applicant_sector").val('');
		return false;
	}
	
	
	if($("#time").val()==""){
		
		$("#time").tips({
			side:3,
            msg:'请选择时间',
            bg:'#AE81FF',
            time:3
        });
		$("#time").focus();
		return false;
	}
	if($.trim($("#supplies_years").val())!=""){
		if(!ismoney($.trim($("#supplies_years").val()))){
			$("#supplies_years").tips({
				side:3,
	            msg:'输入年限格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#supplies_years").focus();
			return false;
		}
	}
	
	if($("#supplies_years").val()==""){
		
		$("#supplies_years").tips({
			side:3,
            msg:'请输入耗材年限',
            bg:'#AE81FF',
            time:2
        });
		
		$("#supplies_years").focus();
		$("#supplies_years").val('');
		return false;
	}
	

	else{
		//获取下拉列表中的所选择的流程的名字
// 		var id = $('#apply_procedure option:selected').attr("id");
// 		$("#apply_procedure_id").val(id);
// 		$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#editly").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}


function ismail(mail){
	return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
	}

//判断用户名是否存在
function hasU(){
	var USERNAME = $.trim($("#loginname").val());
	$.ajax({
		type: "POST",
		url: '<%=basePath%>user/hasU.do',
    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" == data.result){
				$("#userForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			 }else{
				$("#loginname").css("background-color","#D16E6C");
				setTimeout("$('#loginname').val('此用户名已存在!')",500);
			 }
		}
	});
}

//判断邮箱是否存在
function hasE(USERNAME){
	var EMAIL = $.trim($("#EMAIL").val());
	$.ajax({
		type: "POST",
		url: '<%=basePath%>user/hasE.do',
    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" != data.result){
				 $("#EMAIL").tips({
						side:3,
			            msg:'邮箱已存在',
			            bg:'#AE81FF',
			            time:3
			        });
				setTimeout("$('#EMAIL').val('')",2000);
			 }
		}
	});
}

//判断编码是否存在
function hasN(USERNAME){
	var NUMBER = $.trim($("#NUMBER").val());
	$.ajax({
		type: "POST",
		url: '<%=basePath%>user/hasN.do',
    	data: {NUMBER:NUMBER,USERNAME:USERNAME,tm:new Date().getTime()},
		dataType:'json',
		cache: false,
		success: function(data){
			 if("success" != data.result){
				 $("#NUMBER").tips({
						side:3,
			            msg:'编号已存在',
			            bg:'#AE81FF',
			            time:3
			        });
				setTimeout("$('#NUMBER').val('')",2000);
			 }
		}
	});
}

		
		
		</script>
		
		
		
		
		
		
		
	</body>
</html>