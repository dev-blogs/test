package com.devblogs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import com.devblogs.task.Quoter;
import com.devblogs.task.TerminatorQuoter;

//@Configuration
//@ComponentScan(value = "com.devblogs")
public class Config {
	//@Bean
	public Quoter terminator() {
		TerminatorQuoter terminatorQuoter = new TerminatorQuoter();
		terminatorQuoter.setMessage("TEST TERMINATOR");
		return terminatorQuoter;
	}
}