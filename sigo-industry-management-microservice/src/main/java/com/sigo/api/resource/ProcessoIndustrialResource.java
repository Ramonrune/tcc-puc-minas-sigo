package com.sigo.api.resource;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sigo.api.model.JsonWebToken;
import com.sigo.api.model.ProcessoIndustrial;
import com.sigo.api.repository.ProcessoIndustrialRepository;
import com.sigo.api.service.ProcessoIndustrialService;
import com.sigo.api.token.JwtTokenDecoder;

@RestController
@RequestMapping("/industry-management")
public class ProcessoIndustrialResource {

	@Autowired
	private ProcessoIndustrialRepository processoIndustrialRepository;

	@Autowired
	private ProcessoIndustrialService processoIndustrialService;

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody ProcessoIndustrial processoIndustrial,
			@RequestHeader(name = "Authorization") String token) {
		JsonWebToken decoded = JwtTokenDecoder.decode(token);

		if (!decoded.getAuthorities().contains("ROLE_COMMON_USER")
				&& !decoded.getAuthorities().contains("ROLE_ADMIN")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		ProcessoIndustrial save = processoIndustrialService.save(processoIndustrial);

		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping
	public ResponseEntity<?> findAll(@RequestParam("codigo_filial") Long codigoFilial,
			@RequestParam("data_inicio_planejamento") LocalDate dataInicioPlanejamento,
			@RequestParam("data_fim_planejamento") LocalDate dataFimPlanejamento) {

		if (dataInicioPlanejamento == null || dataFimPlanejamento == null || codigoFilial == null) {
			return ResponseEntity.badRequest().build();
		}

		List<ProcessoIndustrial> findByCodigoFilialAndPeriodoData = processoIndustrialRepository
				.findByCodigoFilialAndPeriodoData(codigoFilial, dataInicioPlanejamento, dataFimPlanejamento);

		return ResponseEntity.ok(findByCodigoFilialAndPeriodoData);
	}

	/*
	 * @GetMapping("/{codigo}") public ResponseEntity<?> findOne(@PathVariable Long
	 * codigo) { Norma norma = normaRepository.findOne(codigo);
	 * 
	 * return norma != null ? ResponseEntity.ok(norma) :
	 * ResponseEntity.notFound().build(); }
	 * 
	 * @GetMapping("/pdf/{codigo}") public ResponseEntity<byte[]>
	 * findPdf(@PathVariable Long codigo) throws IOException {
	 * 
	 * S3 s3 = new S3();
	 * 
	 * InputStream inputStream = s3.get(codigo.toString());
	 * 
	 * byte[] contents = StreamUtils.copyToByteArray(inputStream);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_PDF); // Here you have to set
	 * the actual filename of your pdf String filename = codigo.toString() + ".pdf";
	 * headers.setContentDispositionFormData(filename, filename);
	 * headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	 * ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers,
	 * HttpStatus.OK); return response;
	 * 
	 * }
	 * 
	 * @GetMapping public ResponseEntity<?> findAll() { List<Norma> findAll =
	 * normaRepository.findAll();
	 * 
	 * findAll = findAll.stream().map(e -> {
	 * 
	 * Norma norma = e;
	 * 
	 * norma.setDescricao("");
	 * 
	 * return norma; }).collect(Collectors.toList());
	 * 
	 * return ResponseEntity.ok(findAll); }
	 * 
	 * @GetMapping("/search/{page}") public ResponseEntity<?> search(@PathVariable
	 * Integer page, @RequestParam("size") Integer size,
	 * 
	 * @RequestParam("sortBy") String sortBy, @RequestParam("order") String order) {
	 * 
	 * if (order.isEmpty() || sortBy.isEmpty()) { return
	 * ResponseEntity.badRequest().build(); } if (size == null) { size = 5; }
	 * 
	 * Sort sortable = new Sort(order.equals("ASC") ? Direction.ASC :
	 * Direction.DESC, sortBy); PageRequest pageable = new
	 * PageRequest(page.intValue(), size.intValue(), sortable);
	 * 
	 * Page<Norma> findAll = normaRepository.findAll(pageable); PageNormaDTO
	 * normaDTO = new PageNormaDTO(findAll);
	 * 
	 * return ResponseEntity.ok(normaDTO); }
	 * 
	 * @GetMapping("/external") public ResponseEntity<?> findExternalStandards() {
	 * Norma norma = normaRepository.findOne(Long.valueOf(1));
	 * 
	 * NormaModificacaoDTO normaModificacaoDTO = new NormaModificacaoDTO();
	 * normaModificacaoDTO.setNorma(norma);
	 * normaModificacaoDTO.setTipoMudanca(TipoMudanca.NOVA_VERSAO);
	 * normaModificacaoDTO.setDescricao("Nova versão da norma publicada 1 dia atrás"
	 * );
	 * 
	 * List<NormaModificacaoDTO> list = new ArrayList<>();
	 * list.add(normaModificacaoDTO);
	 * 
	 * NormaModificacoesDTO normaModificacoesDTO = new NormaModificacoesDTO();
	 * normaModificacoesDTO.setList(list);
	 * 
	 * return !list.isEmpty() ? ResponseEntity.ok(normaModificacoesDTO) :
	 * ResponseEntity.notFound().build(); }
	 * 
	 * @DeleteMapping("/{codigo}") public ResponseEntity<?> remove(@PathVariable
	 * Long codigo, @RequestHeader(name = "Authorization") String token) {
	 * JsonWebToken decoded = JwtTokenDecoder.decode(token);
	 * 
	 * if (!decoded.getAuthorities().contains("ROLE_ADMIN")) { return
	 * ResponseEntity.status(HttpStatus.FORBIDDEN).build(); }
	 * 
	 * normaRepository.delete(codigo); S3 s3 = new S3();
	 * s3.remove(codigo.toString());
	 * 
	 * return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	 * 
	 * }
	 * 
	 * 
	 */
}
