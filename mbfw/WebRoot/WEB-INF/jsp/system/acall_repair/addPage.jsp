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
	
	if($("#cost").val()==""){
		$("#cost").tips({
			side:3,
            msg:'请输入维修费用',
            bg:'#AE81FF',
            time:2
        });
		$("#cost").focus();
		return false;
	}
	if(!/^[0-9]*$/.test($("#cost").val())){
		$("#cost").tips({
			side:3,
            msg:'请输入数字',
            bg:'#AE81FF',
            time:2
        });
		$("#cost").focus();
		return false;
	}
	
	if($("#drep_department").val()==""){
		$("#drep_department").tips({
			side:3,
            msg:'请输入维修机构',
            bg:'#AE81FF',
            time:2
        });
		$("#drep_department").focus();
		return false;
	}else{
		$("#drep_department").val($.trim($("#drep_department").val()));
	}	
	
	if($("#maintain_result").val()==""){
		$("#maintain_result").tips({
			side:3,
            msg:'请输入维修结果',
            bg:'#AE81FF',
            time:2
        });
		$("#maintain_result").focus();
		return false;
	}
	if($("#defect_time").val()==""){
		$("#defect_time").tips({
			side:3,
            msg:'请输入保修时间',
            bg:'#AE81FF',
            time:2
        });
		$("#defect_time").focus();
		return false;
	}
	if($("#status").val()==""){
		$("#status").tips({
			side:3,
            msg:'请输入保修状态',
            bg:'#AE81FF',
            time:2
        });
		$("#status").focus();
		return false;
	}
	
	
	if(($("#cost").val()!="")&&($("#drep_department").val()!="")&&($("#maintain_result").val()!="")&&($
			("#defect_time").val()!="")&&$("#status").val()!=""){

		
     $("#RAR1Form").submit(); 
	$("#zhongxin").hide();
	$("#zhongxin2").show();  
	}
}
//判断用户名是否存在
function hasU(){
	var USERNAME = $("#loginname").val();
	$.ajax({
		type: "POST",
		url: '<%=basePath%>happuser/hasU.do',
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
	var EMAIL = $("#EMAIL").val();
	$.ajax({
		type: "POST",
		url: '<%=basePath%>happuser/hasE.do',
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
	var NUMBER = $("#NUMBER").val();
	$.ajax({
		type: "POST",
		url: '<%=basePath%>happuser/hasN.do',
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
<div class="container-fluid" id="main-container">

		
			<div class="row-fluid">


				<div class="row-fluid">

	<form action="asset/saveRAR1.do" name="RAR1Form" id="RAR1Form"
		method="post">
		<input type="hidden" name="id" id="user_id"
			value="${sd.id }" />
		<div id="zhongxin">
			<table id="table_report" class="text-table table table-striped table-bordered table-hover">
				<tbody>
					<tr>
						<td><label>资产编码</label>
						</td>
						<td><input type="text" id="asset_code" name="asset_code" style="color:#000000;" value="${sd.asset_code} " onfocus="this.blur()" placeholder="资产编码"
							class="col-xs-10 col-sm-5">
						</td>
						<td><label>维修费用<b style="color: red">*</b></label>
						</td>
						<td><input type="text" id="cost" name ="cost" placeholder="维修费用" style="color:#000000;"
							class="col-xs-10 col-sm-5">
						</td>
					</tr>
					<tr>	
						<td><label>维修完成时间<b style="color: red">*</b></label>
						</td>
						<td><input type="text" id="finishi_time" name="finishi_time"  placeholder="维修完成时间" data-date-format="yyyy-mm-dd"
							class="span10 date-picker" style="width: 221px;color:#000000;">
						</td>
				

						<td><label>维修机构<b style="color: red">*</b></label>
						</td>
						<td><input type="text" id="drep_department" name ="drep_department" placeholder="维修机构" style="color:#000000;"
							class="col-xs-10 col-sm-5">
						</td>
					</tr>
					<tr>
						<td><label>维修结果<b style="color: red">*</b></label>
						</td>
						<td><select id="maintain_result" name="maintain_result" 
							style="color:#000000;">
								
								<option selected="selected" value="已维修">已维修</option>
								<option value="无法维修">无法维修</option>
								<option value="未维修">未维修</option>

						</select>
						</td>
				

						<td><label>保修截止时间<b style="color: red">*</b></label>
						</td>
						<td><input type="text" id="defect_time" name="defect_time"  placeholder="保修时间" data-date-format="yyyy-mm-dd"
							class="span10 date-picker" style="width: 221px;color:#000000;">
						</td>
					</tr>
					<tr>
						<td><label>保修状态<b style="color: red">*</b></label>
						</td>
						<td><select id="status" name="status"
							style="color:#000000;">
			
								<option selected="selected" value="在保修期">在保修期</option>
								<option value="已过保修期">已过保修期</option>
							</select></td>
						<td><label>备注</label>
						</td>
						<td><input type="text" id="remark" name ="remark" placeholder="备注"
							class="col-xs-10 col-sm-5">
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
		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div>

	</form>
</div>
</div>
</div>

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