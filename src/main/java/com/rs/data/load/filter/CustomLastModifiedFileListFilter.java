package com.rs.data.load.filter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.integration.file.filters.LastModifiedFileListFilter;
import org.springframework.lang.Nullable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CustomLastModifiedFileListFilter extends LastModifiedFileListFilter {

	private static final long ONE_SECOND = 1000;

	@Nullable
	private Consumer<File> discardCallback;

	@Override
	public List<File> filterFiles(File[] files) {
		List<File> list = new ArrayList<>();
		long now = System.currentTimeMillis() / ONE_SECOND;
		for (File file : files) {
			if (!fileIsAged(file, now)) {
				list.add(file);
			} else if (this.discardCallback != null) {
				this.discardCallback.accept(file);
			}
		}
		return list;
	}

	@Override
	public boolean accept(File file) {
		if (!fileIsAged(file, System.currentTimeMillis() / ONE_SECOND)) {
			return true;
		} else if (this.discardCallback != null) {
			this.discardCallback.accept(file);
		}
		return false;
	}

	private boolean fileIsAged(File file, long now) {

		BasicFileAttributes attrs;
		try {
			attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			FileTime time = attrs.creationTime();
			Long createTime = time.toMillis();
			return getAge() < now - (createTime / 1000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
