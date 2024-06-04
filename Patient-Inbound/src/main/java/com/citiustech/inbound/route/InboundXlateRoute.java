package com.citiustech.inbound.route;


import java.io.Serializable;
import java.net.ConnectException;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InboundXlateRoute extends RouteBuilder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8698187947944912000L;

	private static Logger logger = LoggerFactory.getLogger(InboundXlateRoute.class);
	
	private String sourceURI;
	private String treatmentURI;
	private String destinationQueue;
	
	public String getSourceURI() {
		return sourceURI;
	}

	public void setSourceURI(String sourceURI) {
		this.sourceURI = sourceURI;
	}

	public String getTreatmentURI() {
		return treatmentURI;
	}

	public void setTreatmentURI(String treatmentURI) {
		this.treatmentURI = treatmentURI;
	}
	
	public String getDestinationQueue() {
		return destinationQueue;
	}
	
	public void setDestinationQueue(String destinationQueue) {
		this.destinationQueue = destinationQueue;
	}

	@Override
	public void configure() throws Exception {
	
//		It throws exception when rest api is not running
		onException(ConnectException.class)
		.log(LoggingLevel.ERROR, "Rest Api not running : ${exception.message}")
		.handled(true);
		
//		It will handle the exception when id not exist
		onException(HttpOperationFailedException.class)
		.log(LoggingLevel.ERROR, "Treatment details not found : ${exception.message}")
		.handled(true);
		
//		onException(PatientNotFoundException.class)
//		.log(LoggingLevel.ERROR, "Treatment details not found : ${exception.message}")
//		.handled(true);
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
		
		from(getSourceURI())
		  .log(LoggingLevel.INFO, "Received patient ids from file : ${body}")
	      .split(body().tokenize("\n"))
	      .log(LoggingLevel.INFO, "Receiving tokenized patient id : ${body}")
	      .convertBodyTo(String.class)
	      .setHeader(Exchange.HTTP_METHOD, constant("GET"))
	      .toD(getTreatmentURI()).convertBodyTo(String.class)
	      .log(LoggingLevel.INFO, "Received treatment details from Rest API : ${body}")
	      .to("direct:restResult");

	      
		from("direct:restResult")
		   .log(LoggingLevel.INFO, "Received treatment details from direct : ${body}")
		   .to(getDestinationQueue())
		   .log(LoggingLevel.INFO, "Successfully sent to patient Inbound destination queue");
	}

}


