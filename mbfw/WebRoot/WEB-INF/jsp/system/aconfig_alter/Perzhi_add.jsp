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
	<form action="asset/savePz.do" name="savePz" id="savePz" method="post">
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
			<td><label>变更配置<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select name="chan_config" id="chan_config" class="chzn-select"  data-placeholder="请选择变更配置" style="width: 221px;vertical-align: center" onchange="add_asset(this.value);">
						<option></option>
						<c:forEach items="${product_code_used}" var="pp">
						<option value="${pp.asset_code}@${pp.asset_name}">${pp.asset_name}-${pp.asset_provider}</option> 
						</c:forEach>			
					</select>
				</td> 
			
				<td><label>资产编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="asset_code" id="asset_code" ></td>
			</tr>
			<tr>
			    <td><label>配置保修截止日期<b style="color: red">*</b></label></td>
			    <td>
			    <input class="span10 date-picker" name="deadline" id="deadline"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="配置保修截止日期(必填)" title="配置保修截止日期"/>
			    </td>
		 		<td><label>变更原因<b style="color: red">*</b></label></td>
				<td><input type="text" name="reason_change" id="reason_change"  ></td>
			</tr>
			<tr>
				<td><label>配置来源<b style="color: red">*</b></label></td>
				<td><input type="text" name="config_sour" id="config_sour"  ></td>
				<td><label>配置费用<b style="color: red">*</b></label></td>
				<td><input type="text" name="config_cost" id="config_cost"  ></td>
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



//保存
function save(){
	if($("#chan_config").val()==""){
		
		$("#chan_config").tips({
			side:3,
            msg:'请输入变更配置',
            bg:'#AE81FF',
            time:2
        });
		
		$("#chan_config").focus();
		$("#chan_config").val('');
		$("#chan_config").css("background-color","white");
		return false;
	}else{
		$("#chan_config").val($.trim($("#chan_config").val()));
	}
	
	if($("#asset_code").val()==""){
		
		$("#asset_code").tips({
			side:3,
            msg:'请输入资产编码',
            bg:'#AE81FF',
            time:2
        });
		
		$("#asset_code").focus();
		$("#asset_code").val('');
		$("#asset_code").css("background-color","white");
		return false;
	}else{
		$("#asset_code").val($.trim($("#asset_code").val()));
	}
	
	if($("#deadline").val()==""){
		
		$("#deadline").tips({
			side:3,
            msg:'请输入配置保修截止日期',
            bg:'#AE81FF',
            time:3
        });
	//
		$("#deadline").focus();
		return false;
	}

	if($("#reason_change").val()==""){
		
		$("#reason_change").tips({
			side:3,
            msg:'请输入变更原因',
            bg:'#AE81FF',
            time:3
        });
		$("#reason_change").focus();
		return false;
	}
	
	
	if($("#config_sour").val()==""){
		
		$("#config_sour").tips({
			side:3,
            msg:'请输入配置来源',
            bg:'#AE81FF',
            time:3
        });
		$("#config_sour").focus();
		return false;
	}
	
	//验证价格
	if($("#config_cost").val()==""){
		
		$("#config_cost").tips({
			side:3,
            msg:'请输入配置费用',
            bg:'#AE81FF',
            time:3
        });
		$("#config_cost").focus();
		$("#config_cost").val('');
		return false;
	}else if($.trim($("#config_cost").val())!=""){
		if(!ismoney($.trim($("#config_cost").val()))){
			$("#config_cost").tips({
				side:3,
	            msg:'输入价格格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#config_cost").focus();
			return false;
		}
	}
	
	if($("#applicant").val()==""){
		
		$("#applicant").tips({
			side:3,
            msg:'请输入申请人',
            bg:'#AE81FF',
            time:3
        });
		$("#applicant").focus();
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
	

	else{
		//获取下拉列表中的所选择的流程的名字
// 		var id = $('#apply_procedure option:selected').attr("id");
// 		$("#apply_procedure_id").val(id);
// 		$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#savePz").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}



// function save(){
// 		//这里还要写对其中填写的内容进行判断是否合法
// 		var issuccess = true;
		
// 		if($("#asset_code").val() == "" || $("#asset_code").val() == null){
// 			alert("资产编码不能为空！");
// 			issuccess = false;
			
// 		}	
// 		if($("#chan_config").val() == "" || $("#chan_config").val() == null){
// 			alert("变更配置不能为空！");	
// 			issuccess = false
// 		}
// 		if($("#deadline").val() == "" || $("#deadline").val() == null){
// 			alert("配置保修截止日期不能为空！");	
// 			issuccess = false
// 		}
// 		if($("#reason_change").val() == "" || $("#reason_change").val() == null){
// 			alert("变更不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#config_sour").val() == "" || $("#config_sour").val() == null){
// 			alert("配置来源不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#config_cost").val() == "" || $("#config_cost").val() == null){
// 			alert("配置费用不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#applicant").val() == "" || $("#applicant").val() == null){
// 			alert("申请人不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#applicant_sector").val() == "" || $("#applicant_sector").val() == null){
// 			alert("申请部门不能为空！");
// 			issuccess = false;
// 		}
// 		 if($("#company_apply").val() == "" || $("#company_apply").val() == null){
// 		alert("申请公司不能为空！");	
// 		issuccess = false
// 	   } 
// 		 if($("#time").val() == "" || $("#time").val() == null){
// 				alert("申请时间不能为空！");
// 				issuccess = false;
// 			}
		
		
// 		//如果合法，则跳转到更新界面
// 		if(issuccess ){
// 		 	$('#savePz').submit(); 
// 		 	$('#zhongxin').hide(); 
// 		 	$('#zhongxin2').show(); 
		 	
// 		}
// 	}
	
//入库选择下拉框到后台请求对应入库单内容
function add_asset(id){
	var asset_code = id.split("@")[0];
	$.ajax({
		url:'${pageContext.request.contextPath}/asset/fill_assetAlter',
		async:false,
		data:{"asset_code":asset_code},
		type:'POST',
		success:function(data){
			
			$('#asset_code').val(data.asset_code);
// 			$('#chan_config').val(data.asset_name);
			$('#config_cost').val(data.asset_price);
// 			$('#supplier').val(data.asset_provider);
// 			$('#asset_standard_model').val(data.asset_standard_model);
// 			$('#quantity').val(data.product_total);
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