package com.Elotech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.Elotech.Enum.Situacao;
import com.Elotech.entity.Parcela;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;

public class ParcelaDTO {
	private Integer numero;
	@Future(message = "A data de vencimento deve ser maior que a data atual")
	private LocalDate dataVencimento;
	@DecimalMin(value = "0", inclusive = false, message = "O valor deve ser maior que 0")
	private BigDecimal valor;

	private Situacao situacao;
	public ParcelaDTO() {
		
	}
	
	public ParcelaDTO(Parcela parcela) {
		this.numero = parcela.getNumero();
		this.dataVencimento = parcela.getDataVencimento();
		this.valor = parcela.getValor();
		this.situacao = parcela.getSituacao();
	}
	public Integer getNumero() {
		return numero;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
}
