package com.Elotech.dto;

import java.math.BigDecimal;

public class TotalDebitosDTO {
	private BigDecimal valorTotal;



	public TotalDebitosDTO(BigDecimal valorTotal) {
		super();
		this.valorTotal = valorTotal;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
}
