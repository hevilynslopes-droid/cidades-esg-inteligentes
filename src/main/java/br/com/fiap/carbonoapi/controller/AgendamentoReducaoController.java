package br.com.fiap.carbonoapi.controller;

import br.com.fiap.carbonoapi.dto.request.AgendamentoReducaoRequest;
import br.com.fiap.carbonoapi.dto.request.AgendamentoReducaoUpdateRequest;
import br.com.fiap.carbonoapi.dto.response.AgendamentoReducaoResponse;
import br.com.fiap.carbonoapi.service.AgendamentoReducaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agendamento-reducao-carbono")
@RequiredArgsConstructor
public class AgendamentoReducaoController {

    private final AgendamentoReducaoService agendamentoService;

    @GetMapping
    public ResponseEntity<Page<AgendamentoReducaoResponse>> listar(
            @PageableDefault(size = 10, sort = "dataCadastro") Pageable pageable) {
        return ResponseEntity.ok(agendamentoService.listar(pageable));
    }

    @PostMapping
    public ResponseEntity<AgendamentoReducaoResponse> agendar(
            @RequestBody @Valid AgendamentoReducaoRequest request) {
        AgendamentoReducaoResponse response = agendamentoService.agendar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoReducaoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AgendamentoReducaoUpdateRequest request) {
        AgendamentoReducaoResponse response = agendamentoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        agendamentoService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}