package br.com.fiap.carbonoapi.dto.response;

import br.com.fiap.carbonoapi.entity.enums.TipoCompensacao;
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
public class CompensacaoCarbonoResponse {
    private Long id;
    private Long empresaId;
    private String empresaNome;
    private TipoCompensacao tipo;
    private BigDecimal quantidadeTco2e;
    private String descricao;
    private String certificadoUrl;
    private LocalDate dataCompensacao;
    private LocalDateTime dataRegistro;
}