package br.com.fiap.byteshoponlineapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.byteshoponlineapp.api.dto.AtualizarSolicitacaoRequest;
import br.com.fiap.byteshoponlineapp.api.dto.SolicitacaoSuporteRequest;
import br.com.fiap.byteshoponlineapp.domain.SolicitacaoSuporte;
import br.com.fiap.byteshoponlineapp.domain.enums.StatusSolicitacao;
import br.com.fiap.byteshoponlineapp.domain.repository.SolicitacaoSuporteRepository;
import br.com.fiap.byteshoponlineapp.service.exception.RegraDeNegocioException;
import br.com.fiap.byteshoponlineapp.service.exception.SolicitacaoNaoEncontradaException;
import br.com.fiap.byteshoponlineapp.service.exception.TransicaoStatusInvalidaException;

/**
 * Serviço responsável pela lógica de negócio das Solicitações de Suporte
 */
@Service
public class SolicitacaoSuporteService {

    private final SolicitacaoSuporteRepository repository;

    public SolicitacaoSuporteService(SolicitacaoSuporteRepository repository) {
        this.repository = repository;
    }

    /**
     * Cria uma nova solicitação de suporte
     */
    public SolicitacaoSuporte criar(SolicitacaoSuporteRequest request) {
        SolicitacaoSuporte solicitacao = new SolicitacaoSuporte(
                null,
                request.getTitulo(),
                request.getDescricao(),
                request.getPrioridade()
        );
        
        return repository.save(solicitacao);
    }

    /**
     * Busca uma solicitação por ID
     */
    public SolicitacaoSuporte buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SolicitacaoNaoEncontradaException(id));
    }

    /**
     * Lista todas as solicitações
     */
    public List<SolicitacaoSuporte> listarTodas() {
        return repository.findAll();
    }

    /**
     * Lista solicitações por status
     */
    public List<SolicitacaoSuporte> listarPorStatus(StatusSolicitacao status) {
        return repository.findByStatus(status);
    }

    /**
     * Atualiza informações de uma solicitação
     */
    public SolicitacaoSuporte atualizar(Long id, AtualizarSolicitacaoRequest request) {
        SolicitacaoSuporte solicitacao = buscarPorId(id);

        // Regra de negócio: não permite atualizar solicitações já fechadas
        if (solicitacao.getStatus() == StatusSolicitacao.FECHADA) {
            throw new RegraDeNegocioException("Não é possível atualizar uma solicitação já fechada");
        }

        if (request.getTitulo() != null && !request.getTitulo().isBlank()) {
            solicitacao.setTitulo(request.getTitulo());
        }

        if (request.getDescricao() != null && !request.getDescricao().isBlank()) {
            solicitacao.setDescricao(request.getDescricao());
        }

        if (request.getPrioridade() != null) {
            solicitacao.setPrioridade(request.getPrioridade());
        }

        solicitacao.atualizarDataModificacao();
        return repository.update(solicitacao);
    }

    /**
     * Atualiza o status de uma solicitação com validação de transição
     */
    public SolicitacaoSuporte atualizarStatus(Long id, StatusSolicitacao novoStatus) {
        SolicitacaoSuporte solicitacao = buscarPorId(id);
        StatusSolicitacao statusAtual = solicitacao.getStatus();

        // Regra de negócio: valida transições de status permitidas
        validarTransicaoStatus(statusAtual, novoStatus);

        solicitacao.setStatus(novoStatus);
        return repository.update(solicitacao);
    }

    /**
     * Encerra uma solicitação (marca como FECHADA)
     */
    public SolicitacaoSuporte encerrar(Long id) {
        SolicitacaoSuporte solicitacao = buscarPorId(id);

        // Regra de negócio: só pode encerrar solicitações resolvidas
        if (solicitacao.getStatus() != StatusSolicitacao.RESOLVIDA) {
            throw new RegraDeNegocioException(
                    "Apenas solicitações com status RESOLVIDA podem ser encerradas. Status atual: " + 
                    solicitacao.getStatus().getDescricao()
            );
        }

        solicitacao.setStatus(StatusSolicitacao.FECHADA);
        return repository.update(solicitacao);
    }

    /**
     * Remove uma solicitação
     */
    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new SolicitacaoNaoEncontradaException(id);
        }
        
        // Regra de negócio: apenas solicitações abertas podem ser removidas
        SolicitacaoSuporte solicitacao = buscarPorId(id);
        if (solicitacao.getStatus() != StatusSolicitacao.ABERTA) {
            throw new RegraDeNegocioException(
                    "Apenas solicitações com status ABERTA podem ser removidas. Status atual: " + 
                    solicitacao.getStatus().getDescricao()
            );
        }
        
        repository.deleteById(id);
    }

    /**
     * Valida se a transição de status é permitida
     * Regras de negócio:
     * - ABERTA -> EM_ANDAMENTO, FECHADA
     * - EM_ANDAMENTO -> RESOLVIDA, ABERTA
     * - RESOLVIDA -> FECHADA, EM_ANDAMENTO
     * - FECHADA -> (não permite mudanças)
     */
    private void validarTransicaoStatus(StatusSolicitacao statusAtual, StatusSolicitacao novoStatus) {
        if (statusAtual == novoStatus) {
            return; // Mesma transição, não precisa validar
        }

        boolean transicaoValida = switch (statusAtual) {
            case ABERTA -> novoStatus == StatusSolicitacao.EM_ANDAMENTO || 
                          novoStatus == StatusSolicitacao.FECHADA;
            case EM_ANDAMENTO -> novoStatus == StatusSolicitacao.RESOLVIDA || 
                                novoStatus == StatusSolicitacao.ABERTA;
            case RESOLVIDA -> novoStatus == StatusSolicitacao.FECHADA || 
                             novoStatus == StatusSolicitacao.EM_ANDAMENTO;
            case FECHADA -> false; // Não permite mudanças quando fechada
        };

        if (!transicaoValida) {
            throw new TransicaoStatusInvalidaException(
                    statusAtual.getDescricao(), 
                    novoStatus.getDescricao()
            );
        }
    }

    /**
     * Retorna estatísticas das solicitações
     */
    public EstatisticasSuporte obterEstatisticas() {
        List<SolicitacaoSuporte> todas = repository.findAll();
        
        long abertas = todas.stream().filter(s -> s.getStatus() == StatusSolicitacao.ABERTA).count();
        long emAndamento = todas.stream().filter(s -> s.getStatus() == StatusSolicitacao.EM_ANDAMENTO).count();
        long resolvidas = todas.stream().filter(s -> s.getStatus() == StatusSolicitacao.RESOLVIDA).count();
        long fechadas = todas.stream().filter(s -> s.getStatus() == StatusSolicitacao.FECHADA).count();
        
        return new EstatisticasSuporte(todas.size(), abertas, emAndamento, resolvidas, fechadas);
    }

    /**
     * Record para estatísticas
     */
    public record EstatisticasSuporte(
            long total,
            long abertas,
            long emAndamento,
            long resolvidas,
            long fechadas
    ) {}
}
