package com.citiustech.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.citiustech.processor.JsonFileReaderProcessor;
import com.citiustech.processor.XMLFileWriterProcessor;

public class WithoutFileComponentRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
//		from("timer:data/in?period=10000").routeId("NotUsingFileComponent")
		from("timer:data/in?repeatCount=1").routeId("NotUsingFileComponent")
		   .process(new JsonFileReaderProcessor())
		   .log(LoggingLevel.INFO, "Source file name = ${header.fileName} : ${body}")
		   .unmarshal()
		   .json(JsonLibrary.Jackson)
		   .log(LoggingLevel.INFO, "unmarhsaled data to json : ${body}")
		   .log(LoggingLevel.INFO, "Number of records present in JSON: ${body.size()}")
		   .marshal().jacksonxml(true)
		   .convertBodyTo(String.class)
		   .transform(simple("${body.replace('ArrayList', 'PatientList')}"))
		   .transform(simple("${body.replace('item', 'Patient')}"))
		   .log(LoggingLevel.INFO, "Xml Content :\n ${body}")
		   .process(new XMLFileWriterProcessor());

	}
}