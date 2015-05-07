package com.bjtu.foodie.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.SystemClock;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.Moment;

public class TestData {
	private List<Moment> moments;

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
}
