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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(
        summary = "Criar nova solicitação de suporte",
        description = "Cria uma nova solicitação de suporte técnico. O status inicial será sempre ABERTA e as datas de criação/atualização são definidas automaticamente."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Solicitação criada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "id": 1,
                      "titulo": "Sistema lento após atualização",
                      "descricao": "Após a última atualização, o sistema está demorando mais de 30 segundos para carregar",
                      "status": "ABERTA",
                      "prioridade": "ALTA",
                      "dataCriacao": "2025-11-06T21:15:30",
                      "dataAtualizacao": "2025-11-06T21:15:30"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados de entrada inválidos - validação falhou",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "timestamp": "2025-11-06T21:30:00",
                      "status": 400,
                      "erro": "Erro de Validação",
                      "mensagem": "Um ou mais campos contêm valores inválidos",
                      "caminho": "/api/suporte",
                      "erros": [
                        {
                          "campo": "titulo",
                          "mensagem": "O título deve ter entre 5 e 100 caracteres"
                        }
                      ]
                    }
                    """
                )
            )
        )
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
    @Operation(
        summary = "Listar todas as solicitações",
        description = "Retorna uma lista com todas as solicitações de suporte cadastradas no sistema, independente do status."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista retornada com sucesso (pode estar vazia)",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class)
            )
        )
    })
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
    @Operation(
        summary = "Buscar solicitação por ID",
        description = "Retorna os detalhes completos de uma solicitação específica através do seu identificador único."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Solicitação encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Solicitação não encontrada",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "timestamp": "2025-11-06T21:30:00",
                      "status": 404,
                      "erro": "Recurso Não Encontrado",
                      "mensagem": "Solicitação de suporte não encontrada com ID: 999",
                      "caminho": "/api/suporte/999"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoSuporteResponse> buscarPorId(
            @Parameter(description = "ID da solicitação", required = true, example = "1")
            @PathVariable Long id) {
        SolicitacaoSuporte solicitacao = service.buscarPorId(id);
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/suporte/status/{status}
     * Lista solicitações por status
     * Status Code: 200 OK
     */
    @Operation(
        summary = "Listar solicitações por status",
        description = "Filtra e retorna apenas as solicitações que possuem o status especificado. Status válidos: ABERTA, EM_ANDAMENTO, RESOLVIDA, FECHADA."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Lista filtrada retornada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Status inválido fornecido",
            content = @Content(mediaType = "application/json")
        )
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SolicitacaoSuporteResponse>> listarPorStatus(
            @Parameter(
                description = "Status da solicitação para filtrar", 
                required = true,
                example = "ABERTA",
                schema = @Schema(
                    type = "string",
                    allowableValues = {"ABERTA", "EM_ANDAMENTO", "RESOLVIDA", "FECHADA"}
                )
            )
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
    @Operation(
        summary = "Atualizar solicitação",
        description = "Atualiza título, descrição e/ou prioridade de uma solicitação. REGRA: Não é possível atualizar solicitações com status FECHADA."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Solicitação atualizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Tentativa de atualizar solicitação fechada ou dados inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "timestamp": "2025-11-06T21:30:00",
                      "status": 400,
                      "erro": "Erro de Regra de Negócio",
                      "mensagem": "Não é possível atualizar uma solicitação já fechada",
                      "caminho": "/api/suporte/1"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Solicitação não encontrada",
            content = @Content(mediaType = "application/json")
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoSuporteResponse> atualizar(
            @Parameter(description = "ID da solicitação a ser atualizada", required = true, example = "1")
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
    @Operation(
        summary = "Atualizar status da solicitação",
        description = """
            Altera o status de uma solicitação seguindo as regras de transição:
            - ABERTA → EM_ANDAMENTO ou FECHADA
            - EM_ANDAMENTO → RESOLVIDA ou ABERTA
            - RESOLVIDA → FECHADA ou EM_ANDAMENTO
            - FECHADA → Nenhuma transição permitida (imutável)
            """
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Status atualizado com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Transição de status inválida",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "timestamp": "2025-11-06T21:30:00",
                      "status": 400,
                      "erro": "Transição de Status Inválida",
                      "mensagem": "Transição inválida: não é possível mudar de Aberta para Resolvida",
                      "caminho": "/api/suporte/1/status"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Solicitação não encontrada",
            content = @Content(mediaType = "application/json")
        )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<SolicitacaoSuporteResponse> atualizarStatus(
            @Parameter(description = "ID da solicitação", required = true, example = "1")
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
    @Operation(
        summary = "Encerrar solicitação",
        description = "Finaliza uma solicitação marcando-a como FECHADA. REGRA: Só pode encerrar solicitações com status RESOLVIDA."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Solicitação encerrada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = SolicitacaoSuporteResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Tentativa de encerrar solicitação que não está RESOLVIDA",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "timestamp": "2025-11-06T21:30:00",
                      "status": 400,
                      "erro": "Erro de Regra de Negócio",
                      "mensagem": "Apenas solicitações com status RESOLVIDA podem ser encerradas. Status atual: Em Andamento",
                      "caminho": "/api/suporte/1/encerrar"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Solicitação não encontrada",
            content = @Content(mediaType = "application/json")
        )
    })
    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<SolicitacaoSuporteResponse> encerrar(
            @Parameter(description = "ID da solicitação a ser encerrada", required = true, example = "1")
            @PathVariable Long id) {
        SolicitacaoSuporte solicitacao = service.encerrar(id);
        SolicitacaoSuporteResponse response = new SolicitacaoSuporteResponse(solicitacao);
        
        return ResponseEntity.ok(response);
    }

    /**
     * DELETE /api/suporte/{id}
     * Remove uma solicitação
     * Status Code: 204 No Content ou 404 Not Found
     */
    @Operation(
        summary = "Remover solicitação",
        description = "Remove permanentemente uma solicitação do sistema. REGRA: Só pode remover solicitações com status ABERTA."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Solicitação removida com sucesso (sem conteúdo na resposta)"
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Tentativa de remover solicitação que não está ABERTA",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "timestamp": "2025-11-06T21:30:00",
                      "status": 400,
                      "erro": "Erro de Regra de Negócio",
                      "mensagem": "Apenas solicitações com status ABERTA podem ser removidas. Status atual: Em Andamento",
                      "caminho": "/api/suporte/1"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Solicitação não encontrada",
            content = @Content(mediaType = "application/json")
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(
            @Parameter(description = "ID da solicitação a ser removida", required = true, example = "1")
            @PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/suporte/estatisticas
     * Obtém estatísticas das solicitações
     * Status Code: 200 OK
     */
    @Operation(
        summary = "Obter estatísticas",
        description = "Retorna um resumo estatístico com a contagem de solicitações por status (total, abertas, em andamento, resolvidas e fechadas)."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Estatísticas retornadas com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "total": 10,
                      "abertas": 3,
                      "emAndamento": 4,
                      "resolvidas": 2,
                      "fechadas": 1
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/estatisticas")
    public ResponseEntity<SolicitacaoSuporteService.EstatisticasSuporte> obterEstatisticas() {
        SolicitacaoSuporteService.EstatisticasSuporte estatisticas = service.obterEstatisticas();
        return ResponseEntity.ok(estatisticas);
    }
}
