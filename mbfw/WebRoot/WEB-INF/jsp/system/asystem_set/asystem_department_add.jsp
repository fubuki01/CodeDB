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
	</head>
<body>
	<form action="asset/asystem_departmentaddoption.do" method="post" name="adddepartmentform" id="adddepartmentform" >
			<div id="zhongxin">
					<!-- 下面的隐藏框用来提交负责人姓名数据到到后台 -->
					<input type="hidden" id="department_PrincipalName" name="department_PrincipalName" >
					<table id="table_report" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>部门代号码</th>
								<td><input type="text" id="department_Code" name="department_Code"
									placeholder="部门代号码" class="col-xs-10 col-sm-5">
								</td>
								
								<th>部门名称</th>
								<td><input type="text" id="department_Name" name="department_Name"
									placeholder="部门名称" class="col-xs-10 col-sm-5">
								</td>
							</tr>
							<tr>	
								<th>部门上级</th>
								<td><input type="text" id="department_Pro" name="department_Pro"
									placeholder="部门上级" class="col-xs-10 col-sm-5">
								</td>

								<th>部门等级</th>
								<td><input type="text" id="department_Grade" name="department_Grade"
									placeholder="部门等级" class="col-xs-4 col-sm-4">
								</td>
							</tr>
							<tr>	
								<th>部门类型</th>
								<td><input type="text" id="department_Style" name="department_Style"
									placeholder="部门类型" class="col-xs-4 col-sm-4">
								</td>
							
								<th>部门负责人</th>
								<td><select id="department_PrincipalId" name="department_PrincipalId" style="display: none:”">
											<option selected="selected" value="aaaa">研发部</option>
											<option value="bbbbb">销售部</option>
											<option value="cccccc">业务部</option>
											<option value="ddddddd">财会部</option>
								</select>
								</td>						
							</tr>

							<tr>
								<td style="text-align: center; " colspan="6">
									<button class="btn btn-info " onclick="sumbmitAdd();" style="margin-top: 20px;">
										<i class="icon-ok-sign"></i> 提交
									</button>
									<button class="btn  btn-danger" type="button"
										onClick="backDepartment()" style="margin-left: 50px;margin-top: 20px;">
										<i class="icon-home"></i> 取消
									</button>
								</td>
							</tr>

							<tr>
								<th>
									<label>温馨提示</label>
								</th>
								<td colspan="6">
									<textarea  style="color: red;width: 98%;font-size: 18px;"  placeholder="1、提交后进入部门列表;2、点击取消则返回部门显示主界面“"></textarea>									
								</td>
							</tr>
						</thead>
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
		<script type="text/javascript">
		$(top.hangge());
		
		//点击保存按钮的操作
		function sumbmitAdd(){
			if($("#department_Code").val()=="" || $("#department_Name").val()==null){		
				$("#department_Code").tips({
					side:3,
		            msg:'输入部门代号',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#department_Code").focus();
				return false;
			}
			if($("#department_Name").val()=="" || $("#department_Name").val()==null){
				
				$("#department_Name").tips({
					side:3,
		            msg:'输入部门名称',
		            bg:'#AE81FF',
		            time:2
		        });	
				$("#department_Name").focus();
				return false;
			}			
			if($("#department_Pro").val()=="" || $("#department_Pro").val()==null){
				
				$("#department_Pro").tips({
					side:3,
		            msg:'输入部门上级，如果没用上级，则填写本身部门',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#department_Pro").focus();
				return false;
			}	
			if($("#department_Grade").val()=="" || $("#department_Grade").val()==null){			
				$("#department_Grade").tips({
					side:3,
		            msg:'输入部门等级',
		            bg:'#AE81FF',
		            time:2
		        });
				
				$("#department_Grade").focus();
				return false;
			}
			if($("#department_Style").val()=="" ||$("#department_Style").val() == null){				
				$("#department_Style").tips({
					side:3,
		            msg:'请输入部门类型',
		            bg:'#AE81FF',
		            time:3
		        });			
				$("#department_Style").focus();
				return false;
			}
			if($("#department_PrincipalId").val()=="" ||$("#department_PrincipalId").val() == null){							
				$("#department_PrincipalId").tips({
					side:3,
		            msg:'输入部门负责人',
		            bg:'#AE81FF',
		            time:3
		        });
				$("#department_PrincipalId").focus();
				return false;
			}
			//如果都满足的话，就进行提交到后台
			//首先需要把下拉列表中的负责人的名字赋值到一个隐藏的input提交到后台，方便插入到数据库
			$('#department_PrincipalName').val($('#department_PrincipalId option:checked').text());
			$('#adddepartmentform').submit();
		}	
</script>
	
</body>
</html>