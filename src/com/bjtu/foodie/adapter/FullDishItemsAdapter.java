package com.bjtu.foodie.adapter;

import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DishItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FullDishItemsAdapter extends BaseAdapter {

	private List<DishItem> dishes;
	private Context context;

	public FullDishItemsAdapter(Context contex, List<DishItem> dishes) {
		this.dishes = dishes;
		this.context = contex;
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
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.dishlist,null);
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
		holder.button.setOnClickListener(new OnClickCommitBtnListener(position));
		return view;
	}
	
	private class OnClickCommitBtnListener implements OnClickListener {
		private int pos;
		
		public OnClickCommitBtnListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "On click item" + pos, Toast.LENGTH_SHORT).show();
		}
	}

	private static class Holder {
		ImageView iamge = null;
	    TextView title = null;
	    TextView text = null;
	    Button button = null;

		public Holder(View view) {
			iamge = (ImageView) view.findViewById(R.id.array_image);
    		title = (TextView) view.findViewById(R.id.array_title);
    		text = (TextView) view.findViewById(R.id.array_text);
    		button = (Button)view.findViewById(R.id.array_button);
		}
	}
}

