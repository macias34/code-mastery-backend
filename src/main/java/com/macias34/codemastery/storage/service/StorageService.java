package com.macias34.codemastery.storage.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Service
public class StorageService {

	@Autowired
	@Qualifier("code-mastery")
	private AmazonS3 amazonS3Client;

	@Value("${cloud.do.bucket.name}")
	private String bucketName;

	@Value("${cloud.do.cdn.endpoint}")
	private String cdn_endpoint;

	/**
	 * Upload file into DigitalOcean Spaces
	 *
	 * @param keyName
	 * @param file
	 * @return String
	 */
	public void uploadFile(final String fileName, final MultipartFile file) throws IOException {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(contentType(file));

		PutObjectResult result = amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

		System.out.println("Content - Length in KB : " + result.getMetadata().getContentLength());

	}

	/**
	 * Deletes file from DigitalOcean Spaces
	 *
	 * @param fileName
	 * @return
	 */
	public String deleteFile(final String fileName) {
		amazonS3Client.deleteObject(bucketName, fileName);
		return "Deleted File: " + fileName;
	}

	/**
	 * Downloads file from DigitalOcean Spaces
	 *
	 * @param keyName
	 * @return ByteArrayOutputStream
	 */
	public ByteArrayOutputStream downloadFile(String keyName) throws IOException {
		S3Object s3object = amazonS3Client.getObject(new GetObjectRequest(bucketName, keyName));

		InputStream is = s3object.getObjectContent();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len;
		byte[] buffer = new byte[4096];
		while ((len = is.read(buffer, 0, buffer.length)) != -1) {
			outputStream.write(buffer, 0, len);
		}

		return outputStream;

	}

	/**
	 * Get all files from DO Spaces
	 *
	 * @return
	 */
	public List<String> listFiles() {

		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
				.withEncodingType(null);

		List<String> files = new ArrayList<>();
		ObjectListing objects = amazonS3Client.listObjects(listObjectsRequest);

		while (true) {
			List<S3ObjectSummary> objectSummaries = objects.getObjectSummaries();
			if (objectSummaries.size() < 1) {
				break;
			}

			for (S3ObjectSummary item : objectSummaries) {
				if (!item.getKey().endsWith("/"))
					files.add(item.getKey());
			}

			objects = amazonS3Client.listNextBatchOfObjects(objects);
		}

		return files;
	}

	private String contentType(final MultipartFile file) {

		final String fileName = file.getOriginalFilename();
		return file.getContentType() == null ? fileName.substring(fileName.lastIndexOf(".") + 1)
				: file.getContentType();
	}
}