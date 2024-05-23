package com.citiustech.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLFileWriterProcessor implements Processor {

	private static Logger logger = LoggerFactory.getLogger(XMLFileWriterProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		String directoryPath = "data/out/withoutfilecomponent";
		String filePath = "data/out/withoutfilecomponent/xml-producer.xml";

		File directory = new File(directoryPath);
		File file = new File(filePath);

		if (!directory.exists()) {
			directory.mkdirs();
			logger.info("Directory created successfully.");
		} else {
			logger.info("Directory already exists.");
		}

		try (FileWriter fileWriter = new FileWriter(file)) {

			fileWriter.write(exchange.getIn().getBody(String.class));

			fileWriter.flush();

		} catch (IOException e) {

			new Exception(e);

		}

	}

}
