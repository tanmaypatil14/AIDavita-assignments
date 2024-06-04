package com.citiustech.processor;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateDecodedUsernamePasswordProcessor implements Processor {

	private static Logger logger = LoggerFactory.getLogger(ValidateDecodedUsernamePasswordProcessor.class);

	private Map<String, String> credentials;

	public ValidateDecodedUsernamePasswordProcessor() {
		credentials = new HashMap<>();
		credentials.put("admin", "admin");
		credentials.put("user", "user");
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		String authHeader = exchange.getIn().getHeader("Authorization", String.class);

//		Removing the prefix "Basic " from the Base64 string
		String encodedString = authHeader.replace("Basic ", "");
		String decodedUsernamePassword = new String(Base64.getDecoder().decode(encodedString));
//      Splitting the string with ":"
		String[] tokens = decodedUsernamePassword.split(":");

		String username = tokens[0];
		String password = tokens[1];

		boolean isUsernameAndPasswordMatches = credentials.entrySet().stream()
				.anyMatch(credential -> credential.getKey().equals(username) && credential.getValue().equals(password));
		if (isUsernameAndPasswordMatches) {
			logger.info("User authenticated Successfully");
			exchange.getIn().setHeader("authorized", isUsernameAndPasswordMatches);
		} else {
			logger.error("User entered bad credentials");
		}

	}

}
