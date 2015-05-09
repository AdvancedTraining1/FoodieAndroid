package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullDishItemsAdapter;
import com.bjtu.foodie.data.TestDishes;
import com.bjtu.foodie.model.DishItem;


public class CouponActivity extends Activity implements OnClickListener{
    
	private ListView m_listview;
    private ArrayList<String> m_CouponData;
    private FullDishItemsAdapter m_adapter;
    //private FullDishItemsAdapter2 m_adapter2;
    //private Button m_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        
        m_CouponData=getIntent().getStringArrayListExtra("list");
        //m_submit = (Button)findViewById(R.id.button0);
//        for (int i = 0; i < m_CouponData.size()-1; i++) {
//	    	
//	    	if (m_CouponData.get(i).length()>=1) {
//	    		Toast.makeText(CouponActivity.this, m_CouponData.get(i) ,Toast.LENGTH_SHORT).show();
//			}
//		}
        
        m_listview = (ListView)findViewById(R.id.listview0);
        
        m_adapter = new FullDishItemsAdapter(this,m_CouponData);	
        m_listview.setAdapter(m_adapter);
        m_adapter.setAdapter(m_adapter);
        //m_adapter.no
        
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
    
    
}
