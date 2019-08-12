package com.krzek.votacao.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Voto {

    @Id
    private String id;
    private String cpf;
    private Pauta pauta;
    private Boolean escolha = false;

    public Voto() {
    }

    public Voto(String id, String cpf, Pauta pauta, Boolean escolha) {
        this.id = id;
        this.cpf = cpf;
        this.pauta = pauta;
        this.escolha = escolha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Boolean getEscolha() {
        return escolha;
    }

    public void setEscolha(Boolean escolha) {
        this.escolha = escolha;
    }

    public boolean isNew() {
        return this.getId() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voto voto = (Voto) o;
        return Objects.equals(id, voto.id) &&
                Objects.equals(cpf, voto.cpf) &&
                Objects.equals(pauta, voto.pauta) &&
                Objects.equals(escolha, voto.escolha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf, pauta, escolha);
    }

    @Override
    public String toString() {
        return "Voto{" +
                "id='" + id + '\'' +
                ", cpf='" + cpf + '\'' +
                ", pauta=" + pauta +
                ", escolha=" + escolha +
                '}';
    }
}