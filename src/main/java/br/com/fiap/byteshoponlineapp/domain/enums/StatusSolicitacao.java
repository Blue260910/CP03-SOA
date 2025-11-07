package br.com.fiap.byteshoponlineapp.domain.enums;

/**
 * Enum que representa os possíveis status de uma solicitação de suporte
 */
public enum StatusSolicitacao {
    ABERTA("Aberta"),
    EM_ANDAMENTO("Em Andamento"),
    RESOLVIDA("Resolvida"),
    FECHADA("Fechada");

    private final String descricao;

    StatusSolicitacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
