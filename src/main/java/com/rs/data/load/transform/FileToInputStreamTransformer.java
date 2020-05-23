package com.rs.data.load.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.integration.file.transformer.AbstractFilePayloadTransformer;

public class FileToInputStreamTransformer extends AbstractFilePayloadTransformer<InputStream>{

	

	@Override
	protected InputStream transformFile(File file) throws IOException {
		return  new FileInputStream(file);
	}

}
