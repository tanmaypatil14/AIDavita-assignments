package com.citiustech.nurse.route;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.citiustech.nurse.processor.NurseProcessor;

public class NurseFileOut extends RouteBuilder {
	
	private String sourceQueue;
	private String activeDestinationURI;
	private String inActiveDestinationURI;
	private String fileName;
	
	public String getSourceQueue() {
		return sourceQueue;
	}
	public void setSourceQueue(String sourceQueue) {
		this.sourceQueue = sourceQueue;
	}
	public String getActiveDestinationURI() {
		return activeDestinationURI;
	}
	public void setActiveDestinationURI(String activeDestinationURI) {
		this.activeDestinationURI = activeDestinationURI;
	}
	public String getInActiveDestinationURI() {
		return inActiveDestinationURI;
	}
	public void setInActiveDestinationURI(String inActiveDestinationURI) {
		this.inActiveDestinationURI = inActiveDestinationURI;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void configure() throws Exception {
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
		
//		it handles the exception while creating a file
		onException(GenericFileOperationFailedException.class)
		.log(LoggingLevel.ERROR, "Generic file exception occured while creating file : ${exception.message}")
		.handled(true);
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
//		from(getSourceQueue())
		from("file:src/main/resources/data/in?noop=true")
				.log(LoggingLevel.INFO, "Received treatmentDetails from topic : ${body}")
				.process(new NurseProcessor())
				.log(LoggingLevel.INFO, "Received nurse detail from processor : ${body}, for patient id : ${header.patientId}")
				.convertBodyTo(String.class)
				.setHeader("CamelFileName", simple(getFileName()))
				.setBody(simple("${body}"))
				.unmarshal().json(JsonLibrary.Jackson)
				.marshal().jacksonxml(true)
				.convertBodyTo(String.class)
				.transform(simple("${body.replace('LinkedHashMap', 'NurseDetail')}"))
				.choice()
				    .when(header("isActive").isEqualTo(true))
				       .log(LoggingLevel.INFO, "Received nurse detail for active patient : \n${body}")
				       .to(getActiveDestinationURI())
				    .otherwise()
			       	   .log(LoggingLevel.INFO, "Received nurse detail for inactive patient : \n${body}")
			       	   .to(getInActiveDestinationURI())
			       	   .end()
			     .log(LoggingLevel.INFO, "Nurse details stored in folder as per the patient status");
	}

}
