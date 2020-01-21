package com.example.batch;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.model.Product;

public class ProductJdbcItemWriter implements ItemWriter<Product> {
	public static final String INSERT_PRODUCT = "insert into products " + "(id,name,description,price) values(?,?,?,?)";
	public static final String UPDATE_PRODUCT = "update products set " + "name=?, description=?, price=? where id=?";
	
	private JdbcTemplate jdbcTemplate;

	public ProductJdbcItemWriter(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}
	
	public void write(List<? extends Product> items) throws Exception {
		boolean firstReasone = false;
		boolean secondReasone = false;
		for (Product item : items) {
			
			if (firstReasone) {
				throw new com.example.exception.FirstReasoneException();
			}
			if (secondReasone) {
				throw new com.example.exception.SecondReasoneException();
			}
			
			int updated = jdbcTemplate.update(UPDATE_PRODUCT, item.getName(), item.getDescription(), item.getPrice(), item.getId());
			if (updated == 0) {
				jdbcTemplate.update(INSERT_PRODUCT, item.getId(), item.getName(), item.getDescription(), item.getPrice());
			}
		}
	}
}