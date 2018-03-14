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

<link href="static/js/mytree/font-awesome.css" rel="stylesheet">
<link href="static/js/mytree/bootstrap.css" rel="stylesheet">
<link href="static/js/mytree/bootstrap.datepicker.css" rel="stylesheet">
<link href="static/js/mytree/kendo.common.min.css" rel="stylesheet">
<link href="static/js/mytree/kendo.default.min.css" rel="stylesheet">
<link href="static/js/mytree/kendo.common-bootstrap.min.css"
	rel="stylesheet">
<link href="static/js/mytree/kendo.bootstrap.min.css" rel="stylesheet">
<link href="static/js/mytree/AdminLTE.min.css" rel="stylesheet">
<link href="static/js/mytree/skin-blue.min.css" rel="stylesheet">
<link href="static/js/mytree/zTreeStyle.css" rel="stylesheet">
<link href="static/js/mytree/Site.css" rel="stylesheet">
<script src="static/js/mytree/modernizr-2.6.2.js"></script>

<!-- confirm提示框 -->
<link rel="stylesheet" type="text/css"
	href="static/js/myconfirm/css/xcConfirm.css" />
<script src="static/js/myconfirm/js/jquery-1.9.1.js"
	type="text/javascript" charset="utf-8"></script>
<script src="static/js/myconfirm/js/xcConfirm.js" type="text/javascript"
	charset="utf-8"></script>

<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->

<style>
.h3 .tooltip-arrow {
	border-bottom-color: #3c8dbc !important;
}

.h3 .tooltip-inner {
	background-color: #3c8dbc;
	padding: 10px;
	font-size: 14px;
	line-height: 28px;
	max-width: 400px;
	text-align: left;
}

.ztree li span.button.bottom_open {
	background-position: -37px -52px;
	height: 50px;
	margin-top: -10px;
}

.ztree li span.button.bottom_close {
	background-position: -19px -52px;
	height: 50px;
	margin-top: -10px;
}

.ztree li span.button.root_open {
	background-position: -38px 0px;
}

.ztree li span.button.root_close {
	background-position: -20px 0px;
}
</style>

<script type="text/javascript">
		var value="";
		var names="";
		var parent="";
		var curMenu = null, zTree_Menu = null;
		var setting = {
			view : {
				showLine : true,
				showIcon : true,
			/* 	selectedMulti : false,
				dblClickExpand : false,
				addDiyDom : addDiyDom  */
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "number"
				}
			},
			callback : {
				beforeClick : beforeClick
			}
		};

		var zn = '${zTreeNodes}';
		var zNodes = eval(zn);
		function beforeClick(treeId, treeNode) {
			value=treeNode.number;
			names = treeNode.name;
			parent=treeNode.pId; 
			return true;
		}

		$(document).ready(function() {
			var treeObj = $("#treeDemo");
			$.fn.zTree.init(treeObj, setting, zNodes);

		});
					
					
					function addPoint() {
						
						if($("#name").val()==""){
							var txt=  "请输入类别名称";
							window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
						
							$("#name").focus();
							return false;
						}
					}

					function addChilePoint() {
						if($("#name").val()==""){
							
						
							var txt=  "请输入类别名称";
							window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
					       
							$("#name").focus();
							return false;
						}
						if(value==""){
							var txt=  "请先选择一个父类";
							window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
							return false;
						}else if(parent!=null){
							var txt=  "子级不能添加下级！";
							window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
							return false;
						} 
						var pName = $('#name').val();
						var url = '<%=basePath%>/asset/addChildPoint.do?pId='+value+'&name='+pName;
						$("#applyForm").attr("action",url).submit();
	
					}
					
					function delect_piont() {
						if(value==""){
							var txt=  "请先选择一个类别";
							window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
							return false;
						}
						var url = '<%=basePath%>/asset/delectPoint.do?number='+value+'&names='+encodeURI(encodeURI(names))+'&parent='+parent;
						$("#applyForm").attr("action", url).submit();
					}
</script>
</head>
<body>
	
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<c:if test="${QX.cha == 1 }">
					<div class="row">
						<div class="col-md-12">
							<div class="box box-primary">
								<div class="box-header">
	
									<div class="h3">
										<form action="asset/addPoint.do" method="post"
											name="applyForm" id="applyForm">
	
											<c:if test="${QX.add == 1 }">
											<input type="text" id="name" name="name"
												style="height: 100%; margin-top: 8px;" maxlength="100"
												placeholder="类别名称">
	
											<button class="btn btn-success" id="btnsave"
												data-loading-text="保存中..." onclick="return addPoint();">
												<i class="fa fa-plus"></i> 新增
											</button>
											<a class="btn btn-primary" onclick="return addChilePoint();"
												id="btndelete"> <i class="fa  fa-plus"></i> 新增子级
											</a> 
											</c:if>
											
											<c:if test="${QX.del == 1 }">
											<a class="btn btn-danger" onclick="return delect_piont();"
												id="btndelete"> <i class="fa  fa-remove"></i> 删除
											</a> 
											</c:if>
											
											<c:if test="${QX.add == 1 }">
											<a class="btn  btn-light" onclick="fromExcel();" title="从EXCEL导入"><i  class="icon-cloud-upload"></i></a> 
											<!-- <a class="btn  btn-light" data-toggle="modal" data-target="#modal01" title="从EXCEL导入"><i  class="icon-cloud-upload"></i></a> -->
											</c:if> 
											
											<c:if test="${QX.add == 1 }">
											<a class="help-link" id="helptooltip" data-toggle="tooltip"
												data-placement="bottom"
												style="text-algin: left; font-size: 14px; text-decoration: underline; cursor: pointer; position: relative; display: table; margin-top: 10px;"
												title=""
												data-original-title="在注册并首次登陆系统后，平台默认创建国家标准的资产类别数据。企业也可以跟着自己的业务需求，创建新的资产分类。资产管理平台只支持两级资产分类的创建。"><i
												class="fa fa-info-circle" style="margin-right: 6px;"></i>如何设置资产类别</a>
											</c:if>
											
										</form>
									</div>
								</div>
							</div>
						</div>
	
	
						<div class="box-body" style="margin-top: 120px;">
							<div class="row">
								<div class="col-lg-12">
									<div class="box box-primary box-solid">
										<div class="box-header with-border" >
											<h3 class="box-title" >资产类别</h3>
										</div>
										<div class="box-body">
											<div class="zTreeDemoBackground newTree">
												<ul id="treeDemo" class="ztree" class="tree"></ul>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
	
					</div>
				</c:if>
				<c:if test="${QX.cha == 0 }">
					<div><label><b style="color: red"><h2>抱歉，您没有此页面的操作权限！</h2></b></label></div>
				</c:if>
			</div>
		</div>
	</div>
	
	
	


	<script type="text/javascript">
		$(top.hangge());
		//判断节点增加是否成功
		var addPoints = '${addPoint}';
		if (addPoints == "success") {
			var txt = "增加成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		} else if (addPoints == "fail") {
			var txt = "添加失败，类别最多99种！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}else if(addPoints == "exist"){
			var txt = "不能重复添加节点！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}

		//判断子级节点增加是否成功
		var addChildPoint = '${addChildPoint}';
		if (addChildPoint == "success") {
			var txt = "增加子级节点成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		} else if (addChildPoint == "fail") {
			var txt = "添加失败，子级类别最多99种！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}else if(addChildPoint == "exist"){
			var txt = "不能重复添加节点！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}

		//判断删除是否成功
		var delectresult = '${delectresult}';
		if (delectresult == "success") {
			var txt = "删除成功！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		} else if (delectresult == "fail") {
			var txt = "此节点或包含的节点正在被使用！不能删除！";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.info);
		}
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = '<%=basePath%>/asset/uploadAssetClassExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 top.jzts();
					 setTimeout("self.location.reload()",100);
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		
	</script>



	<script type="text/javascript">
		window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>


	<script src="static/js/jquery.tips.js"></script>

	<script src="static/js/mytree/jquery-1.11.3.min.js"></script>
	<script src="static/js/mytree/bootstrap.min.js"></script>
	<script src="static/js/mytree/bootbox.min.js"></script>
	<script src="static/js/mytree/bootstrap.datepicker.js"></script>
	<script src="static/js/mytree/knockout-3.4.0.js"></script>
	<script src="static/js/mytree/jszip.min.js"></script>
	<script src="static/js/mytree/kendo.all.min.js"></script>
	<script src="static/js/mytree/kendo.messages.zh-CN.js"></script>
	<script src="static/js/mytree/date.js"></script>
	<script src="static/js/mytree/moment.js"></script>
	<script src="static/js/mytree/app.min.js"></script>
	<script src="static/js/mytree/lodash.min.js"></script>
	<script src="static/js/mytree/icheck.min.js"></script>
	<script src="static/js/mytree/jquery.ztree.all-3.5.min.js"></script>
	<script src="static/js/mytree/base.js"></script>
	<script src="static/js/mytree/jquery.slimscroll.min.js"></script>
	<script src="static/js/mytree/Message.js"></script>
	<script src="static/js/mytree/jquery.ztree.all-3.5.min.js"></script>

	<!--     <script src="static/js/mytree/assettype.js"></script> -->
</body>
</html>