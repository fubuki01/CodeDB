package com.hnran.perfmanagesys.activity;

import com.hnran.perfmanagesys.R;
import com.hnran.perfmanagesys.utils.ToastUtil;
import com.hnran.perfmanagesys.view.ImageDeleteEditText;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.LinearLayout;

/**
 * 搜索页面
 * @author tan
 * 2017-05-21
 *
 */
public class SearchActivity extends BaseActivity {

	public final static int SEARCH_RESULT_CODE = 9000;
	
	private ImageDeleteEditText etSearch;
	
//	/**
//	 * 定义接口，当搜索关键字输入完成是，回调获取数据
//	 * 2017-07-19
//	 *
//	 */
//	public interface SearchListener{
//		public void onSearchInputFinished();
//	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		setContentView(R.layout.activity_search);

		etSearch = (ImageDeleteEditText)findViewById(R.id.search_et_input);
//		/**
//		 * 获取焦点，并弹出软键盘
//		 */
		etSearch.requestFocus();
//		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		etSearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode == KeyEvent.KEYCODE_ENTER ){
					startSearch();
				}
				return false;
			}
		});

		LinearLayout llBottom = (LinearLayout)findViewById(R.id.search_ll_bottom);
		llBottom.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	}


	public void startSearch(){
		String result = etSearch.getText().toString().trim();
		if(!"".equals(result) ){
			Intent intent=new Intent();  
			intent.putExtra("Extra_result", result); 
			setResult(SEARCH_RESULT_CODE, intent);
			this.finish();
		}else{
			ToastUtil.showToast(this, "输入不能为空");
		}
	}

	@Override
	protected void notifyView() {
		// TODO Auto-generated method stub
		
	}
}
