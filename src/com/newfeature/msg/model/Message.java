package com.newfeature.msg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newfeature.msg.database.SparksContract.NotificationSentColumns;

import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.provider.BaseColumns;

public class Message {
	private int messageId;
	private String from;
	private String content;
	private Date timestamp;

	public Message() {
	}
	
	public Message(int messageId, String from, String content, Date timestamp) {
		this.messageId = messageId;
		this.from = from;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = new Date(timestamp);
	}

	public Builder toContentProviderOperation(Builder builder) {
		builder.withValue(NotificationSentColumns.MESSAGE_ID, messageId);
		return builder;
	}

	public static List<Message> fromCursor(Cursor cursor) {
		List<Message> messages = new ArrayList<Message>();
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			messages.add(Message.fromSingleCursor(cursor));
			cursor.moveToNext();
		}
		
		return messages;
	}

	private static Message fromSingleCursor(Cursor cursor) {
		Message message = new Message();
		message.setMessageId(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
		message.setFrom(cursor.getString(cursor.getColumnIndex("address")));
		message.setContent(cursor.getString(cursor.getColumnIndex("body")));
		message.setTimestamp(cursor.getLong(cursor.getColumnIndex("date")));
		return message;
	}

	public boolean find(int messageId) {
		if(this.messageId == messageId) {
			return true;
		}
		return false;
	}
}
