package com.Elotech.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.Elotech.Enum.Situacao;
import com.Elotech.dto.ParcelaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Parcela {
	@Id
	private Integer numero;
	private LocalDate dataVencimento;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Situacao situacao = Situacao.ABERTO;		
	@ManyToOne
	@Id
	private Debito debito;
	
	public Parcela() {
		
	}
	
	public Parcela(ParcelaDTO parcela, Debito debito){
		this.numero = parcela.getNumero();
		this.dataVencimento = parcela.getDataVencimento();
		this.valor = parcela.getValor();
		this.debito = debito; 
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

	public Debito getDebito() {
		return debito;
	}

	public void setDebito(Debito debito) {
		this.debito = debito;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	@PrePersist
    public void prePersist() { 
		  if (this.situacao == null) {
			  this.situacao = Situacao.ABERTO;  
		  }
	}	
	
}

