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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<%@ include file="../admin/top.jsp"%> 
		
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save() {

		if ($("#provider_name").val() == "") {

			$("#provider_name").tips({
				side : 3,
				msg : '请输入供应商名称',
				bg : '#AE81FF',
				time : 2
			});

			$("#provider_name").focus();
			$("#provider_name").val('');
			$("#provider_name").css("background-color", "white");
			return false;
		} else {
			$("#provider_name").val($.trim($("#provider_name").val()));
		}

		if ($("#device_model").val() == "") {

			$("#device_model").tips({
				side : 3,
				msg : '请输入设备型号',
				bg : '#AE81FF',
				time : 2
			});

			$("#device_model").focus();
			$("#provider_name").val('');
			return false;
		}

		if ($("#provider_address").val() == "") {

			$("#provider_address").tips({
				side : 3,
				msg : '请输入供应商地址',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_address").focus();
			$("#provider_address").val('');
			return false;
		}
		
		var tel = $("#provider_tel").val();
		var regTel1 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(tel);//带区号的固定电话
		var regTel2 = /^(\d{7,8})(-(\d{3,}))?$/.test(tel);//不带区号的固定电话
		var phonereg = /^1\d{10}$/.test(tel);// 手机号
		if ($("#provider_tel").val() == "") {
			$("#provider_tel").tips({
				side : 3,
				msg : '请输入联系电话或手机号',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_tel").focus();
			return false;
		}
		// 检测是否是手机号
		if(tel.substring(0,1) == 1){
			if( $("#provider_tel").val().length != 11 || !phonereg ){
				$("#provider_tel").tips({
					side : 3,
					msg : '你输入的手机号有错',
					bg : '#AE81FF',
					time : 3
				});
				$("#provider_tel").focus();
				return false;
			}
		}else if(!regTel1 && !regTel2){ // 检测是否是固定电话
			$("#provider_tel").tips({
				side : 3,
				msg : '你输入的固定电话有错',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_tel").focus();
			return false;
		}
			
		// 验证传真  /^(\d{3,4}-)?\d{7,8}$/
		 if($.trim($("#provider_fax").val())!=""){
			 if(!isfax($.trim($("#provider_fax").val()))){
					$("#provider_fax").tips({
						side:3,
			            msg:'传真格式不正确',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#provider_fax").focus();
					return false;
			}
		} 
		
		//验证邮箱
		 if($.trim($("#provider_email").val())!=""){
			 if(!ismail($.trim($("#provider_email").val()))){
					$("#provider_email").tips({
						side:3,
			            msg:'邮箱格式不正确',
			            bg:'#AE81FF',
			            time:3
			        });
					$("#provider_email").focus();
					return false;
			}
		} 
		
		if ($("#provider_conn_person").val() == "") {

			$("#provider_conn_person").tips({
				side : 3,
				msg : '请输入负责人或联系人姓名',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_conn_person").focus();
			return false;
		}
		
		
		
		var tel_conn = $("#provider_conn_tel").val();
		var regTel1 = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/.test(tel_conn);//带区号的固定电话
		var regTel2 = /^(\d{7,8})(-(\d{3,}))?$/.test(tel_conn);//不带区号的固定电话
		var phonereg2 = /^1\d{10}$/.test(tel_conn);// 手机号
		if ($("#provider_conn_tel").val() == "") {

			$("#provider_conn_tel").tips({
				side : 3,
				msg : '请输入电话或手机',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_conn_tel").focus();
			return false;
		}
		
		// 检测是否是手机号
		if(tel_conn.substring(0,1) == 1){
			if( $("#provider_conn_tel").val().length != 11 || !phonereg2 ){
				$("#provider_conn_tel").tips({
					side : 3,
					msg : '你输入的手机号有错',
					bg : '#AE81FF',
					time : 3
				});
				$("#provider_conn_tel").focus();
				return false;
			}
		}else if(!regTel1 && !regTel2){ // 检测是否是固定电话
			$("#provider_conn_tel").tips({
				side : 3,
				msg : '你输入的固定电话有错',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_conn_tel").focus();
			return false;
		}
		
		
		
		if ($("#products_quality_standard").val() == "") {

			$("#products_quality_standard").tips({
				side : 3,
				msg : '请输入产品质量标准',
				bg : '#AE81FF',
				time : 3
			});
			$("#products_quality_standard").focus();
			return false;
		}

		if ($("#provider_aptitude").val() == "") {

			$("#provider_aptitude").tips({
				side : 3,
				msg : '请选择供应商资质',
				bg : '#AE81FF',
				time : 3
			});
			$("#provider_aptitude").focus();
			return false;
		} else {
			$("#provider_editForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

</script>
	</head>
<body>
	<form action="provider/${msg }.do" name="provider_editForm" id="provider_editForm" method="post">
		<div id="zhongxin">
		<%-- <input type="hidden" name="id" id="id" value="${pd.id}"> --%>
		<table id="table_report" class="table text-table table-striped table-bordered table-hover">
			<tr>
				<td><label>供应商代码<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_code" id="provider_code" 
					<c:if test="${ pd.provider_code >=10 }">value="${pd.provider_code }"</c:if>  
					<c:if test="${ pd.provider_code < 10 }"> value="0${pd.provider_code }"</c:if> 
					placeholder="供应商代码" title="供应商代码" readonly="true" />
				</td>
				<td><label>供应商名称<b style="color: red">*</b></label></td>
				<c:if test="${provider_no_edit=='no' }">
				<td><input type="text" name="provider_name" id="provider_name"   value="${pd.provider_name }" readonly="readonly" placeholder="供应商名称" title="供应商名称"/></td>
				</c:if>
				<c:if test="${provider_yes_edit=='yes' }">
				<td><input type="text" name="provider_name" id="provider_name"   value="${pd.provider_name }"  placeholder="供应商名称" title="供应商名称"/></td>
				</c:if>
			</tr>
			<tr >
				<td><label>供应商地址<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_address" id="provider_address" value="${pd.provider_address }"   placeholder="供应商地址" title="供应商地址"/></td>
				<td><label>联系电话<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_tel" id="provider_tel" value="${pd.provider_tel }"   placeholder="联系电话" title="联系电话"/></td>
			</tr>
			
			<tr >
				<td><label>传真</label></td>
				<td><input type="text" name="provider_fax" id="provider_fax" value="${pd.provider_fax }"   placeholder="传真" title="传真"/></td>
				<td><label>邮箱</label></td>
				<td><input type="text" name="provider_email" id="provider_email" value="${pd.provider_email }"   placeholder="邮箱" title="邮箱"/></td>
			</tr>
			<tr >
				<td><label>负责人或联系人姓名<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_conn_person" id="provider_conn_person" value="${pd.provider_conn_person }"   placeholder="负责人或联系人姓名" title="负责人或联系人姓名"/></td>
				<td><label>联系人电话<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_conn_tel" id="provider_conn_tel" value="${pd.provider_conn_tel }"   placeholder="联系人电话" title="联系人电话"/></td>
			</tr>
			<tr >
				<td><label>产品质量标准<b style="color: red">*</b></label></td>
				<td><input type="text" name="products_quality_standard" id="products_quality_standard" value="${pd.products_quality_standard }"   placeholder="产品质量标准" title="产品质量标准"/></td>
				<td><label>主要产品<b style="color: red">*</b></label></td>
				<td><input type="text" name="provider_main_products" id="provider_main_products" value="${pd.provider_main_products }"   placeholder="主要产品" title="主要产品"/></td>
			</tr>
			<tr >
				<td><label>供应商资质<b style="color: red">*</b></label></td>
				<td>
					<select class="chzn-select" name="provider_aptitude" id="provider_aptitude"  data-placeholder="供应商资质" style="width:200; vertical-align: center">
					<option></option>
					<option  <c:if test="${pd.provider_aptitude == '一级' }">selected</c:if> >一级</option>
					<option  <c:if test="${pd.provider_aptitude == '二级' }">selected</c:if> >二级</option>
					<option  <c:if test="${pd.provider_aptitude == '三级' }">selected</c:if> >三级</option>
					<option  <c:if test="${pd.provider_aptitude == '四级' }">selected</c:if> >四级</option>
					<option  <c:if test="${pd.provider_aptitude == '五级' }">selected</c:if> >五级</option>
					
					</select>
				</td>
				<td><label>评价</label></td>
				<td>
					<select class="chzn-select" name="provider_comment" id="provider_comment" data-placeholder="评价" style="width:200;vertical-align: center">
					<option></option>
					<option <c:if test="${pd.provider_comment == '非常好' }">selected</c:if> >非常好</option>
					<option <c:if test="${pd.provider_comment == '很好' }">selected</c:if> >很好</option>
					<option <c:if test="${pd.provider_comment == '较好' }">selected</c:if> >较好</option>
					<option <c:if test="${pd.provider_comment == '一般' }">selected</c:if> >一般</option>
					<option <c:if test="${pd.provider_comment == '差' }">selected</c:if> >差</option>
					<option <c:if test="${pd.provider_comment == '很差' }">selected</c:if> >很差</option>
					</select>
				</td>
			</tr>
			
			<tr >
				<td><label>备注：</label></td>
				<td colspan="3"><textarea rows="2" style="width: 89%;vertical-align: center"  placeholder="详细评价(不必填)" name="provider_note" id="provider_note">${pd.provider_note }</textarea></td>
			</tr>
			
			<tr>
				<td style="text-align: center;" colspan="4">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="provider_close();">取消</a>
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
		
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		function provider_close(){
			window.location.href='<%=basePath%>provider/apl_provider_manager.do';
		}
		</script>
	
</body>
</html>