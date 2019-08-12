package com.krzek.votacao.repository;

import com.krzek.votacao.model.Voto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VotoRepository extends MongoRepository<Voto, String> {
    Optional<Voto> findByCpfAndPautaId(String cpf, String pautaId);
    Optional<List<Voto>> findByPautaId(String pautaId);
    Optional<Voto> findByCpf(String cpf);
}
