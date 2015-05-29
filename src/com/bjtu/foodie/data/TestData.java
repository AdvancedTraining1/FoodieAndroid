package com.bjtu.foodie.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DateModel;
import com.bjtu.foodie.model.Moment;
import com.bjtu.foodie.model.Restaurant;
import com.bjtu.foodie.model.User;

public class TestData {
	private List<Moment> moments;
	private List<DateModel> dates;
	private List<User> friends;
	private static List<Restaurant> restaurants;

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
					"date%d", i), R.drawable.icon_avatar, "Barbecue",
					"Address: barbecue in huoshaoshi", new Date(
							System.currentTimeMillis()), new Date(
							System.currentTimeMillis()), 3);
			dates.add(d);
		}
		return dates;
	}

	public List<User> getFriendsData() {
		friends = new ArrayList<User>();
		for (int i = 0; i < 10; ++i) {
			User f = new User(String.format("friend%d", i), "123", "123");
			friends.add(f);
		}
		return friends;
	}

	public static List<Restaurant> getRecommendsData() {
		restaurants = new ArrayList<Restaurant>();
		for (int i = 0; i < 7; ++i) {
			Restaurant r = new Restaurant("123", "abc" + i, i, null, null,
					null, null, "fast food-No."+i, 0, 0, 0, 0, 0, 0);
			restaurants.add(r);
		}
		return restaurants;
	}
}
