package com.practice.bars.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.practice.bars.controller.BarsController;
import com.practice.bars.domain.Request;
import com.practice.bars.exception.BarsException;

public class TextInputFileImpl extends AbstractInputFile{
	
	public static final int START_OF_STARTDATE = 2;
	public static final int START_OF_ENDDATE = 10;
	public static final int END_OF_ENDDATE = 18;
	
	public static Logger logger = LoggerFactory.getLogger(BarsController.class);
	
	AbstractInputFile abstractInputFile;


	@Override
	public void setFile(File file) {
		// TODO Auto-generated method stub
		super.setFile(file);
	}
	
	@Override
	public File getFile() {
		// TODO Auto-generated method stub
		return super.getFile();
	}

	@Override
	public List<Request> readFile() throws IOException, BarsException {
		List<Request> requests = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
		
		FileReader fileReader = new FileReader(getFile());
		
		int billingCycle;
		LocalDate startDate;
		LocalDate endDate;
		int counter = 0;
		String line;
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				counter++;
				try {
					LocalDate.parse(line.substring(START_OF_STARTDATE, START_OF_ENDDATE), formatter);
				} catch (Exception e) {
					logger.info(BarsException.INVALID_START_DATE_FORMAT, e);
					throw new BarsException(BarsException.INVALID_START_DATE_FORMAT + " " + counter + ".");
				}
				try {
					LocalDate.parse(line.substring(START_OF_ENDDATE, END_OF_ENDDATE), formatter);
				} catch (Exception e) {
					logger.info(BarsException.INVALID_End_DATE_FORMAT);
					throw new BarsException( BarsException.INVALID_End_DATE_FORMAT+ " " + counter + ".");
				}
				
				try {
					billingCycle = Integer.parseInt(line.substring(0, START_OF_STARTDATE));
					if(MIN_BILLING_CYCLE <= billingCycle && billingCycle <= MAX_BILLING_CYCLE) {
						startDate = LocalDate.parse(line.substring(START_OF_STARTDATE, START_OF_ENDDATE), formatter);
						endDate = LocalDate.parse(line.substring(START_OF_ENDDATE, END_OF_ENDDATE), formatter);
						requests.add(new Request(billingCycle, startDate, endDate));
					} else {
						throw new BarsException(BarsException.BILLING_CYCLE_NOT_ON_RANGE + " " + counter + ".");
					}
				}catch (Exception e) {
					logger.info(BarsException.BILLING_CYCLE_NOT_ON_RANGE, e);
					throw new BarsException(BarsException.BILLING_CYCLE_NOT_ON_RANGE + " " + counter + ".");
				}
			}
		}
		finally {
			bufferedReader.close();
		}
		return requests;
		
		
		
		
	}

	
	
	
}
