package com.bjtu.foodie.UI;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullDishItemsAdapter;
import com.bjtu.foodie.adapter.FullDishItemsAdapter2;
import com.bjtu.foodie.data.TestDishes;
import com.bjtu.foodie.model.DishItem;

public class DishMenu extends Activity implements OnClickListener,
CreateNdefMessageCallback,OnNdefPushCompleteCallback{
    
	
	ConnectToServer connect=new ConnectToServer();
	private ListView m_listview;
    private List<DishItem> m_DishData;
    private List<DishItem> dishData;
    private FullDishItemsAdapter m_adapter;
    private FullDishItemsAdapter2 m_adapter2;
    private Button m_submit;
    private String dishchoose="dish choose";
	NfcAdapter mNfcAdapter;
 	String dishMenu="";
    private static final int MESSAGE_SENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishmenu);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
        
        m_submit = (Button)findViewById(R.id.button0);
        
        m_listview = (ListView)findViewById(R.id.listview0);
        m_DishData = (new TestDishes()).getData();
        dishData=getDish();
        //m_adapter = new FullDishItemsAdapter(this,m_DishData);	
        //m_listview.setAdapter(m_adapter);
        
        m_adapter2 = new FullDishItemsAdapter2(this, m_DishData);
        m_listview.setAdapter(m_adapter2);
        m_listview.setOnItemClickListener(new OnItemClickListener(){   
            @Override   
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   
                    long arg3) {   
                //���ѡ�����HashMap����   
                HashMap<String,String> map=(HashMap<String,String>)m_listview.getItemAtPosition(arg2);   
                String title=map.get("name");   
                String content=map.get("desc");   
                Toast.makeText(getApplicationContext(),    
                		"你选择了第"+arg2+"个Item，itemTitle的值是："+title+"itemContent的值是:"+content,    
                        Toast.LENGTH_SHORT).show();   
            }   
               
        });
        
        m_submit.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {	
    	HashMap<Integer, Boolean> state =m_adapter2.state;
    	String options="选择的项是:";
   
    	for(int j=0;j<m_adapter2.getCount();j++){
    	System.out.println("state.get("+j+")=="+state.get(j));
    	if(state.get(j)!=null){
    	@SuppressWarnings("unchecked")
    	DishItem dishChecked=(DishItem)m_adapter2.getItem(j);//�õ�ѡ�е�һ����
    	String id=dishChecked.getId().toString();
    	int img =  dishChecked.getPic();
    	String username=dishChecked.getName().toString();
    	String desc=dishChecked.getDesc().toString();
    	int cor = dishChecked.getColor();
    	dishMenu+=id+";"+username+";"+img+";"+desc+";"+cor+";";
    	options+="\n"+username+"."+desc;
    	}
    	}

  //  	Toast.makeText(getApplicationContext(), dishMenu, Toast.LENGTH_LONG).show();
    	String s="You have choosed ";
    	s=s+options;
    	s=s+", and please touch to restaurant mobile to submit the dishes. Thanks!";
    	mNfcAdapter.setNdefPushMessageCallback(this, this);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
           // Register callback to listen for message-sent success
        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);   	
       /*switch(v.getId()){
    	
       case R.id.button0:
        	String s="You have choosed ";
            ArrayList<Boolean> checkList = m_adapter2.getChecklist();
            ArrayList<String> idList = m_adapter2.getIDList();
            for(int i=0;i<checkList.size();i++){
                if(checkList.get(i)){
                    s=s+","+idList.get(i);
                    dishchoose = dishchoose + ",dish"+i;
                }
            }
            s=s+", and please touch to restaurant mobile to submit the dishes. Thanks!";
            
            mNfcAdapter.setNdefPushMessageCallback(this, this);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            //Intent intent1=new Intent(this,ShowList1.class);123,23,412,123
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
            //Intent intent1=new Intent(this,ShowList1.class);
            //send music names
            //intent1.putStringArrayListExtra("list", getData());
            //startActivity(intent1);
            break;
    }*/
    	
    	
    	}
        

	@Override
	public void onNdefPushComplete(NfcEvent event) {
		// TODO Auto-generated method stub
		mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
		
	}
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_SENT:
                Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                break;
            }
        }
    };

	@Override	public NdefMessage createNdefMessage(NfcEvent event) {
		
		dishchoose = dishMenu;
		
		// TODO Auto-generated method stub
		   NdefMessage msg = new NdefMessage(
	                new NdefRecord[] { createMimeRecord(
	                        "text/dishchoose", dishchoose.getBytes())
	                });
		dishchoose = "";
		dishMenu = "";
		return msg;
	}
	                
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
	           
    public List<DishItem> getDish(){
		//List<DateModel> friendDates= new ArrayList<DateModel>();
    	List<DishItem> dish = new ArrayList<DishItem>();
		
		//User user = userDao.find();
		//String tokenString = user.getToken();
		
		String dishResult;		
		String urlString="/service/dish/getalldishs";
		try {
			dishResult = connect.testURLConn1(urlString);
			JSONObject jsonObject = new JSONObject(dishResult);System.out.println("jsonObject=="+jsonObject);
			JSONArray jsonArray = jsonObject.getJSONArray("date");System.out.println("jsonArray=="+jsonArray);
			//userJSON = jsonObject.getJSONObject("user");
			
			for(int i=0;i<jsonArray.length();i++){   
				 JSONObject jo = (JSONObject)jsonArray.opt(i);
		            String id = jo.getString("_id");
		            String dishName = jo.getString("dishName");
		            String desc = jo.getString("desc");
		            int pic=jo.getInt("pic");
		            int col=jo.getInt("col");
		            DishItem dishitem = new DishItem(id, dishName, desc,pic,col);
	            dish.add(dishitem);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	 
		return dish;
		
	}
	                
        @Override
        public void onResume() {
            super.onResume();
            // Check to see that the Activity started due to an Android Beam
            System.out.println("onResume.......");
            System.out.println("onResume  get Action......."+getIntent().getAction());
            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
                processIntent(getIntent());
            }
        }

        @Override
        public void onNewIntent(Intent intent) {
            // onResume gets called after this to handle the intent
            setIntent(intent);
        }
        
        void processIntent(Intent intent) {
       	 System.out.println("processIntent.......");
           Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                   NfcAdapter.EXTRA_NDEF_MESSAGES);
           // only one message sent during the beam
           NdefMessage msg = (NdefMessage) rawMsgs[0];
           System.out.println("transfor information:"+new String(msg.getRecords()[0].getPayload()));
           // record 0 contains the MIME type, record 1 is the AAR, if present
         //  mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
       }


    
}

