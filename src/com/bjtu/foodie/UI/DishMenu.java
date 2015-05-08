package com.bjtu.foodie.UI;

import java.util.ArrayList;
import com.bjtu.foodie.adapter.FullDishItemsAdapter;
import com.bjtu.foodie.adapter.FullDishItemsAdapter2;
import com.bjtu.foodie.data.TestDishes;

import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DishItem;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DishMenu extends Activity implements OnClickListener{
    
	private ListView m_listview;
    private List<DishItem> m_DishData;
    private FullDishItemsAdapter m_adapter;
    private FullDishItemsAdapter2 m_adapter2;
    private Button m_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishmenu);
        
        m_submit = (Button)findViewById(R.id.button0);
        
        m_listview = (ListView)findViewById(R.id.listview0);
        m_DishData = (new TestDishes()).getData();
        //m_adapter = new FullDishItemsAdapter(this,m_DishData);	
        //m_listview.setAdapter(m_adapter);
        
        m_adapter2 = new FullDishItemsAdapter2(this, m_DishData);
        m_listview.setAdapter(m_adapter2);
        
        m_submit.setOnClickListener(this);
    }
    
    public void onClick(View v) {
    	switch(v.getId()){
        case R.id.button0:
        	String s="You have choosed ";
            ArrayList<Boolean> checkList = m_adapter2.getChecklist();
            for(int i=0;i<checkList.size();i++){
                if(checkList.get(i)){
                    s=s+","+i;
                }
            }
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            //Intent intent1=new Intent(this,ShowList1.class);
            //send music names
            //intent1.putStringArrayListExtra("list", getData());
            //startActivity(intent1);
            break;
    }
        
    }
    
}
