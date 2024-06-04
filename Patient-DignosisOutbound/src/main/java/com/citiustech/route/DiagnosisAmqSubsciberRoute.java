package com.citiustech.route;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import com.citiustech.processor.UpdateDiagnosisProcessor;

public class DiagnosisAmqSubsciberRoute extends RouteBuilder {
	
	private String sourceQueue;
	private String destinationQueue;
	
	public String getSourceQueue() {
		return sourceQueue;
	}
	
	public void setSourceQueue(String sourceQueue) {
		this.sourceQueue = sourceQueue;
	}
	
	public String getDestinationQueue() {
		return destinationQueue;
	}
	
	public void setDestinationQueue(String destinationQueue) {
		this.destinationQueue = destinationQueue;
	}

	@Override
	public void configure() throws Exception {
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
		from(getSourceQueue())
//		from("file:data/in?noop=true")
				.log(LoggingLevel.INFO, "Received treatmentDetails from topic : ${body}")
				.process(new UpdateDiagnosisProcessor())
				.log(LoggingLevel.INFO, "Received updated diagnosis detail from processor : ${body}")
				.convertBodyTo(String.class)
				.to(getDestinationQueue())
				.log(LoggingLevel.INFO, "Deliverd to Diagnosis Amq subscriber : ${body}");

	}

}
