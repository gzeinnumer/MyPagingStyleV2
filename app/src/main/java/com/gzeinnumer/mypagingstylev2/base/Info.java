package com.gzeinnumer.mypagingstylev2.base;

import com.google.gson.annotations.SerializedName;

public class Info {

	@SerializedName("next")
	private String next;

	@SerializedName("total")
	private int total;

	@SerializedName("totalPage")
	private String totalPage;

	@SerializedName("prev")
	private String prev;

	@SerializedName("page")
	private String page;

	public String getNext(){
		return next;
	}

	public int getTotal(){
		return total;
	}

	public String getTotalPage(){
		return totalPage;
	}

	public String getPrev(){
		return prev;
	}

	public String getPage(){
		return page;
	}
}