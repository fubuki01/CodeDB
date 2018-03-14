<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<form action="" method="post" name="addapproverform" id="addapproverform">
						<!-- 放置一个隐藏的input，来保存下拉列表选择的是哪一个用户的姓名，因为这样方便插入到数据库中 -->
						<input type="hidden" name="user_Name"  id="user_Name">
						<table width="100%">
							<tr>
								<td >
									<div class="col-xs-6">
									<h4 class="lighter">
									<i class="icon-hand-right icon-animated-hand-pointer blue"></i>
									<a data-toggle="modal" class="blue">添加审核人</a>
			
									<!-- 进行模糊匹配姓名，用来显示到对应的下拉列表中方便进行选择 -->
									<!-- <span class="input-icon">
										这里检索是通过审批人员得姓名来检索
										<input autocomplete="on"  type="text" name="researchName" id="researchName" value="" placeholder="输入姓名进行检索" />
										<i id="nav-search-icon" class="icon-search"></i>
									</span>						
									<button type="button" style="margin-left: -60px;margin-top: -13px;" class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i>检索</button> -->
								
									<select class="chzn-select" id="user_Id"  name="user_Id" placeholder="请选择用户" >
										<option value="commenduser">请选择用户</option>
										<!-- 循环产生用户人员的信息 -->
										<c:forEach items="${approverinfo}" var="approver" varStatus="">
											<option value="${approver.USER_ID}">${approver.NAME}</option>
										</c:forEach>
									</select>	
									
									</h4>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<h4 class="lighter">
										<i class="icon-hand-down icon-animated-hand-pointer blue"></i>
										<a data-toggle="modal" class="blue">审批人描述信息 </a>
										</h4>
								</td>	
							</tr>
							<tr>
								<td>
									<textarea name="approver_Description" id="approver_Description" rows="4" cols="" style="width: 100%" placeholder="请输入审批人员信息" title="审批人员信息"></textarea>																			
								</td>
							</tr>
							<tr>
								<td>
									<h4 class="lighter">
										<i class="icon-hand-down icon-animated-hand-pointer blue"></i>
										<a data-toggle="modal" class="blue">审批人权限描述信息 </a>
										</h4>
								</td>
							</tr>							
							<tr>
								<td>
									<textarea rows="5" cols="" placeholder="输入审批权限描述信息" style="width:100%;" name="approver_Rights_Description" id="approver_Rights_Description"></textarea>
								</td>
							</tr>				
						</table>
					</form>
					<div class="page-header position-relative" style="text-align: center;">
							<table style="width: 100%;">
								<tbody>
									<tr>
										<td>				
											<button class="btn btn-info" type="button"
												onClick="submmitContent();">
												<i class="ace-icon fa fa-check bigger-110"></i> 提交设置
											</button>
											
											<button class="btn btn-danger" type="button"
												onClick="backToShow();" style="margin-left: 80px;">
												<i class="ace-icon fa fa-check bigger-110"></i> 返回审批人员界面
											</button>
										</td>

										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
				</div>
			</div>
		</div>
	</div> 
	
	
	<!-- 引入 -->
		<script type="text/javascript">window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
		</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>

	<script type="text/javascript">
		$(function() {	
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 		
		});
		
	   
		function submmitContent(){
			//判断填写的内容是否规范
			//审批人信息	
			var issuccess = true;
			//判断下拉列表是否有选中用户
			if($('#user_Id').val() == "commenduser"){
				$("#user_Id").tips({
					side:3,
		            msg:'请选择一个用户',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#user_Id").focus();
				return false;				
			}					
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
			//将选择的下拉列表中的用户名字保存到隐藏的input中，方便后台处理
			var currentName =  $('#user_Id option:checked').text();
			$('#user_Name').val(currentName);
			//跳转
			$('#addapproverform').attr({action : 'asset/saveOneApprover.do'});
			$('#addapproverform').submit();

		}
		
		//处理返回界面的操作
		function backToShow(){
			if(confirm("确定要返回到审核人员显示界面吗？")){
				window.location = "${pageContext.request.contextPath}/asset/asystem_showapprover";
			}
		}
		
		//进行姓名的检索操作（废除了这个方法，用了控件去实现了这个功能）
		function search(){
			//(1)获取输入框中的内容
			var researContent = $('#researchName').val();
			//(2)ajax请求数据
			var resultName = ""; //用于ajax中保存用户信息
			var resultId = ""; //用户ajax中保存用户ID
			 $.ajax({
				url:'${pageContext.request.contextPath}/asset/asystem_showresearchcontentresult',
				async:false,
				data:{"researContent":researContent},
				type:'POST',
				success:function(data){
					//(1)首先将原来的下拉列表中的数据全部清除
					$('#user_Id option').remove();
					if(data.length == 0){  //表示没有匹配到对应的信息内容
						var $content = $('<option value="commenduser">没有匹配到相关人员</option>');
						$content.appendTo($('#user_Id'));
					}
					else{
						//填充人员信息到下拉列表中
						$.each(data , function(key , value) {
							$.each(value , function(key , value){
								if(key == "NAME"){
									resultName = value;
								}
								if(key == "USER_ID"){
									resultId = value;
								}
							});
							//(2)添加下拉选择到下拉框中
							var $content = $('<option value="'+resultId+'">'+resultName+'</option>');
							$content.appendTo($('#user_Id'));
						});
					}
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