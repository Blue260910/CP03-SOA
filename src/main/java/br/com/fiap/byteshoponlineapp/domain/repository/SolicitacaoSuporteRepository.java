package br.com.fiap.byteshoponlineapp.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.fiap.byteshoponlineapp.domain.SolicitacaoSuporte;
import br.com.fiap.byteshoponlineapp.domain.enums.StatusSolicitacao;

/**
 * Repositório em memória para gerenciar Solicitações de Suporte
 * Utiliza ConcurrentHashMap para thread-safety
 */
@Repository
public class SolicitacaoSuporteRepository {
    
    private final Map<Long, SolicitacaoSuporte> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Salva uma nova solicitação de suporte
     */
    public SolicitacaoSuporte save(SolicitacaoSuporte solicitacao) {
        if (solicitacao.getId() == null) {
            solicitacao.setId(idGenerator.getAndIncrement());
        }
        database.put(solicitacao.getId(), solicitacao);
        return solicitacao;
    }

    /**
     * Busca uma solicitação por ID
     */
    public Optional<SolicitacaoSuporte> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    /**
     * Lista todas as solicitações
     */
    public List<SolicitacaoSuporte> findAll() {
        return new ArrayList<>(database.values());
    }

    /**
     * Lista solicitações por status
     */
    public List<SolicitacaoSuporte> findByStatus(StatusSolicitacao status) {
        return database.values().stream()
                .filter(s -> s.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza uma solicitação existente
     */
    public SolicitacaoSuporte update(SolicitacaoSuporte solicitacao) {
        database.put(solicitacao.getId(), solicitacao);
        return solicitacao;
    }

    /**
     * Remove uma solicitação por ID
     */
    public void deleteById(Long id) {
        database.remove(id);
    }

    /**
     * Verifica se uma solicitação existe
     */
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }

    /**
     * Conta o total de solicitações
     */
    public long count() {
        return database.size();
    }

    /**
     * Limpa todo o repositório (útil para testes)
     */
    public void deleteAll() {
        database.clear();
    }
}
