package com.newfeature.msg.provider;

import com.newfeature.msg.database.SparksContract;
import com.newfeature.msg.database.SparksContract.NotificationSent;
import com.newfeature.msg.database.SparksContract.NotificationSentColumns;
import com.newfeature.msg.database.SparksContract.Tables;
import com.newfeature.msg.database.SparksDatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class SparksProvider extends ContentProvider {

	private UriMatcher uriMatch = buildUriMatcher();
	private SparksDatabase database;

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	private UriMatcher buildUriMatcher() {
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(SparksContract.AUTHORITY, Tables.notificationSent, 100);
		uriMatcher.addURI(SparksContract.AUTHORITY, Tables.promotions, 101);
		return uriMatcher;
	}

	@Override
	public String getType(Uri uri) {
		int match = uriMatch.match(uri);
		switch(match) {
		case 100:
			return NotificationSent.CONTENT_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		SQLiteDatabase writableDatabase = database.getWritableDatabase();
		int match = uriMatch.match(uri);

		switch(match) {
		case 100:
			writableDatabase.insertOrThrow(Tables.notificationSent, null, contentValues);
			return NotificationSent.buildUri(contentValues.getAsString(NotificationSentColumns.MESSAGE_ID));
		case 101:
			writableDatabase.insertOrThrow(Tables.promotions, null, contentValues);
			return NotificationSent.buildUri(contentValues.getAsString(NotificationSentColumns.MESSAGE_ID));
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		database = new SparksDatabase(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String orderBy) {
		int match = uriMatch.match(uri);

		SQLiteDatabase readableDatabase = database.getReadableDatabase();

		Cursor cursor = null;
		switch (match) {
		default:
			String table = getTableName(match);
			cursor = readableDatabase.query(table, projection, selection, selectionArgs, null, null, orderBy);
			break;
		}
		return cursor;
	}

	private String getTableName(int match) {
		switch (match) {
		case 100:
			return Tables.notificationSent;

		case 101:
			return Tables.promotions;
		default:
			break;
		}
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		return 0;
	}
}
