package com.newfeature.msg.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import com.newfeature.msg.database.SparksContract;
import com.newfeature.msg.database.SparksContract.NotificationSent;
import com.newfeature.msg.database.SparksContract.Promotions;
import com.newfeature.msg.model.Message;
import com.newfeature.msg.model.PendingMsgModel;

public class PersistHelper {

	public static void persist(List<Message> messageList, ContentResolver resolver) throws RemoteException, OperationApplicationException {
		Log.e("sdfsdf", "Size: " + messageList.size());
		if(messageList == null || messageList.size() == 0) {
			return;
		}
		Uri uri = NotificationSent.CONTENT_URI;

		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		for(int i = 0; i < messageList.size(); i++) {
			ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
			batch.add(messageList.get(i).toContentProviderOperation(builder).build());
		}

		ContentProviderResult[] applyBatch = resolver.applyBatch(SparksContract.AUTHORITY, batch);
		for(ContentProviderResult a : applyBatch) {
			Log.e("dsfdsfs", "  dsfsdfsdf " + a.count);
		}
	}

	public static void persistPromotions(List<PendingMsgModel> result,
			ContentResolver resolver) throws RemoteException, OperationApplicationException {
		if(result == null || result.size() == 0) {
			return;
		}
		
		Uri uri = Promotions.CONTENT_URI;
		ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
		for(int i = 0; i < result.size(); i++) {
			ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(uri);
			batch.add(result.get(i).toContentProviderOperation(builder).build());
		}

		resolver.applyBatch(SparksContract.AUTHORITY, batch);
		
		resolver.notifyChange(uri, null);
		resolver.notifyChange(Promotions.CONTENT_URI, null);
		resolver.notifyChange(NotificationSent.CONTENT_URI, null);
	}
}
