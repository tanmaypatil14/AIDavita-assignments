package com.citiustech.inbound.test.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.junit.Test;

public class InboundXlateRouteTest extends CamelBlueprintTestSupport {

	@Override
	protected String getBlueprintDescriptor() {
		return "OSGI-INF/blueprint/blueprint.xml";
	}

//	@Override
//	protected String getBundleFilter() {
//		return "(!(Bundle-SymbolicName= org.test.junit.bundleTest))";
//	}

	@Test
	public void testContextOk() throws Exception {
		assertTrue(context.getStatus().isStarted());
	}

	@Test
	public void httpInboundXlateTestRoute() throws Exception {

		context.getRouteDefinitions().get(0).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {
//				To replace the route input with a new endpoint
				replaceFromWith("direct:in");
//				Manipulates the route at the node IDs that matches the pattern.
//				weaveById("patientreportqueue")
//				   .replace()
//				   .setBody(constant("1234")).to("mock:result");

//				we are setting the value of body as 1234
				weaveByToUri("direct:restResult").replace().setBody(constant("1234")).to("mock:result");
			}
		});

		context.start();

//		By using exchange method we are sending 1234 as a body
		Exchange exchnage = ExchangeBuilder.anExchange(context).withBody("1235").build();

		template.send("direct:in", exchnage);

		MockEndpoint mock = getMockEndpoint("mock:result");
		System.out.println("mock" + mock);
		mock.expectedMessageCount(1);
		
		assertMockEndpointsSatisfied();

	}
	
	@Test
	public void queueInboundXlateTestRoute() throws Exception {

		context.getRouteDefinitions().get(1).adviceWith(context, new AdviceWithRouteBuilder() {
			@Override
			public void configure() throws Exception {

				replaceFromWith("direct:restResult");
				weaveByToUri("activemq:queue:patientInboundDestinationQueue").replace().setBody(constant("1234")).to("mock:result");
			}
		});

		context.start();

//		By using exchange method we are sending 1234 as a body
		Exchange exchnage = ExchangeBuilder.anExchange(context).withBody("1234").build();

		template.send("direct:restResult", exchnage);

		MockEndpoint mock = getMockEndpoint("mock:result");
		mock.expectedMessageCount(1);
		
		assertMockEndpointsSatisfied();

	}

}
