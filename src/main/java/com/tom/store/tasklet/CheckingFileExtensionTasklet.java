package com.tom.store.tasklet;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CheckingFileExtensionTasklet implements Tasklet {

	@Value("${order.fileToTreat}")
	String fileToTreat;
	@Value("${order.fileRefused}")
	String fileRefusedUrl;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		File folder = new File(fileToTreat);

		if (folder.listFiles().length == 0) {
			chunkContext.getStepContext().getStepExecution().setTerminateOnly();
		}

		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				String[] split = file.getName().split("\\.");
				String fe = split[split.length - 1];
				if (!fe.equals("json")) {
					System.out.println(file.getName());
					Files.move(Paths.get(file.getAbsolutePath()), Paths.get(fileRefusedUrl + "\\" + file.getName()));

				}
			}			

		}
		return RepeatStatus.FINISHED;

	}
}
