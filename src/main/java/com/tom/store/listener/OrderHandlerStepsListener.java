package com.tom.store.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OrderHandlerStepsListener implements StepExecutionListener {
	
	@Value("${order.CRN}")
	private String fileUrl;

	@Override
	public void beforeStep(StepExecution stepExecution) {
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		File file = new File(fileUrl + stepExecution.getJobExecution().getJobInstance().getJobName() + "_" + stepExecution.getJobExecution().getJobId() + ".txt");
		try {
			FileWriter fileWriter = new FileWriter(file, true); 
			String string = String.format("Nom de la Step : %s / "
					+ "Etat de la step : %s \n",
					stepExecution.getStepName(), stepExecution.getStatus());
			if (stepExecution.getReadCount() != 0) {
				string = string + String.format("Nombre de commit : %s / nombre d'items lus : %s \n", stepExecution.getCommitCount(), stepExecution.getReadCount());
			}
			fileWriter.write(string);
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	

}
