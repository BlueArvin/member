package com.frame.member.bean;



public class RegisterResult extends BaseBean {
//	{
//	    "code": "200",
//	    "message": "注册成功",
//	    "data": {
//			"mobile": "18689467368",
//	        "token": "1o0r2wesza5pohj8"
//	    }
//	}
	public String token;
	public String mobile;
	
	@Override
	public String toString() {
		return "RegisterResult [token=" + token + ", mobile=" + mobile + "]";
	}

	
	
}