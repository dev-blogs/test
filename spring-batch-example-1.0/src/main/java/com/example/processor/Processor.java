package com.example.processor;

import java.math.BigDecimal;
import org.springframework.batch.item.ItemProcessor;
import com.example.model.Product;
import com.example.service.DiscountHolder;

public class Processor implements ItemProcessor<Product, Product> {
	private DiscountHolder discountHolder;
	
	public void setDiscountHolder(DiscountHolder discountHolder) {
		this.discountHolder = discountHolder;
	}
	
	@Override
	public Product process(Product item) throws Exception {
		/*count++;
		if (count == 12 || count == 25) {
			throw new FlatFileParseException("test", "test");
		}*/
		item.setPrice(new BigDecimal(item.getPrice().doubleValue() - item.getPrice().doubleValue() * ((double) discountHolder.getHolder()/100)));
		return item;
	}
}