package com.krzek.votacao.repository;

import com.krzek.votacao.model.Sessao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface SessaoRepository extends MongoRepository<Sessao, String> {
    Optional<Sessao> findByIdAndPautaId(String id, String pauta);
    Long countByPautaId(String id);
}
