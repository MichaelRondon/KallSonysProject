package com.javeriana.aggregators;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class ProviderAgregator implements AggregationStrategy {
 
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange == null) {
            // the first time we only have the new exchange so it wins the first round
            return newExchange;
        }
        Double oldPrice = Double.valueOf(oldExchange.getIn().getHeader("responseManufacter", String.class));
        Double newPrice = Double.valueOf(newExchange.getIn().getHeader("responseManufacter", String.class));
        // return the "winner" that has the highest price
        return newPrice < oldPrice ? newExchange : oldExchange;
	}
}