package com.sigo.api.resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sigo.api.infra.S3;
import com.sigo.api.model.Consultoria;
import com.sigo.api.model.JsonWebToken;
import com.sigo.api.repository.ConsultoriaRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/consultancy")
public class NormaResource {

	@Autowired
	private ConsultoriaRepository consultoriaRepository;

	@PostMapping("/upload/{codigo}")
	public ResponseEntity<?> handleFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile,
			@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) throws IOException {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		S3 s3 = new S3();
		s3.save(uploadfile, codigo.toString());

		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Consultoria consultoria,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Long maxTransactionId = consultoriaRepository.getMaxTransactionId();
		maxTransactionId = maxTransactionId != null ? maxTransactionId : 0;
		consultoria.setCodigo(maxTransactionId + 1);
		consultoria.setDataRegistro(LocalDate.now());

		Consultoria consultoriaNew = consultoriaRepository.save(consultoria);

		return ResponseEntity.status(HttpStatus.CREATED).body(consultoriaNew);
	}

	

	@GetMapping("/pdf/{codigo}")
	public ResponseEntity<byte[]> findPdf(@PathVariable Long codigo) throws IOException {

		S3 s3 = new S3();

		InputStream inputStream = s3.get(codigo.toString());

		byte[] contents = StreamUtils.copyToByteArray(inputStream);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		// Here you have to set the actual filename of your pdf
		String filename = codigo.toString() + ".pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
		return response;

	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Consultoria> findAll = consultoriaRepository.findAll();

		return ResponseEntity.ok(findAll);
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		consultoriaRepository.delete(codigo);
		S3 s3 = new S3();
		s3.remove(codigo.toString());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

}
