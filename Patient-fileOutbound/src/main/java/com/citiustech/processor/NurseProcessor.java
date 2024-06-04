package com.citiustech.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NurseProcessor implements Processor {

	private static Logger logger = LoggerFactory.getLogger(NurseProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		String treatmentJsonData = exchange.getIn().getBody(String.class);

		boolean flag = false;

		JSONObject treatmentDetail = new JSONObject(treatmentJsonData);
		JSONObject nurseDetail = treatmentDetail.getJSONObject("nurseDetails");
		JSONObject patientDemographicDetail = treatmentDetail.getJSONObject("patientDemographicDetails");
		Integer patientId = (Integer) patientDemographicDetail.get("patientId");
		exchange.getIn().setHeader("patientId", patientId);
		

		if (!treatmentDetail.isEmpty()) {
			logger.info("treatmentDetails exist = " + treatmentDetail);
		} else {
			logger.error("treatmentDetails does not exist");
		}

		if (!nurseDetail.isEmpty() && treatmentDetail.has("nurseDetails")) {
			logger.info("nurseDetail exist = " + nurseDetail);
			exchange.getIn().setHeader("nurseId", (Integer) nurseDetail.get("nurseId"));
			if ((boolean) patientDemographicDetail.get("active")) {
				logger.info("patient is active with id : " + patientId);
				flag = true;
				exchange.getIn().setHeader("isActive", flag);
				exchange.getIn().setBody(nurseDetail);
				logger.info("Nurse detail for active patient : " + exchange.getIn().getBody(JSONObject.class));
			} else {
				logger.info("patient is not active with id : " + patientId);
				exchange.getIn().setHeader("isActive", flag);
				exchange.getIn().setBody(nurseDetail);
				logger.info("Nurse detail for in active patient : " + exchange.getIn().getBody(JSONObject.class));
			}
		} else {
			logger.error("equipmentDetail does not exist");
		}

	}

}
