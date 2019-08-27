package com.krzek.votacao.service;

import com.krzek.votacao.model.Pauta;
import com.krzek.votacao.repository.PautaRepository;
import com.krzek.votacao.service.exception.PautaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PautaService {

    private PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    public List<Pauta> findAll() {
        return pautaRepository.findAll();
    }

    public Pauta findById(String id) {
        return pautaRepository.findById(id).orElseThrow(PautaNotFoundException::new);
    }

    public Pauta save(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public void delete(Pauta pauta) {
        pautaRepository.findById(pauta.getId()).orElseThrow(PautaNotFoundException::new);
        pautaRepository.delete(pauta);
    }
}
