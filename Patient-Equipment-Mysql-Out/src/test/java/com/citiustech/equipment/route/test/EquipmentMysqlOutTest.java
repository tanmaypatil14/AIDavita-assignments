package com.citiustech.equipment.route.test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class EquipmentMysqlOutTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		// TODO Auto-generated method stub
		return "OSGI-INF/blueprint/patient-equipment-mysql-out-blueprint.xml";
	}

	@Test
	public void testContextOk() throws Exception {
		assertTrue(context.getStatus().isStarted());
	}

	@Test
	public void testActiveEquipmentUpdate() throws Exception {
		context.getRouteDefinition("equipmentMysqlOut").adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				replaceFromWith("direct:in");

				weaveAddLast().to("mock:result");

			}
		});

		context.start();

		String activePatient = getFileContents("src/test/resources/data/in/treatment1.json");
//		String inActivePatient = getFileContents("src/test/resources/data/in/treatment2.json");

		MockEndpoint mockEndpoint = getMockEndpoint("mock:result");

		template.sendBody("direct:in", activePatient);

		mockEndpoint.expectedHeaderReceived("isActive", true);
		assertMockEndpointsSatisfied();

		context.stop();

	}

	@Test
	public void testInActiveEquipmentUpdate() throws Exception {
		context.getRouteDefinition("equipmentMysqlOut").adviceWith(context, new AdviceWithRouteBuilder() {

			@Override
			public void configure() throws Exception {
				replaceFromWith("direct:in");

				weaveAddLast().to("mock:result");

			}
		});

		context.start();

		String inActivePatient = getFileContents("src/test/resources/data/in/treatment2.json");

		MockEndpoint mockEndpoint = getMockEndpoint("mock:result");

		template.sendBody("direct:in", inActivePatient);

		mockEndpoint.expectedHeaderReceived("isActive", false);
		assertMockEndpointsSatisfied();

		context.stop();

	}

	private String getFileContents(String path) throws Exception {
		Path filePath = new File(path).toPath();
		return new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
	}

}
