package br.com.fiap.carbonoapi.controller;

import br.com.fiap.carbonoapi.dto.request.EmissaoCarbonoRequest;
import br.com.fiap.carbonoapi.dto.response.EmissaoCarbonoResponse;
import br.com.fiap.carbonoapi.service.EmissaoCarbonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class EmissaoCarbonoController {

    private final EmissaoCarbonoService emissaoService;

    @GetMapping("/emissoes-carbono")
    public ResponseEntity<Page<EmissaoCarbonoResponse>> listar(
            @PageableDefault(size = 10, sort = "dataEmissao") Pageable pageable) {
        return ResponseEntity.ok(emissaoService.listarEmissoes(pageable));
    }

    @GetMapping("/historico-emissoes/{empresaId}")
    public ResponseEntity<Page<EmissaoCarbonoResponse>> historicoPorEmpresa(
            @PathVariable Long empresaId,
            @PageableDefault(size = 10, sort = "dataEmissao") Pageable pageable) {
        return ResponseEntity.ok(emissaoService.listarPorEmpresa(empresaId, pageable));
    }

    @PostMapping("/relatorio-emissao")
    public ResponseEntity<EmissaoCarbonoResponse> registrar(
            @RequestBody @Valid EmissaoCarbonoRequest request) {
        EmissaoCarbonoResponse response = emissaoService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}