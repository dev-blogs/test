package com.example.listener;

import javax.sql.DataSource;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseSkipListener {
	private JdbcTemplate jdbcTemplate;
	
	public DatabaseSkipListener(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}
	
	@OnSkipInRead
	public void log(Throwable t) {
		if (t instanceof FlatFileParseException) {
			FlatFileParseException ffpe = (FlatFileParseException) t;
			jdbcTemplate.update("INSERT INTO skipped_products (line, line_number) VALUES (?, ?)", ffpe.getInput(), ffpe.getLineNumber());
		}
	}
}