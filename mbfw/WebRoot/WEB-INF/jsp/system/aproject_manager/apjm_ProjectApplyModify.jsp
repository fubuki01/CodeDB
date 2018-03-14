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
	window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<style type="text/css">
.a-upload {
    padding: 4px 10px;
    height: 20px;
    line-height: 20px;
    position: relative;
    cursor: pointer;
    color: #888;
    background: #fafafa;
    border: 1px solid #ddd;
    border-radius: 4px;
    overflow: hidden;
    display: inline-block;
    *display: inline;
    *zoom: 1
}

.a-upload  input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
    filter: alpha(opacity=0);
    cursor: pointer
}

.a-upload:hover {
    color: #444;
    background: #eee;
    border-color: #ccc;
    text-decoration: none
}
</style>
</head>
<body>

	<form action="asset/atp_project_update.do" name="modifyProjectMForm"
		id="modifyProjectMForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin" style="margin-left: 10px;margin-right: 10px;margin-top: 15px;">
		<!-- 隐藏传送审批ID -->
		<input type="hidden" id="apply_procedure_id" name ="apply_procedure_id">
			<table id="table_report"
				class="table text-table table-striped table-bordered table-hover">
				
				<!-- 隐藏的元素 -->
				<div style="display: none;">
					<input name="apply_id" value="${pd.apply_id }"/>
					<input name="key" id="key"  value="${pd.key }"/>
					<input name="apply_report_address" value="${pd.apply_report_address }"/>
					<input name="apply_status" value="${pd.apply_status }"/>
								
				</div>
				
				<tr>
					<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>修改项目</h2></b></label></td>
				</tr>
				
				<tr>
					<td><label>项目名称<b style="color: red">*</b></label></td>
					<td><input type="text" name="apply_name" id="apply_name" value="${pd.apply_name }"
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
					<td><input name="apply_time" id="apply_time" type="text" class="span10 date-picker" 
						value="${pd.apply_time }" data-date-format="yyyy-mm-dd"
						style="width: 208px;" placeholder="申请日期" title="申请日期" /></td>
						
					
				</tr>
				
				<tr>
					<td><label>申请公司<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select" 
						name="apply_company" id="apply_company" data-placeholder="请选择申请公司"
						style="width: 221px; vertical-align: center" onchange="select_company();">
							<option></option>
					</select></td>

					<td><label>申请部门<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="apply_dept" id="apply_dept" data-placeholder="请选择申请部门"
						style="width: 221px; vertical-align: center">
							<option></option>
					</select></td>
					
					<td><label>申请人<b style="color: red">*</b></label></td>
					<td><input type="text" name="apply_person" id="apply_person" value="${pd.apply_person }"
						placeholder="申请人"></td>
				</tr>


				<tr>
					<td><label>型号<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_model" id="device_model" value="${pd.device_model }"
						placeholder="型号"></td>
					
					<td><label>市场价格（元）<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_price" id="device_price" value="${pd.device_price }"
						placeholder="资产预单价"></td>
						
					<td><label>数量（个）<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_number" id="device_number" value="${pd.device_number }"
						placeholder="资产数量"></td>
				</tr>

				<tr>
					<td><label>设备用途<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_purpose"  value="${pd.device_purpose }"
						id="device_purpose" placeholder="设备用途"></td>
						
					<td><label>预计使用年限(年)<b style="color: red">*</b></label></td>
					<td><input type="text" name="device_use_years" value="${pd.device_use_years }"
						id="device_use_years" placeholder="预计使用年限"></td>
						
					<td><label>审批流程<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="apply_procedure" id="apply_procedure"
						data-placeholder="请选择审批流程"
						style="width: 221px; vertical-align: center">
							<option></option>
							<c:forEach items="${approvalProcess }" var="process">
								<option id=${process.process_Id }>${process.process_Name }</option>
							</c:forEach>
					</select></td>
				</tr>

				
				<tr>
					<td><label>重新上传申请报告 </label></td>
					<td style="vertical-align: middle;"><a href='javascript:void(0);' class="a-upload">选择文件
							<input style="float:left" type="file" name="address" onchange="return myFileUpload(this.value);" /></a>
									
							<a href='javascript:void(0);' style="display: block; text-decoration:none; height:15px;float:right; 
							margin-right: 25%;padding-top: 5px;" class="show">${file_name }</a>
					</td>
						
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
						class="btn  btn-danger" onclick=" return modify();">修改</a> 
						<button type="button" class="btn  btn-primary" onclick="return rBank();">取消</button>
					</td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br />
			<br />
			<br />
			<br />
			<img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div>

	</form>


	<script type="text/javascript">
		$(top.hangge());
		//日期框
		$('.date-picker').datepicker({
			 todayHighlight : true,
			 startDate: "${pd.apply_time }"
		});
		
		var permission = '${permission}';
		var zn = '${institutionInfo}';
		if(permission==1||permission==2){
			//显示申请公司和部门的数据信息
	 		var jsons = JSON.parse(zn)  
	 		//开始进入 初始化公司下拉框
	 		$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 	 			var option = document.createElement("option");
		            option.innerHTML = key+"";
		            if(key=='${pd.apply_company }'){
		            	option.selected='selected';
		            }
		            $("#apply_company").append(option);
	 	 		});
	 		}); 
	 		
	 		
			$.each(jsons,function(key, value){
	 			$.each(value,function(key, value){
	 				if(key=='${pd.apply_company }'){
	 					for (var i=0;i<value.length;i++) {
	 						var te = value[i];
	 						var option = document.createElement("option");
	 						if(te=='${pd.apply_dept }'){
	 							option.selected='selected';
	 						}
	 				        option.innerHTML = te+"";
	 				        $("#apply_dept").append(option); 
	 			        }
	 				}
	 				/* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
	 				$("#apply_dept").trigger("liszt:updated");
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
	
		
		//显示设备名称
		var str = "${pd.device_name }";
	    var device_name = str.split("@")[2]+"-"+str.split("@")[3];
		$("#device_name option").each(function(){
			var text = $(this).text();
			if(text==device_name){
				$(this).attr("selected","selected");
			}
		})
		
		//显示审批流程
		$("#apply_procedure").val('${pd.apply_procedure }');
		
		//显示申请原因
		$("#apply_reason").val('${pd.apply_reason }');
		
		//显示备注
		$("#apply_remarks").val('${pd.apply_remarks }');
		
		
		function rBank() {
			var temp = '${pd.key}';
			window.location = '${pageContext.request.contextPath}/asset/atp_showForm.do?key='+encodeURI(encodeURI(temp));
		}
		
		$(function() {

			//日期框
			$('.date-picker').datepicker();

			//下拉框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
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

		function myFileUpload(value){
			var arrs = value.split('\\');
			var filename=arrs[arrs.length-1]; 
	        $(".show").html(filename); 
		}
		
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
		
		function modify() {
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
			
			
			if ($("#apply_time").val() == "") {

				$("#apply_time").tips({
					side : 3,
					msg : '请输入申请时间',
					bg : '#AE81FF',
					time : 3
				});
				$("#apply_time").focus();
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
			} else {
				var id = $('#apply_procedure option:selected').attr("id");
				$("#apply_procedure_id").val(id);
				$("#modifyProjectMForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
	</script>

</body>
</html>