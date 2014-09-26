package com.newfeature.msg.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newfeature.msg.R;

public class SmsListAdapter extends CursorAdapter {

	public SmsListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView senderTextView = (TextView) view.findViewById(R.id.sender);
		TextView bodyTextView = (TextView) view.findViewById(R.id.body);
		
		senderTextView.setText(cursor.getString(cursor.getColumnIndex("address")));
		bodyTextView.setText(cursor.getString(cursor.getColumnIndex("body")));
		
		Log.e(getClass().toString(), "Thread id: " + cursor.getString(cursor.getColumnIndex("person")));
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = ((Activity) context).getLayoutInflater().inflate(R.layout.message_item,
				parent, false);
		return view;
	}

}
