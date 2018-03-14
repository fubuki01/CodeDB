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
	<form action="asset/saveRuku.do" name="savely" id="savely" method="post">
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>耗材名称<b style="color: red">*</b></label></td>
<!-- 				<td><input type="text" name="supplies_name" id="supplies_name" ></td> -->
				<td style="vertical-align:top;">
					<select name="supplies_name" id="supplies_name" class="chzn-select"  data-placeholder="请选择耗材名称" style="width: 221px;vertical-align: center" onchange="add_supplies(this.value);">
						<option></option>
						<c:forEach items="${product_code_used}" var="pp">
						<option value="${pp.id}@${pp.supplies_name}" >${pp.supplies_name}</option> 
						</c:forEach>			
					</select>
				</td> 
				<td><label>耗材编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_model" id="supplies_model" ></td>
			</tr>
			<tr>
			 <td><label>耗材类型<b style="color: red">*</b></label></td>
			 <td><input type="text" name="supplies_type" id="supplies_type" ></td>
			 <td><label>耗材品牌<b style="color: red">*</b></label></td>
			 <td><input type="text" name="supplies_brand" id="supplies_brand" ></td>
			</tr>
			<tr>
			    <td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="actual_amount" id="actual_amount" ></td>
		 		<td><label>采购价格<b style="color: red">*</b></label></td>
				<td><input type="text" name="purchase_price" id="purchase_price"  ></td>
			</tr>
			<tr>
				<td><label>票据</label></td>
				<td><input type="text" name="bill" id="bill"  ></td>
				<td><label>库位<b style="color: red">*</b></label></td>
				<td><input type="text" name="location" id="location"  ></td>
			</tr>
			<tr>
				<td><label>申请人<b style="color: red">*</b></label></td>
			    <td><input type="text" name="applicant" id="applicant"></td>

				  <td><label>申请公司<b style="color: red">*</b></label></td>
                <td style="vertical-align: top;"><select class="chzn-select"
						data-placeholder="请选择申请公司" name="company_apply" id="company_apply"
						onchange="select_company();">
							<option></option>

					</select></td>
				
			</tr>
			<tr>
				<td><label>申请部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select"
						name="applicant_sector" id="applicant_sector" data-placeholder="请选择下级机构"
						style="width: 221px; vertical-align: center;">
							<option></option>
					</select></td>

				<td><label>申请时间<b style="color: red">*</b></label></td>
			    <td>
					<input class="span10 date-picker" name="time" id="time"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="申请日期(必填)" title="申请日期"/>
				</td>
			</tr>
			<tr>
			   <td><label>耗材年限<b style="color: red">*</b></label></td>
			 <td><input type="text" name="supplies_years" id="supplies_years" ></td>
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
		
		var zn = '${institutionInfo}';
 		var jsons = JSON.parse(zn)  
 		//开始进入 初始化公司下拉框
 		$.each(jsons,function(key, value){
 			$.each(value,function(key, value){
 	 			var option = document.createElement("option");
	            option.innerHTML = key+"";
	            $("#company_apply").append(option);
 	 		});
 		}); 
		
 		//申请公司的点击change事件
		function select_company() {
			var apply_company = document.getElementById("company_apply");
	        var apply_dept = document.getElementById("applicant_sector");
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
	 				        $("#applicant_sector").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#applicant_sector").trigger("liszt:updated");
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


  
//入库选择下拉框到后台请求对应入库单内容
function add_supplies(id){
    var product_code = id.split("@")[0];
  
	$.ajax({
		url:'${pageContext.request.contextPath}/asset/fill_supplies',
		async:false,
		data:{"product_code":product_code},
		type:'POST',
		success:function(data){
			$('#supplies_model').val(data.supplies_model);
			$('#purchase_price').val(data.Market_quotes);
			$('#supplies_type').val(data.supplies_type);
// 			$('#asset_provider').val(data.provider_name);
// 			$('#asset_standard_model').val(data.asset_standard_model);
			$('#actual_amount').val(data.quantity);
// 			$('#product_code').val(data.product_code);
		},
		error:function(){
			alert("网络出现问题，请稍后再试");
		},
		dataType:"json"
	});
}
		
		
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
	
	
// if($("#supplies_type").val()==""){
		
// 		$("#supplies_type").tips({
// 			side:3,
//             msg:'请输入耗材类型',
//             bg:'#AE81FF',
//             time:3
//         });
// 		$("#supplies_type").focus();
// 		return false;
// 	}
	
	
// if($("#supplies_brand").val()==""){
	
// 	$("#supplies_brand").tips({
// 		side:3,
//         msg:'请输入耗材品牌',
//         bg:'#AE81FF',
//         time:3
//     });
// 	$("#supplies_brand").focus();
// 	return false;
// }
	
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
	

// 	if($("#bill").val()==""){
		
// 		$("#bill").tips({
// 			side:3,
//             msg:'请输入票据',
//             bg:'#AE81FF',
//             time:3
//         });
// 		$("#bill").focus();
// 		return false;
// 	}
	
	
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
	
// 	if($.trim($("#supplies_years").val())!=""){
// 		if(!ismoney($.trim($("#supplies_years").val()))){
// 			$("#supplies_years").tips({
// 				side:3,
// 	            msg:'输入年限格式不正确',
// 	            bg:'#AE81FF',
// 	            time:2
// 	        });
// 			$("#supplies_years").focus();
// 			return false;
// 		}
// 	}
	
// 	if($("#supplies_years").val()==""){
		
// 		$("#supplies_years").tips({
// 			side:3,
//             msg:'请输入耗材年限',
//             bg:'#AE81FF',
//             time:2
//         });
		
// 		$("#supplies_years").focus();
// 		$("#supplies_years").val('');
// 		return false;
// 	}
	else{
		//获取下拉列表中的所选择的流程的名字
// 		var id = $('#apply_procedure option:selected').attr("id");
// 		$("#apply_procedure_id").val(id);
// 		$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#savely").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}


		
		</script>
		
		
		
		
		
		
		
	</body>
</html>