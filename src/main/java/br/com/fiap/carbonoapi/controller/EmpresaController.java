package br.com.fiap.carbonoapi.controller;

import br.com.fiap.carbonoapi.dto.request.EmpresaRequest;
import br.com.fiap.carbonoapi.dto.response.EmpresaResponse;
import br.com.fiap.carbonoapi.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas-sustentaveis")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<Page<EmpresaResponse>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(empresaService.listarEmpresas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponse> cadastrar(
            @RequestBody @Valid EmpresaRequest request) {
        EmpresaResponse response = empresaService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}