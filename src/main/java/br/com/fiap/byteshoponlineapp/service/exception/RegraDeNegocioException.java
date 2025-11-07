package br.com.fiap.byteshoponlineapp.service.exception;

/**
 * Exceção genérica para erros de negócio
 */
public class RegraDeNegocioException extends RuntimeException {
    
    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }

    public RegraDeNegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
