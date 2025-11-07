package br.com.fiap.byteshoponlineapp.api.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.fiap.byteshoponlineapp.service.exception.RegraDeNegocioException;
import br.com.fiap.byteshoponlineapp.service.exception.SolicitacaoNaoEncontradaException;
import br.com.fiap.byteshoponlineapp.service.exception.TransicaoStatusInvalidaException;

/**
 * Tratamento centralizado de exceções da API
 * Utiliza @RestControllerAdvice para capturar exceções globalmente
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata exceção quando solicitação não é encontrada
     * Status Code: 404 Not Found
     */
    @ExceptionHandler(SolicitacaoNaoEncontradaException.class)
    public ResponseEntity<ErroResposta> handleSolicitacaoNaoEncontrada(
            SolicitacaoNaoEncontradaException ex, 
            WebRequest request) {
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                "Recurso Não Encontrado",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    /**
     * Trata exceção de transição de status inválida
     * Status Code: 400 Bad Request
     */
    @ExceptionHandler(TransicaoStatusInvalidaException.class)
    public ResponseEntity<ErroResposta> handleTransicaoStatusInvalida(
            TransicaoStatusInvalidaException ex, 
            WebRequest request) {
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Transição de Status Inválida",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * Trata exceção de regra de negócio
     * Status Code: 400 Bad Request
     */
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResposta> handleRegraDeNegocio(
            RegraDeNegocioException ex, 
            WebRequest request) {
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Regra de Negócio",
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * Trata erros de validação de campos (@Valid)
     * Status Code: 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationErrors(
            MethodArgumentNotValidException ex, 
            WebRequest request) {
        
        List<ErroResposta.CampoErro> camposErro = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErroResposta.CampoErro(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .collect(Collectors.toList());
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                "Um ou mais campos contêm valores inválidos",
                request.getDescription(false).replace("uri=", "")
        );
        erro.setErros(camposErro);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * Trata erro quando o tipo do argumento não corresponde
     * Status Code: 400 Bad Request
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, 
            WebRequest request) {
        
        String mensagem = String.format(
                "O parâmetro '%s' possui valor inválido: '%s'", 
                ex.getName(), 
                ex.getValue()
        );
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Argumento Inválido",
                mensagem,
                request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * Trata erro quando o JSON da requisição está malformado
     * Status Code: 400 Bad Request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResposta> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, 
            WebRequest request) {
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Requisição Malformada",
                "O corpo da requisição está malformado ou contém valores inválidos",
                request.getDescription(false).replace("uri=", "")
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    /**
     * Trata exceções genéricas não capturadas
     * Status Code: 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(
            Exception ex, 
            WebRequest request) {
        
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.",
                request.getDescription(false).replace("uri=", "")
        );
        
        // Em produção, seria melhor logar a exceção completa
        ex.printStackTrace();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
