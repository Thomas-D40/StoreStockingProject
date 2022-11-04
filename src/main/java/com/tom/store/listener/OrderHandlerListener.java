package com.tom.store.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderHandlerListener implements JobExecutionListener {
	
	@Value("${order.CRN}")
	private String fileUrl;
	
	

	@Override
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		File file = new File(fileUrl + jobExecution.getJobInstance().getJobName() + ".txt");
		try {
			FileWriter fileWriter = new FileWriter(file);
			String string = jobExecution.getStatus().toString();
			System.out.println(string);
			fileWriter.write(string);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	

}
