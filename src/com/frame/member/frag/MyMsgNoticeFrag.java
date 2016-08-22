package com.frame.member.frag;

import java.util.ArrayList;
import java.util.List;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.BookingClassParser;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.activity.BaseActivity;
import com.frame.member.activity.ClassDetailActivity;
import com.frame.member.activity.BaseActivity.DataCallback;
import com.frame.member.activity.BaseActivity.RequestResult;
import com.frame.member.bean.BookingClassResult;
import com.frame.member.widget.refreshlistview.PullToRefreshBase;
import com.frame.member.widget.refreshlistview.PullToRefreshListView;
import com.frame.member.widget.refreshlistview.PullToRefreshBase.Mode;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的消息-通知页面
 * @author Ron
 * @date 2016-8-18  下午11:52:27
 */
public class MyMsgNoticeFrag extends BaseFrag implements OnClickListener {

	private static MyMsgNoticeFrag mFrag;
	public static MyMsgNoticeFrag newInstance(String title) {

		MyMsgNoticeFrag fragment = new MyMsgNoticeFrag();
		Bundle bundle = new Bundle();
		bundle.putString(AppConstants.FRAG_TITLE_KEY, title);
		fragment.setArguments(bundle);
        return fragment;
    }
	
	/**
	 * 页面创建
	 * @author Ron
	 * @date 2016-8-19  下午11:25:42
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.frag_my_msg_notice, container,
				false);
		
		initView();
		initOnclick();
		initProgress();
		return rootView;
	}
	private void initView(){
	}
	/**
	 * 点击监听事件绑定
	 * @author Ron
	 * @date 2016-8-19  下午11:21:34
	 */
	private void initOnclick(){
	}
	/**
	 * 主逻辑代码
	 * @author Ron
	 * @date 2016-8-19  下午11:21:49
	 */
	private void initProgress(){
		getData();
	}
	int page;
	private List<BookingClassResult> list_result = new ArrayList<BookingClassResult>();
	//请求获取服务端数据
	private void getData(){
		if(page == 0)
			page = 1;
		BaseParser<List<BookingClassResult>> parser = new BookingClassParser();
		HttpRequest request = new HttpRequestImpl(getActivity(), 
				AppConstants.APP_SORT_STUDENT +"/skiingclass", parser);
		request.addParam("page_size", "10")
				.addParam("page_num", ""+page);
		((BaseActivity)getActivity()).getDataFromServer(request, false,callBack);
		
	}
	/**
	 * 网络请求回调事件
	 */
	private DataCallback<List<BookingClassResult>> callBack = new DataCallback<List<BookingClassResult>>() {

		@Override
		public void processData(List<BookingClassResult> object, RequestResult result) {
		}
		
	};
	
	/**
	 *点击事件判断 
	 * @author Ron
	 * @date 2016-8-19  下午11:24:54
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
