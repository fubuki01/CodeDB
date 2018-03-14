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
<%@ include file="top.jsp"%>

<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<link href="static/css/css/bootstrap.min.css" rel="stylesheet">
<link href="static/css/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="static/css/css/font-awesome.css" rel="stylesheet">
<link href="static/css/css/style.css" rel="stylesheet">
<link href="static/css/css/pages/dashboard.css" rel="stylesheet">

<script type="text/javascript" src="static/js/myjs/head.js"></script>
</head>
<body>

	<div class="container-fluid" id="main-container">

		<div id="page-content" class="clearfix">
			<div class="page-header position-relative">
				<h1>
					后台首页 <small><i class="icon-double-angle-right"></i> </small>
				</h1>
			</div>
			<!--/page-header-->

			<div class="row-fluid">

				<div class="row-fluid">
					<c:if test="${permission == 1 }">
						<c:if test="${name == 'admin' }">
							<div class="widget-content" style="vertical-align: middle;margin-top: 3.5%;">
								<div class="shortcuts">
									<a target="mainFrame" onclick="siMenu('z5','lm1','用户管理','user/listUsers.do')" class="shortcut"> 
										<i class="shortcut-icon icon-user"></i> 
										<span class="shortcut-label">用户管理</span>
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z145','lm1','审批流程管理','asset/asystem_showapprovalprocess.do')" class="shortcut">
										<i class="shortcut-icon  icon-retweet"></i>
										<span class="shortcut-label">审批流程</span> 
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z142','lm1','审批节点管理','asset/asystem_showallnode.do')" class="shortcut">
										<i class="shortcut-icon icon-certificate"> </i>
										<span class="shortcut-label">审批节点</span> 
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z141','lm1','审核人员管理','asset/asystem_showapprover.do')" class="shortcut">
										<i class="shortcut-icon icon-user-md"></i>
										<span class="shortcut-label">审核人员</span>
		
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z40','lm1','机构信息设置','asset/asystem_institutionalinfoshow.do')" class="shortcut">
										<i class="shortcut-icon icon-sitemap"></i>
										<span class="shortcut-label">机构管理</span>
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z150','lm149','供应商管理','provider/apl_provider_manager.do')" class="shortcut">
										<i class="shortcut-icon  icon-truck"></i>
										<span class="shortcut-label">供应商管理</span>
									</a>
									
									<a target="mainFrame" onclick="siMenu('z132','lm131','资产查询','asset/afc_assetFind.do')" class="shortcut">
										<i class="shortcut-icon icon-search"></i> 
										<span class="shortcut-label">资产查询</span>
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z84','lm81','耗材查询','asset/acm_inquiry.do')" class="shortcut"> 
										<i class="shortcut-icon  icon-eye-open"></i>
										<span class="shortcut-label">耗材查询</span>
									</a>
								</div>
							</div>
						</c:if>
						
						<c:if test="${name != 'admin' }">
							<div class="widget-content" style="vertical-align: middle;margin-top: 5%;">
								<div class="shortcuts">
									<a target="mainFrame" onclick="siMenu('z5','lm1','用户管理','user/listUsers.do')" class="shortcut"> 
										<i class="shortcut-icon icon-user"></i> 
										<span class="shortcut-label">用户管理</span>
									</a>
									
									<a target="mainFrame" onclick="siMenu('z64','lm63','项目立项','asset/atp_showForm.do')" class="shortcut">
										<i class="shortcut-icon icon-paper-clip"></i>
										<span class="shortcut-label">项目立项</span>
									</a>
									
									<a target="mainFrame" onclick="siMenu('z65','lm63','项目审批','asset/atp_approvalprojectlist.do')" class="shortcut">
										<i class="shortcut-icon icon-edit"></i>
										<span class="shortcut-label">项目审批</span>
									</a> 
									
									<a target="mainFrame" onclick="siMenu('z123','lm121','资产下发','asset/arda_assertIssued.do')" class="shortcut">
										<i class="shortcut-icon icon-share"></i>
										<span class="shortcut-label">资产下发</span> 
									</a> 
								</div>
							</div>
						</c:if>
					</c:if>
					
					<c:if test="${permission == 2 }">
						<div class="widget-content" style="vertical-align: middle;margin-top: 5%;">
							<div class="shortcuts">
								<a target="mainFrame" onclick="siMenu('z5','lm1','用户管理','user/listUsers.do')" class="shortcut"> 
									<i class="shortcut-icon icon-user"></i> 
									<span class="shortcut-label">用户管理</span>
								</a>
								
								<a target="mainFrame" onclick="siMenu('z64','lm63','项目立项','asset/atp_showForm.do')" class="shortcut">
									<i class="shortcut-icon icon-paper-clip"></i>
									<span class="shortcut-label">项目立项</span>
								</a>
								
								<a target="mainFrame" onclick="siMenu('z65','lm63','项目审批','asset/atp_approvalprojectlist.do')" class="shortcut">
									<i class="shortcut-icon icon-edit"></i>
									<span class="shortcut-label">项目审批</span>
								</a> 
								
								<a target="mainFrame" onclick="siMenu('z124','lm121','资产接收','asset/arda_assertReceived.do')" class="shortcut">
									<i class="shortcut-icon icon-arrow-down"></i>
									<span class="shortcut-label">资产接收</span> 
								</a> 
							</div>
						</div>
					</c:if>
					
					
					<c:if test="${permission == 3 }">
						<div class="widget-content" style="vertical-align: middle;margin-top: 5%;">
							<div class="shortcuts">
								<a target="mainFrame" onclick="siMenu('z5','lm1','用户管理','user/listUsers.do')" class="shortcut"> 
									<i class="shortcut-icon icon-user"></i> 
									<span class="shortcut-label">用户管理</span>
								</a>
								
								<a target="mainFrame" onclick="siMenu('z64','lm63','项目立项','asset/atp_showForm.do')" class="shortcut">
									<i class="shortcut-icon icon-paper-clip"></i>
									<span class="shortcut-label">项目立项</span>
								</a>
								
								<a target="mainFrame" onclick="siMenu('z92','lm91','资产领用','asset/aucs_manage.do')" class="shortcut">
									<i class="shortcut-icon  icon-shopping-cart"></i>
									<span class="shortcut-label">资产领用</span>
								</a> 
								
								<a target="mainFrame" onclick="siMenu('z85','lm81','耗材申请','asset/acm_apply.do')" class="shortcut">
									<i class="shortcut-icon icon-plus"></i>
									<span class="shortcut-label">耗材申请</span> 
								</a> 
							</div>
						</div>
					</c:if>
				</div>
			</div>

		</div>


	</div>


	<!--/.fluid-container#main-container-->
	<!-- <a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a> -->
	<!-- basic scripts -->
	<script src="static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->

	<!--[if lt IE 9]>
		<script type="text/javascript" src="static/js/excanvas.min.js"></script>
		<![endif]-->
	<script type="text/javascript"
		src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript"
		src="static/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript"
		src="static/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript"
		src="static/js/jquery.flot.resize.min.js"></script>
	<!-- ace scripts -->
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->


	<script type="text/javascript">
		$(top.hangge());
		/* var name = '${name }';
		alert(name); */
	</script>
</body>
</html>
