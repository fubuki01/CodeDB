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
	<form action="asset/edit_Ly.do" name="editLy" id="editLy" method="post">
	    <input type="hidden" name="id" id="Id" value="${eLy.id}"
		 />
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>耗材编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_model" id="supplies_model" value="${eLy.supplies_model}"></td>
				<td><label>耗材名称<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_name" id="supplies_name" value="${eLy.supplies_name}"></td>
			</tr>
			<tr>
			<td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quantity" id="quantity" value="${eLy.quantity}"></td>
			    <td><label>领用人<b style="color: red">*</b></label></td>
			    <td><input type="text" name="leader" id="leader" value="${eLy.leader}"></td>
			    </tr>
			<tr>
				<td><label>领用部门<b style="color: red">*</b></label></td>
			    <td><input type="text" name="department" id="department" value="${eLy.department}"></td>
				<td><label>领用公司<b style="color: red">*</b></label></td>
			    <td><input type="text" name="company" id="company" value="${eLy.company}"></td>
			    </tr>
			<tr>
				<td><label>发放人<b style="color: red">*</b></label></td>
				<td><input type="text" name="release_people" id="release_people"  value="${eLy.release_people}"></td>
			    <td><label>领用时间<b style="color: red">*</b></label></td>
		 <td>
					<input class="span10 date-picker" name="time" id="time"  value="${eLy.time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="领用日期(必填)" title="领用日期"/>
				</td>
			    </tr>
			
			
			
			<tr>
				<td><label>备注：</label></td>
				<td colspan="6"><textarea rows="3" cols="40"placeholder="备注:" style="width: 87%" name="remarks" value="${eLy.remarks}"></textarea></td>
			</tr>

			 <tr style="height: 40">
				<td style="text-align: center;" colspan="4">
				  <a class="btn btn-mini btn-primary" onclick="save();">提交</a>
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
$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});



$(document).ready(function(){
if($("#id").val()!=""){
	$("#loginname").attr("readonly","readonly");
	$("#loginname").css("color","gray");
}
});

//保存
function save(){
	if($("#supplies_model").val()==""){
		 $("#supplies_model").tips({
			side:3,
            msg:'请输入耗材编码',
            bg:'#AE81FF',
            time:2
        }); 
		
		$("#supplies_model").focus();
		return false;
	}
	
	if($("#supplies_name").val()=="" ){
		
		$("#supplies_name").tips({
			side:3,
            msg:'请选择耗材名称',
            bg:'#AE81FF',
            time:2
        });
		
		$("#supplies_name").focus();
		return false;
	}
	
	if($("#quantity").val()==""){
		
		$("#quantity").tips({
			side:3,
            msg:'请输入实际数量',
            bg:'#AE81FF',
            time:2
        });
		$("#quantity").focus();
		return false;
	}
	
	if($("#leader").val()==""){
		
		$("#leader").tips({
			side:3,
            msg:'请输入领用人',
            bg:'#AE81FF',
            time:2
        });
		$("#leader").focus();
		return false;
	}

	if($("#department").val()==""){
		
		$("#department").tips({
			side:3,
            msg:'请输入领用部门',
            bg:'#AE81FF',
            time:2
        });
		$("#department").focus();
		return false;
	}
	
   if($("#company").val()==""){
		
		$("#company").tips({
			side:3,
            msg:'请输入领用公司',
            bg:'#AE81FF',
            time:2
        });
		$("#company").focus();
		return false;
	}
   if($("#release_people").val()==""){
		
		$("#release_people").tips({
			side:3,
           msg:'请输入发放人',
           bg:'#AE81FF',
           time:2
       });
		$("#release_people").focus();
		return false;
	}
   
   if($("#time").val()==""){
		
		$("#time").tips({
			side:3,
           msg:'请输入申请时间',
           bg:'#AE81FF',
           time:2
       });
		$("#time").focus();
		$("#time").val('');
		$("#time").css("background-color","white");
		return false;
	}

	
	if(   ($("#id").val()!="")  &&  ($("#supplies_model").val()!="")   &&($("#supplies_name").val()!="")  &&  ($
			("#quantity").val()!="")   &&   ($("#department").val()!="")  &&  ($("#leader").val()!="")
				&&   ($("#release_people").val()!="")   &&  ($("#time").val()!="")   &&  ($("#remarks").val()!="")   &&  ($("#company").val()!="")  
				                        ){
		

		
    $("#editLy").submit(); 
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
		
		
		
		
		
		
		
	</body>
</html>