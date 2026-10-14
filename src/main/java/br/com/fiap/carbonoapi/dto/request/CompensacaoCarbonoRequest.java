package br.com.fiap.carbonoapi.dto.request;

import br.com.fiap.carbonoapi.entity.enums.TipoCompensacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
public class CompensacaoCarbonoRequest {

    @NotNull(message = "ID da empresa é obrigatório")
    private Long empresaId;

    @NotNull(message = "Tipo é obrigatório")
    private TipoCompensacao tipo;

    @NotNull(message = "Quantidade de tCO2e é obrigatória")
    @DecimalMin(value = "0.0001", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantidadeTco2e;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @Size(max = 500, message = "URL do certificado deve ter no máximo 500 caracteres")
    private String certificadoUrl;

    @NotNull(message = "Data da compensação é obrigatória")
    @PastOrPresent(message = "Data da compensação não pode ser futura")
    private LocalDate dataCompensacao;
}