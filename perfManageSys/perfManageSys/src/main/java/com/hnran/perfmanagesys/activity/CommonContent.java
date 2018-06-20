package com.hnran.perfmanagesys.activity;

public class CommonContent {
	
	public final static String[] YINGXIAO_LEIXING = {"","主动营销","自然增长"};

	public final static String[] WUJI_FENLEI = {"", "正常", "关注", "次级", "可疑", "损失"};
	
	public final static String ERROR_NETWORK = "网络错误";
	public final static String ERROR_NO_RESULT = "未查询到数据";
	
	public final static String ERROR_HOME_RESULT = "请检查数据库配置";

	public final static String OPERATION_SUCCESS = "操作成功";
	public final static String OPERATION_FAILED= "操作失败";
	
	public final static int NETWORK_TIME_OUT_1 = 10000;		//网络请求超时时间
	public final static int NETWORK_TIME_OUT_2 = 20000;		//网络请求超时时间
	public final static int NETWORK_TIME_OUT_3 = 30000;		//网络请求超时时间

	public static final String ERROR_CONTACTLOG_RESULT = "获取通话记录信息失败";

	/*拜访其他信息*/
	public static final String NOPHOTOSAVE = "没有附件信息可以保存";
	public static final String NOATTACHNAME = "附件名称未填写";
	public static final String PLEASESELECTATTACHTYPE = "请选择附件种类";

	/*拜访联系人信息*/
	public static final String PLEASEINPUTFAMILYNUMBER = "请选择家庭成员";
	public static final String PLEASEFAMILYNUMBERPHONE = "请输入家庭成员的联系方式";
	public static final String NOFINDFILEPATH = "未找到文件路径";
	public static final String NOFINDRECORDFILE = "抱歉，未找到录音文件";
}
