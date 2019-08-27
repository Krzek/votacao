package com.krzek.votacao.service;

import com.krzek.votacao.dto.VotacaoDto;
import com.krzek.votacao.model.Pauta;
import com.krzek.votacao.model.Voto;
import com.krzek.votacao.repository.SessaoRepository;
import com.krzek.votacao.repository.VotoRepository;
import com.krzek.votacao.service.exception.BusinessException;
import com.krzek.votacao.service.exception.VotacaoNotFoundException;
import com.krzek.votacao.service.exception.VotoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotacaoService {
    private VotoRepository votoRepository;
    private SessaoRepository sessaoRepository;


    @Autowired
    public VotacaoService(VotoRepository votoRepository, SessaoRepository sessaoRepository) {
        this.votoRepository = votoRepository;
        this.sessaoRepository = sessaoRepository;
    }

    public Voto save(final Voto voto) {
        verifyIfExists(voto);
        return votoRepository.save(voto);
    }

    private void verifyIfExists(final Voto voto) {
        Optional<Voto> votoByCpfAndPauta = votoRepository.findByCpf(voto.getCpf());

        if (votoByCpfAndPauta.isPresent() && (voto.isNew() || isUpdatingToADifferent(voto, votoByCpfAndPauta))) {
            throw new BusinessException(null, null);
        }
    }

    private boolean isUpdatingToADifferent(Voto voto, Optional<Voto> votoByCpfAndPauta) {
        return !voto.isNew() && !votoByCpfAndPauta.get().equals(voto);
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

    public VotacaoDto buildVotacaoPauta(String id) {
        Optional<List<Voto>> votosByPauta = votoRepository.findByPautaId(id);
        if (!votosByPauta.isPresent() || votosByPauta.get().isEmpty()) {
            throw new VotacaoNotFoundException();
        }

        Pauta pauta = votosByPauta.get().iterator().next().getPauta();

        Long totalSessoes = sessaoRepository.countByPautaId(pauta.getId());


        Integer total = votosByPauta.get().size();

        Integer totalSim = (int) votosByPauta.get().stream().filter(voto -> Boolean.TRUE.equals(voto.getEscolha()))
                .count();

        Integer totalNao = total - totalSim;

        VotacaoDto votacaoDto = new VotacaoDto();
        votacaoDto.setPauta(pauta);
        votacaoDto.setTotalSessoes(totalSessoes.intValue());
        votacaoDto.setTotalVotos(total);
        votacaoDto.setTotalSim(totalSim);
        votacaoDto.setTotalNao(totalNao);

        return votacaoDto;

    }
}
