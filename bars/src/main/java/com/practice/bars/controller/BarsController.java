package com.practice.bars.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.bars.domain.Record;
import com.practice.bars.domain.Request;
import com.practice.bars.entity.Billing;
import com.practice.bars.exception.BarsException;
import com.practice.bars.repository.BillingRepository;


@RestController
@RequestMapping(path = "/billings")
public class BarsController {
	
	@Autowired
	FileProcessor fileProcessor;
	
	public static Logger logger = LoggerFactory.getLogger(BarsController.class);
	
	@GetMapping("/bars")
	public List<Record> requestBilling(@RequestParam String fileName) throws BarsException, IOException {
		
		File file = new File("C:\\BARS_TEST\\" + fileName);
		
		List<Request> requests = (List<Request>) fileProcessor.execute(file);
		
		List<Record> records = fileProcessor.retrieveRecordFromDB(requests);
		
		return records;
		
	}
	
	@Autowired
	BillingRepository repository;
	
	@GetMapping("/users")
	public List<Billing> getAllUsers() {
		return repository.findAll();
	}
	
}
