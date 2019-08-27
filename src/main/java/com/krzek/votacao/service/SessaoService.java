package com.krzek.votacao.service;

import com.krzek.votacao.model.Sessao;
import com.krzek.votacao.repository.SessaoRepository;
import com.krzek.votacao.service.exception.SessaoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessaoService {
    private SessaoRepository sessaoRepository;
    private static final Long MINUTOS_DEFAULT = 1L;

    @Autowired
    public SessaoService(SessaoRepository sessaoRepository) {
        this.sessaoRepository = sessaoRepository;
    }

    public List<Sessao> findAll() {
        return sessaoRepository.findAll();
    }

    public Sessao findById(String id) {
        return sessaoRepository.findById(id).orElseThrow(SessaoNotFoundException::new);
    }

    public Sessao save(Sessao sessao) {
        if (sessao.getDataInicio() == null) {
            sessao.setDataInicio(LocalDateTime.now());
        }

        if (sessao.getMinutosValidade() == null) {
            sessao.setMinutosValidade(MINUTOS_DEFAULT);
        }

        return sessaoRepository.save(sessao);
    }

    public void delete(Sessao sessao) {
        sessaoRepository.findById(sessao.getId()).orElseThrow(SessaoNotFoundException::new);
        sessaoRepository.delete(sessao);
    }

    public Sessao findByIdAndPautaId(String idSessao, String idPauta) {
        return sessaoRepository.findByIdAndPautaId(idSessao, idPauta).orElseThrow(SessaoNotFoundException::new);
    }
}
