package com.bjtu.foodie.UI;

import java.io.IOException;  
import java.io.StringReader;  
  
import android.util.JsonReader;
import android.util.Log;  
  

  
public class JsonUtils {  
    private static final String TAG = "JsonUtils";  
      
    public void parseJson(String jsonData){  
        JsonReader reader = new JsonReader(new StringReader(jsonData));  
        try {  
            reader.beginArray();    // 开始解析数组  
            while (reader.hasNext()) {  
                reader.beginObject();   // 开始解析对象  
                while (reader.hasNext()) {  
                    String tagName = reader.nextName(); // 得到键值对中的key  
                    if (tagName.equals("name")) {   // key为name时  
                        Log.i(TAG, "name--------->" + reader.nextString());  // 得到key中的内容  
                    }else if (tagName.equals("age")) {  // key为age时  
                        Log.i(TAG, "age--------->" + reader.nextInt());  // 得到key中的内容  
                    }  
                }  
                reader.endObject();  
            }  
            reader.endArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  