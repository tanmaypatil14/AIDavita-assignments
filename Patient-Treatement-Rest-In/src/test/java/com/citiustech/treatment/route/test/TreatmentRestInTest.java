package com.citiustech.treatment.route.test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class TreatmentRestInTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/patient-treatement-rest-in-blueprint.xml";

	}

	@Test
	public void testContextOk() throws Exception {
		assertTrue(context.getStatus().isStarted());
	}

	@Test
	public void testValidPatientIdsInFile() throws Exception {

		context.getRouteDefinition("treatmentRestIn").adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
//				To replace the route input with a new endpoint
				replaceFromWith("direct:in");
				weaveByToUri("activemq:queue:patientInboundDestinationQueue").replace().to("mock:testResult");
			}
		});

		context.start();
//		By using exchange method we are sending 1234 as a body
		Object fileContents = getFileContents("src/test/resources/data/in/PatientIds.txt");
		Exchange exchange = ExchangeBuilder.anExchange(context).withBody(fileContents).build();
		template.send("direct:in", exchange);
		MockEndpoint mockEndpoint = getMockEndpoint("mock:testResult");
		mockEndpoint.expectedMessageCount(2);
		assertMockEndpointsSatisfied();
		context.stop();
	}

	@Test
	public void testFileIsEmpty() throws Exception {
		context.getRouteDefinition("treatmentRestIn").adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				replaceFromWith("direct:in");
				weaveByToUri("activemq:queue:patientInboundDestinationQueue").replace().to("mock:testResult");
			}
		});
		Object patientIds = getFileContents("src/test/resources/data/in/Empty.txt");
		Exchange payload = ExchangeBuilder.anExchange(context).withBody(patientIds).build();
		template.send("direct:in", payload);
		MockEndpoint mockEndpoint = getMockEndpoint("mock:testResult");
		mockEndpoint.expectedMessageCount(0);
		assertMockEndpointsSatisfied();
		context.stop();
	}

	@Test
	public void testWhitespaceInFile() throws Exception {
		context.getRouteDefinition("treatmentRestIn").adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
				// TODO Auto-generated method stub
				replaceFromWith("direct:in");
				weaveById("httpRequest").replace().to("mock:result");
			}
		});
		context.start();
		MockEndpoint mockEndpoint = getMockEndpoint("mock:result");
		String fileContent = getFileContents("src/test/resources/data/in/PatientIds.txt");
		mockEndpoint.expectedBodiesReceived("1234", "1235", "1236");
		template.sendBody("direct:in", fileContent);
		assertMockEndpointsSatisfied();
		context.stop();
	}

	@Test
	public void testFileWithBlankLines() throws Exception {
		context.getRouteDefinition("treatmentRestIn").adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				// TODO Auto-generated method stub
				replaceFromWith("direct:in");
				weaveById("httpRequest").replace().to("mock:result");
			}
		});
		context.start();
		MockEndpoint mockEndpoint = getMockEndpoint("mock:result");
		String fileContent = getFileContents("src/test/resources/data/in/PatientIds.txt");
		mockEndpoint.expectedBodiesReceived("1234", "1235", "1236");
		template.sendBody("direct:in", fileContent);
		assertMockEndpointsSatisfied();
		context.stop();
	}

	private String getFileContents(String path) throws Exception {
		Path filePath = new File(path).toPath();
		return new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
	}

}
