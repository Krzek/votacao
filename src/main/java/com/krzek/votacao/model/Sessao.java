package com.krzek.votacao.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;

public class Sessao {

    @Id
    private String id;
    private LocalDateTime dataInicio;
    private Long minutosValidade;
    private Pauta pauta;

    public Sessao() {
    }

    public Sessao(String id, LocalDateTime dataInicio, Long minutosValidade, Pauta pauta) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.minutosValidade = minutosValidade;
        this.pauta = pauta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Long getMinutosValidade() {
        return minutosValidade;
    }

    public void setMinutosValidade(Long minutosValidade) {
        this.minutosValidade = minutosValidade;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessao sessao = (Sessao) o;
        return Objects.equals(id, sessao.id) &&
                Objects.equals(dataInicio, sessao.dataInicio) &&
                Objects.equals(minutosValidade, sessao.minutosValidade) &&
                Objects.equals(pauta, sessao.pauta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataInicio, minutosValidade, pauta);
    }

    @Override
    public String toString() {
        return "Sessao{" +
                "id='" + id + '\'' +
                ", dataInicio=" + dataInicio +
                ", validade=" + minutosValidade +
                ", pauta=" + pauta +
                '}';
    }
}
