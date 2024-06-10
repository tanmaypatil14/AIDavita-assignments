package com.citiustech.diagnosis.route;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import com.citiustech.diagnosis.processor.UpdateDiagnosisProcessor;

public class DiagnosisActiveMQOut extends RouteBuilder {
	
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
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
		
//		from(getSourceQueue())
		from("file:src/main/resources/data/in?noop=true").routeId("diagnosisActiveMQOut")
				.log(LoggingLevel.INFO, "Received treatmentDetails from topic : ${body}")
				.process(new UpdateDiagnosisProcessor())
				.log(LoggingLevel.INFO, "Received updated diagnosis detail from processor : ${body}")
				.convertBodyTo(String.class)
				.to(getDestinationQueue())
				.log(LoggingLevel.INFO, "Deliverd to Diagnosis Amq subscriber : ${body}");

	}

}
