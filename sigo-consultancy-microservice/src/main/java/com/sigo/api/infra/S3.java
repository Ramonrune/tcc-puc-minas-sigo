package com.sigo.api.infra;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3 {

	private AmazonS3 client;

	public S3() {
		BasicAWSCredentials credentials = new BasicAWSCredentials(System.getenv("ACCESS_KEY"),
				System.getenv("SECRET_KEY"));

		SimpleAWSCredentialsProvider credentialsProvider = new SimpleAWSCredentialsProvider(credentials);

		this.client = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider)
				.withRegion(Regions.US_EAST_2).build();
	}

	public void save(MultipartFile arquivo, String codigoConsultoria, String codigo) {

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(arquivo.getContentType());
		objectMetadata.setContentLength(arquivo.getSize());

		String nomeUnico = codigo + ".pdf";

		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest("sigo-consultancies", codigoConsultoria + "/" + nomeUnico,
					arquivo.getInputStream(), objectMetadata);

			client.putObject(putObjectRequest);

			System.out.println("Arquivo enviado com sucesso");

		} catch (Exception e) {
			throw new RuntimeException("Problemas ao tentar enviar o arquivo para o S3", e);
		}

	}
	
	public InputStream get(String codigoConsultoria, String codigo) {
		
		S3Object object = client.getObject(new GetObjectRequest("sigo-consultancies", codigoConsultoria + "/" +  codigo + ".pdf"));
		
		return object.getObjectContent();
	}

	public void remove(String codigoConsultoria, String codigo) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest("sigo-consultancies", codigoConsultoria + "/" + codigo + ".pdf");
		client.deleteObject(deleteObjectRequest);
		
		System.out.println("Arquivo removido com sucesso");

	}
}
