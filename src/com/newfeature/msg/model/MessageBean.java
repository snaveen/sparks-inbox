package com.newfeature.msg.model;

import java.util.List;

import android.database.Cursor;

public class MessageBean {
	private String deviceId;
	private String location;
	private List<Message> messages;
	
	public MessageBean() {
	}
	
	public MessageBean(String deviceId, String location, List<Message> messages) {
		this.deviceId = deviceId;
		this.location = location;
		this.messages = messages;
	}

	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public List<Message> getMessageList() {
		return messages;
	}
	public void setMessageList(List<Message> messages) {
		this.messages = messages;
	}

	public static MessageBean fromCursor(Cursor cursor, String deviceId, String location) {
		MessageBean messageBean = new MessageBean();
		messageBean.setDeviceId(deviceId);
		messageBean.setLocation(location);
		messageBean.setMessageList(Message.fromCursor(cursor));
		
		return messageBean;
	}

	public void removeMessage(int messageId) {
		for(Message m : messages) {
			if(m.find(messageId)) {
				messages.remove(m);
				break;
			}
		}
	}
}
