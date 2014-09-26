package com.newfeature.msg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.newfeature.msg.R;
import com.newfeature.msg.adapters.SmsListAdapter;

public class PersonalFragment extends Fragment {

	private View view;
	private ListView listView;
	private SmsListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(view == null) {
			view = inflater.inflate(R.layout.activity_main, container);
		} else {
			((ViewGroup) view.getParent()).removeView(view);
		}
		return view;
	}
	
	private void initFragment() {
		listView = (ListView) view.findViewById(R.id.sms_listview);
		adapter = new SmsListAdapter(getActivity(), null, false);
		listView.setAdapter(adapter);
	}
}
