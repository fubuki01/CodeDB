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
		<input type="hidden" id="count">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>耗材名称<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select name="supplies_name" id="supplies_name" class="chzn-select"  data-placeholder="请选择耗材名称" style="width: 221px;vertical-align: center" onchange="add_supplies(this.value);">
						<option></option>
						<c:forEach items="${product_code_used}" var="pp">
						<option value="${pp.supplies_model}@${pp.supplies_name}">${pp.supplies_name}</option> 
						</c:forEach>			
					</select>
				</td> 
				<td><label>耗材编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_model" id="supplies_model" ></td>
			</tr>
			<tr>
			    <td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quantity" id="quantity" ></td>
		 		<td><label>领用人<b style="color: red">*</b></label></td>
				<td><input type="text" name="leader" id="leader"  ></td>
			</tr>
			<tr>
				 <td><label>领用公司<b style="color: red">*</b></label></td>
                <td style="vertical-align: top;"><select class="chzn-select"
						data-placeholder="请选择申请公司" name="company" id="company"
						onchange="select_company();">
							<option></option>
					</select></td>
				<td><label>领用部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
						name="department" id="department" data-placeholder="请选择下级机构"
						style="width: 221px; vertical-align: center;">
							<option></option>
					</select></td>
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
		
		
		var zn = '${institutionInfo}';
 		var jsons = JSON.parse(zn)  
 		//开始进入 初始化公司下拉框
 		$.each(jsons,function(key, value){
 			$.each(value,function(key, value){
 	 			var option = document.createElement("option");
	            option.innerHTML = key+"";
	            $("#company").append(option);
 	 		});
 		}); 
		
 		//申请公司的点击change事件
		function select_company() {
			var apply_company = document.getElementById("company");
	        var apply_dept = document.getElementById("department");
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
	 				        $("#department").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#department").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
 		
		
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
	
	var count =$("#count").val();
	
	if($("#quantity").val()==""){
		
		$("#quantity").tips({
			side:3,
            msg:'请输入实际数量',
            bg:'#AE81FF',
            time:3
        });
		$("#quantity").focus();
		$("#quantity").val('');
		return false;
	}else if($.trim($("#quantity").val())!=""){
		if(!ismoney($.trim($("#quantity").val()))){
			$("#quantity").tips({
				side:3,
	            msg:'输入数量格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#quantity").focus();
			return false;
		}else if ($("#quantity").val()>$("#count").val()) {
			$("#quantity").tips({
				side:3,
	            msg:'输入数量超过库存数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#quantity").focus();
			return false;
		}
	}
	
	
	if($("#leader").val()==""){
		
		$("#leader").tips({
			side:3,
            msg:'请输入领用人',
            bg:'#AE81FF',
            time:3
        });
		$("#leader").focus();
		$("#leader").val('');
		return false;
	}
	

	if($("#company").val()==""){
		
		$("#company").tips({
			side:3,
            msg:'请输入领用公司',
            bg:'#AE81FF',
            time:3
        });
		$("#company").focus();
		return false;
	}
	
	if($("#department").val()==""){
		
		$("#department").tips({
			side:3,
            msg:'请输入领用部门',
            bg:'#AE81FF',
            time:3
        });
		$("#department").focus();1
		return false;
	}
	
	
	if($("#release_people").val()==""){
		
		$("#release_people").tips({
			side:3,
            msg:'请输入发放人',
            bg:'#AE81FF',
            time:3
        });
		$("#release_people").focus();1
		return false;
	}
	
	if($("#time").val()==""){
		
		$("#time").tips({
			side:3,
            msg:'请选择领用时间',
            bg:'#AE81FF',
            time:3
        });
		$("#time").focus();
		return false;
	}
	

	else{
		//获取下拉列表中的所选择的流程的名字
// 		var id = $('#apply_procedure option:selected').attr("id");
// 		$("#apply_procedure_id").val(id);
// 		$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#saveLy").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}






// function save(){
// 		//这里还要写对其中填写的内容进行判断是否合法
// 		var issuccess = true;
// 		if($("#supplies_model").val() == "" || $("#supplies_model").val() == null){
// 			alert("耗材编码不能为空！");
// 			issuccess = false;
			
// 		}	
		
// 		if($("#supplies_name").val() == "" || $("#supplies_name").val() == null){
// 			alert("耗材名称不能为空！");	
// 			issuccess = false
// 		}
// 		if($("#quantity").val() == "" || $("#quantity").val() == null){
// 			alert("实际数量不能为空！");	
// 			issuccess = false
// 		}
// 		if($("#leader").val() == "" || $("#leader").val() == null){
// 			alert("领用人不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#department").val() == "" || $("#department").val() == null){
// 			alert("领用部门不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#company").val() == "" || $("#company").val() == null){
// 			alert("领用公司不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#release_people").val() == "" || $("#release_people").val() == null){
// 			alert("发放人不能为空！");
// 			issuccess = false;
// 		}
		
// 		 if($("#time").val() == "" || $("#time").val() == null){
// 				alert("领用时间不能为空！");
// 				issuccess = false;
// 			}
		
		
// 		//如果合法，则跳转到更新界面
// 		if(issuccess ){
// 		 	$('#saveLy').submit(); 
// 		 	$('#zhongxin').hide(); 
// 		 	$('#zhongxin2').show(); 
		 	
// 		}
// 	}
	
//入库选择下拉框到后台请求对应入库单内容
function add_supplies(id){
	var product_code = id.split("@")[0];
	$.ajax({
		url:'${pageContext.request.contextPath}/asset/fill_suppliesuse',
		async:false,
		data:{"product_code":product_code},
		type:'POST',
		success:function(data){
			
			$('#supplies_model').val(data.supplies_model);
// 			$('#purchase_price').val(data.product_price);
// 			$('#asset_unit').val(data.product_unit);
// 			$('#asset_provider').val(data.provider_name);
// 			$('#asset_standard_model').val(data.asset_standard_model);
 			$('#count').val(data.inventory_quantity);
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