package com.newfeature.msg.util;

public enum SmsUtils {
	INSTANCE;
	
//	public static final String inboxQuery = "SELECT MESSAGES.*, C.MSG_COUNT (SELECT THREAD_ID, COUNT(*) AS MSG_COUNT, MAX(DATE) AS MSG_MAX FROM " + + " GROUP BY THREAD_ID) AS C, " ++ " WHERE C.THREAD_ID = MESSAGES.THREAD_ID AND MESSAGES.DATE = C.DATE";
}
