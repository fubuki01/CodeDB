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
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	</head>
<body>
	<form action="user/${msg}.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }"/>
		<div id="zhongxin">
			<table class="table text-table table-striped table-bordered table-hover">									
				<tr>
					<td><label>用户名<b style="color: red">*</b></label></td>
					<td><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }"
						placeholder="请输入用户名"></td>

					<td><label>用户编号<b style="color: red">*</b></label></td>
					<td><input type="text" name="NUMBER" id="NUMBER"  value="${pd.NUMBER }"
						onblur="hasN('${pd.USERNAME }')" placeholder="请输入编号"></td>
				</tr>
				
				<tr>
					<td><label>密码<b style="color: red">*</b></label></td>
					<td><input type="password" name="PASSWORD" id="password"
						placeholder="请输入密码"></td>

					<td><label>确认密码<b style="color: red">*</b></label></td>
					<td><input type="password" name="chkpwd" id="chkpwd"
						placeholder="请确认密码"></td>
				</tr>
				
				<tr>
					<td><label>姓名<b style="color: red">*</b></label></td>
					<td><input type="text" name="NAME" id="name" value="${pd.NAME }"
						placeholder="请输入姓名"></td>
						
					<td><label>手机号<b style="color: red">*</b></label></td>
					<td><input type="input" name="PHONE" id="PHONE" value="${pd.PHONE }"
						placeholder="请输入手机号码(数字)" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/"></td>
				</tr>
				
				<tr>
					<td><label>邮箱<b style="color: red">*</b></label></td>
					<td><input type="email" name="EMAIL" id="EMAIL" value="${pd.EMAIL}"
						placeholder="请输入邮箱"></td>
						
					<td><label>备注内容<b style="color: red">*</b></label></td>
					<td><input type="BZ" name="BZ" id="BZ" value="${pd.BZ}" placeholder="请输入备注"></td>
				</tr>
				
				<tr>
					<td><label>申请公司<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select" data-placeholder="请选择申请公司"
						name="superior_organization_name" id="superior_organization_name" onchange="select_company();">
							<option></option>
								
					</select></td>
					<td><label>申请部门<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;">
					<select class="chzn-select" name="organization_name" id="organization_name"  data-placeholder="请选择下级机构"
						style="width: 221px; vertical-align: center;">
						<option></option>						
						</select>
					</td>
				</tr>
				
				<c:if test="${fx != 'head'}">
				<c:if test="${pd.ROLE_ID != '1'}">	
				<tr>
					<td><label>系统角色<b style="color: red">*</b></label></td>
					<td>
					<select class="chzn-select" name="ROLE_ID" id="role_id" data-placeholder="请选择系统角色" style="vertical-align:top;" >
					<option value=""></option>
					<c:forEach items="${roleList}" var="role">
					<!-- 默认现在只有一个级别的系统角色，如果需要改变就再用循环遍历就是了 -->
 					<option value="${role.ROLE_ID }" <c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>					
				<%--  					
					<option value="${role.ROLE_ID }" selected="selected">${role.ROLE_NAME }</option>					
 				--%> 					
 				</c:forEach>
					</select>
					</td>
				</c:if>
				<c:if test="${pd.ROLE_ID == '1'}">
				<input name="ROLE_ID" id="role_id" value="1" type="hidden" />
				</c:if>
				</c:if>
				
				<c:if test="${fx == 'head'}">
					<input name="ROLE_ID" id="role_id" value="${pd.ROLE_ID }" type="hidden" />
				</c:if>

					<td>
					<label>部门权限等级<b style="color: red">*</b></label>
					</td>
					<td>
					<select name="user_Permission" id="user_Permission" data-placeholder="请选择用户权限等级" onchange="changeDepartmentSel(this.value)">
						<option value="nopermission">请选择用户部门权限</option>
						<c:forEach items="${userDepartmentAuthoritys}" var="authority">
							<option value="${authority.authority_Code}" <c:if test="${pd.user_Permission==authority.authority_Code }">selected</c:if>>${authority.authority_Code}(${authority.authority_Name})</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				
				<tr>
					<th><font style="color: red;font-size: 16px;">温馨提示</font></th>
					<td colspan="6">
					<input style="width: 100%;border:none;" type="text" readonly="readonly" value="权限等级选择中，1为最高权限，依次递减">
					</td>
				</tr>
			</table>
			
			<div style="text-align: center;">
				<a class="btn  btn-primary" onclick="save();" style="margin-right: 30px;">保存</a>
				<a class="btn  btn-danger" onclick="top.Dialog.close();">取消</a>
			</div>
		</table>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
		
		<script type="text/javascript">
			$(top.hangge());
			
			//-----------------------二级联动
			var zn = '${institutionInfo}';
			var jsons = JSON.parse(zn)  
			var superior_organization_name_value = '${pd.superior_organization_name}'; //获取上一级部门
			var organization_name_value = '${pd.organization_name}'; //获取当前部门
			if(organization_name != '' || organization_name != null){
				select_company_edit();
			}
			//开始进入 初始化公司下拉框
			$.each(jsons,function(key, value){
				$.each(value,function(key, value){
					 var $option = '';   //选择选中的上一级部门
		 			 if(key == superior_organization_name_value){
		 				 $option = $('<option selected>'+key+'</option>');
		 			}
		 			else{ 
		 				 $option = $('<option >'+key+'</option>');
		 			}
		 			$option.appendTo($("#superior_organization_name"));
		 		});
			}); 
		
			//申请公司的点击change事件
		function select_company() {
			var superior_organization_name = document.getElementById("superior_organization_name");
	        var organization_name = document.getElementById("organization_name");
			var options = superior_organization_name.options;
			var company_name = options[superior_organization_name.selectedIndex].innerHTML;
			organization_name.length = 1; //清除以前的的信息
			$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 				if(key==company_name){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i];
	 						var option = document.createElement("option");
	 				        option.innerHTML = te+"";
	 				        $("#organization_name").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#organization_name").trigger("liszt:updated");
	 	 		});
	 		}); 
		}
			
		//如果是处于编辑模式进来的，则需要显示对应的option公司内容(专门用户回显之前用户对应的公司内容)
		function select_company_edit() {
			$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 				if(key == superior_organization_name_value){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i];
	 						var $option = '';
	 						if( te == organization_name_value){
	 			 				 $option = $('<option selected>'+te+'</option>');
	 						}
	 						else{
	 			 				 $option = $('<option >'+te+'</option>');
	 						}
	 				        $("#organization_name").append($option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#organization_name").trigger("liszt:updated");
	 	 		});
	 		}); 			
		}
		//-----------------二级联动结束------------
		$(function() {	
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		
		$(document).ready(function(){
			if($("#user_id").val()!=""){
				$("#loginname").attr("readonly","readonly");
				$("#loginname").css("color","gray");
			}
		});
		
		//保存
		function save(){
			if($("#role_id").val()==""){				
				$("#role_id").tips({
					side:3,
		            msg:'选择角色',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#role_id").focus();
				return false;
			}
			if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
				
				$("#loginname").tips({
					side:3,
		            msg:'输入用户名',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#loginname").focus();
				$("#loginname").val('');
				$("#loginname").css("background-color","white");
				return false;
			}else{
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}
			
			if($("#NUMBER").val()==""){
				
				$("#NUMBER").tips({
					side:3,
		            msg:'输入编号',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#NUMBER").focus();
				return false;
			}else{
				$("#NUMBER").val($.trim($("#NUMBER").val()));
			}
			
			if($("#user_id").val()=="" && $("#password").val()==""){			
				$("#password").tips({
					side:3,
		            msg:'输入密码',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#password").focus();
				return false;
			}
			if($("#password").val()!=$("#chkpwd").val()){
				
				$("#chkpwd").tips({
					side:3,
		            msg:'两次密码不相同',
		            bg:'#AE81FF',
		            time:3
		        });
				
				$("#chkpwd").focus();
				return false;
			}
			if($("#name").val()==""){
				
				$("#name").tips({
					side:3,
		            msg:'输入姓名',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#name").focus();
				return false;
			}
			
			var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
			if($("#PHONE").val()==""){
				
				$("#PHONE").tips({
					side:3,
		            msg:'输入手机号',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#PHONE").focus();
				return false;
			}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
				$("#PHONE").tips({
					side:3,
		            msg:'手机号格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#PHONE").focus();
				return false;
			}
			
			if($("#EMAIL").val()==""){			
				$("#EMAIL").tips({
					side:3,
		            msg:'输入邮箱',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#EMAIL").focus();
				return false;
			}else if(!ismail($("#EMAIL").val())){
				$("#EMAIL").tips({
					side:3,
		            msg:'邮箱格式不正确',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#EMAIL").focus();
				return false;
			}
			
			//判断用户是否选择了公司
			if($('#superior_organization_name').val() == "" || $('#superior_organization_name').val() == null ){
				$("#superior_organization_name").tips({
					side:3,
		            msg:'选择用户所属公司，此项为必选项',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#superior_organization_name").focus();
				return false;
			}
			
			//判断用户是否选择了部门
			if($('#organization_name').val() == "" || $('#organization_name').val() == null ){
				$("#organization_name").tips({
					side:3,
		            msg:'选择用户所属部门，此项为必选项',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#organization_name").focus();
				return false;
			}
			
			//判断是否选择了用户在部门的权限，这个必须选择
			if($('#user_Permission').val() == "nopermission"){
				$("#user_Permission").tips({
					side:3,
		            msg:'选择用户在所属部门的权限，此项为必选项',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#user_Permission").focus();
				return false;
			}
			
			if($("#user_id").val()==""){
				hasU();
			}else{
				$("#userForm").submit();
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
						 $("#loginname").tips({
								side:3,
					            msg:'用户名已存在',
					            bg:'#AE81FF',
					            time:3
					        });
						setTimeout("$('#loginname').val('')",2000);
						$('#loginname').focus();
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
		
		//对权限下拉框选择的一些部门权限的选择限制，content参数是选择权限的ID
		function changeDepartmentSel(content){
			//首先获取到申请公司的内容
			var company = $('#superior_organization_name option:selected').text();
			//判断选择的公司是总行的还是支行的，如果是总行，那么部门权限就能够有1，2,3个等级，如果是支行，那么只有3等级
			var superInformation = '${superinstitutional}';	 //总行的所有名称
			var branchInformation = '${branchinstitutional}';//支行的所有名称
			if(superInformation.search(company) != -1){ //总行，则权限都可以为任意，所以不需要进行处理
				
			}
			else if(branchInformation.search(company) != -1){//支行
				if(content <= 2){ //当选择的权限符合普通用户的时候则进行弹出一个提示
					alert('支行用户的权限只能为<<---普通员工--->>权限');
					$('#user_Permission').val(3);
				}
			}
		}
		
	</script>
	
</body>
</html>