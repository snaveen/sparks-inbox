package com.newfeature.msg.asynctask;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.util.Log;

import com.newfeature.msg.database.SparksContract.NotificationSent;
import com.newfeature.msg.database.SparksContract.NotificationSentColumns;
import com.newfeature.msg.interfaces.FragmentCallback;
import com.newfeature.msg.model.MessageBean;
import com.newfeature.msg.util.PersistHelper;
import com.newfeature.msg.util.RestUtils;

public class SendNotificationAsyncTask extends AsyncTask<String, Void, String> {

	private Context context;
	private MessageBean list;
	private FragmentCallback fragmentCallback;
	private ContentResolver contentResolver;
	
	public SendNotificationAsyncTask(Context context, FragmentCallback fragmentCallback) {
		this.context = context;
		this.fragmentCallback = fragmentCallback;
		contentResolver = context.getContentResolver();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(fragmentCallback != null) {
			fragmentCallback.onPreExecute();
		}
	}
	
	@Override
	protected String doInBackground(String... list) {
		if(list == null || list.length != 2) {
			return "Success";
		} else {
			try {
				String deviceId = list[0];
				String location = list[1];
				
				Cursor cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address not like ?", new String[] {"+%"}, null);
				MessageBean messageBean = MessageBean.fromCursor(cursor, deviceId, location);
				Cursor found = contentResolver.query(NotificationSent.CONTENT_URI, null, null, null, null);
				Log.e(getClass().toString(), "Found: " + found.getCount());
				found.moveToFirst();
				while(!found.isAfterLast()) {
					int messageId = found.getInt(found.getColumnIndex(NotificationSentColumns.MESSAGE_ID));
					messageBean.removeMessage(messageId);
					found.moveToNext();
				}
				
				this.list = messageBean;
				
				return RestUtils.getInstance().POST("http://10.134.124.73:4567/saveMessage", messageBean);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		Log.e(getClass().toString(), "Result: " + result + " : " + list);
		if(result == null) {
			if(fragmentCallback != null) {
				fragmentCallback.onError();
			}
		} else if (result.equalsIgnoreCase("success") && list != null) {
			try {
				PersistHelper.persist(list.getMessageList(), context.getContentResolver());
			} catch (RemoteException e) {
			} catch (OperationApplicationException e) {
			}
		}
		if(fragmentCallback != null) {
			fragmentCallback.onPostExecute(result);
		}
		super.onPostExecute(result);
	}

}
