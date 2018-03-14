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
	<!-- jsp文件头和头部 -->
	<%@ include file="../admin/top.jsp"%>
	
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
		if($("#id").val()==""){
			
			$("#id").tips({
				side:3,
	            msg:'请输入资产编码',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#id").focus();
			return false;
		}
		if($("#time").val()==""){
			
			$("#time").tips({
				side:3,
	            msg:'输选择时间',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#time").focus();
			$("#time").val('');
			$("#time").css("background-color","white");
			return false;
		}
		
		if($("#bank").val()==""){
			
			$("#bank").tips({
				side:3,
	            msg:'请输入银行',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#bank").focus();
			return false;
		}else{
			$("#bank").val($.trim($("#bank").val()));
		}
		
		if($("#it_department").val()=="" ){
			
			$("#it_department").tips({
				side:3,
	            msg:'请选择部门',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#it_department").focus();
			return false;
		}
		if($("#ot_department").val()==""){
			
			$("#ot_department").tips({
				side:3,
	            msg:'请选择部门',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#ot_department").focus();
			return false;
		}
		if($("#manager").val()==""){
			
			$("#manager").tips({
				side:3,
	            msg:'请输入资产使用(管理)人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#manager").focus();
			return false;
		}
		
	
		if($("#receiver").val()==""){
			
			$("#receiver").tips({
				side:3,
	            msg:'请输入资产接收人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#receiver").focus();
			return false;
		}
		
		if($("#reason").val()==""){
			
			$("#reason").tips({
				side:3,
	            msg:'若没有原因请输入无',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#EMAIL").focus();
			return false;
		}
		
		if(($("#id").val()!="")&&($("#time").val()!="")&&($("#bank").val()!="")&&($
				("#it_department").val()!="")&&$("#ot_department").val()!=""&&$("#manager").val()!=""
					&&$("#receiver").val()!=""&&$("#reason").val()!=""){
			
	
			
	     $("#allotForm").submit(); 
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
	
</script>
	</head>
<body>
	<form action="asset/editAllot.do?" name="AllotForm" id="allotForm"
		method="post" >
		<input  type="hidden" name="id" id="allot_code" value="${allot.id}"
		 />
		<div id="zhongxin">
			<table id="table_report"
				class="text-table table table-striped table-bordered table-hover">
					<tr>
						<td><label>资产编码<b style="color: red">*</b>
						</label>
						</td>
						<td><input type="text" id="asset_code" value="${allot.asset_code}" name="asset_code" placeholder="资产编码"
							class="col-xs-10 col-sm-5" >
						</td>
						<td><label>调拨时间<b style="color: red">*</b>
						</label>
						</td>
						<td><input class="span10 date-picker" type="text"  id="time" name="allot_time" 
						value="${allot.allot_time}" data-date-format="yyyy-mm-dd" style="width: 210px;"
							>
						</td>
						<td><label>银行名称<b style="color: red">*</b></label>
						<td>
							<input type="text" id="bank" value="${allot.bank_name}" name="bank_name" placeholder="银行名称"  class="clo-xs-10 col-sm-5">
						</td>
					</tr>

					<tr>
						<td><label>调出部门<b style="color: red">*</b>
						</label>
						</td>
						 <td><input type="text" id="ot_department"
										name="ot_department"  value="${allot.ot_department}" placeholder="调出部门"
										class="col-xs-10 col-sm-5">
						</td>
								
						
						<td><label>调入部门<b style="color: red">*</b>
						</label>
						<td><input type="text" id="it_department"
										name="it_department"  value="${allot.it_department}" placeholder="调入部门"
										class="col-xs-10 col-sm-5">
						</td>

						<td><label>资产原管理（使用）人<b style="color: red">*</b>
						</label>
						</td>
						<td><input type="text" id="manager" name="asset_user" value="${allot.asset_user}"
							placeholder="资产原管理（使用）人" class="col-xs-10 col-sm-5">
						</td>
					</tr>


					<tr>
						
						<td><label>资产接收（使用）人<b style="color: red">*</b>
						</label>
						</td>
						<td><input type="text" id="receiver" name="asset_receiver" value="${allot.asset_receiver}"
							placeholder="资产接收（使用）人" class="col-xs-10 col-sm-5">
						</td>
	
						<td><label>调拨原因<b style="color: red">*</b>
						</label>
						</td>
						<td colspan="5"><textarea rows="2" id="reason" name="allot_reason"
								style="width: 95%">${allot.allot_reason}</textarea></td>

					</tr>

					<tr>
						<td style="text-align: center;" colspan="10"><a
							class="btn btn-mini btn-primary" onclick="save();">修改</a> <a
							class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
						</td>
					</tr>

				
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div> 

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
	<script type="text/javascript"
src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
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