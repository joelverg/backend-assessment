package com.practice.bars.factory;

import java.io.File;

import com.practice.bars.exception.BarsException;
import com.practice.bars.file.AbstractInputFile;
import com.practice.bars.file.CSVInputFileImpl;
import com.practice.bars.file.TextInputFileImpl;

public class InputFileFactory {
	
	private static InputFileFactory factory;
	
	private InputFileFactory() {
		//Empty Constructor
	}

	public static InputFileFactory getInstance() {
		if (factory == null) {
			factory = new InputFileFactory();		
		}
		return factory;
	}
	
	public AbstractInputFile getInputFile(File file) {
		if(file.getName().endsWith("txt")) {
			return new TextInputFileImpl();		
		} else 
			if(file.getName().endsWith("csv")) {
				return new CSVInputFileImpl();
		}
			else {
				throw new BarsException(BarsException.FILE_NOT_SUPPORTED);
			}
		
		
		
		
	}
	
}
