package com.newfeature.msg.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class SparksContract {
	
	public static final String AUTHORITY = "com.newfeature.msg";
	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	public static final Uri INBOX_CONTENT_URI = Uri.parse("content://sms/inbox");
	
	public interface NotificationSentColumns {
		public final static String MESSAGE_ID = "message_id";
	}
	
	public static class NotificationSent implements BaseColumns, NotificationSentColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, Tables.notificationSent);
		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/vnd.zdocs.document";	// No I18N

		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd.zdocs.document";	// No I18N

		public static Uri buildUri(String messageID) 
		{
			return CONTENT_URI.buildUpon().appendPath(messageID).build();
		}
	}
	
	public interface PromotionsColumns {
		public final static String COUPON_TITLE = "coupon_title";
		public final static String COUPON_LINK = "coupon_link";
		public final static String STORE = "store";
		public final static String crawl_time = "crawl_time";
	}
	
	public static class Promotions implements BaseColumns, PromotionsColumns {
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, Tables.promotions);
		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/vnd.zdocs.document";	// No I18N

		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd.zdocs.document";	// No I18N

		public static Uri buildUri(String title) 
		{
			return CONTENT_URI.buildUpon().appendPath(title).build();
		}
	}
	
	public interface Tables {
		public final static String notificationSent = "notifications_sent";
		public final static String promotions = "promotions";
	}
}
