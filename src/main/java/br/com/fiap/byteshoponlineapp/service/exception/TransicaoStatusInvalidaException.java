package br.com.fiap.byteshoponlineapp.service.exception;

/**
 * Exceção lançada quando há uma transição de status inválida
 */
public class TransicaoStatusInvalidaException extends RuntimeException {
    
    public TransicaoStatusInvalidaException(String statusAtual, String novoStatus) {
        super(String.format("Transição inválida: não é possível mudar de %s para %s", statusAtual, novoStatus));
    }

    public TransicaoStatusInvalidaException(String mensagem) {
        super(mensagem);
    }
}
