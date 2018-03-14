package com.hnran.perfmanagesys.utils;

import com.android.internal.http.multipart.Part;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * https://my.oschina.net/ggsddu/blog/601285
 * @author Tower
 *
 */

public class MultipartRequest extends StringRequest {
	private Part[] parts;

	public MultipartRequest(String url, Part[] parts, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		super(Method.POST, url, listener, errorListener);
		this.parts = parts;
	}

	@Override
	public String getBodyContentType() {
		return "multipart/form-data; boundary=" + Part.getBoundary();
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Part.sendParts(baos, parts);
		} catch (IOException e) {
			VolleyLog.e(e, "error when sending parts to output!");
		}
		return baos.toByteArray();
	}
	
	//使用代码
//	//构造参数列表
//	List<Part> partList = new ArrayList<Part>();
//	partList.add(new StringPart("username", "hellfire"));
//	partList.add(new StringPart("email", "ouyangjun@aliyun.com"));
//	try {
//	    partList.add(new FilePart("photo", new File("/mnt/sdcard/Test/hellfire.jpg")));
//	} catch (FileNotFoundException e) {
//	    e.printStackTrace();
//	}
//	//获取队列
//	RequestQueue requestQueue = Volley.newRequestQueue(this);
//	String url = "http://test/profileUpdate.do";
//	//生成请求
//	MultipartRequest profileUpdateRequest = new MultipartRequest(url, partList.toArray(new Part[partList.size()]), new Response.Listener<String>() {
//	    @Override
//	    public void onResponse(String response) {
//	        //处理成功返回信息
//	        String info = response.substring(0, 20);
//	        Toast.makeText(getApplication(), info, Toast.LENGTH_SHORT).show();
//	    }
//	}, new Response.ErrorListener() {
//	    @Override
//	    public void onErrorResponse(VolleyError error) {
//	        //处理失败错误信息
//	        Log.e("MultipartRequest", error.getMessage(), error);
//	        Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_SHORT).show();
//	    }
//	});
//	//将请求加入队列
//	requestQueue.add(profileUpdateRequest);
	
}
