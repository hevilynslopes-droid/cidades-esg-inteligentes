package br.com.fiap.carbonoapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImpactoMitigacaoResponse {
    private Long agendamentoId;
    private String titulo;
    private BigDecimal metaReducaoTco2e;
    private BigDecimal totalEmitidoEmpresa;
    private BigDecimal percentualImpacto;
    private LocalDate dataFimPrevista;
    private String status;
}