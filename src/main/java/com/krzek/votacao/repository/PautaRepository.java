package com.krzek.votacao.repository;

import com.krzek.votacao.model.Pauta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PautaRepository extends MongoRepository<Pauta, String> {
}
