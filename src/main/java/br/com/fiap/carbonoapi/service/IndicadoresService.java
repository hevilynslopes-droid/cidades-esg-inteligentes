package br.com.fiap.carbonoapi.service;

import br.com.fiap.carbonoapi.dto.response.ImpactoMitigacaoResponse;
import br.com.fiap.carbonoapi.dto.response.IndicadoresResponse;
import br.com.fiap.carbonoapi.entity.AgendamentoReducao;
import br.com.fiap.carbonoapi.entity.enums.StatusAgendamento;
import br.com.fiap.carbonoapi.repository.AgendamentoReducaoRepository;
import br.com.fiap.carbonoapi.repository.CompensacaoCarbonoRepository;
import br.com.fiap.carbonoapi.repository.EmissaoCarbonoRepository;
import br.com.fiap.carbonoapi.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class IndicadoresService {

    private final EmpresaRepository empresaRepository;
    private final EmissaoCarbonoRepository emissaoRepository;
    private final AgendamentoReducaoRepository agendamentoRepository;
    private final CompensacaoCarbonoRepository compensacaoRepository;
    private final AgendamentoReducaoService agendamentoService;
    private final EmissaoCarbonoRepository emissaoCarbonoRepository;

    @Transactional(readOnly = true)
    public IndicadoresResponse calcularIndicadores() {
        long totalEmpresas = empresaRepository.count();
        long totalEmissoes = emissaoRepository.count();
        long totalAgendamentos = agendamentoRepository.count();
        long agendamentosConcluidos = agendamentoRepository
                .findAll()
                .stream()
                .filter(a -> a.getStatus() == StatusAgendamento.CONCLUIDO)
                .count();

        BigDecimal totalEmitido = emissaoRepository.findAll()
                .stream()
                .map(e -> e.getQuantidadeTco2e())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalCompensado = compensacaoRepository.findAll()
                .stream()
                .map(c -> c.getQuantidadeTco2e())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoLiquido = totalEmitido.subtract(totalCompensado);

        return IndicadoresResponse.builder()
                .totalEmpresas(totalEmpresas)
                .totalEmissoes(totalEmissoes)
                .totalTco2eEmitido(totalEmitido)
                .totalAgendamentos(totalAgendamentos)
                .agendamentosConcluidos(agendamentosConcluidos)
                .totalTco2eCompensado(totalCompensado)
                .saldoLiquidoTco2e(saldoLiquido)
                .build();
    }

    @Transactional(readOnly = true)
    public ImpactoMitigacaoResponse calcularImpacto(Long agendamentoId) {
        AgendamentoReducao agendamento = agendamentoService.buscarOuLancarExcecao(agendamentoId);
        Long empresaId = agendamento.getEmpresa().getId();

        BigDecimal totalEmitido = emissaoCarbonoRepository
                .sumQuantidadeByEmpresaId(empresaId);

        if (totalEmitido == null) {
            totalEmitido = BigDecimal.ZERO;
        }

        BigDecimal percentual = BigDecimal.ZERO;
        if (totalEmitido.compareTo(BigDecimal.ZERO) > 0) {
            percentual = agendamento.getMetaReducaoTco2e()
                    .divide(totalEmitido, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return ImpactoMitigacaoResponse.builder()
                .agendamentoId(agendamento.getId())
                .titulo(agendamento.getTitulo())
                .metaReducaoTco2e(agendamento.getMetaReducaoTco2e())
                .totalEmitidoEmpresa(totalEmitido)
                .percentualImpacto(percentual)
                .dataFimPrevista(agendamento.getDataFimPrevista())
                .status(agendamento.getStatus().name())
                .build();
    }
}