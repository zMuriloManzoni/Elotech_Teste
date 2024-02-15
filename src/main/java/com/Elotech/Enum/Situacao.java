package com.Elotech.Enum;

public enum Situacao {
	ABERTO("A"),

	PAGO("P"),

	CANCELADO("C");

	private final String sigla;

	private Situacao(String sigla) {
		this.sigla = sigla;
	}

	public String getSigla() {
		return sigla;
	}		
}
