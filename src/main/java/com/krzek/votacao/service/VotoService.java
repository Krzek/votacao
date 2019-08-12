package com.krzek.votacao.service;

import com.krzek.votacao.dto.CpfValidationDto;
import com.krzek.votacao.dto.VotacaoDto;
import com.krzek.votacao.model.Pauta;
import com.krzek.votacao.model.Sessao;
import com.krzek.votacao.model.Voto;
import com.krzek.votacao.mq.Sender;
import com.krzek.votacao.repository.VotoRepository;
import com.krzek.votacao.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        Optional<Voto> votoByCpfAndPauta = votoRepository.findByCpfAndPautaId(voto.getCpf(), voto.getPauta().getId());

        if (votoByCpfAndPauta.isPresent()) {
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
            if (CPF_UNABLE_TO_VOTE.equalsIgnoreCase(cpfValidation.getBody().getStatus())) {
                throw new UnableCpfException();
            }
        } else {
            throw new InvalidCpfException();
        }
    }

    protected ResponseEntity<CpfValidationDto> getCpfValidation(final Voto voto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange(urlCpfValidator.concat("/").concat(voto.getCpf()), HttpMethod.GET, entity,
                CpfValidationDto.class);
    }

    public List<Voto> findAll() {
        return votoRepository.findAll();
    }

    public void delete(Voto voto) {
        Optional<Voto> votoById = votoRepository.findById(voto.getId());
        if (!votoById.isPresent()) {
            throw new VotoNotFoundException();
        }
        votoRepository.delete(voto);
    }

    public List<Voto> findVotosByPautaId(String pautaId) {
        Optional<List<Voto>> findByPautaId = votoRepository.findByPautaId(pautaId);

        if (!findByPautaId.isPresent()) {
            throw new VotoNotFoundException();
        }

        return findByPautaId.get();
    }

    public void setUrlCpfValidator(String urlCpfValidator) {
        this.urlCpfValidator = urlCpfValidator;
    }
}
