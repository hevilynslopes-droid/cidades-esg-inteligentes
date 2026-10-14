package br.com.fiap.carbonoapi.service;

import br.com.fiap.carbonoapi.dto.request.AgendamentoReducaoRequest;
import br.com.fiap.carbonoapi.dto.request.AgendamentoReducaoUpdateRequest;
import br.com.fiap.carbonoapi.dto.response.AgendamentoReducaoResponse;
import br.com.fiap.carbonoapi.entity.AgendamentoReducao;
import br.com.fiap.carbonoapi.entity.Empresa;
import br.com.fiap.carbonoapi.entity.enums.StatusAgendamento;
import br.com.fiap.carbonoapi.exception.BusinessException;
import br.com.fiap.carbonoapi.mapper.AgendamentoReducaoMapper;
import br.com.fiap.carbonoapi.repository.AgendamentoReducaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendamentoReducaoService {

    private final AgendamentoReducaoRepository agendamentoRepository;
    private final EmpresaService empresaService;
    private final AgendamentoReducaoMapper agendamentoMapper;

    @Transactional
    public AgendamentoReducaoResponse agendar(AgendamentoReducaoRequest request) {
        if (request.getDataFimPrevista().isBefore(request.getDataInicio())) {
            throw new BusinessException("Data fim prevista deve ser posterior à data de início");
        }
        Empresa empresa = empresaService.buscarEmpresaOuLancarExcecao(request.getEmpresaId());
        AgendamentoReducao agendamento = agendamentoMapper.toEntity(request, empresa);
        AgendamentoReducao salvo = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toResponse(salvo);
    }

    @Transactional
    public AgendamentoReducaoResponse atualizar(Long id, AgendamentoReducaoUpdateRequest request) {
        AgendamentoReducao agendamento = buscarOuLancarExcecao(id);
        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new BusinessException("Não é possível atualizar um agendamento cancelado");
        }
        agendamento.setTitulo(request.getTitulo());
        agendamento.setDescricao(request.getDescricao());
        agendamento.setMetaReducaoTco2e(request.getMetaReducaoTco2e());
        agendamento.setStatus(request.getStatus());
        agendamento.setDataFimPrevista(request.getDataFimPrevista());
        AgendamentoReducao atualizado = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toResponse(atualizado);
    }

    @Transactional
    public void cancelar(Long id) {
        AgendamentoReducao agendamento = buscarOuLancarExcecao(id);
        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            throw new BusinessException("Agendamento já está cancelado");
        }
        if (agendamento.getStatus() == StatusAgendamento.CONCLUIDO) {
            throw new BusinessException("Não é possível cancelar um agendamento concluído");
        }
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoReducaoResponse> listar(Pageable pageable) {
        return agendamentoRepository.findAll(pageable)
                .map(agendamentoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public AgendamentoReducaoResponse buscarPorId(Long id) {
        return agendamentoMapper.toResponse(buscarOuLancarExcecao(id));
    }

    public AgendamentoReducao buscarOuLancarExcecao(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new br.com.fiap.carbonoapi.exception.ResourceNotFoundException("Agendamento não encontrado com id: " + id));
    }
}