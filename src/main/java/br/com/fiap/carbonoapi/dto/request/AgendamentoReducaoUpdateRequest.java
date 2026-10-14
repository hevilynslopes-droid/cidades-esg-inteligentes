package br.com.fiap.carbonoapi.dto.request;

import br.com.fiap.carbonoapi.entity.enums.StatusAgendamento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AgendamentoReducaoUpdateRequest {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título deve ter no máximo 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @NotNull(message = "Meta de redução é obrigatória")
    @DecimalMin(value = "0.0001", message = "Meta deve ser maior que zero")
    private BigDecimal metaReducaoTco2e;

    @NotNull(message = "Status é obrigatório")
    private StatusAgendamento status;

    @NotNull(message = "Data fim prevista é obrigatória")
    @Future(message = "Data fim prevista deve ser futura")
    private LocalDate dataFimPrevista;
}