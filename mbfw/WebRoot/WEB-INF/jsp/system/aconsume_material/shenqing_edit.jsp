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
	<form action="asset/edit_Sq.do" name="editSq" id="editSq" method="post">
	    <input type="hidden" name="id" id="Id" value="${eSq.id}"
		 />
		<div id="zhongxin">
		<table  id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>耗材编码<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_model" id="supplies_model" value="${eSq.supplies_model}"></td>
				<td><label>耗材名称<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_name" id="supplies_name" value="${eSq.supplies_name}"></td>
			</tr>
			<tr>
			<td><label>实际数量<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quantity" id="quantity" value="${eSq.quantity}"></td>
			    <td><label>用途<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplies_use" id="supplies_use" value="${eSq.supplies_use}"></td>
			    </tr>
			<tr>
				<td><label>品牌<b style="color: red">*</b></label></td>
			    <td><input type="text" name="brand" id="brand" value="${eSq.brand}"></td>
				<td><label>市场报价<b style="color: red">*</b></label></td>
			    <td><input type="text" name="Market_quotes" id="Market_quotes" value="${eSq.Market_quotes}"></td>
			    </tr>
			<tr>
				<td><label>耗材类型<b style="color: red">*</b></label></td>
				<td><input type="text" name="supplies_type" id="supplies_type"  value="${eSq.supplies_type}"></td>
			    <td><label>报价依据<b style="color: red">*</b></label></td>
			    <td><input type="text" name="quote_basis" id="quote_basis" value="${eSq.quote_basis}"></td>
			    </tr>
			
			<tr>
				<td><label>经办人<b style="color: red">*</b></label></td>
			    <td ><input type="text" name="manager" id="manager" value="${eSq.manager}"></td>
			    <td><label>供应商<b style="color: red">*</b></label></td>
			    <td><input type="text" name="supplier" id="supplier" value="${eSq.supplier}"></td>
			    </tr>
			<tr>
				<td><label>申请人<b style="color: red">*</b></label></td>
			    <td><input type="text" name="applicant" id="applicant" value="${eSq.applicant}"></td>
			    <td><label>申请公司<b style="color: red">*</b></label></td>
			    <td><input type="text" name="company_apply" id="company_apply" value="${eSq.company_apply}"></td>
				</tr>
			<tr>
			     <td><label>申请部门<b style="color: red">*</b></label></td>
			    <td><input type="text" name="applicant_sector" id="applicant_sector" value="${eSq.applicant_sector}"></td>			    
			    <td><label>申请时间<b style="color: red">*</b></label></td>
			    <td>
					<input class="span10 date-picker" name="time_apply" id="time_apply"  value="${eSq.time_apply}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:222px;" placeholder="申请日期(必填)" title="申请日期"/>
				</td>
				
			</tr>
			<tr>
				<td><label>资金来源<b style="color: red">*</b></label></td>
			    <td><input type="text" name="sour_of_funds" id="sour_of_funds" value="${eSq.sour_of_funds}"></td>
              <td><label>审批流程<b style="color: red">*</b></label></td>
			    <td><input type="text" name="apply_procedure" id="apply_procedure" value="${eSq.apply_procedure}"></td>
			    </tr>
			  <td><label>备注：</label></td>
				<td colspan="6"><textarea rows="3" cols="40"placeholder="备注:" style="width: 87%" name="remarks" value="${eSq.remarks}"></textarea></td>

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
	if($("#supplies_name").val()==""){
		
		$("#supplies_name").tips({
			side:3,
            msg:'请输入耗材名称',
            bg:'#AE81FF',
            time:2
        });
		
		$("#supplies_name").focus();
		$("#supplies_name").val('');
		$("#supplies_name").css("background-color","white");
		return false;
	}else{
		$("#supplies_name").val($.trim($("#supplies_name").val()));
	}
	
	if($("#supplies_model").val()==""){
		
		$("#supplies_model").tips({
			side:3,
            msg:'请输入耗材编码',
            bg:'#AE81FF',
            time:2
        });
		
		$("#supplies_model").focus();
		$("#supplies_model").val('');
		$("#supplies_model").css("background-color","white");
		return false;
	}else{
		$("#supplies_model").val($.trim($("#supplies_model").val()));
	}
	
	
	if($("#quantity").val()==""){
		
		$("#quantity").tips({
			side:3,
            msg:'请输入实际数量',
            bg:'#AE81FF',
            time:3
        });
		$("#quantity").focus();
		$("#quantity").val('');
		return false;
	}else if($.trim($("#quantity").val())!=""){
		if(!ismoney($.trim($("#quantity").val()))){
			$("#quantity").tips({
				side:3,
	            msg:'输入数量格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#quantity").focus();
			return false;
		}
	}
	
	
	
	if($("#supplies_use").val()==""){
		
		$("#supplies_use").tips({
			side:3,
            msg:'请输入用途',
            bg:'#AE81FF',
            time:3
        });
		$("#supplies_use").focus();
		return false;
	}

	if($("#brand").val()==""){
		
		$("#brand").tips({
			side:3,
            msg:'请输入品牌',
            bg:'#AE81FF',
            time:3
        });
		$("#brand").focus();
		return false;
	}
	
	
	if($("#Market_quotes").val()==""){
		
		$("#Market_quotes").tips({
			side:3,
            msg:'请输入市场报价',
            bg:'#AE81FF',
            time:3
        });
		$("#Market_quotes").focus();
		$("#Market_quotes").val('');
		return false;
	}else if($.trim($("#Market_quotes").val())!=""){
		if(!ismoney($.trim($("#Market_quotes").val()))){
			$("#Market_quotes").tips({
				side:3,
	            msg:'输入价格格式不正确',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#Market_quotes").focus();
			return false;
		}
	}
	

	if($("#supplies_type").val()==""){
		
		$("#supplies_type").tips({
			side:3,
            msg:'请输入耗材类型',
            bg:'#AE81FF',
            time:3
        });
		$("#supplies_type").focus();
		return false;
	}
	
	if($("#quote_basis").val()==""){
		
		$("#quote_basis").tips({
			side:3,
            msg:'请输入报价依据',
            bg:'#AE81FF',
            time:3
        });
		$("#quote_basis").focus();
		return false;
	}
	
	

	if($("#manager").val()==""){
		
		$("#manager").tips({
			side:3,
            msg:'请选择经办人',
            bg:'#AE81FF',
            time:3
        });
		$("#manager").focus();
		return false;
	}
	
	if($("#supplier").val()==""){
		
		$("#supplier").tips({
			side:3,
            msg:'请选择供应商',
            bg:'#AE81FF',
            time:3
        });
		$("#supplier").focus();
		return false;
	}
	
	if($("#applicant").val()==""){
		
		$("#applicant").tips({
			side:3,
            msg:'请选择申请人',
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
            time:2
        });
		
		$("#company_apply").focus();
		$("#company_apply").val('');
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
	if($("#time_apply").val()==""){
		
		$("#time_apply").tips({
			side:3,
            msg:'请选择申请时间',
            bg:'#AE81FF',
            time:3
        });
		$("#time_apply").focus();
		return false;
	}
	
	
	
	if($("#apply_procedure").val()==""){
		
		$("#apply_procedure").tips({
			side:3,
            msg:'请选择审批流程',
            bg:'#AE81FF',
            time:3
        });
		$("#apply_procedure").focus();
		return false;
	} 
if($("#sour_of_funds").val()==""){
		
		$("#sour_of_funds").tips({
			side:3,
            msg:'请输入资金来源',
            bg:'#AE81FF',
            time:3
        });
		$("#sour_of_funds").focus();
		return false;
	}
	

	else{
		//获取下拉列表中的所选择的流程的名字
 		var id = $('#apply_procedure option:selected').attr("id");
		$("#apply_procedure_id").val(id);
		
 		//$('#apply_name').val(safeStr($('#apply_name').val()));
		$("#editSq").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
}
// //保存
// function save(){
// 	if($("#supplies_model").val()==""){
// 		 $("#supplies_model").tips({
// 			side:3,
//             msg:'请输入耗材编码',
//             bg:'#AE81FF',
//             time:2
//         }); 
		
// 		$("#supplies_model").focus();
// 		return false;
// 	}
	
// 	if($("#supplies_name").val()=="" ){
		
// 		$("#supplies_name").tips({
// 			side:3,
//             msg:'请选择耗材名称',
//             bg:'#AE81FF',
//             time:2
//         });
		
// 		$("#supplies_name").focus();
// 		return false;
// 	}
	
// 	if($("#quantity").val()==""){
		
// 		$("#quantity").tips({
// 			side:3,
//             msg:'请输入实际数量',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#quantity").focus();
// 		return false;
// 	}
	
// 	if($("#supplies_use").val()==""){
		
// 		$("#supplies_use").tips({
// 			side:3,
//             msg:'请输入用途',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#supplies_use").focus();
// 		return false;
// 	}
	

// 	if($("#brand").val()==""){
		
// 		$("#brand").tips({
// 			side:3,
//             msg:'请输入品牌',
//             bg:'#AE81FF',
//             time:2
//         });
		
// 		$("#brand").focus();
// 		return false;
// 	}
	
		
// 	if($("#Market_quotes").val()==""){
		
// 		$("#Market_quotes").tips({
// 			side:3,
//             msg:'请输入市场报价',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#Market_quotes").focus();
// 		return false;
// 	}
	
	
	
// 	if($("#supplies_type").val()==""){
		
// 		$("#supplies_type").tips({
// 			side:3,
//             msg:'请输入耗材类型',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#supplies_type").focus();
// 		return false;
// 	}
	
	
//     if($("#quote_basis").val()==""){
		
// 		$("#quote_basis").tips({
// 			side:3,
//             msg:'请输入报价依据',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#quote_basis").focus();
// 		return false;
// 	}
	
//     if($("#sour_of_funds").val()==""){
		
// 		$("#sour_of_funds").tips({
// 			side:3,
//             msg:'请输入资金来源',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#sour_of_funds").focus();
// 		return false;
// 	}
    
// if($("#approver").val()==""){
		
// 		$("#approver").tips({
// 			side:3,
//             msg:'请输入审批人',
//             bg:'#AE81FF',
//             time:2
//         });
// 		$("#approver").focus();
// 		return false;
// 	}
	
// if($("#manager").val()==""){
	
// 	$("#manager").tips({
// 		side:3,
//         msg:'请输入经办人',
//         bg:'#AE81FF',
//         time:2
//     });
// 	$("#manager").focus();
// 	return false;
// }

// if($("#supplier").val()==""){
	
// 	$("#supplier").tips({
// 		side:3,
//         msg:'请输入供应商',
//         bg:'#AE81FF',
//         time:2
//     });
// 	$("#applicant").focus();
// 	return false;
// }

// if($("#applicant").val()==""){
	
// 	$("#applicant").tips({
// 		side:3,
//         msg:'请输入申请人',
//         bg:'#AE81FF',
//         time:2
//     });
// 	$("#applicant").focus();
// 	return false;
// }
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
   
   
//    if($("#time_apply").val()==""){
		
// 		$("#time_apply").tips({
// 			side:3,
//            msg:'请输入申请时间',
//            bg:'#AE81FF',
//            time:2
//        });
// 		$("#time_apply").focus();
// 		$("#time_apply").val('');
// 		$("#time_apply").css("background-color","white");
// 		return false;
// 	}

	
// 	if(   ($("#id").val()!="")  &&  ($("#supplies_model").val()!="")   &&($("#supplies_name").val()!="")  &&  ($
// 			("#quantity").val()!="")   &&   ($("#supplies_use").val()!="")  &&  ($("#brand").val()!="")
// 				&&   ($("#Market_quotes").val()!="")  &&  ($("#supplies_type").val()!="")  &&  ($("#quote_basis").val()!="")   &&  ($("#sour_of_funds").val()!="")   &&  ($("#approver").val()!="")  
// 				&&  ($("#manager").val()!="")   &&  ($("#supplier").val()!="")    &&  ($("#applicant").val()!="")  &&  ($("#applicant_sector").val()!="") &&  ($("#company_apply").val()!="")  
// 				&&  ($("#time_apply").val()!="")                         ){
		

		
//     $("#editSq").submit(); 
// 	$("#zhongxin").hide();
// 	$("#zhongxin2").show();   
	 
// 	} 
// }



		
		
		</script>
		
		
		
		
		
		
		
	</body>
</html>