package com.Elotech.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Elotech.dto.DebitoDTO;
import com.Elotech.dto.ParcelaDTO;
import com.Elotech.dto.TotalDebitosDTO;
import com.Elotech.facade.DebitoFacade;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/debitos")
public class DebitoController {
	
	@Autowired
	private DebitoFacade facade;

	@GetMapping("/{id}")
	public DebitoDTO findById(@PathVariable Long id){
		return facade.findById(id);
	}
	
	@GetMapping
	public Page<DebitoDTO> findAll(@RequestParam(required = false) LocalDate dataLancamento,@RequestParam(required = false) String cpf,@RequestParam(required = false) String nome, Pageable pageable){
		return facade.findAll(dataLancamento, cpf, nome, pageable);
	}	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		facade.delete(id);
	}
	
	@PostMapping
	public DebitoDTO create(@RequestBody @Valid DebitoDTO debito) {
		return facade.create(debito);
	}
	
	@GetMapping("/valortotal")
	public TotalDebitosDTO getValorTotal() {
		return facade.getValorTotal();
	}
	
	@PostMapping("/{id}/novaparcela") 
	public DebitoDTO postNovaParcela(@PathVariable Long id, @RequestBody @Valid ParcelaDTO parcela) {	
		return facade.adicionarParcela(id, parcela);
	}
	
	@PutMapping("/{id}/{numeroParcela}/vencimento")
	public DebitoDTO alteraVencimento(@PathVariable Long id, @PathVariable Integer numeroParcela, @RequestBody LocalDate dataVencimento) {
		return facade.alterarVencimento(id, numeroParcela, dataVencimento);
	}		
	
    @PutMapping("/{id}/{numeroParcela}/pagarparcela")
	public DebitoDTO pagarParcela(@PathVariable Long id, @PathVariable Integer numeroParcela) {
    	return facade.pagarParcela(id, numeroParcela);
    }
    
    @PutMapping("/{id}/{numeroParcela}/cancelarparcela")
	public DebitoDTO cancelarParcela(@PathVariable Long id, @PathVariable Integer numeroParcela) {
    	return facade.cancelarParcela(id, numeroParcela);
    }    
}
