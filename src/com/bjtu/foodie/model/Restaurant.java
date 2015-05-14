package com.bjtu.foodie.model;

import java.io.Serializable;

import com.baidu.mapapi.model.LatLng;

// define varieties of restaurant from baiduMap
public class Restaurant {
	
	private String Uid;
	private String name;
	private double price; // per person
	private String address; 
	private LatLng location; // 坐标
	private String telephone;
	private String openHours; //营业时间
	private String type; // 餐厅类型
	
	//評分
	private double overallRate;
	private double environmentRate;
	private double facilityRate;
	private double hygieneRate;// 衛生
	private double serviceRate;
	private double tasteRate;
	
	public Restaurant() {
	}
	
	public Restaurant(RestaurantForSerializable rs) {
		Uid = rs.getUid();
		this.name = rs.getName();
		this.price = rs.getPrice();
		this.address = rs.getAddress();
		this.telephone = rs.getTelephone();
		this.openHours = rs.getOpenHours();
		this.type = rs.getType();
		this.overallRate = rs.getOverallRate();
		this.environmentRate = rs.getEnvironmentRate();
		this.facilityRate = rs.getFacilityRate();
		this.hygieneRate = rs.getHygieneRate();
		this.serviceRate = rs.getServiceRate();
		this.tasteRate = rs.getTasteRate();
	}
	
	public Restaurant(String uid, String rName, String rAddress,
			LatLng rLocation, String telephone, String type) {
		super();
		Uid = uid;
		this.name = rName;
		this.address = rAddress;
		this.location = rLocation;
		this.telephone = telephone;
		this.type = type;
	}

	public Restaurant(String uid, String rName, double rPrice,
			String rAddress, LatLng rLocation, String telephone,
			String openHours, String type) {
		super();
		Uid = uid;
		this.name = rName;
		this.price = rPrice;
		this.address = rAddress;
		this.location = rLocation;
		this.telephone = telephone;
		this.openHours = openHours;
		this.type = type;
	}

	public Restaurant(String uid, String rName, double rPrice,
			String rAddress, LatLng rLocation, String telephone,
			String openHours, String type, double overallRate,
			double environmentRate, double facilityRate, double hygieneRate,
			double serviceRate, double tasteRate) {
		super();
		Uid = uid;
		this.name = rName;
		this.price = rPrice;
		this.address = rAddress;
		this.location = rLocation;
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

	public void setName(String rName) {
		this.name = rName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double rPrice) {
		this.price = rPrice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String rAddress) {
		this.address = rAddress;
	}

	public LatLng getLocation() {
		return location;
	}

	public void setLocation(LatLng rLocation) {
		this.location = rLocation;
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