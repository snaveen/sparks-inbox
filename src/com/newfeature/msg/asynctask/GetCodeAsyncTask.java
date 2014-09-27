package com.newfeature.msg.asynctask;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.OperationApplicationException;
import android.os.AsyncTask;
import android.os.RemoteException;

import com.newfeature.msg.interfaces.FragmentCallback;
import com.newfeature.msg.model.PendingMsgModel;
import com.newfeature.msg.util.PersistHelper;
import com.newfeature.msg.util.RestUtils;

public class GetCodeAsyncTask extends AsyncTask<String, Void, List<PendingMsgModel>> {

	private Context context;
	private FragmentCallback fragmentCallback;
	
	public GetCodeAsyncTask(Context context, FragmentCallback fragmentCallback) {
		this.context = context;
		this.fragmentCallback = fragmentCallback;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(fragmentCallback != null) {
			fragmentCallback.onPreExecute();
		}
	}
	
	@Override
	protected List<PendingMsgModel> doInBackground(String... params) {
		String deviceId = params[0];
		
		try {
			String get = RestUtils.getInstance().GET("http://10.134.124.73:4567/fetchDeals?deviceId=" + deviceId);
			JSONArray resultArray = new JSONArray(get);

			return PendingMsgModel.getList(resultArray);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		} catch (JSONException e) {
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(List<PendingMsgModel> result) {
		if(result == null) {
			if(fragmentCallback != null) {
				fragmentCallback.onError();
			}	
		} else {
			if(fragmentCallback != null) {
				fragmentCallback.onPostExecute(result);
			}
			try {
				PersistHelper.persistPromotions(result, context.getContentResolver());
			} catch (RemoteException e) {
			} catch (OperationApplicationException e) {
			}
		}
		super.onPostExecute(result);
	}

}
