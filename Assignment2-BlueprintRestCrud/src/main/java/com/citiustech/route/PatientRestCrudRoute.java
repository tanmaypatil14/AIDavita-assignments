package com.citiustech.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

import com.citiustech.exception.PatientNotFoundException;
import com.citiustech.processor.ValidateDecodedUsernamePasswordProcessor;

public class PatientRestCrudRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		onException(PatientNotFoundException.class) 
		.handled(true) 
		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204)) 
		.setBody(constant("No Patient found found for id : ${header.id} !"));
		
//		In order to specify the REST implementation used by the REST DSL
//		To provide REST DSL configuration
		restConfiguration()
//		to use spark-rest component and run on port 8080
		   .component("spark-rest")
		   .port("{{server.port}}")
//		It will convert body of the incoming message from inType to the json or xml
//		And the response from json or xml to outType
		   .bindingMode(RestBindingMode.json);
		
		rest("/patients")
		  .get()
		     .to("direct:getAllPatient")
		  .get("{id}")
		     .to("direct:getPatient")
		  .post()
		     .to("direct:createPatient")
		  .put("{id}")
		     .to("direct:updatePatient")
		  .patch("{id}")
		     .to("direct:updatePatientAge")
		  .delete("{id}")
		     .to("direct:deletePatient");
		
//		Fetch All patients
		from("direct:getAllPatient")
		   .log(LoggingLevel.INFO, "${header.Authorization}")
		   .process(new ValidateDecodedUsernamePasswordProcessor())
		   .choice()
		      .when(header("authorized").isEqualTo(true))
		           .to("sql:{{sql.getAllPatient}}?dataSource=#mySqlDriverManager"
		   		       + "&outputType=SelectList&outputClass=com.citiustech.model.Patient")
		      .otherwise()
		           .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
		           .setBody(constant("{\r\n"
		           		+ "\"Http_status\":401,\r\n"
		           		+ "\"Exception\":\"Invalid username/password\"\r\n"
		           		+ "}"))
		           .log(LoggingLevel.ERROR, "Invalid username/password").end();

//		Fetch patient by id
		from("direct:getPatient")
		   .process(new ValidateDecodedUsernamePasswordProcessor())
		   .log("Fetching patient for id : ${header.id}")
		   .choice()
		      .when(header("authorized").isEqualTo(true))
		           .to("sql:{{sql.getPatientById}}?dataSource=#mySqlDriverManager"
		   		       + "&outputType=SelectOne&outputClass=com.citiustech.model.Patient")
			  .otherwise()
			       .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
		           .setBody(constant("{\r\n"
			           		+ "\"Http_status\":401,\r\n"
			           		+ "\"Exception\":\"Invalid username/password\"\r\n"
			           		+ "}"))
		           .log(LoggingLevel.ERROR, "Invalid username/password").end();
		
//		Create patient
		from("direct:createPatient")
		   .process(new ValidateDecodedUsernamePasswordProcessor())
		   .log("To save patient data : ${body}")
		   .choice()
		      .when(header("authorized").isEqualTo(true))
		           .to("sql:{{sql.insertPatient}}?dataSource=#mySqlDriverManager")
		           .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
			  .otherwise()
			       .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
		           .setBody(constant("{\r\n"
			           		+ "\"Http_status\":401,\r\n"
			           		+ "\"Exception\":\"Invalid username/password\"\r\n"
			           		+ "}"))
		           .log(LoggingLevel.ERROR, "Invalid username/password").end();
		
//		Update Patient details
		from("direct:updatePatient")
		   .process(new ValidateDecodedUsernamePasswordProcessor())
		   .log("Update patient data : ${body}")
		   .choice()
		      .when(header("authorized").isEqualTo(true))
		           .to("sql:{{sql.updatePatient}}?dataSource=#mySqlDriverManager")
			  .otherwise()
				   .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
		           .setBody(constant("{\r\n"
			           		+ "\"Http_status\":401,\r\n"
			           		+ "\"Exception\":\"Invalid username/password\"\r\n"
			           		+ "}"))
		           .log(LoggingLevel.ERROR, "Invalid username/password").end();
		
//		Update Patient Specific data
		from("direct:updatePatientAge")
		   .process(new ValidateDecodedUsernamePasswordProcessor())
		   .log("Update patient data : ${body}")
		   .choice()
		      .when(header("authorized").isEqualTo(true))
		           .to("sql:{{sql.updatePatientAge}}?dataSource=#mySqlDriverManager")
		   .otherwise()
		        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
		           .setBody(constant("{\r\n"
			           		+ "\"Http_status\":401,\r\n"
			           		+ "\"Exception\":\"Invalid username/password\"\r\n"
			           		+ "}"))
		           .log(LoggingLevel.ERROR, "Invalid username/password").end();
		
//		Delete Patient data
		from("direct:deletePatient")
		   .process(new ValidateDecodedUsernamePasswordProcessor())
		   .log("Delete patient data by id : ${header.id}")
		   .choice()
		      .when(header("authorized").isEqualTo(true))
		           .to("sql:{{sql.deletePatient}}?dataSource=#mySqlDriverManager")
		           .setBody(simple("Message : Patient deleted for id : ${header.id}"))
		           
			  .otherwise()
			       .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
		           .setBody(constant("{\r\n"
			           		+ "\"Http_status\":401,\r\n"
			           		+ "\"Exception\":\"Invalid username/password\"\r\n"
			           		+ "}"))
		           .log(LoggingLevel.ERROR, "Invalid username/password").end();
	}
}

