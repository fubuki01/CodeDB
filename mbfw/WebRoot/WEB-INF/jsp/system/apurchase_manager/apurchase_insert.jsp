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
  
	<form action="asset/${msg }.do" name="apurchaseMForm" id="apurchaseMForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<!-- 隐藏传送审批ID -->
		<input type="hidden" id="apply_procedure_id" name ="apply_procedure_id">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td colspan="4"><label><b style="font-weight: 20px;color:red"><h3>采购申请<h3></h3></b></label></td>
			</tr>
			<tr>
				<td><label>项目名称<b style="color: red">*</b></label></td>
				<td><input type="text" name="apply_name" id="apply_name" placeholder="命名格式: 资产数量+资产名称" ></td>
				<td><label>申请人<b style="color: red">*</b></label></td>
				<td><input type="text" name="apply_person" id="apply_person" placeholder="申请人"></td>
			</tr>
			
			<tr>
				<td><label>设备名称<b style="color: red">*</b></label></td>
				<td>
					<select class="chzn-select" name="device_name" id="device_name"  data-placeholder="设备名称-供应商" style="width: 221px; vertical-align: center;">
						<option></option>
						<c:forEach items="${product_find}" var="pf" varStatus="vs">
							<option value="${pf.product_code}@${pf.product_class}@${pf.product_name}@${pf.provider_name}">${pf.product_name}-${pf.provider_name}</option>
						</c:forEach>
					</select>
				</td>
				<td><label>数量（个）<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_number" id="device_number" placeholder="资产数量"></td>
			</tr>
			<tr>
				<td><label>申请公司<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;"><select class="chzn-select" data-placeholder="请选择申请公司"
					name="apply_company" id="apply_company" onchange="select_company();">
						<option></option>
								
					</select>
				</td>
				<td><label>申请部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" name="apply_dept" id="apply_dept"  data-placeholder="请选择下级机构"
					style="width: 221px; vertical-align: center;">
					<option></option>						
					</select>
				</td>
			</tr>
			<tr>
				<td><label>市场价格（元）<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_price" id="device_price" placeholder="资产预单价"></td>
				<td><label>设备用途<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_purpose" id="device_purpose" placeholder="设备用途"></td>
				
			</tr>
			
			<tr>
				<td><label>预计使用年限<b style="color: red">*</b></label></td>
				<td><input type="text" name="device_use_years" id="device_use_years" placeholder="预计使用年限"></td>
				<td><label>上传申请报告 </label></td>
				<td><input type="file" name="address" id="address" placeholder="上传申请报告" style="width: 221px"></td>
			</tr>
			<tr>
				<td><label>申请时间<b style="color: red">*</b></label></td>
				<td>
					<input name="apply_time" id="apply_time" type="text" value="${date}"  data-date-format="yyyy-mm-dd" readonly="readonly" style="width:221px;" placeholder="申请日期" title="采购单申请日期"/>
				</td>
				<td><label>型号<b style="color: red">*</b></label></td>
				<td>
					<input type="text" name="device_model" id="device_model" placeholder="型号">
				</td>
			</tr>
			<tr>
				<td><label>审批流程<b style="color: red">*</b></label></td>
				<td style="vertical-align:top;">
					<select class="chzn-select" name="apply_procedure" id="apply_procedure" data-placeholder="请选择审批流程" style="width: 221px;vertical-align: center">
					<option></option>
					<c:forEach items="${processinfo}" var="info" varStatus="processorder">
						<option id=${info.process_Id }>${info.process_Name}</option>				
					</c:forEach>
					</select>
				</td> 
				<td><label>申请备注：</label></td>
				<td><textarea rows="1" cols="" name="apply_remarks" id="apply_remarks" placeholder="申请备注"style="width: 75%"></textarea>
			</tr>	
				
			<tr>
				<td><label>申请原因<b style="color: red">*</b></label></td>
				<td colspan="3"><textarea rows="1" cols=""name="apply_reason" id="apply_reason"  style="width: 90%"> </textarea></td>
			</tr>
			<tr style="height: 40">
				<td style="text-align: center;" colspan="4">
					<a class="btn btn-mini btn-primary" onclick="save();">提交申请</a>
					<a class="btn btn-mini btn-danger" onclick="project_apply_close();">取消</a>
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
		
		</script>
		
		<script type="text/javascript">
		
		function project_apply_close(){
			window.location.href='<%=basePath%>asset/apl_Caigou_apply.do';
		}
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		</script>
	<script type="text/javascript">
	$(top.hangge());
	//保存
	function save(){
		if($("#apply_name").val()==""){
			
			$("#apply_name").tips({
				side:3,
	            msg:'请输入项目名',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#apply_name").focus();
			$("#apply_name").val('');
			$("#apply_name").css("background-color","white");
			return false;
		}else{
			$("#apply_name").val($.trim($("#apply_name").val()));
		}
		
		if($("#apply_person").val()==""){
			
			$("#apply_person").tips({
				side:3,
	            msg:'请输入申请人',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#apply_person").focus();
			$("#apply_person").val('');
			$("#apply_person").css("background-color","white");
			return false;
		}else{
			$("#apply_person").val($.trim($("#apply_person").val()));
		}
		if($("#device_name").val()==""){
			
			$("#device_name").tips({
				side:3,
	            msg:'请输入设备名称',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#device_name").focus();
			return false;
		}

		if($("#device_number").val()==""){
			
			$("#device_number").tips({
				side:3,
	            msg:'请输入资产数量',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#device_number").focus();
			return false;
		}else if($.trim($("#device_number").val() != "")){
			if(!isint($.trim($("#device_number").val()))){
				$("#device_number").tips({
					side:3,
		            msg:'请输入一个整数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#device_number").focus();
				return false;
			}
		}
		if($("#apply_company").val()==""){
			
			$("#apply_company").tips({
				side:3,
	            msg:'请选择申请公司',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#apply_company").focus();
			return false;
		}
		if($("#apply_dept").val()==""){
			
			$("#apply_dept").tips({
				side:3,
	            msg:'请选择部门',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#apply_dept").focus();
			return false;
		}
		
		
		//验证价格
		if($("#device_price").val()==""){
			
			$("#device_price").tips({
				side:3,
	            msg:'请输入市场价格',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#device_price").focus();
			$("#device_price").val('');
			return false;
		}else if($.trim($("#device_price").val())!=""){
			if(!ismoney($.trim($("#device_price").val()))){
				$("#device_price").tips({
					side:3,
		            msg:'输入价格格式不正确',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#device_price").focus();
				return false;
			}
		}
		
		if($("#device_purpose").val()==""){
			
			$("#device_purpose").tips({
				side:3,
	            msg:'请输入设备用途',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#device_purpose").focus();
			return false;
		}
		
		if($("#device_use_years").val()==""){
			
			$("#device_use_years").tips({
				side:3,
	            msg:'请输入设备预计使用年限',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#device_use_years").focus();
			return false;
		}else if($.trim($("#device_use_years").val() != "")){
			if(!isint($.trim($("#device_use_years").val()))){
				$("#device_use_years").tips({
					side:3,
		            msg:'请输入一个整数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#device_use_years").focus();
				return false;
			}
		}

		if($("#device_model").val()==""){
			
			$("#device_model").tips({
				side:3,
	            msg:'请输入设备型号',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#device_model").focus();
			$("#device_model").val('');
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
		if($("#apply_reason").val()==""){
			
			$("#apply_reason").tips({
				side:3,
	            msg:'请输入申请原因',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#apply_reason").focus();
			return false;
		}
		else{
			//获取下拉列表中的所选择的流程的名字
			var id = $('#apply_procedure option:selected').attr("id");
			$("#apply_procedure_id").val(id);
			$('#apply_name').val(safeStr($('#apply_name').val()));
			$("#apurchaseMForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}
	
	var permission = '${permission}';
	if(permission==1||permission==2){
		var zn = '${institutionInfo}';
 		var jsons = JSON.parse(zn)  
 		//开始进入 初始化公司下拉框
 		$.each(jsons,function(key, value){
 			$.each(value,function(key, value){
 	 			var option = document.createElement("option");
	            option.innerHTML = key+"";
	            $("#apply_company").append(option);
 	 		});
 		}); 
		
 		//申请公司的点击change事件
		function select_company() {
			var apply_company = document.getElementById("apply_company");
	        var apply_dept = document.getElementById("apply_dept");
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
	 				        $("#apply_dept").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#apply_dept").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
	}
	if(permission==3){
		var apply_person = '${apply_person}';//申请人
		var apply_company = '${apply_company}';//申请公司
		var apply_dept = '${apply_dept}';//申请部门
		if(apply_person!=""||apply_person!=null){
			$("#apply_person").val(apply_person);
			$("#apply_person").attr("readonly","readonly");
		}
		if(apply_company!=""||apply_company!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_company;
            option.selected='selected';
            $("#apply_company").append(option);
		}
		if(apply_dept!=""||apply_dept!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_dept;
            option.selected='selected';
            $("#apply_dept").append(option);
		}
	}

</script>
	
	</body>
</html>