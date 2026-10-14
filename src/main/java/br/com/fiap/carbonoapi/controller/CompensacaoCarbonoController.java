package br.com.fiap.carbonoapi.controller;

import br.com.fiap.carbonoapi.dto.request.CompensacaoCarbonoRequest;
import br.com.fiap.carbonoapi.dto.response.CompensacaoCarbonoResponse;
import br.com.fiap.carbonoapi.service.CompensacaoCarbonoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compensacao-carbono")
@RequiredArgsConstructor
public class CompensacaoCarbonoController {

    private final CompensacaoCarbonoService compensacaoService;

    @GetMapping
    public ResponseEntity<Page<CompensacaoCarbonoResponse>> listar(
            @PageableDefault(size = 10, sort = "dataCompensacao") Pageable pageable) {
        return ResponseEntity.ok(compensacaoService.listar(pageable));
    }

    @PostMapping
    public ResponseEntity<CompensacaoCarbonoResponse> cadastrar(
            @RequestBody @Valid CompensacaoCarbonoRequest request) {
        CompensacaoCarbonoResponse response = compensacaoService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}