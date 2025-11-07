package br.com.fiap.byteshoponlineapp.domain.enums;

/**
 * Enum que representa os níveis de prioridade de uma solicitação de suporte
 */
public enum Prioridade {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    CRITICA("Crítica");

    private final String descricao;

    Prioridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
