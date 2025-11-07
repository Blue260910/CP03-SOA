package br.com.fiap.byteshoponlineapp.api.dto;

import java.time.LocalDateTime;

import br.com.fiap.byteshoponlineapp.domain.SolicitacaoSuporte;
import br.com.fiap.byteshoponlineapp.domain.enums.Prioridade;
import br.com.fiap.byteshoponlineapp.domain.enums.StatusSolicitacao;

/**
 * DTO de resposta para solicitação de suporte
 */
public class SolicitacaoSuporteResponse {

    private Long id;
    private String titulo;
    private String descricao;
    private StatusSolicitacao status;
    private Prioridade prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public SolicitacaoSuporteResponse() {
    }

    public SolicitacaoSuporteResponse(SolicitacaoSuporte solicitacao) {
        this.id = solicitacao.getId();
        this.titulo = solicitacao.getTitulo();
        this.descricao = solicitacao.getDescricao();
        this.status = solicitacao.getStatus();
        this.prioridade = solicitacao.getPrioridade();
        this.dataCriacao = solicitacao.getDataCriacao();
        this.dataAtualizacao = solicitacao.getDataAtualizacao();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
