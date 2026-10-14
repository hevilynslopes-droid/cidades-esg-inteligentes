package br.com.fiap.carbonoapi.dto.response;

import br.com.fiap.carbonoapi.entity.enums.StatusAgendamento;
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
public class AgendamentoReducaoResponse {
    private Long id;
    private Long empresaId;
    private String empresaNome;
    private String titulo;
    private String descricao;
    private BigDecimal metaReducaoTco2e;
    private StatusAgendamento status;
    private LocalDate dataInicio;
    private LocalDate dataFimPrevista;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}