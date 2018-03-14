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
	<form action="asset/saveSq.do" name="savely" id="savely" method="post">
		<!-- 隐藏传送审批ID -->
		<input type="hidden" id="apply_procedure_id" name ="apply_procedure_id">
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered">
			<tr>
				<td><label>耗材名称<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select name="supplies_name" id="supplies_name" class="chzn-select"  data-placeholder="请选择耗材名称" style="width: 170px;vertical-align: center" onchange="add_supplies(this.value);">
						<option></option>
						<c:forEach items="${product_code_used}" var="pp">
<%-- 						<option value="${pp.product_code}@${pp.provider_name}">${pp.product_name}-${pp.provider_name}</option> --%>
						<option value="${pp.product_code}@${pp.product_name}">${pp.product_name}-${pp.provider_name}</option> 
						</c:forEach>			
					</select>
					<a class="btn btn-small btn-success" onclick="add();" style="vertical-align:top;">新增</a>
				</td> 
				<td><label>耗材编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_model" id="supplies_model" ></td>
			</tr>
			<tr>
			<td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quantity" id="quantity" ></td>
			    <td><label>用途<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_use" id="supplies_use" ></td>
			    </tr>
			<tr>
				<td><label>品牌<b style="color: red">*</b></label></td>
			    <td><input type="text" name="brand" id="brand" ></td>
				<td><label>市场报价<b style="color: red">*</b></label></td>
			    <td><input type="text" name="Market_quotes" id="Market_quotes" ></td>
			    </tr>
			<tr>
				<td><label>耗材类型<b style="color: red">*</b></label></td>
				<td><input type="text" name="supplies_type" id="supplies_type"  ></td>
			    <td><label>报价依据<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quote_basis" id="quote_basis" ></td>
			    </tr>
			
			<tr>
				<td><label>经办人<b style="color: red">*</b></label></td>
			    <td ><input type="text" name="manager" id="manager" ></td>
			    <td><label>供应商<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplier" id="supplier" ></td>
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
					<input class="span10 date-picker" name="time_apply" id="time_apply"  value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="申请日期(必填)" title="申请日期"/>
				</td>
				
			</tr>
			
			<tr>
				<td><label>审批流程<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select name="apply_procedure" id="apply_procedure" class="chzn-select" name="state" id="state" data-placeholder="请选择审批流程" style="width: 221px;vertical-align: center">
						<option></option>
						<c:forEach items="${processinfo}" var="info" varStatus="processorder">
						<option id=${info.process_Id }>${info.process_Name}</option>				
						</c:forEach>					
					</select>
				</td> 
				<td><label>资金来源<b style="color: red">*</b></label></td>
			    <td><input type="text" name="sour_of_funds" id="sour_of_funds"></td>
				
				
			</tr>
			<tr>
			<td><label>备注：</label></td>
				<td colspan="6"><textarea rows="3" cols="40"placeholder="备注:" style="width: 87%" name="remarks"></textarea></td>
            </tr>
			 <tr style="height: 40">
				<td style="text-align: center;" colspan="4">
				  <a class="btn btn-mini btn-primary"   onclick="save();">提交</a>
				  <a class="btn btn-mini btn-danger"  onclick="project_apply_close();">取消</a>
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
function project_apply_close(){
	window.location.href='<%=basePath%>asset/acm_apply.do';
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
		}
	}
	
	
	
	if($("#supplies_use").val()==""){
		
		$("#supplies_use").tips({
			side:3,
            msg:'请输入用途',
            bg:'#AE81FF',
            time:3
        });
		$("#supplies_use").focus();
		return false;
	}

	if($("#brand").val()==""){
		
		$("#brand").tips({
			side:3,
            msg:'请输入品牌',
            bg:'#AE81FF',
            time:3
        });
		$("#brand").focus();
		return false;
	}
	
	
	if($("#Market_quotes").val()==""){
		
		$("#Market_quotes").tips({
			side:3,
            msg:'请输入市场报价',
            bg:'#AE81FF',
            time:3
        });
		$("#Market_quotes").focus();
		$("#Market_quotes").val('');
		return false;
	}else if($.trim($("#Market_quotes").val())!=""){
		if(!ismoney($.trim($("#Market_quotes").val()))){
			$("#Market_quotes").tips({
				side:3,
	            msg:'输入价格格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#Market_quotes").focus();
			return false;
		}
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
	
	if($("#quote_basis").val()==""){
		
		$("#quote_basis").tips({
			side:3,
            msg:'请输入报价依据',
            bg:'#AE81FF',
            time:3
        });
		$("#quote_basis").focus();
		return false;
	}
	
	

	if($("#manager").val()==""){
		
		$("#manager").tips({
			side:3,
            msg:'请选择经办人',
            bg:'#AE81FF',
            time:3
        });
		$("#manager").focus();
		return false;
	}
	
	if($("#supplier").val()==""){
		
		$("#supplier").tips({
			side:3,
            msg:'请选择供应商',
            bg:'#AE81FF',
            time:3
        });
		$("#supplier").focus();
		return false;
	}
	
	if($("#applicant").val()==""){
		
		$("#applicant").tips({
			side:3,
            msg:'请选择申请人',
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
            time:2
        });
		
		$("#company_apply").focus();
		$("#company_apply").val('');
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
	if($("#time_apply").val()==""){
		
		$("#time_apply").tips({
			side:3,
            msg:'请选择申请时间',
            bg:'#AE81FF',
            time:3
        });
		$("#time_apply").focus();
		return false;
	}
	
	
	
	if($("#apply_procedure").val()==""){
		
		$("#apply_procedure").tips({
			side:3,
            msg:'请选择审批流程',
            bg:'#AE81FF',
            time:3
        });
		$("#apply_procedure").focus();
		return false;
	} 
if($("#sour_of_funds").val()==""){
		
		$("#sour_of_funds").tips({
			side:3,
            msg:'请输入资金来源',
            bg:'#AE81FF',
            time:3
        });
		$("#sour_of_funds").focus();
		return false;
	}
	

	else{
		//获取下拉列表中的所选择的流程的名字
 		var id = $('#apply_procedure option:selected').attr("id");
		$("#apply_procedure_id").val(id);
		
 		//$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#savely").submit();
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
// 		/* if($("#orig_department").val() == "" || $("#orig_department").val() == null){
// 			alert("领使用部门不能为空！");	
// 			issuccess = false
// 		} */
// 		if($("#supplies_name").val() == "" || $("#supplies_name").val() == null){
// 			alert("耗材名称不能为空！");	
// 			issuccess = false
// 		}
// 		if($("#quantity").val() == "" || $("#quantity").val() == null){
// 			alert("实际数量不能为空！");	
// 			issuccess = false
// 		}
// 		if($("#supplies_use").val() == "" || $("#supplies_use").val() == null){
// 			alert("用途不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#brand").val() == "" || $("#brand").val() == null){
// 			alert("品牌不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#Market_quotes").val() == "" || $("#Market_quotes").val() == null){
// 			alert("市场报价不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#supplies_type").val() == "" || $("#supplies_type").val() == null){
// 			alert("耗材类型不能为空！");
// 			issuccess = false;
// 		}
// 		if($("#quote_basis").val() == "" || $("#quote_basis").val() == null){
// 			alert("报价依据不能为空！");
// 			issuccess = false;
// 		}
// 		 if($("#sour_of_funds").val() == "" || $("#sour_of_funds").val() == null){
// 		alert("资金来源不能为空！");	
// 		issuccess = false
// 	   } 
		 
// 			if($("#manager").val() == "" || $("#supplier").val() == null){
// 				alert("经办人不能为空！");
// 				issuccess = false;
// 			}if($("#supplier").val() == "" || $("#supplier").val() == null){
// 				alert("供应商不能为空！");
// 				issuccess = false;
// 			}if($("#applicant").val() == "" || $("#applicant").val() == null){
// 				alert("申请人不能为空！");
// 				issuccess = false;
// 			}if($("#applicant_sector").val() == "" || $("#applicant_sector").val() == null){
// 				alert("申请部门不能为空！");
// 				issuccess = false;
// 			}if($("#company_apply").val() == "" || $("#company_apply").val() == null){
// 				alert("申请公司不能为空！");
// 				issuccess = false;
// 			}
// 		      if($("#time_apply").val() == "" || $("#time_apply").val() == null){
// 				alert("申请时间不能为空！");
// 				issuccess = false;
// 			}
		
		
// 		//如果合法，则跳转到更新界面
// 		if(issuccess ){
// 			//获取下拉列表中的所选择的流程的名字
// 			var id = $('#apply_procedure option:selected').attr("id");
// 			$("#apply_procedure_id").val(id);
			
// 		 	$('#savely').submit(); 
// 		 	$('#zhongxin').hide(); 
// 		 	$('#zhongxin2').show(); 
		 	
// 		}
// 	}


//入库选择下拉框到后台请求对应入库单内容
function add_supplies(id){
	var product_code = id.split("@")[0];
	$.ajax({
		url:'${pageContext.request.contextPath}/asset/fill_suppliesApplication',
		async:false,
		data:{"product_code":product_code},
		type:'POST',
		success:function(data){
// 			alert(data.product_price);
// 			alert(data.product_code);
// 			alert(data.product_class);
// 			alert(data.product_total);
			$('#Market_quotes').val(data.product_price);
			$('#supplies_model').val(data.product_code);
			$('#supplies_type').val(data.product_class);
			$('#supplier').val(data.provider_name);
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
		
		

function add(){
	window.location.href='<%=basePath%>product/apl_product_insert.do';
}
//新增
// function add(){
// 	 top.jzts();
// 	 var diag = new top.Dialog();
// 	 diag.Drag=true;
// 	 diag.Title ="新增产品";
<%-- 	 diag.URL = '<%=basePath%>product/apl_product_insert.do'; --%>
// 	 diag.Width = 800;
// 	 diag.Height = 430;
// 	 diag.CancelEvent = function(){ //关闭事件
// 		 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
// 			 if('${page.currentPage}' == '0'){
// 				 top.jzts();
// 				 setTimeout("self.location=self.location",100);
// 			 }else{
// 				 nextPage(${page.currentPage});
// 			 }
// 		}
// 		diag.close();
// 	 };
// 	 diag.show();
// }

		</script>
		
		
		
		
		
		
		
	</body>
</html>