package com.bjtu.foodie.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DateModel;
import com.bjtu.foodie.model.Moment;

public class TestData {
	private List<Moment> moments;
	private List<DateModel> dates;

	public List<Moment> getMomentsData() {
		moments = new ArrayList<Moment>();
		for (int i = 0; i < 10; ++i) {
			Moment m = new Moment(String.format("%d", i), String.format(
					"moment%d", i), R.drawable.icon_avatar,
					"Awesome!!!lalalala", R.drawable.bg_beach1, (new Date(
							System.currentTimeMillis())));
			moments.add(m);
		}
		return moments;
	}
	
	public List<DateModel> getDatesData() {
		dates = new ArrayList<DateModel>();
		for (int i = 0; i < 10; ++i) {
			DateModel d = new DateModel(String.format("%d", i), String.format(
					"date%d", i), R.drawable.icon_avatar,"Barbecue",
					"We take a barbecue in huoshaoshi", new Date(
							System.currentTimeMillis()), new Date(
							System.currentTimeMillis()),8);
			dates.add(d);
		}
		return dates;
	}
}
