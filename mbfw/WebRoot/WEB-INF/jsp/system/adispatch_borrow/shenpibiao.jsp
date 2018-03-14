<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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

<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		
	     $("#allotForm").submit(); 
		$("#zhongxin").hide();
		$("#zhongxin2").show();   
		 
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
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
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
			data : {
				NUMBER : NUMBER,
				USERNAME : USERNAME,
				tm : new Date().getTime()
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" != data.result) {
					$("#NUMBER").tips({
						side : 3,
						msg : '编号已存在',
						bg : '#AE81FF',
						time : 3
					});
					setTimeout("$('#NUMBER').val('')", 2000);
				}
			}
		});
	}
</script>
</head>
	
<body>
	<form action="asset/editAllot1.do?" name="AllotForm" id="allotForm"
		method="post" >
		<input type="hidden" name="id" id="allot_code"
			value="${sp.id}" />
		<div id="zhongxin">
			<table id="table_report"
				class="text-table table table-striped table-bordered table-hover">

					<!-- 检索  -->
					
					
							<tbody>
									<!-- 开始循环 -->

					<tr>
						<td><label>资产编码</label>
						</td>
						<td><input type="text" name="asset_code" id="form-field-1" value="${sp.asset_code}"
							 class="col-xs-10 col-sm-5"
							disabled="disabled">
						</td>
						<td><label>银行名称<b style="color: red">*</b>
						</label>
						<td><input type="text" id="bank" name="bank_name" value="${sp.bank_name}"
							name="bank_name" placeholder="银行名称" disabled="disabled" class="clo-xs-10 col-sm-5">
						</td>
						<td><label>调拨时间</label>
						</td>
						<td><input type="date" id="form-field-1" name="allot_time"
							value="${sp.allot_time}" class="col-xs-10 col-sm-5"
							disabled="disabled">
						</td>
					</tr>

					<tr>
						<td><label>调入部门</label></td>

						<td><select id="form-field-select-1"
							style="display: none :   ”" disabled="disabled">
								<option selected="selected" value="${sp.it_department}">${sp.it_department}</option>
								<option value="研发部">研发部</option>
								<option value="销售部">销售部</option>
								<option value="业务部">业务部</option>
								<option value="财会部">财会部</option>
						</select></td>
						<td><label>调出部门</label></td>
						<td><select id="form-field-select-2"
							style="display: none :   ”" disabled="disabled">
								<option selected="selected" value="${sp.ot_department}">${sp.ot_department}</option>
								<option value="销售部">销售部</option>
								<option value="研发部">研发部</option>
								<option value="业务部">业务部</option>
								<option value="财会部">财会部</option>
						</select></td>
					


				
						<td><label>资产原使用（管理）人</label>
						</td>
						<td><input type="text" name="asset_user" id="form-field-1"
							value="${sp.asset_user}" class="col-xs-10 col-sm-5"
							disabled="disabled">
						</td>
					</tr>
					<tr>
						<td><label>资产接收（使用）人</label>
						</td>
						<td><input type="text" name="asset_receiver"
							id="form-field-1" value="${sp.asset_receiver}"
							class="col-xs-10 col-sm-5" disabled="disabled">
						</td>
				

				

						<td><label>审批部门<b style="color: red">*</b>
						</label>
						</td>
						<td><input type="text" id="zanding" name="rt_agency"
							value="${sp.rt_agency}"></td>
						<td><label>资产调用状态 <b style="color: red">*</b>
						</label>
						</td>
						<td><select id="form-field-select-2" name="status" 
							style="display: none : ”">
								<option selected="selected" value="${sp.status}">${sp.status}</option>
								<option value="未调用">未调用</option>
								<option value="已调用">已调用</option>

						</select>
						</td>
					</tr>

					<tr>
						<td><label>调拨原因</label>
						</td>
						<td colspan="5"><textarea rows="2" id="form-field-11" name="allot_reason"
							
								style="width: 95%"
								disabled="disabled">${sp.allot_reason}</textarea>
						</td>
					</tr>
					<tr>
						<td><label>审批意见<b style="color: red">*</b>
						</label>
						</td>
						<td colspan="5"><textarea rows="2" id="form-field-11" name="idea"
							
								style="width: 95%">${sp.idea}</textarea>
						</td>
					</tr>

					<tr>
						<td style="text-align: center;" colspan="10"><a
							class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
							class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>
				</tbody>
								
						</table>

						<!-- PAGE CONTENT ENDS HERE -->
				</div>
				<!--/row-->
</form>

	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->

	<script type="text/javascript">
		$(function() {

			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});

			//日期框
			$('.date-picker').datepicker();

		});
	</script>

</body>
</html>