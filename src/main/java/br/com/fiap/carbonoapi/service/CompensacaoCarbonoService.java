package br.com.fiap.carbonoapi.service;

import br.com.fiap.carbonoapi.dto.request.CompensacaoCarbonoRequest;
import br.com.fiap.carbonoapi.dto.response.CompensacaoCarbonoResponse;
import br.com.fiap.carbonoapi.entity.CompensacaoCarbono;
import br.com.fiap.carbonoapi.entity.Empresa;
import br.com.fiap.carbonoapi.mapper.CompensacaoCarbonoMapper;
import br.com.fiap.carbonoapi.repository.CompensacaoCarbonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompensacaoCarbonoService {

    private final CompensacaoCarbonoRepository compensacaoRepository;
    private final br.com.fiap.carbonoapi.service.EmpresaService empresaService;
    private final CompensacaoCarbonoMapper compensacaoMapper;

    @Transactional
    public CompensacaoCarbonoResponse cadastrar(CompensacaoCarbonoRequest request) {
        Empresa empresa = empresaService.buscarEmpresaOuLancarExcecao(request.getEmpresaId());
        CompensacaoCarbono compensacao = compensacaoMapper.toEntity(request, empresa);
        CompensacaoCarbono salva = compensacaoRepository.save(compensacao);
        return compensacaoMapper.toResponse(salva);
    }

    @Transactional(readOnly = true)
    public Page<CompensacaoCarbonoResponse> listar(Pageable pageable) {
        return compensacaoRepository.findAll(pageable)
                .map(compensacaoMapper::toResponse);
    }
}