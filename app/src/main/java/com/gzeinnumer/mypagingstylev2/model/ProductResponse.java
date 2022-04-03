package com.gzeinnumer.mypagingstylev2.model;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ProductResponse{

	@SerializedName("volume")
	private String volume;

	@SerializedName("trans_date")
	private String transDate;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private String id;

	public String getVolume(){
		return volume;
	}

	public String getTransDate(){
		return transDate;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getId(){
		return id;
	}

	@Override
	public String toString() {
	    return new GsonBuilder().setPrettyPrinting().create().toJson(this, ProductResponse.class);
	}
}