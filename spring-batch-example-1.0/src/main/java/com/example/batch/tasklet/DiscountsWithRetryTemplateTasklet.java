package com.example.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import com.example.service.DiscountHolder;
import com.example.service.DiscountService;

public class DiscountsWithRetryTemplateTasklet implements Tasklet {
	private DiscountService discountService;
	private DiscountHolder discountHolder;
	
	public void setDiscountService(DiscountService discountService) {
		this.discountService = discountService;
	}

	public void setDiscountHolder(DiscountHolder discountHolder) {
		this.discountHolder = discountHolder;
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		int discount = discountService.getDiscount();
		discountHolder.setHolder(discount);
		return RepeatStatus.FINISHED;
	}
}