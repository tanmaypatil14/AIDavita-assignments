package com.citiustech.diagnosis.route.test;

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

public class DiagnosisActiveMQOutTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		// TODO Auto-generated method stub
		return "OSGI-INF/blueprint/patient-diagnosis-activemq-out-blueprint.xml";
	}

	@Test
	public void testContextOk() throws Exception {
		assertTrue(context.getStatus().isStarted());
	}

	@Test
	public void testDiagnosisDataSentRoute() throws Exception {

		context.getRouteDefinition("diagnosisActiveMQOut").adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				replaceFromWith("direct:in");

				weaveByToUri("activemq:topic:diagnosisSubscribers").replace().to("mock:diagnosisTest");

			}
		});

		context.start();

		String inputBody = getFileContents("src/test/resources/data/in/treatment.json");

		String expectedOutput = getFileContents("src/test/resources/data/out/result.json");

		Exchange actualInput = ExchangeBuilder.anExchange(context).withBody(inputBody).build();

		MockEndpoint mockEndpoint = getMockEndpoint("mock:diagnosisTest");
		mockEndpoint.expectedBodiesReceived(expectedOutput);

		template.send("direct:in", actualInput);

		assertMockEndpointsSatisfied();

		context.stop();

	}

	private String getFileContents(String path) throws Exception {
		Path filePath = new File(path).toPath();
		return new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
	}

}
