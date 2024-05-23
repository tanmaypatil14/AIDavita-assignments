package com.citiustech.processor;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ValidateDecodedUsernamePasswordProcessor implements Processor {

	private Map<String, String> credentials;

	public ValidateDecodedUsernamePasswordProcessor() {
		credentials = new HashMap<>();
		credentials.put("admin", "admin");
		credentials.put("user", "user");
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		String authHeader = exchange.getIn().getHeader("Authorization", String.class);
		System.out.println(authHeader);

		// Removing the prefix "Basic " from the Base64 string
		String encodedString = authHeader.replace("Basic ", "");
		String decodedUsernamePassword = new String(Base64.getDecoder().decode(encodedString));
		System.out.println(decodedUsernamePassword);
		String[] tokens = decodedUsernamePassword.split(":");

		String username = tokens[0];
		String password = tokens[1];

		boolean isUsernameAndPasswordMatches = credentials.entrySet().stream()
				.anyMatch(credential -> credential.getKey().equals(username) && credential.getValue().equals(password));
		exchange.getIn().setHeader("authorized", isUsernameAndPasswordMatches);
	}

}
