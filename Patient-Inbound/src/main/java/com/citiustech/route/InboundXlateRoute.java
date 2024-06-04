package com.citiustech.route;


import java.net.ConnectException;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InboundXlateRoute extends RouteBuilder {
	
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
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
	
		
//		It throws exception when rest api is not running
		onException(ConnectException.class)
		.log(LoggingLevel.ERROR, "Rest Api not running : ${exception.message}")
		.handled(true);
		
//		It will handle the exception when id not exist
		onException(HttpOperationFailedException.class)
		.log(LoggingLevel.ERROR, "Treatment details not found : ${exception.message}")
		.handled(true);
		
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
		from(getSourceURI())
//		from("file:data/in?fileName=PatientIds.txt&noop=true")
	      .log(LoggingLevel.INFO, "Received patient ids from file : ${body}")
	      .split(body().tokenize("\n"))
	      .log(LoggingLevel.INFO, "Received tokenized patient id from body : ${body}")
	      .setBody(simple("${body.trim()}"))
	      .log(LoggingLevel.INFO, "Removed extra spaces from tokenized patient id from body : ${body}")
	      .setHeader("patientId", body())
	      .log(LoggingLevel.INFO, "Setting new header as patient Id : ${header.patientId}")
	      .log(LoggingLevel.INFO, "Requesting treatment details Rest API for patient id : ${header.patientId}")
	      .setHeader(Exchange.HTTP_METHOD, constant("GET"))
	      .setHeader(Exchange.HTTP_PATH, simple("${header.patientId}"))
	      .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
	      .setHeader("CamelBindingMode", constant("json"))
	      .to(getTreatmentURI())
	      .log(LoggingLevel.INFO, "Received treatment details from Rest API for patient id : ${header.patientId}")
	      .process(exchange -> {
	    			  ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate();
	    		      String treatmentDetails = exchange.getIn().getBody(String.class);
	    		      if(!treatmentDetails.isEmpty()) {
	    		    	  logger.info("Received treatment details from Rest api : " + treatmentDetails);
	    		    	  producerTemplate.sendBody(getDestinationQueue(), treatmentDetails);
		    		      logger.info("Sending treatment details to Inbound destination queue : " + treatmentDetails);
	    		      } else {
	    		    	  logger.error("Treatment details not exist for patient id : " + exchange.getIn().getHeader("patientId"));
	    		      }     
	    		  });
		
	}

}
