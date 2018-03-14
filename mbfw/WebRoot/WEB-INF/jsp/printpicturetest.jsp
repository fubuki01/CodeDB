<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印二维码的测试页面</title>
<script type="text/javascript" src="../Lodop/LodopFuncs.js"></script>
<script type="text/javascript" src="../Lodop/CheckActivX.js"></script>
<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0></object>
</head>
<!-- <script type="text/javascript">
	function CustomizeiniObj()
	{ 
		//这里写入自定义代码的相关对象的初始化过程
		var LODOP=document.getElementById("LODOP");
		CheckLodop();
	} 
	function createPrintPage(){
		CustomizeiniObj();
		LODOP.PRINT_INIT("进行lodop打印测试...");
		LODOP.ADD_PRINT_TEXT(30,60,170,20,document.getElementById("text_0").value);
	　　 LODOP.ADD_PRINT_TEXT(60,60,170,20,document.getElementById("text_1").value);
	　　 LODOP.ADD_PRINT_TEXT(90,60,170,20,document.getElementById("text_2").value);
	　 　LODOP.ADD_PRINT_TEXT(120,60,170,20,document.getElementById("text_3").value);
	　　 LODOP.ADD_PRINT_TEXT(150,60,170,20,document.getElementById("text_4").value);
	　　LODOP.ADD_PRINT_TEXT(180,60,170,20,document.getElementById("text_5").value);
	}
	
	function print_view(){
		createPrintPage();
		LODOP.PREVIEW();
	} 
	//打印方法：
	function print(){
	createPrintPage();
	LODOP.PRINT();
	} 
	//打印维护：
	function print_setup(){
	createPrintPage();
	LODOP.PRINT_SETUP();
	} 
	//打印设计：
	function print_design(){
	createPrintPage();
	LODOP.PRINT_DESIGN();
	} 
</script> -->
<script type="text/javascript">
		/* var LODOP; //声明为全局变量 
		function prn1_preview() {	
		CreateOneFormPage();	
		LODOP.PREVIEW();	
		};
		function prn1_print() {		
		CreateOneFormPage();
		LODOP.PRINT();	
		};
		function prn1_printA() {		
		CreateOneFormPage();
		LODOP.PRINTA(); 	
		};	
		function CreateOneFormPage(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
		LODOP.SET_PRINT_STYLE("FontSize",18);
		LODOP.SET_PRINT_STYLE("Bold",1);
		LODOP.ADD_PRINT_TEXT(50,231,260,39,"打印页面部分内容");
		LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("form1").innerHTML);
		};	                     
		function prn2_preview() {	
		CreateTwoFormPage();	
		LODOP.PREVIEW();	
		};
		function prn2_manage() {	
		CreateTwoFormPage();
		LODOP.PRINT_SETUP();	
		};	
		function CreateTwoFormPage(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单二");
		LODOP.ADD_PRINT_RECT(70,27,634,242,0,1);
		LODOP.ADD_PRINT_TEXT(29,236,279,38,"页面内容改变布局打印");
		LODOP.SET_PRINT_STYLEA(2,"FontSize",18);
		LODOP.SET_PRINT_STYLEA(2,"Bold",1);
		LODOP.ADD_PRINT_HTM(88,40,321,185,document.getElementById("form1").innerHTML);
		LODOP.ADD_PRINT_HTM(87,355,285,187,document.getElementById("form2").innerHTML);
		LODOP.ADD_PRINT_TEXT(319,58,500,30,"注：其中《表单一》按显示大小，《表单二》在程序控制宽度(285px)内自适应调整");
		};              
		function prn3_preview(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_全页");
		LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.documentElement.innerHTML);
		LODOP.PREVIEW();	
		}; */	
		 
    var LODOP; //声明为全局变量
	function CreateImage() {
    	//获取打印对象
		LODOP=getLodop();
    	//设置打印文件名
		LODOP.PRINT_INIT("二维码打印");
		//设置打印内容
		//打印页面中的某个部分的内容，比如form表单
		//LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("form1").innerHTML);
		//打印网络地址的图片
		//LODOP.ADD_PRINT_IMAGE(30,150,400,400,"<img border='0' src='http://s1.sinaimg.cn/middle/4fe4ba17hb5afe2caa990&690' />");
		//打印本地图片
		LODOP.ADD_PRINT_IMAGE(200,150,260,150,"G:/front.png"); 
	};	
	function myPreview1() {		
		CreateImage();
		//调用打印预览函数
		LODOP.PREVIEW();		
	};	
	function myPrint1() {		
		CreateImage();
		//调用打印函数
		LODOP.PRINT();		
	};  
	function myPrintSetup1() {		
		CreateImage();
		//调用打印内容调整函数
		LODOP.PRINT_SETUP();		
	}; 		

	function myPreview2() {	
		LODOP=getLodop();  	
		LODOP.PRINT_INIT("二维码打印");
		LODOP.ADD_PRINT_IMAGE(30,20,600,250,"<img border='0' src='http://s1.sinaimg.cn/middle/4fe4ba17hb5afe2caa990&690'width='100%' height='250'/>");
		LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//(可变形)扩展缩放模式
		LODOP.PREVIEW();
	};	
	function myPreview3() {	
		LODOP=getLodop();  	
		LODOP.PRINT_INIT("二维码打印");
		LODOP.ADD_PRINT_IMAGE(30,20,600,600,"<img border='0' src='http://s1.sinaimg.cn/middle/4fe4ba17hb5afe2caa990&690' />");
		LODOP.SET_PRINT_STYLEA(0,"Stretch",2);//按原图比例(不变形)缩放模式
		LODOP.PREVIEW();		
	};	
	function myPreview4() {	
		LODOP=getLodop();  	
		LODOP.PRINT_INIT("二维码打印");
		LODOP.ADD_PRINT_TEXT(56,56,200,20,"CSAPP");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",13);
		LODOP.ADD_PRINT_IMAGE(28,49,171,153,"<img border='0' src='http://static15.photo.sina.com.cn/middle/4fe4ba17t993d651b55ce&690' />");
		LODOP.SET_PRINT_STYLEA(0,"TransColor","#FFFFFF");
		LODOP.ADD_PRINT_TEXT(120,53,200,20,"CSAPP");
		LODOP.SET_PRINT_STYLEA(0,"FontSize",15);
		LODOP.PREVIEW();
	};	
	//选择打印机
	function select_printer() {		
		CreateImage();
		//调用打印机选择函数
		LODOP.PRINTA(); 	
	};
	
	//设计二维码
	function design(){
		LODOP=getLodop();  	
		//LODOP.SET_PRINT_PAGESIZE(0, "100mm", "40mm", "A4"); //规定纸张大小
		//LODOP.PRINT_INIT("二维码打印");
		<%-- LODOP.ADD_PRINT_IMAGE(28,49,171,153,"<img border='0' src='<%=basePath%>/static/img/banklogo.png'>"); --%>
		LODOP.ADD_PRINT_IMAGE("5mm","15mm","10mm","10mm","<img border='0' src='<%=basePath%>/Lodop/banklogo.png'>"); 
		LODOP.SET_PRINT_STYLEA(0,"Stretch",1);//可缩放模式
		LODOP.ADD_PRINT_TEXT("5mm","25mm","68mm","10mm","湖南慈利农村商业银行");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 16);
		LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
		LODOP.ADD_PRINT_TEXT("10mm","25mm","68mm","10mm","HUNAN CILI RURAL COMMERCIAL BANK");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
		LODOP.SET_PRINT_STYLEA(0, "Bold", 1);		
		//支持两种二维码的形式PDF417和QRCode，后面的内容就是扫描二维码显示的内容，所以需要显示什么可以在这里显示
		//LODOP.ADD_PRINT_BARCODE(30,405,176,67,"PDF417","我是一个二维码，扫我没钱哦");
		LODOP.ADD_PRINT_BARCODE("15mm","40mm","35mm","25mm","QRCode","终于做成功了!");
		LODOP.SET_PRINT_STYLEA(0,"GroundColor","#ffffff"); 
		LODOP.PREVIEW();
		//打印后自动关闭打印页面
		LODOP.SET_PRINT_MODE("AUTO_CLOSE_PREWINDOW",1);
	};
	
	//实现多张图片的打印
	function manypicture_print(){
		//这里先模拟有多个图片的内容作为输入(所以用数组进行存储)
		var pictures = new Array(2);
		//可以传网络地址或者本地服务器中的图片G:/front.png
		pictures[0] = "http://static15.photo.sina.com.cn/middle/4fe4ba17t993d651b55ce&690" ;
		pictures[1] = "http://s1.sinaimg.cn/middle/4fe4ba17hb5afe2caa990&690" ;
		for(var i = 0 ; i < pictures.length ; i++){
			alert(i);
			creatManyPicture(pictures[i]);
		}
	};
	//配合一次性打印多张图片的操作
	function creatManyPicture(content) {
    	//获取打印对象
		LODOP=getLodop();
    	//设置打印文件名
		LODOP.PRINT_INIT("多张图片的打印");
		//设置打印内容
		LODOP.ADD_PRINT_IMAGE(30,150,400,400,"<img border='0' src='" + content +"' />");
		LODOP.PRINT();	
	};

</script>
<body>
	<div id="divcontent">
		<pre>
		<b style="font-size: 18px;">湖南慈利农村商业银行</b></br>
		<b style="font-size: 10px;">HUNAN CILI RURAL COMMERCIAL BANK</b>
		</pre>	
	</div>
   <form action="" name="form1" id="form1">
	   <p>11111111111111111111111111111111</p>
	   <p>11111111111111111111111111111111</p>
	   <p>11111111111111111111111111111111</p>
	   <p>11111111111111111111111111111111</p>
	   <p>11111111111111111111111111111111</p>
	   <p>11111111111111111111111111111111</p>
	   <p>11111111111111111111111111111111</p>
	</form>
	<button onclick="myPreview1();">打印预览</button>
	<button onclick="myPrint1();">直接打印</button>
	<button onclick="select_printer();">选择打印机</button>
	<button onclick="myPrintSetup1();">图片调整打印</button>
	<!-- <BUTTON onclick="print_design();">打印设计</BUTTON> -->
	<button onclick="myPreview2();">大小缩放打印预览</button>
	<button onclick="myPreview3();">原图大小打印预览</button>
	<button onclick="myPreview4();">图片隐藏打印预览</button>
	<button onclick="design();">二维码设计</button>
	<button onclick="manypicture_print();">多张图片的打印(多张的话，就不支持预览了，就只能打印)</button>
	<!-- <img alt="打印" src="../static/img/print.jpg"> 
	<img alt="本地图片" src="../static/avatars/user.jpg">
	<img alt="" src="http://123.207.87.47:8080/tomcat.png">
	<img src="file:///G:/front.png"> 
	<img src="/picture/front.png">-->
	<img border='0' height="20px" width="100" src='<%=basePath%>/Lodop/banklogo.png'>
</body>
</html>