package com.paleBlueDot.feedMe.Beans;

public class AuthReqDataBean {

	private String response_type;
	private static final String client_id = "sandbox";
	private static final String client_secret = "8LDQOW8KPYFPCQV2UL6J";
	private String redirect_uri;
	private String scope;
	private String state;
	private String code;
	private String grant_type;

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public static String getClientId() {
		return client_id;
	}

	public static String getClientSecret() {
		return client_secret;
	}

	public AuthReqDataBean() {
		// TODO Auto-generated constructor stub
	}

}
