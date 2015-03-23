package com.paleBlueDot.feedMe.processes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.ws.rs.core.MediaType;

import com.paleBlueDot.feedMe.Helper.GateRequestGenerator;
import com.paleBlueDot.feedMe.Helper.PostRequestGenerator;
import com.paleBlueDot.feedMe.VO.AuthenticationVO;

public class AuthorizationProcess {

	private final String webServiceURI = "http://sandbox.feedly.com/v3/auth";
	private AuthenticationVO authRequest = new AuthenticationVO();

	public AuthorizationProcess() {
	}

	// Obtain code for user
	public String getCode() {
		String target = webServiceURI
				+ "/auth?response_type=code&client_id=sandbox&redirect_uri=http://localhost:8080&scope=https://cloud.feedly.com/subscriptions";
		GateRequestGenerator request = new GateRequestGenerator(target,
				MediaType.TEXT_HTML);
		String toBeRendred = request.generateRequest();
		return toBeRendred;
	}

	// exchange code for refresh and access token
	public void getTokens(String code) {
		authRequest.setCode(code);
		authRequest.setGrant_type("authorization_code");
		authRequest.setRedirect_uri("http://localhost:8080");
		authRequest.setState("");

		String urlParams = getTokenUrlparams(authRequest);
		PostRequestGenerator postRequest = new PostRequestGenerator(
				webServiceURI + "/token", urlParams);

		postRequest.establishConnection();
		postRequest.connectAndSendData();
		String response = postRequest.getResponse();
		postRequest.closeConnection();
		System.out.println(response);
	}

	// using refresh token
	public void refreshToken(String refreshToken) {
		authRequest.setGrant_type("refresh_token");
		String urlParams = getRefreshTokenUrlparams(refreshToken);
		PostRequestGenerator postRequest = new PostRequestGenerator(
				webServiceURI + "/token", urlParams);
		postRequest.establishConnection();
		postRequest.connectAndSendData();
		String response = postRequest.getResponse();
		postRequest.closeConnection();
		System.out.println(response);
	}

	// revoke token
	public void revokeToken(String refreshToken) {
		authRequest.setGrant_type("revoke_token");
		String urlParams = getRefreshTokenUrlparams(refreshToken);
		PostRequestGenerator postRequest = new PostRequestGenerator(
				webServiceURI + "/token", urlParams);
		postRequest.establishConnection();
		postRequest.connectAndSendData();
		String response = postRequest.getResponse();
		postRequest.closeConnection();
		System.out.println(response);
	}

	// get url parameters for generating tokens
	private String getTokenUrlparams(AuthenticationVO authRequest) {
		try {
			String urlParameters = "code="
					+ URLEncoder.encode(authRequest.getCode(), "UTF-8")
					+ "&client_id="
					+ URLEncoder.encode(authRequest.getClientId(), "UTF-8")
					+ "&client_secret="
					+ URLEncoder.encode(authRequest.getClientSecret(), "UTF-8")
					+ "&redirect_uri="
					+ URLEncoder.encode(authRequest.getRedirect_uri(), "UTF-8")
					+ "&state="
					+ URLEncoder.encode(authRequest.getState(), "UTF-8")
					+ "&grant_type="
					+ URLEncoder.encode(authRequest.getGrant_type(), "UTF-8");

			return urlParameters;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getRefreshTokenUrlparams(String refToken) {

		String urlParameters = "";
		try {
			urlParameters = "refresh_token="
					+ URLEncoder.encode(refToken, "UTF-8") + "&client_id="
					+ URLEncoder.encode(authRequest.getClientId(), "UTF-8")
					+ "&client_secret="
					+ URLEncoder.encode(authRequest.getClientSecret(), "UTF-8")
					+ "&grant_type="
					+ URLEncoder.encode(authRequest.getGrant_type(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return urlParameters;
	}

}
