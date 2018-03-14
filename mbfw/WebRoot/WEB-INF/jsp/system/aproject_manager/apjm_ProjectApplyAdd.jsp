<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../admin/top.jsp"%>
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
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</head>
<body>

	<form action="asset/projectApplySave.do" name="addProjectMForm"
		id="addProjectMForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin" style="margin-left: 10px;margin-right: 10px;margin-top: 15px;">
			<!-- 隐藏传送审批ID -->
			<input type="hidden" id="apply_procedure_id"
				name="apply_procedure_id">
			<table id="table_report"
				class="table text-table table-striped table-bordered table-hover">
				<tr>
					<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>新增项目</h2></b></label></td>
				</tr>
				
				<tr>
					<td><label>项目名称<b style="color: red">*</b></label></td>
					<td><input type="text" name="apply_name" id="apply_name"
						placeholder="请输入项目名称"></td>
					
					<td><label>设备名称<b style="color: red">*</b></label></td>
					<td><select class="chzn-select" name="device_name"
						id="device_name" data-placeholder="设备名称-供应商"
						style="width: 221px; vertical-align: center;">
							<option></option>
							<c:forEach items="${product_find}" var="pf" varStatus="vs">
								<option
									value="${pf.product_code}@${pf.product_class}@${pf.product_name}@${pf.provider_name}">${pf.product_name}-${pf.provider_name}</option>
							</c:forEach>
					</select></td>
					
					<td><label>申请时间<b style="color: red">*</b></label></td>
					<td><input name="apply_time_show" id="apply_time_show" class="span10 date-picker" type="text" disabled="disabled"
						value="${apply_time }" data-date-format="yyyy-mm-dd" style="width: 208px;" placeholder="申请日期" title="申请日期" /></td>
					<!-- 隐藏元素 用于传送申请时间 因为readonly的情况下 
						还能进行选择 disable的情况下 不能进行值传递 只能使用隐藏元素传递值的方法-->
					<input type="text" name="apply_time" id="apply_time" style="display: none;"/>	
				</tr>

				<tr>
					<td><label>申请公司<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						data-placeholder="请选择申请公司" name="apply_company" id="apply_company"
						onchange="select_company();">
							<option></option>

					</select></td>
					
					<!-- 		class="chzn-select" -->
					<td><label>申请部门<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="apply_dept" id="apply_dept" data-placeholder="请选择下级机构"
						style="width: 221px; vertical-align: center;">
							<option></option>
					</select></td>
					
					<td><label>申请人<b style="color: red">*</b></label></td>
					<td><input type="text" name="apply_person" id="apply_person"
						placeholder="申请人"></td>
				</tr>

				<tr>
					
					<td><label>型号<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_model" id="device_model"
						placeholder="型号"></td>
						
					<td><label>市场价格（元）<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_price" id="device_price"
						placeholder="资产预单价"></td>

					<td><label>数量（个）<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_number" id="device_number"
						placeholder="资产数量"></td>
				</tr>


				<tr>
					<td><label>设备用途<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_purpose"
						id="device_purpose" placeholder="设备用途"></td>

					<td><label>预计使用年限(年)<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_use_years"
						id="device_use_years" placeholder="预计使用年限"></td>
					
					<td><label>审批流程<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="apply_procedure" id="apply_procedure"
						data-placeholder="请选择审批流程"
						style="width: 221px; vertical-align: center">
							<option></option>
							<c:forEach items="${approvalProcess }" var="process">
								<option id=${process.process_Id }>${process.process_Name}</option>
							</c:forEach>
					</select></td>
				</tr>


				<tr>
					<td><label>上传申请报告 </label></td>
					<td style="vertical-align: middle;"><input type="file"
						name="address" id="apply_report_address" placeholder="上传申请报告"
						style="width: 221px"></td>

					<td><label>申请备注</label></td>
					<td colspan="3"><textarea rows="2" cols=""
							name="apply_remarks" id="apply_remarks" style="width: 91%"> </textarea></td>
				</tr>
				
				<tr>
					<td><label>申请原因</label></td>
					<td colspan="6"><textarea rows="2" cols="" name="apply_reason" 
							id="apply_reason" style="width: 95%"> </textarea></td>
				</tr>


				<tr style="height: 40">
					<td style="text-align: center;" colspan="6"><a
						class="btn  btn-danger" onclick="save();">提交申请</a> <a
						class="btn  btn-primary" href="asset/atp_showForm.do">取消</a>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div>

	</form>


	<script type="text/javascript">
		$(top.hangge());
		var permission = '${permission}';
		if(permission==1||permission==2){
			var zn = '${institutionInfo}';
	 		var jsons = JSON.parse(zn)  
	 		//开始进入 初始化公司下拉框
	 		$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 	 			var option = document.createElement("option");
		            option.innerHTML = key+"";
		            $("#apply_company").append(option);
	 	 		});
	 		}); 
			
	 		//申请公司的点击change事件
			function select_company() {
				var apply_company = document.getElementById("apply_company");
		        var apply_dept = document.getElementById("apply_dept");
				var options = apply_company.options;
				var company_name = options[apply_company.selectedIndex].innerHTML;
				apply_dept.length = 1; //清除以前的的信息
				$.each(jsons,function(key, value){
		 			$.each(value,function(key, value){
		 				if(key==company_name){
		 					for (var i=0;i<value.length;i++) {
		 						var te = value[i];
		 						var option = document.createElement("option");
		 				        option.innerHTML = te+"";
		 				        $("#apply_dept").append(option); 
		 			        }
		 				}
		 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
		 				$("#apply_dept").trigger("liszt:updated");
		 	 		});
		 		}); 
				
			}
		}
		if(permission==3){
			var apply_person = '${apply_person}';//申请人
			var apply_company = '${apply_company}';//申请公司
			var apply_dept = '${apply_dept}';//申请部门
			if(apply_person!=""||apply_person!=null){
				$("#apply_person").val(apply_person);
				$("#apply_person").attr("readonly","readonly");
			}
			if(apply_company!=""||apply_company!=null){
				var option = document.createElement("option");
	            option.innerHTML = apply_company;
	            option.selected='selected';
	            $("#apply_company").append(option);
			}
			if(apply_dept!=""||apply_dept!=null){
				var option = document.createElement("option");
	            option.innerHTML = apply_dept;
	            option.selected='selected';
	            $("#apply_dept").append(option);
			}
		}
		
		//日期框
		$('.date-picker').datepicker();
		
		//申请时间设置为当前日期
		//获取当前时间，格式YYYY-MM-DD
    	function getNowFormatDate() {
        	var date = new Date();
        	var seperator1 = "-";
        	var year = date.getFullYear();
        	var month = date.getMonth() + 1;
        	var strDate = date.getDate();
        	if (month >= 1 && month <= 9) {
            	month = "0" + month;
       		}
        	if (strDate >= 0 && strDate <= 9) {
            	strDate = "0" + strDate;
        	}
        	var currentdate = year + seperator1 + month + seperator1 + strDate;
        	return currentdate;
    	}
		var mData = getNowFormatDate();
 		$("#apply_time").val(mData);
 		$("#apply_time_show").val(mData);
 		
		
		
		
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({
				//是否允许取消选择
				 allow_single_deselect : true
			});

			//复选框
			$('table th input:checkbox').on(
					'click',
					function() {
						var that = this;
						$(this).closest('table').find(
								'tr > td:first-child input:checkbox').each(
								function() {
									this.checked = that.checked;
									$(this).closest('tr').toggleClass(
											'selected');
								});

					});

		});
		//价格或数字的验证
		function isNumber(number){
	        return (new RegExp(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/).test(number));
	    }
		//正整数
		function isPInt(str) {
		    var g = /^[1-9]*[1-9][0-9]*$/;
		    return g.test(str);
		}
		//中文验证
		function isChina(s) { 
			var patrn= /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi; 
			if (!patrn.exec(s)) { 
				return false; 
			}else{ 
				return true; 
			} 
		} 
		
		//保存
		function save() {
			if ($("#apply_name").val().replace(/\s+/g,'') == "") {

				$("#apply_name").tips({
					side : 3,
					msg : '请输入项目名',
					bg : '#AE81FF',
					time : 2
				});

				$("#apply_name").focus();
				$("#apply_name").val('');
				$("#apply_name").css("background-color", "white");
				return false;
			} else {
				$("#apply_name").val($.trim($("#apply_name").val()));
			}
			
			if ($("#device_name").val().replace(/\s+/g,'') == "") {

				$("#device_name").tips({
					side : 3,
					msg : '请输入设备名称',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_name").focus();
				$("#device_name").val('');
				return false;
			}
			
			
			if ($("#apply_company").val() == "") {

				$("#apply_company").tips({
					side : 3,
					msg : '请选择所属公司',
					bg : '#AE81FF',
					time : 3
				});
				$("#apply_company").focus();
				return false;
			}
			
			if ($("#apply_dept").val() == "") {

				$("#apply_dept").tips({
					side : 3,
					msg : '请选择部门',
					bg : '#AE81FF',
					time : 3
				});
				$("#apply_dept").focus();
				return false;
			}

			
			if ($("#apply_person").val().replace(/\s+/g,'') == "") {

				$("#apply_person").tips({
					side : 3,
					msg : '请输入申请人',
					bg : '#AE81FF',
					time : 2
				});

				$("#apply_person").focus();
				$("#apply_person").val('');
				$("#apply_person").css("background-color", "white");
				return false;
			} else {
				$("#apply_person").val($.trim($("#apply_person").val()));
			}

			
			
			if ($("#device_model").val().replace(/\s+/g,'') == "") {

				$("#device_model").tips({
					side : 3,
					msg : '请输入设备型号',
					bg : '#AE81FF',
					time : 2
				});

				$("#device_model").focus();
				$("#device_model").val('');
				return false;
			}

			if ($("#device_price").val().replace(/\s+/g,'') == "") {

				$("#device_price").tips({
					side : 3,
					msg : '请输入市场价格',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_price").focus();
				$("#device_price").val('');
				return false;
			}else if($.trim($("#device_price").val())!=""){
	            if(!isNumber($.trim($("#device_price").val()))){
	                $("#device_price").tips({
	                    side:3,
	                    msg:'输入价格格式不正确',
	                    bg:'#AE81FF',
	                    time:3
	                });
	                $("#device_price").focus();
	                $("#device_price").val('');
	                return false;
	            }
	        }

			if ($("#device_number").val().replace(/\s+/g,'') == "") {

				$("#device_number").tips({
					side : 3,
					msg : '请输入具体数量',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_number").focus();
				$("#device_number").val('');
				return false;
			}
			if(!isPInt($("#device_number").val())){
				$("#device_number").tips({
					side : 3,
					msg : '输入格式不正确',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_number").focus();
				$("#device_number").val('');
				return false;
			}


			if ($("#device_purpose").val().replace(/\s+/g,'') == "") {

				$("#device_purpose").tips({
					side : 3,
					msg : '请输入设备用途',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_purpose").focus();
				$("#device_purpose").val('');
				return false;
			}

			if ($("#device_use_years").val().replace(/\s+/g,'') == "") {

				$("#device_use_years").tips({
					side : 3,
					msg : '请输入设备预计使用年限',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_use_years").focus();
				$("#device_use_years").val('');
				return false;
			}else if(!isPInt($("#device_use_years").val())){
				$("#device_use_years").tips({
					side : 3,
					msg : '输入格式不正确',
					bg : '#AE81FF',
					time : 3
				});
				$("#device_use_years").focus();
				$("#device_use_years").val('');
				return false;
			}



			if ($("#apply_procedure").val() == "") {

				$("#apply_procedure").tips({
					side : 3,
					msg : '请选择审批流程',
					bg : '#AE81FF',
					time : 3
				});
				$("#apply_procedure").focus();
				return false;
			}
			else {
				var id = $('#apply_procedure option:selected').attr("id");
				$("#apply_procedure_id").val(id);
				$("#addProjectMForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
	</script>

</body>
</html>