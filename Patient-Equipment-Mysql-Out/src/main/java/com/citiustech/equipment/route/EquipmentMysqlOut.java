package com.citiustech.route;

import java.sql.SQLException;

import org.apache.activemq.ConnectionFailedException;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import com.citiustech.processor.UpdateEquipmentFlagProcessor;

public class UpdateEquipmentDetailsRoute extends RouteBuilder {
	
	private String sourceQueue;
	private String updateEquipementQuery;
	
	public String getSourceQueue() {
		return sourceQueue;
	}

	public void setSourceQueue(String sourceQueue) {
		this.sourceQueue = sourceQueue;
	}

	public String getUpdateEquipementQuery() {
		return updateEquipementQuery;
	}

	public void setUpdateEquipementQuery(String updateEquipementQuery) {
		this.updateEquipementQuery = updateEquipementQuery;
	}

	@Override
	public void configure() throws Exception {
		
		onException(Exception.class)
		.log(LoggingLevel.ERROR, "Exception occurred: ${exception.message}")
		.handled(true);
		
		onException(ConnectionFailedException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "Failed to connect ActiveMQ : ${exception.message}");
		
//      It will check for SQL Syntax is correct or not
		onException(SQLException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, "SQL Exception occured : ${exception.message}");

		from(getSourceQueue())
//		from("file:data/in?noop=true")
		        .log(LoggingLevel.INFO, "Received treatmentDetails from topic : ${body}")
				.process(new UpdateEquipmentFlagProcessor())
		        .choice()
		        .when(header("active").isEqualTo(true))
		           .log("Inside the header")
		           .log(LoggingLevel.INFO, "Received active equipment detail from processor : ${body}")
		           .to(getUpdateEquipementQuery())
		        .otherwise()
		           .log(LoggingLevel.INFO, "Received in active equipment detail from processor : ${body}")
		           .end()
		        .log(LoggingLevel.INFO, "Equipment detail flag updated accourding to patient status");
	}

}
