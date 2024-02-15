package com.Elotech.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
	public String mensagem;
	public LocalDateTime dataHora;
	
	public ExceptionResponse(String mensagem) {
		super();
		this.mensagem = mensagem;
		this.dataHora = LocalDateTime.now();
	}	
	
}
