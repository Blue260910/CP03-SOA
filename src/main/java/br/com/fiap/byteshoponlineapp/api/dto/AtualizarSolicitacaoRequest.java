package br.com.fiap.byteshoponlineapp.api.dto;

import br.com.fiap.byteshoponlineapp.domain.enums.Prioridade;
import jakarta.validation.constraints.Size;

/**
 * DTO para atualização parcial de uma solicitação de suporte
 */
public class AtualizarSolicitacaoRequest {

    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres")
    private String titulo;

    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    private String descricao;

    private Prioridade prioridade;

    public AtualizarSolicitacaoRequest() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }
}
