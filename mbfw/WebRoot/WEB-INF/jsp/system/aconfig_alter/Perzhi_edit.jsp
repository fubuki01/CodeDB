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
	<form action="asset/edit_Pz.do" name="editPz" id="editPz" method="post">
	    <input type="hidden" name="id" id="Id" value="${ePz.id}"
		 />
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>资产编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="asset_code" id="asset_code" value="${ePz.asset_code}"></td>
				<td><label>变更配置<b style="color: red">*</b></label></td>
			    <td><input type="text" name="chan_config" id="chan_config" value="${ePz.chan_config}"></td>
			</tr>
			<tr>
			    <td><label>配置保修截止日期<b style="color: red">*</b></label></td>
			    <td>
					<input class="span10 date-picker" name="deadline" id="deadline"  value="${ePz.deadline}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="配置保修截止日期(必填)" title="配置保修截止日期"/>
				</td>
		 		<td><label>变更原因<b style="color: red">*</b></label></td>
				<td><input type="text" name="reason_change" id="reason_change"  value="${ePz.reason_change}"></td>
			</tr>
			<tr>
				<td><label>配置来源<b style="color: red">*</b></label></td>
				<td><input type="text" name="config_sour" id="config_sour"  value="${ePz.config_sour}"></td>
				<td><label>配置费用<b style="color: red">*</b></label></td>
				<td><input type="text" name="config_cost" id="config_cost"  value="${ePz.config_cost}"></td>
			</tr>
			<tr>
				<td><label>申请人<b style="color: red">*</b></label></td>
			    <td><input type="text" name="applicant" id="applicant"value="${ePz.applicant}"></td>

				<td><label>申请部门<b style="color: red">*</b></label></td>
			    <td colspan="3"><input type="text" name="applicant_sector" id="applicant_sector"  value="${ePz.applicant_sector}" style="width: 221px;"></td>
			</tr>
			<tr>
				<td><label>申请公司<b style="color: red">*</b></label></td>
			    <td><input type="text" name="company_apply" id="company_apply" value="${ePz.company_apply}"></td>

				<td><label>申请时间<b style="color: red">*</b></label></td>
			    <td>
					<input class="span10 date-picker" name="time" id="time"  value="${ePz.time}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="申请日期(必填)" title="申请日期"/>
				</td>
			</tr>
			<tr>
				<td><label>备注：</label></td>
				<td colspan="4"><textarea rows="3" cols="40"placeholder="备注:" style="width: 87%" name="remarks"></textarea></td>
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
	if($("#chan_config").val()==""){
		
		$("#chan_config").tips({
			side:3,
            msg:'请输入变更配置',
            bg:'#AE81FF',
            time:2
        });
		
		$("#chan_config").focus();
		$("#chan_config").val('');
		$("#chan_config").css("background-color","white");
		return false;
	}else{
		$("#chan_config").val($.trim($("#chan_config").val()));
	}
	
	if($("#asset_code").val()==""){
		
		$("#asset_code").tips({
			side:3,
            msg:'请输入资产编码',
            bg:'#AE81FF',
            time:2
        });
		
		$("#asset_code").focus();
		$("#asset_code").val('');
		$("#asset_code").css("background-color","white");
		return false;
	}else{
		$("#asset_code").val($.trim($("#asset_code").val()));
	}
	
	if($("#deadline").val()==""){
		
		$("#deadline").tips({
			side:3,
            msg:'请输入配置保修截止日期',
            bg:'#AE81FF',
            time:3
        });
	//
		$("#deadline").focus();
		return false;
	}

	if($("#reason_change").val()==""){
		
		$("#reason_change").tips({
			side:3,
            msg:'请输入变更原因',
            bg:'#AE81FF',
            time:3
        });
		$("#reason_change").focus();
		return false;
	}
	
	
	if($("#config_sour").val()==""){
		
		$("#config_sour").tips({
			side:3,
            msg:'请输入配置来源',
            bg:'#AE81FF',
            time:3
        });
		$("#config_sour").focus();
		return false;
	}
	
	//验证价格
	if($("#config_cost").val()==""){
		
		$("#config_cost").tips({
			side:3,
            msg:'请输入配置费用',
            bg:'#AE81FF',
            time:3
        });
		$("#config_cost").focus();
		$("#config_cost").val('');
		return false;
	}else if($.trim($("#config_cost").val())!=""){
		if(!ismoney($.trim($("#config_cost").val()))){
			$("#config_cost").tips({
				side:3,
	            msg:'输入价格格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#config_cost").focus();
			return false;
		}
	}
	
	if($("#applicant").val()==""){
		
		$("#applicant").tips({
			side:3,
            msg:'请输入申请人',
            bg:'#AE81FF',
            time:3
        });
		$("#applicant").focus();
		return false;
	}
	
	if($("#company_apply").val()==""){
		
		$("#company_apply").tips({
			side:3,
            msg:'请输入申请公司',
            bg:'#AE81FF',
            time:3
        });
		$("#company_apply").focus();
		return false;
	}
	
	if($("#applicant_sector").val()==""){
		
		$("#applicant_sector").tips({
			side:3,
            msg:'请输入申请部门',
            bg:'#AE81FF',
            time:2
        });
		
		$("#applicant_sector").focus();
		$("#applicant_sector").val('');
		return false;
	}
	
	
	if($("#time").val()==""){
		
		$("#time").tips({
			side:3,
            msg:'请选择时间',
            bg:'#AE81FF',
            time:3
        });
		$("#time").focus();
		return false;
	}
	

	else{
		//获取下拉列表中的所选择的流程的名字
// 		var id = $('#apply_procedure option:selected').attr("id");
// 		$("#apply_procedure_id").val(id);
// 		$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#editPz").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}




// //保存
// function save(){
// 	if($("#asset_code").val()==""){
// 		 $("#asset_code").tips({
// 			side:3,
//             msg:'请输入资产编码',
//             bg:'#AE81FF',
//             time:2
//         }); 
		
// 		$("#asset_code").focus();
// 		return false;
// 	}
	
// 	if($("#chan_config").val()=="" ){
		
// 		$("#chan_config").tips({
// 			side:3,
//             msg:'请选择变更配置',
//             bg:'#AE81FF',
//             time:2
//         });
		
// 		$("#chan_config").focus();
// 		return false;
// 	}
	
// 	 if($("#deadline").val()==""){
			
// 			$("#deadline").tips({
// 				side:3,
// 	           msg:'请输入配置保修截止日期',
// 	           bg:'#AE81FF',
// 	           time:2
// 	       });
// 			$("#deadline").focus();
// 			$("#deadline").val('');
// 			$("#deadline").css("background-color","white");
// 			return false;
// 		}
	
// 	if($("#reason_change").val()==""){
		
// 		$("#reason_change").tips({
// 			side:3,
//             msg:'请输入变更原因',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#reason_change").focus();
// 		return false;
// 	}
	
// 	if($("#config_sour").val()==""){
		
// 		$("#config_sour").tips({
// 			side:3,
//             msg:'请输入配置来源',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#config_sour").focus();
// 		return false;
// 	}
	

// 	if($("#config_cost").val()==""){
		
// 		$("#config_cost").tips({
// 			side:3,
//             msg:'请输入配置费用',
//             bg:'#AE81FF',
//             time:2
//         });
		
// 		$("#config_cost").focus();
// 		return false;
// 	}
	
		
	
	
// 	if($("#applicant").val()==""){
		
// 		$("#applicant").tips({
// 			side:3,
//             msg:'请输入申请人',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#applicant").focus();
// 		return false;
// 	}
	
// 	if($("#applicant_sector").val()==""){
		
// 		$("#applicant_sector").tips({
// 			side:3,
//             msg:'请输入申请部门',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#applicant_sector").focus();
// 		return false;
// 	}
	
//    if($("#company_apply").val()==""){
		
// 		$("#company_apply").tips({
// 			side:3,
//             msg:'请输入申请公司',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#company_apply").focus();
// 		return false;
// 	}
   
   
//    if($("#time").val()==""){
		
// 		$("#time").tips({
// 			side:3,
//            msg:'请输入申请时间',
//            bg:'#AE81FF',
//            time:2
//        });
// 		$("#time").focus();
// 		$("#time").val('');
// 		$("#time").css("background-color","white");
// 		return false;
// 	}

	
// 	if(   ($("#id").val()!="")  &&  ($("#supplies_model").val()!="")   &&($("#supplies_name").val()!="")  &&  ($
// 			("#purchase_price").val()!="")   &&   ($("#bill").val()!="")  &&  ($("#location").val()!="")
// 				&&   ($("#location").val()!="")   &&  ($("#applicant").val()!="")  &&  ($("#applicant_sector").val()!="")
// 				&&  ($("#asset_code").val()!="")  &&  ($("#time").val()!="")  ){
		

		
//     $("#editPz").submit(); 
// 	$("#zhongxin").hide();
// 	$("#zhongxin2").show();   
	 
// 	} 
// }

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