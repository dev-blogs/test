package com.example.service.impl;

import com.example.service.DiscountService;

public class DiscountServiceImpl implements DiscountService {
	@Override
	public int getDiscount() {
		boolean error = false;
		if (error) {
			throw new RuntimeException();
		}
		return 10;
	}
}