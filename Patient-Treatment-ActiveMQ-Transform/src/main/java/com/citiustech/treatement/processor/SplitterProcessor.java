package com.citiustech.treatement.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplitterProcessor implements Processor {

	private static Logger logger = LoggerFactory.getLogger(SplitterProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		ProducerTemplate producerTemplate = exchange.getContext().createProducerTemplate();
		String jsonData = exchange.getIn().getBody(String.class);

		JSONArray jsonArray = new JSONArray(jsonData);

		for (int i = 0; i < jsonArray.length(); i++) {

			JSONObject treatmentDetail = jsonArray.getJSONObject(i);
			JSONObject patientDemographicDetail = treatmentDetail.getJSONObject("patientDemographicDetails");

			if (!treatmentDetail.isEmpty()) {
				logger.info("treatmentDetails exist : " + treatmentDetail);
				producerTemplate.sendBody("direct:treatmentDetail", treatmentDetail);
				logger.info("sending treatment detail to direct : " + treatmentDetail);
			} else {
				logger.error("treatmentDetail does not exist");
			}

			if (!patientDemographicDetail.isEmpty() && treatmentDetail.has("patientDemographicDetails")) {
				logger.info("Splitted Patient Demographic detail from treatment detail = " + patientDemographicDetail);
				producerTemplate.sendBody("direct:patientDemographicDetails", patientDemographicDetail);
				logger.info("sending patient demographic detail to direct : " + treatmentDetail);
			} else {
				logger.error("patientDemographicDetail does not exist");
			}

		}

	}

}

//producerTemplate.sendBody("activemq:queue:xlateDestinationQueue", treatmentDetails);
//producerTemplate.sendBody("direct:treatmentDetails", treatmentDetails);

//exchange.setProperty("treatmentDetails", jsonObject);
//exchange.setProperty("patientDemographicDetails", jsonObject.getJSONObject("patientDemographicDetails"));