package com.Elotech.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Elotech.entity.Debito;

import jakarta.validation.constraints.PastOrPresent;

public class DebitoDTO {
	private Long id;
	@PastOrPresent
	private LocalDate dataLancamento;
	private String cpf;
	private String nome;
	private List<ParcelaDTO> parcelas;

	public DebitoDTO(){
		
	}
	
	public DebitoDTO(Debito debito) {
		this.id = debito.getId();
		this.dataLancamento = debito.getDataLancamento();
		this.cpf = debito.getCpf();
		this.nome = debito.getNome();

		this.parcelas = new ArrayList<>();
		if (debito.getParcelas() != null && !debito.getParcelas().isEmpty()) {
			debito.getParcelas().forEach(parcela -> {
				this.parcelas.add(new ParcelaDTO(parcela));
			});
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ParcelaDTO> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<ParcelaDTO> parcelas) {
		this.parcelas = parcelas;
	}


}
