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

public class CSVInputFileImpl extends AbstractInputFile{

	public static final int ENDDATEINDEX = 3;
	public static final int STARTDATEINDEX = 2;
	
	AbstractInputFile abstractInputFile;
	
	public static Logger logger = LoggerFactory.getLogger(BarsController.class);
	
	@Override
	public File getFile() {
		return super.getFile();
	}

	@Override
	public void setFile(File file) {
		super.setFile(file);
	}
	
	
	@Override
	public List<Request> readFile() throws IOException {
		List<Request> requests = new ArrayList<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		
		FileReader fileReader = new FileReader(getFile());
		String line;
		
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		int counter = 0;
		
		try {
			while ((line = bufferedReader.readLine()) != null) {
				String[] data = line.split(",");
				
				counter++;
				try {
					for (int i = 1; i < data.length; i = i + ENDDATEINDEX) {
						LocalDate.parse(data[i], formatter);
					}
				} catch (Exception e) {
					logger.info(BarsException.INVALID_START_DATE_FORMAT, e);
					throw new BarsException(BarsException.INVALID_START_DATE_FORMAT + " " + counter + ".");
				}
				try {
					for (int i = STARTDATEINDEX; i < data.length; i = i + ENDDATEINDEX) {
						LocalDate.parse(data[i], formatter);
					}
				} catch (Exception e) {
					logger.info(BarsException.INVALID_End_DATE_FORMAT, e);
					throw new BarsException(BarsException.INVALID_End_DATE_FORMAT + " " + counter + ".");
				}
				
				for (int i = 0; i < data.length; i = i + ENDDATEINDEX) {
					int billingCycle = Integer.parseInt(data[i]);
					LocalDate start = LocalDate.parse(data[i+1], formatter);
					LocalDate end = LocalDate.parse(data[i+STARTDATEINDEX], formatter);
					
					try {
						if (MIN_BILLING_CYCLE <= billingCycle && billingCycle <= MAX_BILLING_CYCLE) {
							requests.add(new Request(Integer.parseInt(data[i]), start, end));
						} else {
							throw new BarsException(BarsException.BILLING_CYCLE_NOT_ON_RANGE + " " + counter + ".");
						}
							
					}catch (Exception e) {
						logger.info(BarsException.BILLING_CYCLE_NOT_ON_RANGE, e);
						throw new BarsException(BarsException.BILLING_CYCLE_NOT_ON_RANGE);
					}
				}
			}
		} finally {
			bufferedReader.close();
		}
		return requests;
			
		

	}
				
				
		
}
