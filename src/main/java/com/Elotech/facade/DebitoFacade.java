package com.Elotech.facade;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Elotech.Enum.Situacao;
import com.Elotech.dto.DebitoDTO;
import com.Elotech.dto.ParcelaDTO;
import com.Elotech.dto.TotalDebitosDTO;
import com.Elotech.entity.Debito;
import com.Elotech.entity.Parcela;
import com.Elotech.exception.DebitoException;
import com.Elotech.repository.DebitoRepository;

@Service
public class DebitoFacade {

	@Autowired
	private DebitoRepository repository;

	public DebitoDTO findById(Long id) {
		Optional<Debito> debito = repository.findById(id);
		if (debito.isPresent()) {
			return new DebitoDTO(debito.get());
		}
		throw new DebitoException("Não existe Debito para esse ID");
	}

	public Page<DebitoDTO> findAll(LocalDate dataLancamento, String cpf, String nome, Pageable pageable) {
		return repository.findByDataLancamentoAndCpfAndNome(dataLancamento, cpf, nome, pageable);				
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public DebitoDTO create(DebitoDTO debito) {
		validaDebito(debito);
		Debito debitoEntity = new Debito(debito);
		debitoEntity = repository.save(debitoEntity);
		debito.setId(debitoEntity.getId());
		debito.getParcelas().forEach(parcela -> {
			parcela.setSituacao(Situacao.ABERTO);
		});
		return debito;
	}

	public TotalDebitosDTO getValorTotal() {
		return repository.getValorTotal();
	}

	public void validaDebito(DebitoDTO debito) {
		if (debito.getParcelas() == null || debito.getParcelas().isEmpty()) {
			throw new DebitoException("É necessario que o debito tenha ao menos uma parcela");
		}
	}

	public DebitoDTO adicionarParcela(Long id, ParcelaDTO parcela) {
		Optional<Debito> optionalDebito = repository.findById(id);

		if (optionalDebito.isPresent()) {
			Debito debitoExistente = optionalDebito.get();

			Parcela novaParcela = new Parcela(parcela, debitoExistente);

			novaParcela.setDebito(debitoExistente);

			debitoExistente.adicionarParcela(novaParcela);

			repository.save(debitoExistente);

			return new DebitoDTO(debitoExistente);
		} else {
			throw new DebitoException("Não existe débito para esse ID");
		}
	}

	public DebitoDTO alterarVencimento(Long idDebito, Integer numeroParcela, LocalDate novaDataVencimento) {
		Optional<Debito> optionalDebito = repository.findById(idDebito);

		if (optionalDebito.isPresent()) {
			Debito debitoExistente = optionalDebito.get();

			Parcela parcelaParaAtualizar = debitoExistente.getParcelas().stream()
					.filter(parcela -> parcela.getNumero().equals(numeroParcela)).findFirst()
					.orElseThrow(() -> new DebitoException("Não existe parcela com esse número no débito"));

			parcelaParaAtualizar.setDataVencimento(novaDataVencimento);

			repository.save(debitoExistente);

			return new DebitoDTO(debitoExistente);
		} else {
			throw new DebitoException("Não existe débito para esse ID");
		}
	}

	public DebitoDTO pagarParcela(Long idDebito, Integer numeroParcela) {
		Optional<Debito> optionalDebito = repository.findById(idDebito);

		if (optionalDebito.isPresent()) {
			Debito debitoExistente = optionalDebito.get();

			Parcela parcelaParaPagar = debitoExistente.getParcelas().stream()
					.filter(parcela -> parcela.getNumero().equals(numeroParcela)).findFirst()
					.orElseThrow(() -> new DebitoException("Não existe parcela com esse número no débito"));

			if (Situacao.ABERTO.equals(parcelaParaPagar.getSituacao())) {

				parcelaParaPagar.setSituacao(Situacao.PAGO);

				repository.save(debitoExistente);

				return new DebitoDTO(debitoExistente);
			} else if (Situacao.CANCELADO.equals(parcelaParaPagar.getSituacao())) {
				throw new DebitoException("Não é possível pagar uma parcela cancelada");
			} else if (Situacao.PAGO.equals(parcelaParaPagar.getSituacao())) {
				throw new DebitoException("Parcela já está paga");
			} else {
				throw new DebitoException("Situação inválida da parcela");
			}
		} else {
			throw new DebitoException("Não existe débito para esse ID");
		}
	}
	
	public DebitoDTO cancelarParcela(Long idDebito, Integer numeroParcela) {
		Optional<Debito> optionalDebito = repository.findById(idDebito);

		if (optionalDebito.isPresent()) {
			Debito debitoExistente = optionalDebito.get();

			Parcela parcelaParaPagar = debitoExistente.getParcelas().stream()
					.filter(parcela -> parcela.getNumero().equals(numeroParcela)).findFirst()
					.orElseThrow(() -> new DebitoException("Não existe parcela com esse número no débito"));

			if (Situacao.ABERTO.equals(parcelaParaPagar.getSituacao())) {

				parcelaParaPagar.setSituacao(Situacao.CANCELADO);

				repository.save(debitoExistente);

				return new DebitoDTO(debitoExistente);
			} else if (Situacao.PAGO.equals(parcelaParaPagar.getSituacao())) {
				throw new DebitoException("Não é possivel cancelar uma parcela paga");
			} else {
				throw new DebitoException("Situação inválida da parcela");
			}
		} else {
			throw new DebitoException("Não existe débito para esse ID");
		}
	}
}
