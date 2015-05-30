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
		dishes.get(0).setName("烤鸭");
		dishes.get(0).setPic(R.drawable.res1);
		dishes.get(0).setDesc("88/份");
		dishes.get(1).setName("大盘鸡");
		dishes.get(1).setPic(R.drawable.res2);
		dishes.get(1).setDesc("48/份");
		dishes.get(2).setName("宫保鸡丁");
		dishes.get(2).setPic(R.drawable.res3);
		dishes.get(2).setDesc("38/份");
		dishes.get(3).setName("麻婆豆腐");
		dishes.get(3).setPic(R.drawable.res4);
		dishes.get(3).setDesc("28/份");
		dishes.get(4).setName("水煮肉片");
		dishes.get(4).setPic(R.drawable.res5);
		dishes.get(4).setDesc("48/份");
		dishes.get(5).setName("干煸豆角");
		dishes.get(5).setPic(R.drawable.res6);
		dishes.get(5).setDesc("28/份");
		return dishes;
	}
}
