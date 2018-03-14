package com.readboy.HandWrite;

/**
 * 手写识别JNI接口，调用顺序：{@link #initHandWrite(String)}-->
 * [{@link #setReconizeEngilish()}|{@link #iHCR_SetParam(long, long)}...]-->
 * {@link #exitHandWriteInit()}，函数的具体实现在jni\目录下的com_readboy_HandWrite.c中
 * @author css
 * @version 1.0
 */
public class HandWrite {
	
	static {
		System.loadLibrary("TestHandWrite");
	}

	/**
	 * 开始手写识别
	 * @param ncout 笔画采样点数量
	 * @param polongArray 笔画采样点
	 * @param resultArray 识别结果保存buf
	 * @return 函数执行成功则返回识别到的字符(unicode编码)个数，否则，函数执行错误，被定义为-1，
	 * 函数调用状态错误，被定义为-2，函数调用参数错误，被定义为-3
	 */
	public static native int reconizePoint(int ncout, short[] polongArray,
			short[] resultArray);
	
	/**
	 * 初始化 iHCR引擎，加载字典资源（含识别字典和联想字典）
	 * @param dataPath 指向字典资源所在的内存位置。
	 * @return 初始化成功返回 1，失败返回 0
	 */
	public static native int initHandWrite(String dataPath);
	
	/**
	 * 释放内存，退出 iHCR 引擎。
	 * @return 始终为1
	 */
	public static native int exitHandWriteInit();
	
	/**
	 * 设置只识别英文字母。 
	 * @return 识别范围设置成功返回 0，否则小于 0
	 */
	public static native int setReconizeEngilish();

	/**
	 * 设置识别参数。
	 * @param paramName 待设定的参数名称，具体名称范围及意义参考枚举类型iHCR_PARAM
	 * @param paramValue 设定参数值，具体取值范围及意义参考枚举类型iHCR_PARAM
	 * @return 识别范围设置成功返回0，否则小于 0
	 */
	public static native int iHCR_SetParam(long paramName, long paramValue);

	/**
	 * 显式设置手写区域。
	 * @param left 待设置手写区域左边界坐标值（对于左上方原点的相对坐标系来说一般为 0） 
	 * @param right待设置手写区域右边界坐标值（对于左上方原点的相对坐标系来说一般为手写区域的宽度值）
	 * @param top 待设置手写区域上边界坐标值（对于左上方原点的相对坐标系来说一般为 0）
	 * @param bottom 待设置手写区域下边界坐标值（对于左上方原点的相对坐标系来说一般为手写区域的高度值）
	 * @return 设置成功返回0，否则小于 0
	 */
	public static native int HCR_SetWritingArea(long left, long right,
			long top, long bottom);

	/** iHCR 函数执行成功，被定义为0   */
	public static long iHCR_OK = 0;
	/** 函数执行错误，被定义为-1    */
	public static long iHCR_ERROR = -1;
	/** 函数调用状态错误，被定义为-2   */
	public static long iHCR_INVCAL = -2;
	/** 函数调用参数错误，被定义为-3    */
	public static long iHCR_INVARG = -3;
	/** 资源错误，被定义为-4    */
	public static long iHCR_RESERROR = -4;

	public static long iHCR_LANGUAGE_NONE = 0x00000000;
	/* a-z */
	public static long iHCR_LANGUAGE_English_LowerCase = 0x00000001;
	/* A-Z */
	public static long iHCR_LANGUAGE_English_UpperCase = 0x00000002;
	/* lowercase and uppercase */
	public static long iHCR_LANGUAGE_English = iHCR_LANGUAGE_English_LowerCase
			| iHCR_LANGUAGE_English_UpperCase;

	/*
	 * longerpunctions:
	 * 0x0021-0x002F=0xFF01-0xFF0F;,0x003A-0x0040=0xFF1A-0xFF20;,
	 */
	/*
	 * 0x005B-0x0060=0xFF3B-0xFF40;,0x007B-0x007E=0xFF5B-0xFF5E;,
	 * 0x00A2=0xFFE0;,
	 */
	/* 0x00A3=0xFFE1;,0x00A5=0xFFE5;,0x00B0,0x00D7,0x00F7, 0x2014,0x2018,0x2019, */
	/*
	 * 0x201C,0x201D,0x2022,0x2026,0x2030,0x2032,0x2033,0x20AC,0x2103,0x221A,0x2236
	 * ,
	 */
	/* 0x3001,0x3002,0x3008,0x3009,0x300A,0x300B */
	public static long iHCR_LANGUAGE_Symbols = 0x00000004;
	/* 0-9 */
	public static long iHCR_LANGUAGE_Numerals = 0x00000008;
	/* space=0x0020;, enter=0x000D;, backspace=0x0008;, delete=0x001E; */
	public static long iHCR_LANGUAGE_Gestures = 0x00000010;

	public static long iHCR_LANGUAGE_REGION_Mask = 0xF0000000;
	public static long iHCR_LANGUAGE_REGION_European = 0x10000000;
	public static long iHCR_LANGUAGE_REGION_African = 0x20000000;
	public static long iHCR_LANGUAGE_REGION_MiddleEastern = 0x30000000;
	public static long iHCR_LANGUAGE_REGION_American = 0x40000000;
	public static long iHCR_LANGUAGE_REGION_Indic = 0x50000000;
	public static long iHCR_LANGUAGE_REGION_Philippine = 0x60000000;
	public static long iHCR_LANGUAGE_REGION_SouthEastAsian = 0x70000000;
	public static long iHCR_LANGUAGE_REGION_EastAsian = 0x80000000;
	public static long iHCR_LANGUAGE_REGION_CentralAsian = 0x90000000;
	public static long iHCR_LANGUAGE_REGION_AncientAsian = 0xA0000000;
	public static long iHCR_LANGUAGE_REGION_Other = 0xB0000000;

	public static long iHCR_LANGUAGE_German = iHCR_LANGUAGE_REGION_European | 0x00000100;
	public static long iHCR_LANGUAGE_French = iHCR_LANGUAGE_REGION_European | 0x00000200;
	public static long iHCR_LANGUAGE_Italy = iHCR_LANGUAGE_REGION_European | 0x00000400;
	public static long iHCR_LANGUAGE_Spanish = iHCR_LANGUAGE_REGION_European | 0x00000800;
	public static long iHCR_LANGUAGE_Portuguese = iHCR_LANGUAGE_REGION_European | 0x00001000;
	public static long iHCR_LANGUAGE_Polish = iHCR_LANGUAGE_REGION_European | 0x00002000;
	public static long iHCR_LANGUAGE_Dutch = iHCR_LANGUAGE_REGION_European | 0x00004000;
	public static long iHCR_LANGUAGE_Czech = iHCR_LANGUAGE_REGION_European | 0x00008000;
	public static long iHCR_LANGUAGE_Hungarian = iHCR_LANGUAGE_REGION_European | 0x00010000;
	public static long iHCR_LANGUAGE_Danish = iHCR_LANGUAGE_REGION_European | 0x00020000;
	public static long iHCR_LANGUAGE_Finnish = iHCR_LANGUAGE_REGION_European | 0x00040000;
	public static long iHCR_LANGUAGE_Icelandic = iHCR_LANGUAGE_REGION_European | 0x00080000;
	public static long iHCR_LANGUAGE_Norwegian = iHCR_LANGUAGE_REGION_European | 0x00100000;
	public static long iHCR_LANGUAGE_Swedish = iHCR_LANGUAGE_REGION_European | 0x00200000;
	public static long iHCR_LANGUAGE_Turkish = iHCR_LANGUAGE_REGION_European | 0x00400000;
	public static long iHCR_LANGUAGE_Russian = iHCR_LANGUAGE_REGION_European | 0x00800000;
	public static long iHCR_LANGUAGE_Serbian = iHCR_LANGUAGE_REGION_European | 0x01000000;
	public static long iHCR_LANGUAGE_Greek = iHCR_LANGUAGE_REGION_European | 0x02000000;
	public static long iHCR_LANGUAGE_Macedonian = iHCR_LANGUAGE_REGION_European | 0x04000000;

	public static long iHCR_LANGUAGE_Arabic = iHCR_LANGUAGE_REGION_MiddleEastern | 0x00000100;
	/* Not supported yet */
	public static long iHCR_LANGUAGE_Hebrew = iHCR_LANGUAGE_REGION_MiddleEastern | 0x00000200;

	public static long iHCR_LANGUAGE_Thai = iHCR_LANGUAGE_REGION_SouthEastAsian | 0x00000100;
	public static long iHCR_LANGUAGE_Vietnamese = iHCR_LANGUAGE_REGION_SouthEastAsian | 0x00000200;

	/* GB2312, total number: 6763 */
	public static long iHCR_LANGUAGE_Chinese_GB = iHCR_LANGUAGE_REGION_EastAsian | 0x00000200;
	/* GBK, total number: 21003 */
	public static long iHCR_LANGUAGE_Chinese_GBK = iHCR_LANGUAGE_REGION_EastAsian | 0x00000100;
	/* Big5, total number: 13053 */
	public static long iHCR_LANGUAGE_Chinese_Big5 = iHCR_LANGUAGE_REGION_EastAsian | 0x00000400;
	public static long iHCR_LANGUAGE_Chinese_Symbols = iHCR_LANGUAGE_REGION_EastAsian | 0x00000800;
	public static long iHCR_LANGUAGE_Hangul = iHCR_LANGUAGE_REGION_EastAsian | 0x00004000;
	public static long iHCR_LANGUAGE_Japanese_Hiragana = iHCR_LANGUAGE_REGION_EastAsian | 0x00008000;
	public static long iHCR_LANGUAGE_Japanese_Katagana = iHCR_LANGUAGE_REGION_EastAsian | 0x00010000;
	public static long iHCR_LANGUAGE_Japanese_Kanji = iHCR_LANGUAGE_REGION_EastAsian | 0x00020000;

	/* toggle recognition support for cursive strokes */
	public static long iHCR_RECOGNITION_CURSIVE = 0x0001;
	/* toggle recognition support for slant handwritten word */
	public static long iHCR_RECOGNITION_SLANT = 0x0002;
	/* toggle recognition support for disorder written strokes */
	public static long iHCR_RECOGNITION_DISORDERED = 0x0004;
	/* toggle recognition support for rotated handwritten word */
	public static long iHCR_RECOGNITION_ROTATION = 0x0008;
	public static long iHCR_RECOGNITION_DEFAULT = iHCR_RECOGNITION_CURSIVE;

	public static long iHCR_RECOGNITION_CONFIG_FLAG = 0xF000;
	/* default recognition manner, treat input polongs as character */
	public static long iHCR_RECOGNITION_CHAR = 0x1000;
	/* treat input polongs as sentence, *new* in iHCR3.0 */
	public static long iHCR_RECOGNITION_SENT_MASK = 0x2000;
	/* treat input polongs as sentence written in a line */
	public static long iHCR_RECOGNITION_SENT_LINE = iHCR_RECOGNITION_SENT_MASK | 0x0001;
	/* treat input polongs as sentence written in full-screen */
	public static long iHCR_RECOGNITION_SENT_FS = iHCR_RECOGNITION_SENT_MASK | 0x0002;
	/* treat input polongs as sentence written overlaply */
	public static long iHCR_RECOGNITION_SENT_OVLP = iHCR_RECOGNITION_SENT_MASK | 0x0003;

	/* new function in iHCR2.1 */
	/* a one-stop recognition function */

	public static long iHCR_ACTION_Recognition = 0x00000000;
	public static long iHCR_ACTION_Trad2Simp = 0x00000001;
	public static long iHCR_ACTION_Simp2Trad = 0x00000002;
	public static long iHCR_ACTION_Homophone = 0x00000003;
	public static long iHCR_ACTION_Adaptation = 0x00000004;
	public static long iHCR_ACTION_Reset = 0x00000005;

	public static long iHCR_USERDICT_SIZE = 96 * 1024;
	public static long iHCR_USERWORD_SIZE = 8 * 1024;
	
	public enum iHCR_PARAM
    {
        /* parameter indicating recognition range */
        /* supported value range: iHCR_LANGUAGE_* and their '|' combination */
        /* with iHCR_LANGUAGE_ flag of english/numeral/symbols */
        iHCR_PARAM_LANGUAGE,

        /* parameter indication recognition mode */
        /* support value range: iHCR_RECONIGTION_CURSIVE, iHCR_RECOGNITION_SLANT, */
        /* iHCR_RECOGNITION_DISORDERED, iHCR_RECOGNITION_ROTATION and their '|' combination */
        iHCR_PARAM_RECOGMODE,

        /* parmeter indicating recognition engine's working manner, *new* in 3.0 */
        /* supported value range: iHCR_RECOGNITION_CHAR, iHCR_RECOGNITION_SENT_LINE, */
        /* iHCR_RECOGNITION_SENT_FS, iHCR_RECOGNITION_SENT_OVLP */
        iHCR_PARAM_RECOGMANNER,

        /* parameter indication maximum word number of returned recognition result */
        iHCR_PARAM_MAXCANDNUM
    } 

}
