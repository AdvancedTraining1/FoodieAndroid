package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DishItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FullDishItemsAdapter2 extends BaseAdapter {
	public HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	private List<DishItem> dishes;
	private Context context;
	ArrayList<Boolean> checkedItem = new ArrayList<Boolean>();
	ArrayList<String> idList = new ArrayList<String>();
	
	
	public FullDishItemsAdapter2(Context contex, List<DishItem> dishes) {
		this.dishes = dishes;
		this.context = contex;
		for(int i=0;i<dishes.size();i++){
            checkedItem.add(i,false);
            idList.add(i,null);
        }
	}
	
	public ArrayList<Boolean> getChecklist() {
		return checkedItem;
	}
	
	public ArrayList<String> getIDList() {
		return idList;
	}

	@Override
	public int getCount() {
		//Toast.makeText(context, "number" + dishes.size(), Toast.LENGTH_SHORT).show();
		return dishes == null ? 0 : dishes.size();
	}

	@Override
	public Object getItem(int arg0) {
		return dishes == null ? null : dishes.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public String getMomentId(int arg0) {
		return dishes.get(arg0).getId();
	}

	@Override
	public View getView(final int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		final int p=position;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.dishlist2,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		DishItem dish = this.dishes.get(position);
		holder.title.setText(dish.getName());
		holder.text.setText(dish.getDesc());
		view.setBackgroundColor(dish.getColor());
		
		//---------getPic == id of the pic in drawable-------
		holder.iamge.setImageResource(dish.getPic());
		final String id = dish.getId();
		holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
	                   //update the status of checkbox to checked
	                    
					state.put(position, isChecked);
	                }else{
	                  //update the status of checkbox to unchecked
	                	state.remove(position);
	                }
			}

        });
		return view;
	}
	

	private static class Holder {
		ImageView iamge = null;
	    TextView title = null;
	    TextView text = null;
	    //Button button = null;
	    CheckBox checked = null;

		public Holder(View view) {
			iamge = (ImageView) view.findViewById(R.id.img);
    		title = (TextView) view.findViewById(R.id.name);
    		text = (TextView) view.findViewById(R.id.desc);
    		//button = (Button)view.findViewById(R.id.array_button);
    		checked = (CheckBox)view.findViewById(R.id.select);
		}
	}
}

