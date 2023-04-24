package com.practice.bars.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.practice.bars.domain.Record;
import com.practice.bars.domain.Request;
import com.practice.bars.entity.Billing;
import com.practice.bars.exception.BarsException;
import com.practice.bars.factory.InputFileFactory;
import com.practice.bars.file.AbstractInputFile;
import com.practice.bars.repository.BillingRepository;

@Component
public class FileProcessor {
	
	@Autowired
	private BillingRepository billingRepository;
	
	public FileProcessor() {
		//empty constructor
	}
	
	public List<Request> execute(File file) throws BarsException, IOException{
		
		InputFileFactory inputFileFactory = InputFileFactory.getInstance();
		
		
		
		
//		AbstractInputFile abstractInputFile = InputFileFactory.getInstance().getInputFile(file);
		
		AbstractInputFile abstractInputFile = inputFileFactory.getInputFile(file);
		
		
		System.out.println(abstractInputFile);
		
		abstractInputFile.setFile(file);
		
		List<Request> requests = abstractInputFile.readFile();
		
		System.out.println("requests" + requests);
		
		if(requests.isEmpty()) {
			throw new BarsException(BarsException.NO_REQUESTS_TO_READ);
		} else {
			return requests;
		}
	}
	
	public List<Record> retrieveRecordFromDB(List<Request> requests) {
		
		List<Record> records = new ArrayList<Record>();
		
		System.out.println(requests);
		
		for (Request request : requests) {
			Billing billing = billingRepository.findByBillingCycleAndStartDateAndEndDate(request.getBillingCycle(), request.getStartDate(), request.getEndDate());
			
			if(billing != null ) {
				records.add(new Record(billing.getBillingCycle(),
							billing.getStartDate(),
							billing.getEndDate(),
							billing.getAccountId().getAccountName(),
							billing.getAccountId().getCustomerId().getFirstName(),
							billing.getAccountId().getCustomerId().getLastName(),
							billing.getAmount()));
							
			}
			
		}
		if (records.isEmpty()) {
			throw new BarsException(BarsException.NO_RECORDS_TO_WRITE);
		} else {
				
//			for (int j=0; j<records; j++) {
//				   System.out.format( records[j]);
//				}
			
			System.out.println(records.toString());
			
			 for(Record record : records) {
		            System.out.println(record);
		        }
			
			return records;
		}
	}
	
	
	
	
}
