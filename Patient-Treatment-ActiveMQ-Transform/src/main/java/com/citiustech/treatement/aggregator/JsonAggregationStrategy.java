package com.citiustech.treatement.aggregator;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.json.JSONArray;

public class JsonAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		Object newBody = newExchange.getIn().getBody(Object.class);
		JSONArray list = null;
		if (oldExchange == null) {
			list = new JSONArray();
			list.put(newBody);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			list = oldExchange.getIn().getBody(JSONArray.class);
			list.put(newBody);
			return oldExchange;
		}

	}

}
