package com.tom.store.tasklet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Component
public class CheckingJsonFiles implements Tasklet {

	@Value("Orders/*.json")
	private Resource[] jsonFiles;


	@Value("${order.fileToTreat}")
	String fileToTreat;
	@Value("${order.fileRefused}")
	String fileRefusedUrl;

	private JSONParser jsonParser = new JSONParser();

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File folder = new File(fileToTreat);
		
		if (folder.listFiles().length == 0) {
			chunkContext.getStepContext().getStepExecution().setTerminateOnly();
		}
		
		for (File file:folder.listFiles()) {
			boolean isFileValid = true;
			try (FileReader reader = new FileReader(file)) {
				// Read JSON file
				Object obj = jsonParser.parse(reader);

				JSONArray productList = (JSONArray) obj;
				System.out.println(productList);

				for (int i = 0; i < productList.size(); i++) {
					JSONObject product = (JSONObject) productList.get(i);
					if (product.get("quantity") == null || product.get("name") == null || product.get("refNumber") == null) {
						isFileValid = false;
						break;
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			if (!isFileValid) {
				Files.move(Paths.get(file.getAbsolutePath()), Paths.get(fileRefusedUrl + "\\" + file.getName()));
				System.out.println(chunkContext.getStepContext().getStepExecution().getStatus());
			}
		}
		
		// Check if there is still files for Job		
		if (folder.listFiles().length == 0) {
			chunkContext.getStepContext().getStepExecution().setTerminateOnly();
		}
		
		System.out.println(chunkContext.getStepContext().getStepExecution().getStatus());
		System.out.println(BatchStatus.class.descriptorString());
		
		return null;
	}

}
