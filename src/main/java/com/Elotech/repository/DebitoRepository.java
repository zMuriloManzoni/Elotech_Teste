package com.Elotech.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Elotech.dto.DebitoDTO;
import com.Elotech.dto.TotalDebitosDTO;
import com.Elotech.entity.Debito;


public interface DebitoRepository extends JpaRepository<Debito, Long>{
	@Query("""
			Select new com.Elotech.dto.TotalDebitosDTO(Sum(p.valor))  
			from Debito as d
			left join Parcela as p on p.debito = d
			""")
	TotalDebitosDTO getValorTotal();
	
	@Query("""
			Select new com.Elotech.dto.DebitoDTO(d) from Debito as d
			where (d.dataLancamento = :dataLancamento or :dataLancamento is null)
			and (d.cpf = :cpf or :cpf is null)
			and (d.nome = :nome or :nome is null) 			
			""")
	Page<DebitoDTO> findByDataLancamentoAndCpfAndNome(LocalDate dataLancamento, String cpf, String nome, Pageable pageable);
}
