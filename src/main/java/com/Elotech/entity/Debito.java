package com.Elotech.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Elotech.dto.DebitoDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Debito {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	private LocalDate dataLancamento;
	private String cpf;
	private String nome;
	@OneToMany(mappedBy = "debito", cascade = CascadeType.ALL )
	private List<Parcela> parcelas;

	public Debito() {
		
	}
	
	public Debito(DebitoDTO debito){
		this.dataLancamento = debito.getDataLancamento();
		this.cpf = debito.getCpf();
		this.nome = debito.getNome();

		this.parcelas = new ArrayList<>();
		if (debito.getParcelas() != null && !debito.getParcelas().isEmpty()) {
			debito.getParcelas().forEach(parcela -> {
				this.parcelas.add(new Parcela(parcela, this));
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

	public List<Parcela> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}

    public void adicionarParcela(Parcela novaParcela) {
        if (parcelas == null) {
            parcelas = new ArrayList<>();
        }
        parcelas.add(novaParcela);
    }
	
	
}
