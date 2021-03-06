/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bjtu.foodie.UI;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bjtu.foodie.R;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.FriendTalkToServer;

/**
 * Generic UI for sample discovery.
 */
public class CardEmulationFragment extends Fragment {

    public static final String TAG = "CardEmulationFragment";
    FriendTalkToServer connect = new FriendTalkToServer();
    public UserDao userDao = null;
    public void setDao(UserDao userDao)
    {
    	this.userDao = userDao;
    }
    /** Called when sample is created. Displays generic UI with welcome text. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        EditText account = (EditText) v.findViewById(R.id.card_account_field);
        //account.setText(MessageStorage.GetAccount(getActivity()));
        //account.addTextChangedListener(new AccountUpdater());
        account.setVisibility(View.INVISIBLE);
        User user = userDao.find();
		String tokenString = user.getToken();

		
		String myId = null;		
		String urlString="/service/friend/myId?token="+tokenString;
		try {
			String result = connect.testURLConn1(urlString);
			JSONObject jsonObject = new JSONObject(result);
			myId = jsonObject.getString("id");
			System.out.println("my id ----" +myId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
        MessageStorage.SetAccount(getActivity(), myId);
        return v;
    }


    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
            MessageStorage.SetAccount(getActivity(), account);
            //Toast.makeText(getActivity(), String.format("%d", MessageStorage.GetInstance().GetMsg()), Toast.LENGTH_SHORT);
        }
    }
}
