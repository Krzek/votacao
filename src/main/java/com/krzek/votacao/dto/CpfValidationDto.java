package com.krzek.votacao.dto;

import java.util.Objects;

public class CpfValidationDto {
    private String status;

    public CpfValidationDto() {
    }

    public CpfValidationDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CpfValidationDto that = (CpfValidationDto) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "CpfValidationDto{" +
                "status='" + status + '\'' +
                '}';
    }
}
