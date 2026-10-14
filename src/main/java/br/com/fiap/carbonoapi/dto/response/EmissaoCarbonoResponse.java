package br.com.fiap.carbonoapi.dto.response;

import br.com.fiap.carbonoapi.entity.enums.EscopoEmissao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmissaoCarbonoResponse {
    private Long id;
    private Long empresaId;
    private String empresaNome;
    private BigDecimal quantidadeTco2e;
    private EscopoEmissao escopo;
    private String descricao;
    private LocalDate dataEmissao;
    private LocalDateTime dataRegistro;
}