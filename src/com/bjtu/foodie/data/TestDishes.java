package com.bjtu.foodie.data;

import java.util.ArrayList;
import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DishItem;

public class TestDishes {
	private List<DishItem> dishes;

	public List<DishItem> getData() {
		
		int[] colors = new int[] { 0x00010101, 0x55808080 };
		dishes = new ArrayList<DishItem >();
		//DishItem(String id, String dishName,String desc,int pic)
		for (int i = 0; i < 6; ++i) {
			
			int colorPos = i % colors.length;
			int temp,col;
			if(colorPos == 0){
				temp=R.drawable.jay;
				col = colors[colorPos];
			}	
		    else{
		    	temp=R.drawable.image;
		    	col = colors[colorPos];
		    }
			DishItem m = new DishItem(String.format("%d", i), String.format(
					"dish%d", i),"Awesome!!!lalalala1234567890", temp,col);
			dishes.add(m);
		}
		return dishes;
	}
}
