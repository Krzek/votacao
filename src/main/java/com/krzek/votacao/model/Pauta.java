package com.krzek.votacao.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;
import javax.validation.constraints.NotBlank;

public class Pauta {

    @Id
    private String id;
    @NotBlank(message = "pauta-1")
    private String nome;


    public Pauta() {
    }

    public Pauta(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pauta pauta = (Pauta) o;
        return Objects.equals(id, pauta.id) &&
                Objects.equals(nome, pauta.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "Pauta{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}