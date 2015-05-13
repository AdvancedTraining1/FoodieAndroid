package com.bjtu.foodie.model;

import java.io.Serializable;

import com.baidu.mapapi.model.LatLng;

//no Location
@SuppressWarnings("serial")
public class RestaurantForSerializable implements Serializable {

	private String Uid;
	private String name;
	private double price; // per person
	private String address;
	private String telephone;
	private String openHours; // 营业时间
	private String type; // 餐厅类型

	// 評分
	private double overallRate;
	private double environmentRate;
	private double facilityRate;
	private double hygieneRate;// 衛生
	private double serviceRate;
	private double tasteRate;

	public RestaurantForSerializable(String uid, String rName, double rPrice,
			String rAddress, String telephone, String openHours, String type,
			double overallRate, double environmentRate, double facilityRate,
			double hygieneRate, double serviceRate, double tasteRate) {
		super();
		Uid = uid;
		this.name = rName;
		this.price = rPrice;
		this.address = rAddress;
		this.telephone = telephone;
		this.openHours = openHours;
		this.type = type;
		this.overallRate = overallRate;
		this.environmentRate = environmentRate;
		this.facilityRate = facilityRate;
		this.hygieneRate = hygieneRate;
		this.serviceRate = serviceRate;
		this.tasteRate = tasteRate;
	}

	public String getUid() {
		return Uid;
	}

	public void setUid(String uid) {
		Uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOpenHours() {
		return openHours;
	}

	public void setOpenHours(String openHours) {
		this.openHours = openHours;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getOverallRate() {
		return overallRate;
	}

	public void setOverallRate(double overallRate) {
		this.overallRate = overallRate;
	}

	public double getEnvironmentRate() {
		return environmentRate;
	}

	public void setEnvironmentRate(double environmentRate) {
		this.environmentRate = environmentRate;
	}

	public double getFacilityRate() {
		return facilityRate;
	}

	public void setFacilityRate(double facilityRate) {
		this.facilityRate = facilityRate;
	}

	public double getHygieneRate() {
		return hygieneRate;
	}

	public void setHygieneRate(double hygieneRate) {
		this.hygieneRate = hygieneRate;
	}

	public double getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(double serviceRate) {
		this.serviceRate = serviceRate;
	}

	public double getTasteRate() {
		return tasteRate;
	}

	public void setTasteRate(double tasteRate) {
		this.tasteRate = tasteRate;
	}

}