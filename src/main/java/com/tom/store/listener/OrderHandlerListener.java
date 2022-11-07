package com.tom.store.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderHandlerListener implements JobExecutionListener {
	
	@Value("${order.CRN}")
	private String fileUrl;
	
	

	@Override
	public void beforeJob(JobExecution jobExecution) {
		File file = new File(fileUrl + jobExecution.getJobInstance().getJobName() + "_" + jobExecution.getJobId() + ".txt");
		try {
			FileWriter fileWriter = new FileWriter(file);
			String firstLine = "JobName : " + jobExecution.getJobInstance().getJobName() + "\n";
			String secondLineString = "Job Execution ID : " + jobExecution.getJobId() + "\n";
			fileWriter.write(firstLine + secondLineString);
			fileWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		File file = new File(fileUrl + jobExecution.getJobInstance().getJobName() + "_" + jobExecution.getJobId() + ".txt");
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			String string = "Status du job : " + jobExecution.getStatus().toString() + "\n";
			System.out.println(string);
			fileWriter.write(string);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

	

}
