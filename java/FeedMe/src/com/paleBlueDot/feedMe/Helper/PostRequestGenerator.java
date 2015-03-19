package com.paleBlueDot.feedMe.Helper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequestGenerator {

	private URL url;
	private HttpURLConnection rc;
	String urlParams;

	public PostRequestGenerator(String targeturl, String urlParameters) {
		try {
			this.url = new URL(targeturl);
			this.urlParams = urlParameters;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void establishConnection() {
		try {
			rc = (HttpURLConnection) url.openConnection();
			rc.setRequestMethod("POST");
			rc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			rc.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParams.getBytes().length));
			rc.setRequestProperty("Content-Language", "en-US");
			rc.setUseCaches(false);
			rc.setDoOutput(true);
			rc.setDoInput(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connectAndSendData() {
		try {
			rc.connect();
			DataOutputStream wr = new DataOutputStream(rc.getOutputStream());
			wr.writeBytes(urlParams);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getResponse() {

		try {
			InputStream is = rc.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			is.close();
			rd.close();
			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public void closeConnection() {
		rc.disconnect();
	}
}
