package br.com.fiap.byteshoponlineapp.domain;

import java.time.LocalDateTime;

import br.com.fiap.byteshoponlineapp.domain.enums.Prioridade;
import br.com.fiap.byteshoponlineapp.domain.enums.StatusSolicitacao;

/**
 * Entidade de domínio que representa uma Solicitação de Suporte
 */
public class SolicitacaoSuporte {
    
    private Long id;
    private String titulo;
    private String descricao;
    private StatusSolicitacao status;
    private Prioridade prioridade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public SolicitacaoSuporte() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.status = StatusSolicitacao.ABERTA;
    }

    public SolicitacaoSuporte(Long id, String titulo, String descricao, Prioridade prioridade) {
        this();
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
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
        this.dataAtualizacao = LocalDateTime.now();
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

    public void atualizarDataModificacao() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
