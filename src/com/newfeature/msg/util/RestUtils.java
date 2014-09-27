package com.newfeature.msg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class RestUtils {

	private static RestUtils restUtils = new RestUtils();

	public static final RestUtils getInstance() {
		if (restUtils == null) {
			restUtils = new RestUtils();
		}
		return restUtils;
	}

	private RestUtils() {

	}

	public String POST(String url, Object objectToPOST)
			throws ClientProtocolException, IOException {
		Log.i("RestUtils", "POST-URL ----------------- " + url);
		Log.i("RestUtils",
				"POST-OBJECT ----------------- "
						+ JSONUtils.getJSONString(objectToPOST));
		HttpClient client = new DefaultHttpClient();

		// HttpClient client = HttpClientBuilder.create().build();

		HttpPost postRequest = new HttpPost(url);
		StringEntity input = null;
		if (objectToPOST instanceof String) {
			input = new StringEntity((String) objectToPOST);
		} else {
			input = new StringEntity(JSONUtils.getJSONString(objectToPOST));
		}

		input.setContentType("application/json");
		postRequest.setEntity(input);

		HttpResponse response = client.execute(postRequest);

		InputStream inputStream = getStreamFromResponse(response);

		String jsonString = getStringFromStream(inputStream);
		Log.i("RestUtils",
				"POST-RESPONSE ----------------- "
						+ JSONUtils.getJSONString(jsonString));

		return jsonString;
	}

	public String GET(String url) throws ClientProtocolException, IOException {
		Log.i("RestUtils", "GET-URL ----------------- " + url);
		HttpClient client = new DefaultHttpClient();

		// HttpClient client = HttpClientBuilder.create().build();

		HttpGet getRequest = new HttpGet(url);

		HttpResponse response = client.execute(getRequest);

		InputStream inputStream = getStreamFromResponse(response);

		String jsonString = getStringFromStream(inputStream);
		Log.i("RestUtils",
				"GET-RESPONSE ----------------- "
						+ JSONUtils.getJSONString(jsonString));

		return jsonString;
	}

	private InputStream getStreamFromResponse(HttpResponse response)
			throws IllegalStateException, IOException {
		InputStream inputStream = null;

		if (response != null && response.getStatusLine() != null) {
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {

				StatusLine statusLine = response.getStatusLine();
				if (statusLine != null) {
					if (statusLine.getStatusCode() == 200) {
						if (httpEntity != null) {
							inputStream = httpEntity.getContent();
						}
					} else {
						throw new IOException(
								"Client has returned the Status Code "
										+ statusLine.getStatusCode());
					}
				}
			}

		}

		return inputStream;
	}

	private String getStringFromStream(InputStream inputStream) {
		StringBuffer result = new StringBuffer();
		if (inputStream != null) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e) {
				Log.e("RestUtils", "IOException while updating job status ", e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e("RestUtils",
							"IOException while updating job status ", e);
				}
			}
		}

		return result.toString();
	}

}
