package com.sigo.api.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;
import com.sigo.api.dto.NormaModificacaoDTO;
import com.sigo.api.dto.NormaModificacoesDTO;
import com.sigo.api.dto.PageNormaDTO;
import com.sigo.api.dto.TipoMudanca;
import com.sigo.api.infra.S3;
import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.Norma;
import com.sigo.api.repository.NormaRepository;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/standards")
public class NormaResource {

	@Autowired
	private NormaRepository normaRepository;

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
	public ResponseEntity<?> save(@Valid @RequestBody Norma norma,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		Long maxTransactionId = normaRepository.getMaxTransactionId();
		norma.setCodigo(maxTransactionId + 1);

		Norma novaNorma = normaRepository.save(norma);

		return ResponseEntity.status(HttpStatus.CREATED).body(novaNorma);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> findOne(@PathVariable Long codigo) {
		Norma norma = normaRepository.findOne(codigo);

		return norma != null ? ResponseEntity.ok(norma) : ResponseEntity.notFound().build();
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
		List<Norma> findAll = normaRepository.findAll();

		findAll = findAll.stream().map(e -> {

			Norma norma = e;

			norma.setDescricao("");

			return norma;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(findAll);
	}

	@GetMapping("/search/{page}")
	public ResponseEntity<?> search(@PathVariable Integer page, @RequestParam("size") Integer size,
			@RequestParam("sortBy") String sortBy, @RequestParam("order") String order) {

		if (order.isEmpty() || sortBy.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		if (size == null) {
			size = 5;
		}

		Sort sortable = new Sort(order.equals("ASC") ? Direction.ASC : Direction.DESC, sortBy);
		PageRequest pageable = new PageRequest(page.intValue(), size.intValue(), sortable);

		Page<Norma> findAll = normaRepository.findAll(pageable);
		PageNormaDTO normaDTO = new PageNormaDTO(findAll);

		return ResponseEntity.ok(normaDTO);
	}

	@GetMapping("/external")
	public ResponseEntity<?> findExternalStandards() {
		Norma norma = normaRepository.findOne(Long.valueOf(1));

		NormaModificacaoDTO normaModificacaoDTO = new NormaModificacaoDTO();
		normaModificacaoDTO.setNorma(norma);
		normaModificacaoDTO.setTipoMudanca(TipoMudanca.NOVA_VERSAO);
		normaModificacaoDTO.setDescricao("Nova versão da norma publicada 1 dia atrás");

		List<NormaModificacaoDTO> list = new ArrayList<>();
		list.add(normaModificacaoDTO);

		NormaModificacoesDTO normaModificacoesDTO = new NormaModificacoesDTO();
		normaModificacoesDTO.setList(list);

		return !list.isEmpty() ? ResponseEntity.ok(normaModificacoesDTO) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> remove(@PathVariable Long codigo, @RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		normaRepository.delete(codigo);
		S3 s3 = new S3();
		s3.remove(codigo.toString());

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	/*
	 * private String saveUploadedFiles(final MultipartFile file) throws IOException
	 * { final byte[] bytes = file.getBytes(); final Path path =
	 * Paths.get("C:\\Users\\Computador\\Desktop\\sigo-app\\" +
	 * file.getOriginalFilename()); Files.write(path, bytes); return ""; }
	 */

}
