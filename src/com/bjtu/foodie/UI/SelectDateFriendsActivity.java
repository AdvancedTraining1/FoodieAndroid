package com.bjtu.foodie.UI;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullDateFriendItemsAdapter;
import com.bjtu.foodie.data.TestData;
import com.bjtu.foodie.model.User;

public class SelectDateFriendsActivity extends Activity implements OnClickListener,
CreateNdefMessageCallback,OnNdefPushCompleteCallback{
    
	private ListView m_listview;
    private List<User> m_FriendData;
    private FullDateFriendItemsAdapter m_adapter;
    private Button m_submit;
    private String friendchoose="friend choose";
	//NfcAdapter mNfcAdapter;
    private static final int MESSAGE_SENT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datefriend);
        //mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        
        
        m_submit = (Button)findViewById(R.id.button0);
        
        m_listview = (ListView)findViewById(R.id.friends);
        m_FriendData = (new TestData()).getFriendsData();
        //m_adapter = new FullDishItemsAdapter(this,m_DishData);	
        //m_listview.setAdapter(m_adapter);
        
        m_adapter = new FullDateFriendItemsAdapter(this, m_FriendData);
        m_listview.setAdapter(m_adapter);
        
        m_submit.setOnClickListener(this);
    }
    
    @Override
	public void onClick(View v) {
    	switch(v.getId()){
        case R.id.button0:
        	String s="You have choosed ";
            ArrayList<Boolean> checkList = m_adapter.getChecklist();
            ArrayList<String> idList = m_adapter.getIDList();
            for(int i=0;i<checkList.size();i++){
                if(checkList.get(i)){
                    s=s+","+idList.get(i);
                    friendchoose = friendchoose + ",friend"+i;
                }
            }
            s=s+", and please touch to restaurant mobile to submit the dishes. Thanks!";
            
            /*mNfcAdapter.setNdefPushMessageCallback(this, this);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            // Register callback to listen for message-sent success
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
            //Intent intent1=new Intent(this,ShowList1.class);
            //send music names
            //intent1.putStringArrayListExtra("list", getData());
            //startActivity(intent1);
*/            break;
    }
        
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

	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		
		
		// TODO Auto-generated method stub
		   NdefMessage msg = new NdefMessage(
	                new NdefRecord[] { createMimeRecord(
	                        "text/friendchoose", friendchoose.getBytes())
	                });
		   friendchoose = "friend choose";
		return msg;
	}
	                
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
	                
	                
//        @Override
//        public void onResume() {
//            super.onResume();
//            // Check to see that the Activity started due to an Android Beam
//            System.out.println("onResume.......");
//            System.out.println("onResume  get Action......."+getIntent().getAction());
//            if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
//                processIntent(getIntent());
//            }
//        }

//        @Override
//        public void onNewIntent(Intent intent) {
//            // onResume gets called after this to handle the intent
//            setIntent(intent);
//        }
        
//        void processIntent(Intent intent) {
//       	 System.out.println("processIntent.......");
//           Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
//                   NfcAdapter.EXTRA_NDEF_MESSAGES);
//           // only one message sent during the beam
//           NdefMessage msg = (NdefMessage) rawMsgs[0];
//           System.out.println("transfor information:"+new String(msg.getRecords()[0].getPayload()));
//           // record 0 contains the MIME type, record 1 is the AAR, if present
//         //  mInfoText.setText(new String(msg.getRecords()[0].getPayload()));
//       }


    
}

