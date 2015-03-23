package com.paleBlueDot.feedMe.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GateRequestGenerator2 {
	private String url = "";
	private URL urlObj;
	private HttpURLConnection con;

	public GateRequestGenerator2(String url) {
		this.url = url;
		try {
			urlObj = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void openConnection() {
		try {
			con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		con.disconnect();
	}

	public String getResponse() {
		try {

			BufferedReader in = new BufferedReader(
					new java.io.InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());

			return response.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void addHeader(HashMap<String, String> datamap) {
		for (Map.Entry<String, String> entry : datamap.entrySet()) {
			con.setRequestProperty(entry.getKey(), entry.getValue());
		}
	}

}
