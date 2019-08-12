package com.krzek.votacao.dto;

import com.krzek.votacao.model.Pauta;

import java.util.Objects;

public class VotacaoDto {

    private Pauta pauta;
    private Integer totalSim;
    private Integer totalNao;
    private Integer totalVotos;
    private Integer totalSessoes;

    public VotacaoDto() {
    }

    public VotacaoDto(Pauta pauta, Integer totalSim, Integer totalNao, Integer totalVotos, Integer totalSessoes) {
        this.pauta = pauta;
        this.totalSim = totalSim;
        this.totalNao = totalNao;
        this.totalVotos = totalVotos;
        this.totalSessoes = totalSessoes;
    }

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public Integer getTotalSim() {
        return totalSim;
    }

    public void setTotalSim(Integer totalSim) {
        this.totalSim = totalSim;
    }

    public Integer getTotalNao() {
        return totalNao;
    }

    public void setTotalNao(Integer totalNao) {
        this.totalNao = totalNao;
    }

    public Integer getTotalVotos() {
        return totalVotos;
    }

    public void setTotalVotos(Integer totalVotos) {
        this.totalVotos = totalVotos;
    }

    public Integer getTotalSessoes() {
        return totalSessoes;
    }

    public void setTotalSessoes(Integer totalSessoes) {
        this.totalSessoes = totalSessoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotacaoDto that = (VotacaoDto) o;
        return Objects.equals(pauta, that.pauta) &&
                Objects.equals(totalSim, that.totalSim) &&
                Objects.equals(totalNao, that.totalNao) &&
                Objects.equals(totalVotos, that.totalVotos) &&
                Objects.equals(totalSessoes, that.totalSessoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pauta, totalSim, totalNao, totalVotos, totalSessoes);
    }

    @Override
    public String toString() {
        return "VotacaoDto{" +
                "pauta=" + pauta +
                ", totalSim=" + totalSim +
                ", totalNao=" + totalNao +
                ", totalVotos=" + totalVotos +
                ", totalSessoes=" + totalSessoes +
                '}';
    }
}
