package com.citiustech.aggregator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class JsonAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		
		Object newBody = newExchange.getIn().getBody(Object.class);
		LinkedList<Object> list = null;
		if (oldExchange == null) {
			list = new LinkedList<Object>();
			list.add(newBody);
			newExchange.getIn().setBody(list);
			return newExchange;
		} else {
			list = oldExchange.getIn().getBody(LinkedList.class);
			list.add(newBody);
			return oldExchange;
		}

	}

}
