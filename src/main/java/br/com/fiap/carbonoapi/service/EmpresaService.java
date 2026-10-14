package br.com.fiap.carbonoapi.service;

import br.com.fiap.carbonoapi.dto.request.EmpresaRequest;
import br.com.fiap.carbonoapi.dto.response.EmpresaResponse;
import br.com.fiap.carbonoapi.entity.Empresa;
import br.com.fiap.carbonoapi.exception.ConflictException;
import br.com.fiap.carbonoapi.mapper.EmpresaMapper;
import br.com.fiap.carbonoapi.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    @Transactional(readOnly = true)
    public Page<EmpresaResponse> listarEmpresas(Pageable pageable) {
        return empresaRepository.findAll(pageable)
                .map(empresaMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public EmpresaResponse buscarPorId(Long id) {
        Empresa empresa = buscarEmpresaOuLancarExcecao(id);
        return empresaMapper.toResponse(empresa);
    }

    @Transactional
    public EmpresaResponse cadastrar(EmpresaRequest request) {
        if (empresaRepository.existsByCnpj(request.getCnpj())) {
            throw new ConflictException("Empresa com CNPJ " + request.getCnpj() + " já cadastrada");
        }
        Empresa empresa = empresaMapper.toEntity(request);
        Empresa salva = empresaRepository.save(empresa);
        return empresaMapper.toResponse(salva);
    }

    public Empresa buscarEmpresaOuLancarExcecao(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new br.com.fiap.carbonoapi.exception.ResourceNotFoundException("Empresa não encontrada com id: " + id));
    }
}