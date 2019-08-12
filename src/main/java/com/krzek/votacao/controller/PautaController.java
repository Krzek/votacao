package com.krzek.votacao.controller;

import com.krzek.votacao.dto.VotacaoDto;
import com.krzek.votacao.model.Pauta;
import com.krzek.votacao.model.Sessao;
import com.krzek.votacao.model.Voto;
import com.krzek.votacao.service.PautaService;
import com.krzek.votacao.service.SessaoService;
import com.krzek.votacao.service.VotacaoService;
import com.krzek.votacao.service.VotoService;
import com.krzek.votacao.service.exception.InvalidSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PautaController {

    @Autowired
    private PautaService pautaService;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private VotoService votoService;

    @Autowired
    private VotacaoService service;

    @GetMapping("v1/pautas")
    public List<Pauta> all() {
        return pautaService.findAll();
    }

    @PostMapping("v1/pautas")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pauta create(@Valid @RequestBody Pauta pauta) {
        return pautaService.save(pauta);
    }

    @GetMapping("v1/pautas/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Pauta findById(@PathVariable String id) {
        return pautaService.findById(id);
    }

    @PostMapping("v1/pautas/{id}/sessoes")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Sessao createSession(@PathVariable String id, @Valid @RequestBody Sessao sessao) {
        Pauta pauta = new Pauta();
        pauta.setId(id);
        sessao.setPauta(pauta);
        return sessaoService.save(sessao);
    }

    @GetMapping("v1/pautas/sessoes/{idSessao}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sessao findSessaoById(@PathVariable String id) {
        return sessaoService.findById(id);
    }

    @GetMapping("v1/pautas/{id}/sessoes/{idSessao}")
    @ResponseStatus(code = HttpStatus.OK)
    public Sessao findSessaoById(@PathVariable String id, @PathVariable String idSessao) {
        return sessaoService.findByIdAndPautaId(idSessao, id);
    }

    @PostMapping("v1/pautas/{id}/sessoes/{idSessao}/votos")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Voto createVoto(@PathVariable String id, @PathVariable String idSessao, @RequestBody Voto voto) {
        Sessao sessao = sessaoService.findByIdAndPautaId(idSessao, id);
        if (!id.equals(sessao.getPauta().getId())) {
            throw new InvalidSessionException();
        }
        voto.setPauta(sessao.getPauta());
        return votoService.verifyAndSave(sessao, voto);
    }

    @GetMapping("v1/pautas/{id}/votacao")
    @ResponseStatus(code = HttpStatus.OK)
    public VotacaoDto findVotosByPautaId(@PathVariable String id) {
        return service.buildVotacaoPauta(id);
    }
}
