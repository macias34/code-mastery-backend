package com.macias34.codemastery.util;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static String getFileExtension(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		if (originalFilename != null) {
			int lastIndex = originalFilename.lastIndexOf('.');
			if (lastIndex != -1) {
				return originalFilename.substring(lastIndex);
			}
		}
		return null;
	}

}
