package com.krzek.votacao.service;

import com.krzek.votacao.dto.CpfValidationDto;
import com.krzek.votacao.dto.VotacaoDto;
import com.krzek.votacao.model.Pauta;
import com.krzek.votacao.model.Sessao;
import com.krzek.votacao.model.Voto;
import com.krzek.votacao.mq.Sender;
import com.krzek.votacao.repository.VotoRepository;
import com.krzek.votacao.service.exception.InvalidCpfException;
import com.krzek.votacao.service.exception.SessaoTimeOutException;
import com.krzek.votacao.service.exception.UnableCpfException;
import com.krzek.votacao.service.exception.VotoAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class VotoServiceTest {

	private final static String PAUTA_ID = "5d50c7f5639c352ca80a21ec";

	private VotoService votoService;
	@Mock
	private VotoRepository votoRepository;
	@Mock
	private RestTemplate restTemplate;
	@Mock
	private Sender sender;
	@Mock
	private VotacaoService votacaoService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		votoService = new VotoService(votoRepository, restTemplate, sender, votacaoService);
		votoService.setUrlCpfValidator("");
	}

	@Test(expected = SessaoTimeOutException.class)
	public void verifyVotoTest() {
		Sessao sessao = new Sessao();
		sessao.setDataInicio(LocalDateTime.now());
		sessao.setMinutosValidade(-1l);

		Voto voto = new Voto();
		Pauta pauta = new Pauta();
		pauta.setId(PAUTA_ID);
		voto.setPauta(pauta);

		when(votacaoService.buildVotacaoPauta(anyString())).thenReturn(new VotacaoDto());

		votoService.verifyVoto(sessao, voto);
	}

	@Test(expected = InvalidCpfException.class)
	public void cpfAbleToVoteTest() {
		Voto voto = new Voto();
		voto.setCpf("1234");

		CpfValidationDto cpf = new CpfValidationDto();
		cpf.setStatus("TESTE");

		ResponseEntity<CpfValidationDto> response = new ResponseEntity<CpfValidationDto>(cpf, HttpStatus.NOT_FOUND);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
				.thenReturn(response);

		votoService.cpfAbleToVote(voto);
	}

	@Test(expected = UnableCpfException.class)
	public void cpfAbleToVote2Test() {
		Voto voto = new Voto();
		voto.setCpf("1234");

		CpfValidationDto cpf = new CpfValidationDto();
		cpf.setStatus("UNABLE_TO_VOTE");

		ResponseEntity<CpfValidationDto> response = new ResponseEntity<CpfValidationDto>(cpf, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
				.thenReturn(response);

		votoService.cpfAbleToVote(voto);
	}

	@Test
	public void cpfAbleToVote3Test() {
		Voto voto = new Voto();
		voto.setCpf("1234");

		CpfValidationDto cpf = new CpfValidationDto();
		cpf.setStatus("ABLE_TO_VOTE");

		ResponseEntity<CpfValidationDto> response = new ResponseEntity<CpfValidationDto>(cpf, HttpStatus.OK);

		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(CpfValidationDto.class)))
				.thenReturn(response);

		votoService.cpfAbleToVote(voto);
	}

	@Test(expected = VotoAlreadyExistsException.class)
	public void votoAlreadyExistsTest() {
		Voto voto = new Voto();
		voto.setCpf("1234");
		Pauta pauta = new Pauta();
		pauta.setId(PAUTA_ID);
		voto.setPauta(pauta );
		when(votoRepository.findByCpfAndPautaId(anyString(), anyString())).thenReturn(Optional.of(new Voto()));
		votoService.votoAlreadyExists(voto);
	}
	
	@Test
	public void votoAlreadyExistssTest() {
		Voto voto = new Voto();
		voto.setCpf("1234");
		Pauta pauta = new Pauta();
		pauta.setId(PAUTA_ID);
		voto.setPauta(pauta );
		
		when(votoRepository.findByCpfAndPautaId(anyString(), anyString())).thenReturn(Optional.ofNullable(null));
		votoService.votoAlreadyExists(voto);
	}

}
