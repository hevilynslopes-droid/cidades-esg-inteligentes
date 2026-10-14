package br.com.fiap.carbonoapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicadoresResponse {
    private Long totalEmpresas;
    private Long totalEmissoes;
    private BigDecimal totalTco2eEmitido;
    private Long totalAgendamentos;
    private Long agendamentosConcluidos;
    private BigDecimal totalTco2eCompensado;
    private BigDecimal saldoLiquidoTco2e;
}