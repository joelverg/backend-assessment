package com.practice.bars.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.bars.entity.Billing;

public interface BillingRepository extends JpaRepository<Billing, Integer>{
	
	public Billing findByBillingCycleAndStartDateAndEndDate(int billingCycle, 
			LocalDate startDate,
			LocalDate endDate);
	
}
