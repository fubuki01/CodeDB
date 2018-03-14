<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	<form action="asset/${msg }.do" name="asset_issuedForm" id="asset_issuedForm" method="post" enctype="multipart/form-data" >
		<div id="zhongxin">
		<input type="hidden" name="id" id="id" value="${id }">
		<input  type="hidden" name="idd" id="idd" value="${issued_bill.id }">
		<input type="hidden" name="receive" id="receive" value="${receive }"> 
		<input type="hidden" name="file_address" id="file_address" value="${file_address }">
		<table  id="table_report" class="table text-table table-striped table-bordered">
			<tr>
				<td colspan="4"><h1 style="color:red;">资产下发</h1></td>
			</tr>
			<c:if test="${issue =='yes' }">
			<tr>
				<td><label>资产编码<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_code" id="asset_code" placeholder="资产编码" value="${asset_code }" readonly="readonly"></td>
				<td><label>下发人员<b style="color: red">*</b></label></td>
				<td><input type="text" name="issue_person" id="issue_person" placeholder="下发人员"  value="${issued_bill.issue_person}"></td>
			</tr>
			<tr>
				<td><label>下发机构<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" data-placeholder="下发机构" name="issued_branch" id="issued_branch" onchange="select_company();">
					<option></option>
				</select>
				</td>
				<td><label>下发部门<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" data-placeholder="下发机构" name="issued_department" id="issued_department" >
					<option></option>
				</select>
				</td>
			</tr>
			<tr>
				<td><label>说明：</label></td>
				<td colspan="3">
					<textarea rows="2" cols="40" style="width:887px;" name="instruction" id="instruction">${issued_bill.instruction}</textarea>
				</td>
			</tr>
			
			</c:if>
			<c:if test="${receive =='yes' }">
			<tr>
				<td><label>资产编码<b style="color: red">*</b></label></td>
				<td><input type="text" name="asset_code" id="asset_code" placeholder="资产编码" value="${asset_code }" readonly="readonly"></td>
				<td><label>下发人员<b style="color: red">*</b></label></td>
				<td><input type="text" name="issue_person" id="issue_person" placeholder="下发人员" readonly value="${issued_bill.issue_person}"></td>
			</tr>
			<tr>
				<td><label>下发机构<b style="color: red">*</b></label></td>
				<td><input type="text" name="issued_branch" id="issued_branch" placeholder="下发机构" readonly value="${issued_bill.issued_branch}"></td>
				<td><label>下发部门<b style="color: red">*</b></label></td>
				<td><input type="text" name="issued_department" id="issued_department" placeholder="下发机构" readonly value="${issued_bill.issued_department}"></td>
			</tr>
			<tr>
				<td><label>接收机构<b style="color: red">*</b></label></td>
				<td style="vertical-align: top;">
				<select class="chzn-select" data-placeholder="接收机构" name="receive_branch" id="receive_branch" onchange="select_company_down();" >
					<option></option>
					</select>
				</td>
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
				<td><label>接收证明<b style="color: red">*</b></label></td>
				<td><%-- <input type="file"  style="width:221px;" name="file_receive" multiple id="file_receive" placeholder="接收证明"  value="${issued_bill.issue_person}"></td> --%>
				<input type="file"  name="file_receive" id="file_receive" multiple onchange="change_file(this.value);" style="width: 221px;vertical-align: center" ><br>
					<c:if test="${file_name !='no_file' }">
					<c:forTokens items="${file_name }" delims="#" var="split_file">
						<a class="file_name" id="ss" name="ss">${fn:split(split_file,"@")[1] }</a>&nbsp;
					</c:forTokens> 
					</c:if>
				</td>
			</tr>
			<tr>
				<td><label>说明：</label></td>
				<td colspan="3">
					<textarea rows="2" cols="40" style="width:887px;" name="instruction" id="instruction" readonly>${issued_bill.instruction}</textarea>
				</td>
			</tr>
			</c:if>

			 <tr style="height: 40">
				<td style="text-align: center;" colspan="6">
				  <a class="btn btn-mini btn-primary" onclick="save();">下发</a>
				  <a class="btn btn-mini btn-danger" onclick="issued_edit_close();">取消</a>
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
		function issued_edit_close(){
			window.location.href='<%=basePath%>asset/arda_assertIssued.do';
		}
		function change_file(value){
			$(".file_name").hide();
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
		var flag= '${receive}';
		if( flag== 'yes'){
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
			if($("#receive_department").val()==""){
				$("#receive_department").tips({
					side:3,
		            msg:'请选择接收部门',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#receive_department").focus();
				$("#receive_department").val('');
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
			if($("#file_receive").val()==""){
				
				$("#file_receive").tips({
					side:3,
		            msg:'请上传接收证明',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#file_receive").focus();
				$("#file_receive").val('');
				return false;
			}else{
				$("#asset_issuedForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}else{
			if($("#issued_branch").val()==""){
				
				$("#issued_branch").tips({
					side:3,
		            msg:'请选择下发机构',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#issued_branch").focus();
				$("#issued_branch").val('');
				return false;
			}
			if($("#issued_department").val()==""){
				
				$("#issued_department").tips({
					side:3,
		            msg:'请选择下发部门',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#issued_department").focus();
				$("#issued_department").val('');
				return false;
			}		
			
			if($("#issue_person").val()==""){
				
				$("#issue_person").tips({
					side:3,
		            msg:'请输入下发人员',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#issue_person").focus();
				return false;
			}else{
				$("#asset_issuedForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
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
	            $("#issued_branch").append(option);
 	 		});
 		}); 
		
 		//申请公司的点击change事件
		function select_company() {
			var apply_company = document.getElementById("issued_branch");
	        var apply_dept = document.getElementById("issued_department");
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
	 				        $("#issued_department").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#issued_department").trigger("liszt:updated");
	 	 		});
	 		}); 
			
		}
	}
	if(permission==3){
		var apply_person = '${apply_person}';//申请人
		var apply_company = '${apply_company}';//申请公司
		var apply_dept = '${apply_dept}';//申请部门
		if(apply_person!=""||apply_person!=null){
			$("#issue_person").val(apply_person);
			$("#issue_person").attr("readonly","readonly");
		}
		if(apply_company!=""||apply_company!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_company;
            option.selected='selected';
            $("#issued_branch").append(option);
		}
		if(apply_dept!=""||apply_dept!=null){
			var option = document.createElement("option");
            option.innerHTML = apply_dept;
            option.selected='selected';
            $("#issued_department").append(option);
		}
	}
	
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
		function select_company_down() {
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