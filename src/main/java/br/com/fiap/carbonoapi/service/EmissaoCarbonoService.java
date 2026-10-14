package br.com.fiap.carbonoapi.service;

import br.com.fiap.carbonoapi.dto.request.EmissaoCarbonoRequest;
import br.com.fiap.carbonoapi.dto.response.EmissaoCarbonoResponse;
import br.com.fiap.carbonoapi.entity.EmissaoCarbono;
import br.com.fiap.carbonoapi.entity.Empresa;
import br.com.fiap.carbonoapi.mapper.EmissaoCarbonoMapper;
import br.com.fiap.carbonoapi.repository.EmissaoCarbonoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmissaoCarbonoService {

    private final EmissaoCarbonoRepository emissaoRepository;
    private final EmpresaService empresaService;
    private final EmissaoCarbonoMapper emissaoMapper;

    @Transactional(readOnly = true)
    public Page<EmissaoCarbonoResponse> listarEmissoes(Pageable pageable) {
        return emissaoRepository.findAll(pageable)
                .map(emissaoMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<EmissaoCarbonoResponse> listarPorEmpresa(Long empresaId, Pageable pageable) {
        empresaService.buscarEmpresaOuLancarExcecao(empresaId);
        return emissaoRepository.findByEmpresaId(empresaId, pageable)
                .map(emissaoMapper::toResponse);
    }

    @Transactional
    public EmissaoCarbonoResponse registrar(EmissaoCarbonoRequest request) {
        Empresa empresa = empresaService.buscarEmpresaOuLancarExcecao(request.getEmpresaId());
        EmissaoCarbono emissao = emissaoMapper.toEntity(request, empresa);
        EmissaoCarbono salva = emissaoRepository.save(emissao);
        return emissaoMapper.toResponse(salva);
    }

    @Transactional(readOnly = true)
    public EmissaoCarbonoResponse buscarPorId(Long id) {
        EmissaoCarbono emissao = emissaoRepository.findById(id)
                .orElseThrow(() -> new br.com.fiap.carbonoapi.exception.ResourceNotFoundException("Emissão não encontrada com id: " + id));
        return emissaoMapper.toResponse(emissao);
    }
}