package com.frame.member.bean;

import java.util.ArrayList;


public class BookingOneResult extends BaseBean {

	public String totalItems;
	public ArrayList<Coach> coaches = new ArrayList<Coach>();
	
	public static class Coach{
		public String coachId,coachName,headImg,badgeName,levelName;
	}

	@Override
	public String toString() {
		return "BookingOneResult [totalItems=" + totalItems + ", coaches=" + coaches + "]";
	}
	
	
	
}
