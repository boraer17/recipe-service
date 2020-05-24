package com.rs.data.load.configuration;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

import com.rs.data.load.filter.CustomLastModifiedFileListFilter;
import com.rs.data.load.transform.FileToInputStreamTransformer;

@Configuration
public class FileLoaderConfiguration {

	@Value("${app.listener.directory}")
	private String directory;

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> fileReadingMessageSource() {
		CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter("*.xml"));
		filters.addFilter(new CustomLastModifiedFileListFilter());
        filters.addFilter(new AcceptOnceFileListFilter<File>());
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(new File(directory));
		source.setFilter(filters);
		return source;
	}

	@Bean(autowireCandidate = true)
	public FileToStringTransformer fileToStringTransformer() {
		return new FileToStringTransformer();
	}
	@Bean(autowireCandidate = true)
	public FileToInputStreamTransformer fileToInputStreamTransformer() {
		return new FileToInputStreamTransformer();
	}
	
	@Bean
	public MessageChannel fileInputChannel() {
		return new DirectChannel();
	}
	
}
