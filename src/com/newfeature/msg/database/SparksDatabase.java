package com.newfeature.msg.database;

import com.newfeature.msg.database.SparksContract.NotificationSentColumns;
import com.newfeature.msg.database.SparksContract.PromotionsColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class SparksDatabase extends SQLiteOpenHelper {

	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "sparks_db";

	public SparksDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public SparksDatabase(Context context) {
		this(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL("CREATE TABLE " + SparksContract.Tables.notificationSent 
				+ "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ NotificationSentColumns.MESSAGE_ID + " INTEGER, "
				+ "UNIQUE (" + NotificationSentColumns.MESSAGE_ID + ") ON CONFLICT REPLACE)");
		
		database.execSQL("CREATE TABLE " + SparksContract.Tables.promotions 
				+ "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ PromotionsColumns.COUPON_TITLE + " TEXT NOT NULL, "
				+ PromotionsColumns.COUPON_LINK + " TEXT, "
				+ PromotionsColumns.STORE + " TEXT, "
				+ PromotionsColumns.crawl_time + " INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
