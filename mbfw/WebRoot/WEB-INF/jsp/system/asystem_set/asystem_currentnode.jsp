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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审批节点详细信息</title>

<!-- 引入 -->
<link rel="stylesheet" href = "static/css/bootstrap.min.css" type="text/css"></link>
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
</head>
<body>

	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
			<!-- 用一个隐藏框还存储是否可以进行编辑审核人员名单，默认进行不可以编辑，点击修改按钮才可以进行删除审核人 -->
			  <input type="hidden" name="saveifeditapprover" id="saveifeditapprover" value="editfail">
			   <form action="" method="post" name="updateApprovalNode" id="updateApprovalNode">
				 <!-- 用来保存着审核人员的id,这样来传送到后台 -->
				 <input type="hidden" id="saveapproverid" name="saveapproverid">
				 <!-- 保存点击的审核节点的ID -->
				  <input type="hidden" id="node_Id" name="node_Id" value="${nodecurrentinfo.node_Id}">
				<table id="table_report" class=" table-text table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>审批节点名称</th>
						<td>
							<input type="text" placeholder="审批节点名称" name="node_Name" id="node_Name" value="${nodecurrentinfo.node_Name}" disabled="disabled">
						</td>
						<th>审批通过最少人数</th>
						<td>
							<input type="text" placeholder="审批节点需要通过的最少人数" name="node_PassNumber" id="node_PassNumber" value="${nodecurrentinfo.node_PassNumber}"  disabled="disabled">
						</td>
						<th>审批总人数</th>
						<td>
							<input type="text" placeholder="审批节点的总审批人数" name="node_TotalNumber" id="node_TotalNumber" value="${nodecurrentinfo.node_TotalNumber}" disabled="disabled">
						</td>
					</tr>
					
					<!-- 关于显示审核人员列表内容 -->
					<tr>
						<th>添加审核人员</th>
						<td>
							<select id="showapproverpeople" name="showapproverpeople">
								<option>选择要添加的人员</option>
								<c:forEach items="${approverpeople}" var="approver" varStatus="vs">
									<option value="${approver.user_Id}">${approver.user_Name}</option>
								</c:forEach>
							</select>
							<button type="button" class="btn btn-success" id="addApprover" name="addApprover" onclick="addApproverjs();" disabled="disabled">添加</button>						
						</td>
					</tr>
					
					<!-- 首先进行是显示之前对应该节点的审核人员名单 -->
					<tr>
						<th>节点审批人员名单</th>
						<td colspan="6" id="approverOrder" style="height: 80px;width: 92%">						
							<c:forEach items="${currentNodeApprvoverInfo}" var="approverinfo">
								<div style="display:inline-block;border:1px #000000 solid;margin: 10px;" id ="${approverinfo.user_Id}">
									<span>${approverinfo.user_Name}</span>
									<button type="button" name="${approverinfo.user_Id}" id="${approverinfo.user_Id}" onclick="deleteApprover('${approverinfo.user_Id}');" class="btn btn-danger">删除</button>
								</div>
							</c:forEach>
						</td>
					</tr>	
											
					<tr>
						<th>审批节点描述</th>
						<td colspan="6">
							<textarea rows="6" cols="30" style="width: 98%" name="node_Description" id="node_Description" disabled="disabled">${nodecurrentinfo.node_Description}</textarea>
						</td>
					
					</tr>	
					<tr>
 						<th>当前状态</th>	
 						<td colspan="6">
 							<textarea  rows="1" cols="80" style="width: 98%"  readonly="readonly" >(1)需要修改内容，请点击修改后，才能进行修改操作;(2)点击取消返回节点列表界面；(3)点击保存进行，保存内容修改 </textarea>
 						</td>								
					</tr>
					
					<tr>
						<td style="text-align:center;" colspan="6">		
							<button class="btn btn-success" type="button" onClick="updatecontent()"> <i class="ace-icon fa fa-check bigger-110"></i>修改</button>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-info" type="button" onClick="submmitcontent()"> <i class="ace-icon fa fa-check bigger-110"></i> 提交 </button>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="btn btn-danger" type="button" onClick="cancelcontent()"> <i class="ace-icon fa fa-check bigger-110"></i> 取消</button>
						</td>
					</tr>							
					</thead>
				</table>
				</form>			
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
	//点击修改按钮的操作
	function updatecontent(){
		//先判断当前修改的这个内容是否被审批流程中有使用，如果有，则无法进行修改操作(这里复用判断能够删除操作的controller方法)
		var nodeid = $('#node_Id').val();
		$.ajax({
			url:'${pageContext.request.contextPath}/asset/asystem_currentnodedeleteStatus',
			async:false,
			data:{"nodeid":nodeid},
			type:'POST',
			success:function(data){
				if(data.deleteResult =="YES"){						
					alert("请进行修改操作");
					//将不可编辑的内容变为可以编辑
					$('#node_Name').removeAttr('disabled');
					$('#node_PassNumber').removeAttr('disabled');
					$('#node_TotalNumber').removeAttr('disabled');
					$('#node_Description').removeAttr('disabled');
					$('#addApprover').removeAttr('disabled');
					$('#saveifeditapprover').val('editSuccess');//修改隐藏框中的内容，从而表示能够对审核人员名单进行修改					
				}
				else if(data.deleteResult =="NO"){
					alert("审批流程数据条目中，存在使用该审批节点，无法进行修改操作，请确认!!");
				}
			},
			error:function(){
				alert("网络出现问题，请稍后再试");
			},
			dataType:"json"
		});		
	}
	//点击取消按钮的操作
	function cancelcontent(){
		window.location = '${pageContext.request.contextPath}/asset/asystem_showallnode';
	}
	
	//点击保存按钮的操作
	function submmitcontent(){
		//如果没有进行修改过内容，那么就提示不能进行保存
		if($('#saveifeditapprover').val() == "editfail"){
			alert("没有任何信息修改，请点击取消");
		}
		else{
			//(1)先进行处理填写的内容是否符合规范，这里先不进行处理
			 //节点名称
			if($('#node_Name').val() == "" || $('#node_Name').val() == null){
				$("#node_Name").tips({
					side:3,
		            msg:'请输入节点名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_Name").focus();
				return false;
			}
			//通过人数
			if($('#node_PassNumber').val() == "" || $('#node_PassNumber').val() == null){
				$("#node_PassNumber").tips({
					side:3,
		            msg:'请输入最小通过人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_PassNumber").focus();
				return false;
			}
			//最大人数
			if($('#node_TotalNumber').val() == "" || $('#node_TotalNumber').val() == null){
				$("#node_TotalNumber").tips({
					side:3,
		            msg:'请输入节点最多人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_TotalNumber").focus();
				return false;
			}
			//节点描述内容
			if($('#node_Description').val() == "" || $('#node_Description').val() == null){
				$("#node_Description").tips({
					side:3,
		            msg:'请输入节点描述信息',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_Description").focus();
				return false;
			}
			//判断添加的审核人员列表中的个数是否大于的填入的总的人数大小
			if($("#approverOrder").children().length > $('#node_TotalNumber').val()){
				$("#node_TotalNumber").tips({
					side:3,
		            msg:'输入的总人数小于审批列表人员个数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_TotalNumber").focus();
				return false;
			}
			//判断是否填入的总的审批人数小于最小通过的人数
			if($('#node_TotalNumber').val() < $('#node_PassNumber').val()){
				$("#node_PassNumber").tips({
					side:3,
		            msg:'输入的最小通过人员个数大于审批总人数',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#node_PassNumber").focus();
				return false;
			}
			
			
			
			//(2)规范通过后，再将审核人员的userid进行保存到一个隐藏框中，方便进行传送到后台进行处理	
			var approverId = "";
			//首先遍历审核人员顺序中的div标签的id，因为之前就已经把userid进行封装了
		  	$('#approverOrder div').each(function(i , n){
			  	approverId = approverId +"-"+$(this).attr("id"); //通过"-"来进行分割
		  	});
			//将得到的审核人员的userid放到form表单中的一个隐藏控制，方便后面进行提交到后台
			$('#saveapproverid').val(approverId);
			//(3)重定向页面进行发送请求，到后台进行处理
			$('#updateApprovalNode').attr({action:'asset/asystem_editapprovalnodeinfo.do'});
			$('#updateApprovalNode').submit();	
		}
	}
	
	//点击审核人员的删除按钮，进行删除，通过userid
	function deleteApprover(userid){
		//首先判断是否可以进行编辑审核人员，当隐藏的input框中的value 为editfail，则不可以编辑，editsuccess则可以编辑
		if($('#saveifeditapprover').val() == "editfail"){
			alert("请先点击修改按钮，才能进行删除操作！");
		}
		else{
			//遍历整个审核人列表的td，找到是点击取消的这个userid的div进行移除
			$('#approverOrder div').each(function(i , n){
			  	//判断是否是点击的那个内容的ID，如果是就进行删除
			  	if($(this).attr("id") == userid ){
				 	 $(this).remove();
			  	}
		  	}); 
		}
	}
	
	
	//点击添加按钮，进行添加审核人员信息
	function addApproverjs(){
		//获取下拉列表中选中的内容
		var selectvalue = $("#showapproverpeople").val();
		var selectshow = $("#showapproverpeople option:checked").text();//获取选中的显示内容
		if( selectshow == "选择要添加的人员"){
			alert("请选择所要添加的名单哦！！");
		}
		else{
			//将选中的内容添加到审核人员顺序中
			var $addcontent = $('<div style="display:inline-block;" id="'+selectvalue+'"><label>'+selectshow+'</label><button class="btn btn-danger" type="button"  onclick="deleteApprover(\''+selectvalue+'\');">取消</button></div>');
			//添加到审核人员的td标签中
			$addcontent.appendTo($("#approverOrder"));
			alert("添加成功!");
		}  
	}
	
	</script>
</body>
</html>