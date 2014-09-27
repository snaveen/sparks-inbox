package com.newfeature.msg.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newfeature.msg.R;
import com.newfeature.msg.database.SparksContract.Promotions;

public class MyAdapter extends CursorAdapter {

	private LayoutInflater inflater;
	
	public MyAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		inflater = ((Activity) context).getLayoutInflater();
	}

	@Override
	public void bindView(View convertView, Context arg1, Cursor cursor) {
		TextView senderTextView = (TextView) convertView.findViewById(R.id.sender);
		TextView bodyTextView = (TextView) convertView.findViewById(R.id.body);
		
		senderTextView.setText(cursor.getString(cursor.getColumnIndex(Promotions.STORE)));
		bodyTextView.setText(cursor.getString(cursor.getColumnIndex(Promotions.COUPON_TITLE)) + "\n" + cursor.getString(cursor.getColumnIndex(Promotions.COUPON_LINK)));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		View view = inflater.inflate(R.layout.message_item, null);
		return view;
	}
	
}