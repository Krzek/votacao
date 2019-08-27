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
import com.krzek.votacao.service.exception.VotoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class VotoService {
    private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    @Value("${app.integracao.cpf.url}")
    private String urlCpfValidator;
    private VotoRepository votoRepository;
    private RestTemplate restTemplate;
    private Sender sender;
    private VotacaoService votacaoService;

    @Autowired
    public VotoService(VotoRepository votoRepository, RestTemplate restTemplate, Sender sender, VotacaoService votacaoService) {
        this.votoRepository = votoRepository;
        this.restTemplate = restTemplate;
        this.sender = sender;
        this.votacaoService = votacaoService;
    }

    public Voto verifyAndSave(Sessao sessao, Voto voto) {
        verifyVoto(sessao, voto);
        return votoRepository.save(voto);
    }

    protected void verifyVoto(Sessao sessao, Voto voto) {

        LocalDateTime dataLimite = sessao.getDataInicio().plusMinutes(sessao.getMinutosValidade());
        if (LocalDateTime.now().isAfter(dataLimite)) {
            sendMessage(voto.getPauta());
            throw new SessaoTimeOutException();
        }

        cpfAbleToVote(voto);
        votoAlreadyExists(voto);
    }

    protected void votoAlreadyExists(Voto voto) {
        if (votoRepository.findByCpfAndPautaId(voto.getCpf(),
                voto.getPauta().getId()).isPresent()) {
            throw new VotoAlreadyExistsException();
        }
    }

    private void sendMessage(Pauta pauta) {
        VotacaoDto votacaoPauta = votacaoService.buildVotacaoPauta(pauta.getId());
        sender.send(votacaoPauta);
    }

    protected void cpfAbleToVote(final Voto voto) {
        ResponseEntity<CpfValidationDto> cpfValidation = getCpfValidation(voto);
        if (HttpStatus.OK.equals(cpfValidation.getStatusCode())) {
            if (CPF_UNABLE_TO_VOTE.equalsIgnoreCase(Objects.requireNonNull(cpfValidation.getBody()).getStatus())) {
                throw new UnableCpfException();
            }
        } else {
            throw new InvalidCpfException();
        }
    }

    protected ResponseEntity<CpfValidationDto> getCpfValidation(final Voto voto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(urlCpfValidator.concat("/").concat(voto.getCpf()), HttpMethod.GET, entity,
                CpfValidationDto.class);
    }

    public List<Voto> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Voto voto) {
        votoRepository.findById(voto.getId()).orElseThrow(VotoNotFoundException::new);
        votoRepository.delete(voto);
    }

    public List<Voto> findVotosByPautaId(String pautaId) {
        return votoRepository.findByPautaId(pautaId).orElseThrow(VotoNotFoundException::new);
    }

    public void setUrlCpfValidator(String urlCpfValidator) {
        this.urlCpfValidator = urlCpfValidator;
    }
}
