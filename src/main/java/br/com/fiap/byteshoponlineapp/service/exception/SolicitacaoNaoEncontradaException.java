package br.com.fiap.byteshoponlineapp.service.exception;

/**
 * Exceção lançada quando uma solicitação de suporte não é encontrada
 */
public class SolicitacaoNaoEncontradaException extends RuntimeException {
    
    public SolicitacaoNaoEncontradaException(Long id) {
        super("Solicitação de suporte não encontrada com ID: " + id);
    }

    public SolicitacaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
