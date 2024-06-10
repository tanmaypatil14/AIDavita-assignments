package com.citiustech.xlate.route;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.file.GenericFileOperationFailedException;

import com.citiustech.xlate.aggregator.JsonAggregationStrategy;
import com.citiustech.xlate.processor.SplitterProcessor;

public class XlateDestinationRoute extends RouteBuilder{
	
	private String sourceQueue;
	private String destinationURI;
	private String destinationQueue;

	public String getSourceQueue() {
		return sourceQueue;
	}
	public void setSourceQueue(String sourceQueue) {
		this.sourceQueue = sourceQueue;
	}
	public void setDestinationURI(String destinationURI) {
		this.destinationURI = destinationURI;
	}
	public String getDestinationURI() {
		return destinationURI;
	}
	public void setDestinationQueue(String detinationQueue) {
		this.destinationQueue = detinationQueue;
	}
	public String getDestinationQueue() {
		return destinationQueue;
	}

	@Override
	public void configure() throws Exception {
		
//		it handles the exception while creating a file
		onException(GenericFileOperationFailedException.class)
		.log(LoggingLevel.ERROR, "Generic file exception occured while creating file : ${exception.message}")
		.handled(true);
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);

//	     from("file:src/main/resources/data/in?noop=true")
		 from(getSourceQueue())
	       .log(LoggingLevel.INFO, "Received treatment detail from inbound destination queue to xlate : ${body}")
	       .process(new SplitterProcessor())
	       .log(LoggingLevel.INFO, "Sent treatment detail to processor : ${body}");

	     from("direct:patientDemographicDetails")
	       .log(LoggingLevel.INFO, "Received patient demographic details from direct : ${body}")
	       .aggregate(constant(true), new JsonAggregationStrategy())
	       .log(LoggingLevel.INFO, "Received patient demographic details from Json aggregator : ${body}")
	       .completionTimeout(5000)
	       .convertBodyTo(String.class)
	       .to(getDestinationURI());

		 from("direct:treatmentDetail")
		   .log(LoggingLevel.INFO, "Received treatment detail from direct : ${body}")
	       .convertBodyTo(String.class)
	       .to(getDestinationQueue())
	       .log(LoggingLevel.INFO, "Received treatment detail to xlate destination topic : ${body}");	
		
	}

}