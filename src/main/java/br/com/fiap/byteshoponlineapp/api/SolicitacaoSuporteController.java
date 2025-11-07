package br.com.fiap.byteshoponlineapp.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.byteshoponlineapp.api.dto.AtualizarSolicitacaoRequest;
import br.com.fiap.byteshoponlineapp.api.dto.AtualizarStatusRequest;
import br.com.fiap.byteshoponlineapp.api.dto.SolicitacaoSuporteRequest;
import br.com.fiap.byteshoponlineapp.api.dto.SolicitacaoSuporteResponse;
import br.com.fiap.byteshoponlineapp.domain.SolicitacaoSuporte;
import br.com.fiap.byteshoponlineapp.domain.enums.StatusSolicitacao;
import br.com.fiap.byteshoponlineapp.service.SolicitacaoSuporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controller REST para gerenciar Solicitações de Suporte
 * 
 * Endpoints disponíveis:
 * POST   /api/suporte              - Cria nova solicitação
 * GET    /api/suporte              - Lista todas as solicitações
 * GET    /api/suporte/{id}         - Busca solicitação por ID
 * GET    /api/suporte/status/{status} - Lista por status
 * PUT    /api/suporte/{id}         - Atualiza solicitação
 * PATCH  /api/suporte/{id}/status  - Atualiza status
 * PATCH  /api/suporte/{id}/encerrar - Encerra solicitação
 * DELETE /api/suporte/{id}         - Remove solicitação
 * GET    /api/suporte/estatisticas - Obtém estatísticas
 */
@Tag(name = "Suporte Técnico", description = "APIs para gerenciamento de solicitações de suporte técnico")
@RestController
@RequestMapping("/api/suporte")
public class SolicitacaoSuporteController {

    private final SolicitacaoSuporteService service;

    public SolicitacaoSuporteController(SolicitacaoSuporteService service) {
        this.service = service;
    }

    /**
     * POST /api/suporte
     * Cria uma nova solicitação de suporte
     * Status Code: 201 Created
     */
    @Operation(summary = "Criar nova solicitação", description = "Cria uma nova solicitação de suporte técnico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Solicitação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<SolicitacaoSuporteResponse> criar(
            @Valid @RequestBody SolicitacaoSuporteRequest request) {
        
        SolicitacaoSuporte solicitacao = service.criar(request);
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/suporte
     * Lista todas as solicitações de suporte
     * Status Code: 200 OK
     */
    @GetMapping
    public ResponseEntity<List<SolicitacaoSuporteResponse>> listarTodas() {
        List<SolicitacaoSuporte> solicitacoes = service.listarTodas();
        
        List<SolicitacaoSuporteResponse> response = solicitacoes.stream()
                .map(SolicitacaoSuporteResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/suporte/{id}
     * Busca uma solicitação por ID
     * Status Code: 200 OK ou 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoSuporteResponse> buscarPorId(@PathVariable Long id) {
        SolicitacaoSuporte solicitacao = service.buscarPorId(id);
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/suporte/status/{status}
     * Lista solicitações por status
     * Status Code: 200 OK
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SolicitacaoSuporteResponse>> listarPorStatus(
            @PathVariable StatusSolicitacao status) {
        
        List<SolicitacaoSuporte> solicitacoes = service.listarPorStatus(status);
        
        List<SolicitacaoSuporteResponse> response = solicitacoes.stream()
                .map(SolicitacaoSuporteResponse::new)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /api/suporte/{id}
     * Atualiza informações de uma solicitação
     * Status Code: 200 OK ou 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoSuporteResponse> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarSolicitacaoRequest request) {
        
        SolicitacaoSuporte solicitacao = service.atualizar(id, request);
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/suporte/{id}/status
     * Atualiza o status de uma solicitação
     * Status Code: 200 OK ou 404 Not Found ou 400 Bad Request
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<SolicitacaoSuporteResponse> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarStatusRequest request) {
        
        SolicitacaoSuporte solicitacao = service.atualizarStatus(id, request.getStatus());
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.ok(response);
    }

    /**
     * PATCH /api/suporte/{id}/encerrar
     * Encerra uma solicitação (marca como FECHADA)
     * Status Code: 200 OK ou 404 Not Found ou 400 Bad Request
     */
    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<SolicitacaoSuporteResponse> encerrar(@PathVariable Long id) {
        SolicitacaoSuporte solicitacao = service.encerrar(id);
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/suporte/{id}
     * Remove uma solicitação
     * Status Code: 204 No Content ou 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/suporte/estatisticas
     * Obtém estatísticas das solicitações
     * Status Code: 200 OK
     */
    @GetMapping("/estatisticas")
    public ResponseEntity<SolicitacaoSuporteService.EstatisticasSuporte> obterEstatisticas() {
        SolicitacaoSuporteService.EstatisticasSuporte estatisticas = service.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
}
