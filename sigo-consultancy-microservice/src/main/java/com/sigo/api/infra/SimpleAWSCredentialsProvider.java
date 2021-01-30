package com.sigo.api.infra;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;

public class SimpleAWSCredentialsProvider implements AWSCredentialsProvider {

	private final AWSCredentials credentials;

	public SimpleAWSCredentialsProvider(AWSCredentials credentials) {

		this.credentials = credentials;
	}

	public AWSCredentials getCredentials() {
		return credentials;
	}

	public void refresh() {
		
	}

	


}