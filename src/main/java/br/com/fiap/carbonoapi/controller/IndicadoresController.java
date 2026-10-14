package br.com.fiap.carbonoapi.controller;

import br.com.fiap.carbonoapi.dto.response.ImpactoMitigacaoResponse;
import br.com.fiap.carbonoapi.dto.response.IndicadoresResponse;
import br.com.fiap.carbonoapi.service.IndicadoresService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class IndicadoresController {

    private final IndicadoresService indicadoresService;

    @GetMapping("/indicadores-sustentabilidade")
    public ResponseEntity<IndicadoresResponse> indicadores() {
        return ResponseEntity.ok(indicadoresService.calcularIndicadores());
    }

    @GetMapping("/impacto-mitigacao/{id}")
    public ResponseEntity<ImpactoMitigacaoResponse> impacto(@PathVariable Long id) {
        return ResponseEntity.ok(indicadoresService.calcularImpacto(id));
    }
}