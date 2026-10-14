package br.com.fiap.carbonoapi.dto.request;

import br.com.fiap.carbonoapi.entity.enums.EscopoEmissao;
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
public class EmissaoCarbonoRequest {

    @NotNull(message = "ID da empresa é obrigatório")
    private Long empresaId;

    @NotNull(message = "Quantidade de tCO2e é obrigatória")
    @DecimalMin(value = "0.0001", message = "Quantidade deve ser maior que zero")
    private BigDecimal quantidadeTco2e;

    @NotNull(message = "Escopo é obrigatório")
    private EscopoEmissao escopo;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "Data da emissão é obrigatória")
    @PastOrPresent(message = "Data da emissão não pode ser futura")
    private LocalDate dataEmissao;
}