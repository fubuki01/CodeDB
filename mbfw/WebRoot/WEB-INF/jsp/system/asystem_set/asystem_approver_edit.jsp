<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		
<script type="text/javascript">
	$(top.hangge());
			
	$(function(){
		//添加两个悬浮事件，进行友好的提示
		$('#approver_Description').hover(function(){
			$("#approver_Description").tips({
				side:3,
	            msg:'输入审批人描述信息',
	            bg:'#AE81FF',
	            time:4
	        });	
		});
		
		$('#approver_Rights_Description').hover(function(){
			$("#approver_Rights_Description").tips({
				side:3,
	            msg:'输入审批人权限描述信息',
	            bg:'#AE81FF',
	            time:4
	        });	
		});
	})
	
	
	//进行数据输入的提交处理
	function save(){
		//这里还要写对其中填写的内容进行判断是否合法
		if ($("#approver_Description").val() == ""|| $("#approver_Description").val() == null) {
				$("#approver_Description").tips({
					side:3,
		            msg:'请输入审批人描述信息',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#approver_Description").focus();
				return false;	
			}
			if ($("#approver_Rights_Description").val() == ""|| $("#approver_Rights_Description").val() == null) {
				$("#approver_Rights_Description").tips({
					side:3,
		            msg:'请输入审批人权限描述信息',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#approver_Rights_Description").focus();
				return false;	
			}
		//如果合法，则跳转到更新审核人员的界面
		 $('#editApproverForm').attr({action:'asset/asystem_editapprover.do'});
		 $('#editApproverForm').submit(); 
		
	}
</script>
</head>
<body>

	<form action="" name="editApproverForm" id="editApproverForm" method="post">
		<input type="hidden" name="user_Id" id="user_id" value="${pd.user_Id }"/>
		<div id="zhongxin">
		<table>			
			<tr><h7>用户姓名：</h7></tr>
			<tr>
				<td>
					<input type="text" name="user_Name" id="user_Name"  value="${pd.user_Name}"   placeholder="用户姓名" title="姓名" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<textarea rows="8" cols="80" placeholder="输入审批人描述信息" style="width: 500px;" name="approver_Description" id="approver_Description" title="输入审批人描述信息" >${pd.approver_Description}</textarea>
				</td>
			</tr>
			
			<tr>
				<td>
					<textarea rows="8" cols="80" placeholder="输入审批权限描述信息" style="width: 500px;" name="approver_Rights_Description" id="approver_Rights_Description" title="输入审批权限描述信息">${pd.approver_Rights_Description}</textarea>
				</td>
			</tr>
			
			<tr>
				<td style="text-align: center;">
					<a class="btn  btn-primary" onclick="save();">保存</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="btn  btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>		
	</form>	
</body>
</html>