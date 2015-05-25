package com.bjtu.foodie.map;

import java.util.List;
import java.util.zip.Inflater;

import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.bjtu.foodie.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MapRoutePlanStepAdapter extends BaseAdapter {
	private List<RouteStep> steps;
	private Context context;

	public MapRoutePlanStepAdapter(List<RouteStep> steps, Context context) {
		this.steps = steps;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (!steps.isEmpty())
			return steps.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (!steps.isEmpty() || steps.size() < position)
			return steps.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (!steps.isEmpty() || steps.size() < position)
			return position;
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		Holder holder;
		String content = null;
		int imageType = 0; //walk=3; bus=2; car=1
		if (null == view) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_routeplan_node, null);
			holder = new Holder(view);
			view.setTag(holder);
		} else {
			holder = (Holder) view.getTag();
		}
		
		RouteStep rstep = steps.get(position);
		if (rstep instanceof DrivingRouteLine.DrivingStep) {
			content = ((DrivingRouteLine.DrivingStep) rstep).getInstructions();
//			((DrivingRouteLine.DrivingStep) rstep).getDistance();
			imageType =  1;	
		}
		if (rstep instanceof TransitRouteLine.TransitStep) {
			content = ((TransitRouteLine.TransitStep) rstep).getInstructions();
			switch (((TransitRouteLine.TransitStep) rstep).getStepType()) {
			case BUSLINE:
				imageType = 2;
				break;
			case SUBWAY:
				imageType = 2;
				break;
			case WAKLING:
				imageType = 3;
				break;
			default:
				break;
			}
		}
		if (rstep instanceof WalkingRouteLine.WalkingStep) {
			content = ((WalkingRouteLine.WalkingStep) rstep).getInstructions();
			imageType = 3;
		}
		holder.tv_content.setText(content);
		switch(imageType) {
		case 1:
			holder.iv_icon.setImageResource(R.drawable.icon_rp_car);
			break;
		case 2:
			holder.iv_icon.setImageResource(R.drawable.icon_rp_bus);
			break;
		case 3:
			holder.iv_icon.setImageResource(R.drawable.icon_walk);
			break;
		default:
			break;
		}
		return view;
	}

	private static class Holder {
		ImageView iv_icon;
		TextView tv_content;

		public Holder(View v) {
			iv_icon = (ImageView) v.findViewById(R.id.iv_transmeans);
			tv_content = (TextView) v.findViewById(R.id.tv_nodeInfo);
		}
	}
}
