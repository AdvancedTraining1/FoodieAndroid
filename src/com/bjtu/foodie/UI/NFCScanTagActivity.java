package com.bjtu.foodie.UI;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import com.bjtu.foodie.R;
import com.bjtu.foodie.data.ReadFileSingleton;
import com.bjtu.foodie.model.DishItem;



import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class NFCScanTagActivity extends Activity {
	private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private TextView mText;
    private int mCount = 0;
    private Tag mytag;
    private Context context;
	private String tag;
	private JSONStringer jsonText;
	private String readResult;
	private  ArrayList<String> msg;
	String filename = "NFC.dat";
    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        
        context = getApplicationContext();
        setContentView(R.layout.activity_nfc_tag);
        mText = (TextView) findViewById(R.id.nfctext);
        mText.setText("Scan Coupon");

        mAdapter = NfcAdapter.getDefaultAdapter(this);

        // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
        // will fill in the intent with the details of the discovered tag before delivering to
        // this activity.
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Setup an intent filter for all MIME based dispatches
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mFilters = new IntentFilter[] {
                ndef,
        };

        // Setup a tech list for all MifareClassic tags
        mTechLists = new String[][] { new String[] { MifareClassic.class.getName() } };
        
        
        jsonText = new JSONStringer();  
        // 首先是{，对象开始。object和endObject必须配对使用  
        try {
			jsonText.object();
			jsonText.key("id");  
	        jsonText.value("1");  
	        jsonText.key("title");  
	        jsonText.value("Discount");
	        jsonText.key("text");  
	        jsonText.value("80% Discount!");  
	          
	        // }，对象结束  
	        jsonText.endObject();  
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
          
        
          
        
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
        WriteModeOn();
    }

    @Override
    public void onNewIntent(Intent intent) {
        //Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        //mText.setText("Discovered tag " + ++mCount + " with intent: " + intent);
        AlertDialog.Builder m_sure = new AlertDialog.Builder(NFCScanTagActivity.this);
        m_sure.setMessage("Congratuations You Get a Coupon!");
        m_sure.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "To another page",Toast.LENGTH_SHORT).show();
				mText.setText("Scan a tag!" );
				
				Intent intent1=new Intent(NFCScanTagActivity.this,CouponActivity.class);
	            //send music names
	            intent1.putStringArrayListExtra("list", (ArrayList<String>) msg);
	            startActivity(intent1);
			}
		});
        
        final AlertDialog dialog = m_sure.create();    
        Window window = dialog.getWindow();    
        WindowManager.LayoutParams lp = window.getAttributes();       
        lp.alpha = 0.8f;  
        lp.y = -180;
        window.setAttributes(lp);
        
		Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);  
		NdefMessage mNdefMsg = (NdefMessage)rawArray[0];  
		NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];  
		try {  
		    if(mNdefRecord != null){  
		        readResult  = new String(mNdefRecord.getPayload(),"UTF-8");  
		        //Toast.makeText(getApplicationContext(), readResult,Toast.LENGTH_SHORT).show();
		        //mText.setText("Discover a coupon! " + readResult );
		        dialog.show();
		     }  
		}  
		catch (UnsupportedEncodingException e) {  
		     e.printStackTrace();  
		};  
		
		//Toast.makeText(getApplicationContext(), readResult,Toast.LENGTH_SHORT).show();
		//WriteFile(jsonText.toString());
		WriteFile(readResult);
		//Toast.makeText(getApplicationContext(), readResult,Toast.LENGTH_SHORT).show();
		//Toast.makeText(getApplicationContext(), jsonText.toString(),Toast.LENGTH_SHORT).show();
		//System.out.println(readResult);
		//System.out.println(jsonText.toString());
		

		
		//WriteFile("you");
		msg = ReadFromFile();  
    	
        //write
//        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) 
//        		|| NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) 
//        		|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) )
//        {
//        	 mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//         	if (mytag != null) {
//         		//Toast.makeText(context, mytag.toString(), Toast.LENGTH_SHORT).show();
//         		Toast.makeText(getApplicationContext(),  mytag.toString(),Toast.LENGTH_SHORT).show();
//         		 try {
//         			//String temp = " Eat food as much as you can!!!";
//					write(jsonText.toString(), mytag);
//					//Toast.makeText(getApplicationContext(),  mytag.toString(),Toast.LENGTH_SHORT).show();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (FormatException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//         	}
//        }

    }
    
    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
		//String lang = "";
		byte[] textBytes = text.getBytes();
		//byte[] langBytes = lang.getBytes("UTF-8");
		//int langLength = langBytes.length;
		//int textLength = textBytes.length;
		//byte[] payload = new byte[1 + langLength + textLength];
	
		// set status byte (see NDEF spec for actual bits)
		//payload[0] = (byte) langLength;
	
		 // copy langbytes and textbytes into payload
		//System.arraycopy(langBytes, 0, payload, 1, langLength);
		//System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);
	//payload
		NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], textBytes);
		return recordNFC;
	}
    
    private void write(String text, Tag tag) throws IOException, FormatException {
		NdefRecord[] records = { createRecord(text) };
		NdefMessage message = new NdefMessage(records);
		// Get an instance of Ndef for the tag.
		Ndef ndef = Ndef.get(tag);
		if (ndef != null) {
			// Enable I/O
			ndef.connect();
			// Write the message
			ndef.writeNdefMessage(message);
			// Close the connection
			ndef.close();
		} else {
			NdefFormatable format = NdefFormatable.get(tag);
			if (format != null) {
				try{
						format.connect();
						format.format(message);
						Log.e(this.tag,"Formatted tag and wrote message");
				} catch (IOException e) {
					Log.e(this.tag,"Failed to format tag.");
				}
			} else {
				Log.e(this.tag,"Tag doesn't support NDEF.");
			}
		}
	}
    
//    private boolean readFromTag(Intent intent){  
//    	Parcelable[] rawArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);  
//        NdefMessage mNdefMsg = (NdefMessage)rawArray[0];  
//        NdefRecord mNdefRecord = mNdefMsg.getRecords()[0];  
//        try {  
//            if(mNdefRecord != null){  
//                String readResult = new String(mNdefRecord.getPayload(),"UTF-8");  
//                return true;  
//             }  
//        }  
//        catch (UnsupportedEncodingException e) {  
//             e.printStackTrace();  
//        };  
//        return false;  
//     }  
    public void WriteFile(String text) {
  	  
  	  //Toast.makeText(context, "To another page",Toast.LENGTH_SHORT).show();
  	  try {  
            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
             *   throws FileNotFoundException; 
             * openFileOutput(String name, int mode); 
             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
             * 第二个参数，代表文件的操作模式 
             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
             *          MODE_WORLD_READABLE 公用  可读 
             *          MODE_WORLD_WRITEABLE 公用 可读写 
             *  */  
            FileOutputStream outputStream = openFileOutput(filename,  
                    Activity.MODE_APPEND);  
            //Toast.makeText(context, "To another page",Toast.LENGTH_SHORT).show();           
            
            outputStream.write(text.getBytes());  
            outputStream.write(new String("\n").getBytes());  
            outputStream.flush();  
            outputStream.close();  
            //Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    public ArrayList<String> ReadFromFile() {
    	ArrayList<String> m_Data = null;
      
      int count = 0;
  	  String content = null;
  	  try {  
  		  
            FileInputStream inputStream = context.openFileInput(filename);  
            byte[] bytes = new byte[1024];  
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
            while (inputStream.read(bytes) != -1) {  
                arrayOutputStream.write(bytes, 0, bytes.length);  
            }  
            inputStream.close();  
            arrayOutputStream.close();  
            content = new String(arrayOutputStream.toByteArray());  
            //String result=content.substring(0,strlastIndexOf("."));
            
            String split = "\n";
            StringTokenizer token = new StringTokenizer(content, split);
            m_Data = new ArrayList<String >();
            while (token.hasMoreTokens()) {
            	m_Data.add(count,token.nextToken());
            	count++;
            	//Toast.makeText(NFCScanTagActivity.this, token.nextToken() ,Toast.LENGTH_SHORT).show();
            }

            
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  	  return m_Data;

    }
	    
    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }
    
    private void WriteModeOn() { 
    	//mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, null);
	}

	private void WriteModeOff() {
		mAdapter.disableForegroundDispatch(this);
	}
}