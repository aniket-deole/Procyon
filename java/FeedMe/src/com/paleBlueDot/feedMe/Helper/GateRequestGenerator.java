package com.paleBlueDot.feedMe.Helper;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class GateRequestGenerator {

	private String targetURL;
	private String mType;
	private Client webClient;
	private ClientConfig clientConfig;

	public GateRequestGenerator(String target, String type) {
		this.targetURL = target;
		this.mType = type;
	}

	private void createClient() {
		clientConfig = new ClientConfig();
		webClient = ClientBuilder.newClient(clientConfig);
	}

	private void closeClient() {
		webClient.close();
	}

	public String generateRequest() {

		createClient();
		URI serviceURI = UriBuilder.fromUri(targetURL).build();
		WebTarget webTarget = webClient.target(serviceURI);
		String data = webTarget.request().accept(mType).get(String.class);
		closeClient();
		System.out.println(data);
		return data;
	}

}
