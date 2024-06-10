package com.citiustech.treatment.route;


import java.net.ConnectException;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.commons.httpclient.HttpStatus;


public class TreatmentRestIn extends RouteBuilder {

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
	
//		It throws exception when rest API is not running
		onException(ConnectException.class)
		.log(LoggingLevel.ERROR, "Internal server error : ${exception.message} - Status Code - " + HttpStatus.SC_INTERNAL_SERVER_ERROR)
		.handled(true);
		
//		It will handle the exception when id not exist
		onException(HttpOperationFailedException.class)
		.log(LoggingLevel.ERROR, "Treatment details not found : ${exception.message}")
		.handled(true);

		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
		
		from(getSourceURI()).routeId("treatmentRestIn")
		  .log(LoggingLevel.INFO, "Received patient ids from file : ${body}")
	      .split(body().tokenize("\n")).setBody(simple("${body.trim()}"))
	      .choice()
	         .when(body().isEqualTo(""))
	            .log(LoggingLevel.ERROR, "Empty Line is present")
	         .otherwise()
	            .log(LoggingLevel.INFO, "Receiving tokenized patient id : ${body}")
	            .convertBodyTo(String.class)
	            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
	            .toD(getTreatmentURI()).id("httpRequest")
	            .convertBodyTo(String.class)
	            .log(LoggingLevel.INFO, "Received treatment details from Rest API : ${body}")
		        .to(getDestinationQueue())
		        .log(LoggingLevel.INFO, "Successfully sent to patient Inbound destination queue");


//	      .to("direct:restResult")
//	      ;	      
//		from("direct:restResult")
//		   .log(LoggingLevel.INFO, "Received treatment details from direct : ${body}")
//		   .to(getDestinationQueue())
//		   .log(LoggingLevel.INFO, "Successfully sent to patient Inbound destination queue");
	}

}


