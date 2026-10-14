package br.com.fiap.carbonoapi.mapper;


import br.com.fiap.carbonoapi.dto.request.AgendamentoReducaoRequest;
import br.com.fiap.carbonoapi.dto.response.AgendamentoReducaoResponse;
import br.com.fiap.carbonoapi.entity.AgendamentoReducao;
import br.com.fiap.carbonoapi.entity.Empresa;
import br.com.fiap.carbonoapi.entity.enums.StatusAgendamento;
import org.springframework.stereotype.Component;

@Component
public class AgendamentoReducaoMapper {

    public AgendamentoReducao toEntity(AgendamentoReducaoRequest request, Empresa empresa) {
        return AgendamentoReducao.builder()
                .empresa(empresa)
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .metaReducaoTco2e(request.getMetaReducaoTco2e())
                .status(StatusAgendamento.PENDENTE)
                .dataInicio(request.getDataInicio())
                .dataFimPrevista(request.getDataFimPrevista())
                .build();
    }

    public AgendamentoReducaoResponse toResponse(AgendamentoReducao agendamento) {
        return AgendamentoReducaoResponse.builder()
                .id(agendamento.getId())
                .empresaId(agendamento.getEmpresa().getId())
                .empresaNome(agendamento.getEmpresa().getNome())
                .titulo(agendamento.getTitulo())
                .descricao(agendamento.getDescricao())
                .metaReducaoTco2e(agendamento.getMetaReducaoTco2e())
                .status(agendamento.getStatus())
                .dataInicio(agendamento.getDataInicio())
                .dataFimPrevista(agendamento.getDataFimPrevista())
                .dataCadastro(agendamento.getDataCadastro())
                .dataAtualizacao(agendamento.getDataAtualizacao())
                .build();
    }
}