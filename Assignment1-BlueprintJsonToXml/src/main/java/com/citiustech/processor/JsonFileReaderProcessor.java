package com.citiustech.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JsonFileReaderProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		File file = new File("data/in/TestJSON.json");

		String fileName = file.getName();

		if (!file.exists()) {
//			return "File Not Exist in the path";
			exchange.getIn().setBody("File Not Exist in the path");
		}
		StringBuilder jsonContent = new StringBuilder();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonContent.append(line);
			}
		} catch (IOException e) {
			new Exception(e);
		}
		exchange.getIn().setHeader("fileName", fileName);
		exchange.getIn().setBody(jsonContent.toString());

	}

}
