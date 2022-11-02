package com.tom.store.tasklet;

import java.io.File;
import java.io.FileReader;


import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.tom.store.entity.JsonProduct;

@Component
public class CheckingJsonFiles implements Tasklet {

	final TypeAdapter<JsonProduct> strictAdapter = new Gson().getAdapter(JsonProduct.class);

	@Value("Orders/*.json")
	private Resource[] jsonFiles;
	
	JsonParser jsonParser;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		for (Resource jsonFile : jsonFiles) {
			File file = jsonFile.getFile();
			


		}
		return null;
	}

}
