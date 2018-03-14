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
	<form action="asset/${msg }.do" name="assetReceivedForm" id="assetReceivedForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered">
			<tr>
				<td><label>资产编码${id }<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_code" id="asset_code" placeholder="资产编码" value="${asset_code }" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label>接收机构<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" data-placeholder="接收机构" name="receive_branch" id="receive_branch" onchange="select_company();" >
					<option></option>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>接收部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" data-placeholder="接收部门" name="receive_department" id="receive_department"  >
					<option></option>
				</select>
				</td>
			</tr>
			<tr>
				<td><label>接收人员<b style="color: red">*</b></label></td>
				<td><input type="text" name="receiver" id="receiver" placeholder="接收人员"  value="${issued_bill.issue_person}"></td>
			</tr>
			<tr>
				<td><label>接收证明<b style="color: red">*</b></label></td>
				<td><input type="file" style="width:221px;" name="file_receive" multiple id="file_receive" placeholder="接收证明"  value="${issued_bill.issue_person}"></td>
			</tr>
			 <tr style="height: 40">
				<td style="text-align: center;" colspan="6">
				  <a class="btn btn-mini btn-primary" onclick="save();">接收</a>
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
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		</script>
		
		<script type="text/javascript">
		
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
		if($("#receive_branch").val()==""){
			
			$("#receive_branch").tips({
				side:3,
	            msg:'请选择接收机构',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#receive_branch").focus();
			$("#receive_branch").val('');
			return false;
		}
		
		
		if($("#receiver").val()==""){
			
			$("#receiver").tips({
				side:3,
	            msg:'请输入接收人员',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#receiver").focus();
			return false;
		}
		if($("#receive_proof").val()==""){
			
			$("#receive_proof").tips({
				side:3,
	            msg:'请上传接收证明',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#receive_proof").focus();
			$("#receive_proof").val('');
			return false;
		}else{
			$("#assetReceivedForm").submit();
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
	            $("#receive_branch").append(option);
 	 		});
 		}); 
		
 		//申请公司的点击change事件
		function select_company() {
			var apply_company = document.getElementById("receive_branch");
	        var apply_dept = document.getElementById("receive_department");
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
	 				        $("#receive_department").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#receive_department").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
	}
	if(permission==3){
		var apply_person = '${apply_person}';//申请人
		var apply_company = '${apply_company}';//申请公司
		var apply_dept = '${apply_dept}';//申请部门
		if(apply_person!=""||apply_person!=null){
			$("#receiver").val(apply_person);
			$("#receiver").attr("readonly","readonly");
		}
		if(apply_company!=""||apply_company!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_company;
            option.selected='selected';
            $("#receive_branch").append(option);
		}
		if(apply_dept!=""||apply_dept!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_dept;
            option.selected='selected';
            $("#receive_department").append(option);
		}
	}
	</script>
	</body>
</html>