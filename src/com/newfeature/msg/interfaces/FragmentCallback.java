package com.newfeature.msg.interfaces;

public interface FragmentCallback {

	public void onPreExecute();
	public void onPostExecute(Object... objects);
	public void onError(Object... objects);
}
