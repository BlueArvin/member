package com.frame.member.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.member.R;
import com.frame.member.AppConstants.AppConstants;
import com.frame.member.Parsers.BaseParser;
import com.frame.member.Parsers.LoginCodeParser;
import com.frame.member.Parsers.RegisterLoginParser;
import com.frame.member.Parsers.RegisterParser;
import com.frame.member.Utils.CommonUtil;
import com.frame.member.Utils.HttpRequest;
import com.frame.member.Utils.HttpRequestImpl;
import com.frame.member.Utils.SPUtils;
import com.frame.member.Utils.TimeCount;
import com.frame.member.bean.LoginCodeResult;
import com.frame.member.bean.RegisterLoginResult;
import com.frame.member.bean.RegisterResult;

public class LoginActivity extends BaseActivity implements OnClickListener {

	TextView tv_code_send,tv_login_button,tv_title_left_login,tv_title_right_login;
	EditText et_phone_num,et_code;
	ImageView iv_login_weixin,iv_login_weibo,iv_login_qq;
	RelativeLayout rl_title_left,rl_title_right;
	View view_title_left,view_title_right;
	TimeCount timer;
	boolean isRight;

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_login);
	}

	@Override
	protected void findViewById() {
		tv_code_send = (TextView) findViewById(R.id.tv_code_send);
		tv_login_button = (TextView) findViewById(R.id.tv_login_button);
//		tv_login2_button = (TextView) findViewById(R.id.tv_login2_button);
		tv_title_left_login = (TextView) findViewById(R.id.tv_title_left_login);
		tv_title_right_login = (TextView) findViewById(R.id.tv_title_right_login);
		et_phone_num = (EditText) findViewById(R.id.et_phone_num);
		et_code = (EditText) findViewById(R.id.et_code);
		iv_login_weixin = (ImageView) findViewById(R.id.iv_login_weixin);
		iv_login_weibo = (ImageView) findViewById(R.id.iv_login_weibo);
		iv_login_qq = (ImageView) findViewById(R.id.iv_login_qq);
		rl_title_left = (RelativeLayout) findViewById(R.id.rl_title_left);
		rl_title_right = (RelativeLayout) findViewById(R.id.rl_title_right);
		view_title_left = findViewById(R.id.view_title_left);
		view_title_right = findViewById(R.id.view_title_right);
	}

	@Override
	protected void setListener() {
		iv_login_weixin.setOnClickListener(this);
		iv_login_weibo.setOnClickListener(this);
		iv_login_qq.setOnClickListener(this);
		tv_code_send.setOnClickListener(this);
		tv_login_button.setOnClickListener(this);
//		tv_login2_button.setOnClickListener(this);
		rl_title_left.setOnClickListener(this);
		rl_title_right.setOnClickListener(this);
		
	}

	

	@Override
	protected void processLogic() {
		timer = new TimeCount(60000, 1000, tv_code_send, 0, this);
		tv_login_button.setText("登录");
		tv_title_left_login.setTextSize(16);
		tv_title_left_login.setTextColor(0xffffffff);
		tv_title_right_login.setTextColor(0xff878788);
		tv_title_right_login.setTextSize(14);
		view_title_left.setVisibility(View.VISIBLE);
		view_title_right.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_login_weixin:
			showToast("微信登录");
			break;
		case R.id.iv_login_weibo:
			showToast("微博登录");
			
			break;	
		case R.id.iv_login_qq:
			showToast("QQ登录");
			break;
		case R.id.tv_code_send:
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else{
				toGetCode();
				
			}
			
			break;
		case R.id.tv_login_button: 		//注册or登录
			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
				showToast("请输入手机号");
			}else if(TextUtils.isEmpty(et_code.getText().toString())){
				showToast("请输入验证码");
			}else{
				if(!isRight){
					toLogin();
				}else{
					toLoginRegister();
				}
				
			}
			
			
			break;
//		case R.id.tv_login2_button:		//登录
//			if(TextUtils.isEmpty(et_phone_num.getText().toString())){
//				showToast("请输入手机号");
//			}else if(TextUtils.isEmpty(et_code.getText().toString())){
//				showToast("请输入验证码");
//			}else{
//				toLogin();
//			}
//			
//			break;
		case R.id.rl_title_left:
			if(isRight){
				tv_title_left_login.setTextColor(0xffffffff);
				tv_title_right_login.setTextColor(0xff878788);
				tv_title_left_login.setTextSize(16);
				tv_title_right_login.setTextSize(14);
				view_title_left.setVisibility(View.VISIBLE);
				view_title_right.setVisibility(View.INVISIBLE);
				tv_login_button.setText("登录");
				isRight = false;
			}
			break;
		case R.id.rl_title_right:
			if(!isRight){
				tv_title_right_login.setTextColor(0xffffffff);
				tv_title_left_login.setTextColor(0xff878788);
				tv_title_right_login.setTextSize(16);
				tv_title_left_login.setTextSize(14);
				view_title_right.setVisibility(View.VISIBLE);
				view_title_left.setVisibility(View.INVISIBLE);
				tv_login_button.setText("注册");
				isRight = true;
			}
			break;
		}
	}
	
	//请求验证码接口
	private void toGetCode(){
		BaseParser<LoginCodeResult> parser = new LoginCodeParser();
		HttpRequestImpl request = new HttpRequestImpl(LoginActivity.this,
				AppConstants.APP_SORT_STUDENT+"/getappverify", parser,HttpRequest.RequestMethod.post);
		
		request.addParam("mobile", et_phone_num.getText().toString());
		getDataFromServer(request, callback);
	}
	private DataCallback<LoginCodeResult> callback = new DataCallback<LoginCodeResult>() {

		@Override
		public void processData(LoginCodeResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(object != null){
					if("200".equals(object.code)){
						timer.start();
						showToast(object.message);
					}
				}
			}
		}
	};
	
	// 注册接口
	private void toLoginRegister(){
		BaseParser<RegisterResult> parser = new RegisterParser();
		HttpRequestImpl request = new HttpRequestImpl(LoginActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/appregister", parser);
		request.addParam("mobile", et_phone_num.getText().toString())
				.addParam("verificationCode", et_code.getText().toString());
//		request.addParam("mobile", et_code.getText().toString())
//				.addParam("verificationCode", et_phone_num.getText().toString());
		getDataFromServer(request, callback2);
//		Intent it = new Intent(this,MainActivity.class);
//		startActivity(it);
	}
	
	private DataCallback<RegisterResult> callback2 = new DataCallback<RegisterResult>() {
		
		@Override
		public void processData(RegisterResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						showToast(object.code);
						SPUtils.getAppSpUtil().put(
								LoginActivity.this, SPUtils.KEY_MEMBERTEL, object.mobile);
						SPUtils.getAppSpUtil().put(
								LoginActivity.this, SPUtils.KEY_TOKEN, object.token);
						startActivity(new Intent(LoginActivity.this,MainActivity.class));
					}
				}
			}
		}
	};
	//登录接口
	private void toLogin(){
		BaseParser<RegisterLoginResult> parser = new RegisterLoginParser();
		HttpRequestImpl request = new HttpRequestImpl(LoginActivity.this, 
				AppConstants.APP_SORT_STUDENT+"/applogin", parser);
		request.addParam("mobile", et_phone_num.getText().toString())
				.addParam("verificationCode", et_code.getText().toString());
//		request.addParam("mobile", et_code.getText().toString())
//				.addParam("verificationCode", et_phone_num.getText().toString());
		getDataFromServer(request, callback1);
	}
	private DataCallback<RegisterLoginResult> callback1 = new DataCallback<RegisterLoginResult>() {

		@Override
		public void processData(RegisterLoginResult object, RequestResult result) {
			if(result == RequestResult.Success){
				if(null != object){
					if("200".equals(object.code)){
						showToast(object.message);
						
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERUSERID, object.memberUserId);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERIDEN, object.memberIden);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERLLEVEL, object.memberlLevel);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERGRADE, object.memberGrade);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_ISTEACHER, object.isTeacher);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_WXOPENID, object.wxOpenId);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_WXHEAD, object.wxHead);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_WXNICHENG, object.wxNiCheng);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERTEL, object.memberTel);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERNAME, object.memberName);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERSEX, object.memberSex);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERSIGN, object.memberSign);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERBIRTH, object.memberBirth);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERPROVINCE, object.memberProvince);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERCITY, object.memberCity);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERAREA, object.memberArea);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERADDRESS, object.memberAddress);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_REGTIME, object.regtime);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERPOINTS, object.memberPoints);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_ISOPEN, object.isOpen);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_UPDETIME, object.updeTime);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_RECOFOLLOW, object.recofollow);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_NOTICESET, object.noticeset);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_MEMBERFROM, object.memberFrom);
							SPUtils.getAppSpUtil().put(
									LoginActivity.this, SPUtils.KEY_TOKEN, object.token);
							startActivity(new Intent(LoginActivity.this,MainActivity.class));
							
						}
				}
			}
		}
	};


}
