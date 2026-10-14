package br.com.fiap.carbonoapi.mapper;

import br.com.fiap.carbonoapi.dto.request.EmpresaRequest;
import br.com.fiap.carbonoapi.dto.response.EmpresaResponse;
import br.com.fiap.carbonoapi.entity.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public Empresa toEntity(EmpresaRequest request) {
        return Empresa.builder()
                .nome(request.getNome())
                .cnpj(request.getCnpj())
                .setor(request.getSetor())
                .pais(request.getPais())
                .ativa(true)
                .build();
    }

    public EmpresaResponse toResponse(Empresa empresa) {
        return EmpresaResponse.builder()
                .id(empresa.getId())
                .nome(empresa.getNome())
                .cnpj(empresa.getCnpj())
                .setor(empresa.getSetor())
                .pais(empresa.getPais())
                .ativa(empresa.getAtiva())
                .dataCadastro(empresa.getDataCadastro())
                .build();
    }
}