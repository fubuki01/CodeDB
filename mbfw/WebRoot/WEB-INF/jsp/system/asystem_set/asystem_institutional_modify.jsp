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
<title>机构信息修改</title>
<!-- 引入 -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
<script type="text/javascript"
	src="static/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="static/js/bootbox.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

</head>
<body>

	<form action="asset/asystem_institutional_update.do" name="modifyInstitutionMForm"
		id="modifyInstitutionMForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin" style="margin-left: 10px;margin-right: 10px;margin-top: 15px;">
			<table id="table_report"
				class="table text-table table-striped table-bordered table-hover">
				
				<!-- 隐藏的元素 -->
				<div style="display: none;">
					<input name="organizational_identification" value="${pd.organizational_identification }"/>
					<input name="sort_ordinal" value="${pd.sort_ordinal }"/>
					<input name="superior_organizational_identification" value="${pd.superior_organizational_identification }"/>
					<input name="report_authority_organization_code" value="${pd.report_authority_organization_code }"/>
					<input name="key" value="${pd.key }"/>
								
				</div>
				
				<tr>
					<td style="text-align: center;" colspan="6"><label><b style="color: red"><h2>修改机构信息</h2></b></label></td>
				</tr>
				
				<tr>
					<td><label>上级组织名称</label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="superior_organization_name" id="superior_organization_name" data-placeholder="请选上级组织"
						style="width: 221px; vertical-align: center">
							<option></option>
					</select></td>
					
					

					<td><label>组织名称<b style="color: red">*</b></label></td>
					<td><input type="text" name="organization_name" id="organization_name"
						value="${pd.organization_name }" placeholder="组织名称"></td>
					
					<td><label>组织简称<b style="color: red">*</b></label></td>
					<td><input type="text" name="organization_abbreviation" id="organization_abbreviation"
						value="${pd.organization_abbreviation }" placeholder="组织简称"></td>
						
				</tr>
				

				<tr>
					<td><label>组织类别<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="organization_class" id="organization_class" data-placeholder="请选择组织类别"
						style="width: 221px; vertical-align: center">
							<c:if test="${permission==1 }">
								<option></option>
								<option>部门</option>
								<option>支行/社</option>
								<option>网点/分社</option>
							</c:if>
							
							<c:if test="${permission==2 }">
								<option></option>
								<option>部门</option>
								<option>网点/分社</option>
							</c:if>
					</select></td>
					
					<td><label>组织级别<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="organizational_level" id="organizational_level" data-placeholder="请选择组织级别"
						style="width: 221px; vertical-align: center">
							<option></option>
							<option>支行/社/部门</option>
							<option>网点/分社</option>
					</select></td>
					
					<td><label>业务机构类型</label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="business_organization_type" id="business_organization_type" data-placeholder="请选择业务机构类型"
						style="width: 221px; vertical-align: center">
							<option></option>
							<option>无</option>
							<option>综合制</option>
					</select></td>
				</tr>
				
				
				<tr>
					<td><label>业务机构代码</label></td>
					<td><input type="text" name="business_organization_code" id="business_organization_code"
						value="${pd.business_organization_code }" placeholder="数字长度不能大于11"></td>
				
					<td><label>业务机构性质</label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="business_organization_nature" id="business_organization_nature" data-placeholder="请选业务机构性质"
						style="width: 221px; vertical-align: center">
							<c:if test="${permission==1 }">
								<option></option>
								<option>无</option>
								<option>有贷款城区支行</option>
								<option>有贷款农村支行</option>
								<option>无贷款支行</option>
							</c:if>
							
							<c:if test="${permission==2 }">
								<option></option>
								<option>无</option>
								<option>无贷款支行</option>
							</c:if>
					</select></td>
					
					<td><label>业务机构标识<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="business_organization_identifier" id="business_organization_identifier" data-placeholder="请选择业务机构标识"
					 	style="width: 221px; vertical-align: center">
							<option></option>
							<option>是</option>
							<option>否</option>
					</select></td>
					
				</tr>

				<tr>
					
					<td><label>启用标识<b style="color: red">*</b></label></td>
					<td style="vertical-align: top;"><select class="chzn-select"
						name="enable_identification" id="enable_identification" data-placeholder="是否启用标识"
						style="width: 221px; vertical-align: center">
							<option></option>
							<option>是</option>
							<option>否</option>
					</select></td>
					
					<td><label>所在区域</label></td>
					<td colspan="3"><textarea rows="2" cols="" name="location"
						id="location" style="width: 90%"> </textarea></td>
				</tr>

				<tr style="height: 40">
					<td style="text-align: center;" colspan="6"><a
						class="btn  btn-danger" onclick="modify();">修改</a> 
						<button type="button" class="btn  btn-primary" onclick="return rBank()">取消</button>
					</td>
				</tr>
				
				<!-- <tr>
					<th style="text-align: center;" colspan="6"><label>提示</label>
					<textarea rows="2" style="width: 40%;color: red;" disabled="disabled"  
					placeholder="">注意：加“*”的为必填项，无“*”的为可选项</textarea>
					</th>
				</tr> -->
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
		var key = '${pd.key }';
		var oa = '${organization_abbreviation}';
		var oas = oa.split(",");
		for (var i = 0; i < oas.length; i++) {
			var option = document.createElement("option");
            option.innerHTML = oas[i]+"";
            $("#superior_organization_name").append(option);
            /* 注意使用bootstrap的class="chzn-select"类动态添加一定要加上这句话 不然不显示 */
			$("#superior_organization_name").trigger("liszt:updated");
		}
		
		//上级组织简称
		$("#superior_organization_name").val('${pd.superior_organization_name }');
		//业务机构性质
		$("#business_organization_nature").val('${pd.business_organization_nature }');
		//业务机构标识
		$("#business_organization_identifier").val('${pd.business_organization_identifier }');
		//组织类别
		$("#organization_class").val('${pd.organization_class }');
		//组织级别
		$("#organizational_level").val('${pd.organizational_level }');
		//业务机构类型
		$("#business_organization_type").val('${pd.business_organization_type }');
		//启用标识
		$("#enable_identification").val('${pd.enable_identification }');
		//所在区域
		$("#location").val('${pd.location }');
		
		
		function rBank() {
			var temp = '${pd.key}';
			window.location = '${pageContext.request.contextPath}/asset/asystem_institutionalinfoshow.do?key='+encodeURI(encodeURI(temp));
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
		
		//正整数
		function isPInt(str) {
		    var g = /^[1-9]*[1-9][0-9]*$/;
		    return g.test(str);
		}
		
		function modify() {
			
			if ($("#organization_name").val() == "") {

				$("#organization_name").tips({
					side : 3,
					msg : '请输入组织名称',
					bg : '#AE81FF',
					time : 2
				});

				$("#organization_name").focus();
				$("#organization_name").val('');
				$("#organization_name").css("background-color", "white");
				return false;
			} else {
				$("#organization_name").val($.trim($("#organization_name").val()));
			}

			if ($("#organization_abbreviation").val() == "") {

				$("#organization_abbreviation").tips({
					side : 3,
					msg : '请输入组织简称',
					bg : '#AE81FF',
					time : 3
				});
				$("#organization_abbreviation").focus();
				return false;
			}


			if ($("#organization_class").val() == "") {

				$("#organization_class").tips({
					side : 3,
					msg : '请选择组织类别',
					bg : '#AE81FF',
					time : 3
				});
				$("#organization_class").focus();
				$("#organization_class").val('');
				return false;
			}

			if ($("#organizational_level").val() == "") {

				$("#organizational_level").tips({
					side : 3,
					msg : '请选择组织级别',
					bg : '#AE81FF',
					time : 3
				});
				$("#organizational_level").focus();
				return false;
			}
			if($("#business_organization_code").val()!=""){
				if(!isPInt($("#business_organization_code").val())){
					$("#business_organization_code").tips({
						side : 3,
						msg : '输入格式不正确',
						bg : '#AE81FF',
						time : 3
					});
					$("#business_organization_code").focus();
					$("#business_organization_code").val('');
					return false;
				}else if ($("#business_organization_code").val().length>11) {

					$("#business_organization_code").tips({
						side : 3,
						msg : '长度超过限制',
						bg : '#AE81FF',
						time : 2
					});
					$("#business_organization_code").focus();
					$("#business_organization_code").css("background-color", "white");
					return false;
				} else {
					$("#organization_name").val($.trim($("#organization_name").val()));
				}
				
			}
			
			if ($("#business_organization_identifier").val() == "") {

				$("#business_organization_identifier").tips({
					side : 3,
					msg : '请选择业务机构标识',
					bg : '#AE81FF',
					time : 3
				});
				$("#business_organization_identifier").focus();
				return false;
			}

			if ($("#enable_identification").val() == "") {

				$("#enable_identification").tips({
					side : 3,
					msg : '请选择启动标示',
					bg : '#AE81FF',
					time : 3
				});
				$("#enable_identification").focus();
				return false;
			}else {
				$("#modifyInstitutionMForm").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		}
		
	</script>

</body>
</html>