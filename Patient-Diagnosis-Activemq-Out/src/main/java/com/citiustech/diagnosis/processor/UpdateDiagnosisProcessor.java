package com.citiustech.diagnosis.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateDiagnosisProcessor implements Processor {

	private static Logger logger = LoggerFactory.getLogger(UpdateDiagnosisProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		String jsonData = exchange.getIn().getBody(String.class);

		JSONObject treatmentDetail = new JSONObject(jsonData);
		JSONObject diagnosisDetail = treatmentDetail.getJSONObject("diagnosisDetails");
		JSONObject patientDemographicDetail = treatmentDetail.getJSONObject("patientDemographicDetails");
		Integer patientId = (Integer) patientDemographicDetail.get("patientId");

		if (!treatmentDetail.isEmpty()) {
			logger.info("treatmentDetails exist = " + treatmentDetail);
		} else {
			logger.error("treatmentDetails does not exist");
		}

		if (!diagnosisDetail.isEmpty() && treatmentDetail.has("diagnosisDetails")) {
			diagnosisDetail.put("patientId", patientId);
			logger.info("Diagnosis detail updated with patient id : " + diagnosisDetail);
		} else {
			logger.error("DiagnosisDetails does not exist");
		}

		exchange.getIn().setBody(diagnosisDetail);
	}

}
