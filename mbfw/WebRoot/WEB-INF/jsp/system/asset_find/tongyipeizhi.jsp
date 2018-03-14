<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<!-- jsp文件头和头部 -->
<%@ include file="../admin/top.jsp"%>
</head>
<body>
	<!--  空格标记--> &nbsp;&nbsp;&nbsp;&nbsp; 
	<!--  换行标记--> <br> 
		罗杰：系统设置 <br> 
		廖学文：资产类别设置，项目管理， <br> 
		陈梧桥：采购管理，资产入库 <br>  
		康新惠：资产登记，资产下发，资产接收， <br>  
	           朱磊：资产领用，资产回收，资产报废 <br>  
		曹杰：资产调拨与借用，资产报修，资产维护 <br>  
		刘悦：移动端界面，资产盘点 <br>  
	          赵志强：配置变更，耗材管理 <br>  
		杨平安：资产查询，资产统计 <br>  <br>  

注意：类名不要太长，当Xxx超过2个单词时候，建议尽量缩写，让这个类和别的同学不一样就可以了 <br>            
&nbsp;&nbsp;&nbsp;&nbsp; 1、自定义的 Controller类都放在 com.mbfw.controller.assets 包里 ，定义Controller类要求： AssetXxxController.java，比如: AssetManagerFindController.java  <br>  
其中Xxx对应我的 ManagerFind Xxx的命名尽量和自己的负责模块相关。 <br><br>
				   
&nbsp;&nbsp;&nbsp;&nbsp;2、自定义的 JavaBean类都放在 com.mbfw.entity.assets 包里（后期要求，现在注意就可以） ，定义JavaBean类要求 AssetXxx.java，比如 AssetTest.java ,test是张老师建立的一张测试表 <br>  
其中Xxx，是自已负责模块表的表名。表是唯一的，所以类一般不会出现冲突 。<br> <br>  

&nbsp;&nbsp;&nbsp;&nbsp;3、自定义的 Service类都放在 com.mbfw.service.assets 包里 ，定义Service类要求：AssetXxxService.java <br>  
对 Xxx 的命名 和Controller类的Xxx命名规则一样，尽量表示自己的业务。 <br> <br>   

&nbsp;&nbsp;&nbsp;&nbsp;4、有的同学开发可能遇到工具类，工具类要求都放在 com.mbfw.util.assets 这个包下，这个包我暂时没有建立。哪个同学用到就自己建立这个包 。<br>  

&nbsp;&nbsp;&nbsp;&nbsp;5、jsp 文件管理(文件夹前面个a,表示asset) <br>  
先找到这个路径/mbfw/WebRoot/WEB-INF/jsp/system里的system文件夹， 在system文件夹里创建自己的文件夹（这个是必须创建）,然后再自己创建的文件夹建立自己的jsp文件。 <br>  
  创建文件夹要求能够表示自己负责模块的业务比如我的assetm_find 表示资产查询 <br> <br> 
&nbsp;&nbsp;&nbsp;&nbsp;罗杰：asystem_set  以后你要在该目录下创建的文件夹，请遵循asystem_xxx形式的命名,第一个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;廖学文：atype_set,aproject_manager 以后你要在该目录下创建的文件夹，请遵循atp_xxx形式的命名,这两个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;陈梧桥：apurchase_manager,ainto_libraries 以后你要在该目录下创建的文件夹，请遵循apl_xxx形式的命名,这两个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;康新惠：asset_register,asset_down,aseet_accept,以后你要在该目录下创建的文件夹，请遵循arda_xxx形式的命名,这三个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;朱磊：asset_used,asset_callback,asset_scrap,以后你要在该目录下创建的文件夹，请遵循aucs_xxx形式的命名,这三个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;曹杰：adispatch_borrow,acall_repair,asset_maintain,以后你要在该目录下创建的文件夹，请遵循abrm_xxx形式的命名,这三个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;刘悦：amove_ui,aseet_checkout,以后你要在该目录下创建的文件夹，请遵循auc_xxx形式的命名,这二个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;赵志强：aconfig_alter，aconsume_material,以后你要在该目录下创建的文件夹，请遵循acm_xxx形式的命名,这二个个已帮你创建完成 <br>  
&nbsp;&nbsp;&nbsp;&nbsp;杨平安：asset_find,asset_count,以后你要在该目录下创建的文件夹，请遵循afc_xxx形式的命名,这二个个已帮你创建完成 <br>  
以上文件夹命名都是根据你们负责的模块写的，并且都已经创建好了，你们只要找到自己的目录添加jap就可以了 <br>  

							



















































































































































	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript"
		src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		$(top.hangge());
		</script>

</body>
</html>

