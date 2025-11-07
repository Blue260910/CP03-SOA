package br.com.fiap.byteshoponlineapp.api.dto;

import br.com.fiap.byteshoponlineapp.domain.enums.StatusSolicitacao;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para atualização de status de uma solicitação
 */
public class AtualizarStatusRequest {

    @NotNull(message = "O status é obrigatório")
    private StatusSolicitacao status;

    public AtualizarStatusRequest() {
    }

    public AtualizarStatusRequest(StatusSolicitacao status) {
        this.status = status;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }
}
