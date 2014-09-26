package com.newfeature.msg.database;

import android.net.Uri;

public class SparksContract {
	
	public static final String AUTHORITY = "com.newfeature.msg";
	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	public static final Uri INBOX_CONTENT_URI = Uri.parse("content://sms/inbox");
}
