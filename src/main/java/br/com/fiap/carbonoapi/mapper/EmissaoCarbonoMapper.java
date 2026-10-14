package br.com.fiap.carbonoapi.mapper;

import br.com.fiap.carbonoapi.dto.request.EmissaoCarbonoRequest;
import br.com.fiap.carbonoapi.dto.response.EmissaoCarbonoResponse;
import br.com.fiap.carbonoapi.entity.EmissaoCarbono;
import br.com.fiap.carbonoapi.entity.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmissaoCarbonoMapper {

    public EmissaoCarbono toEntity(EmissaoCarbonoRequest request, Empresa empresa) {
        return EmissaoCarbono.builder()
                .empresa(empresa)
                .quantidadeTco2e(request.getQuantidadeTco2e())
                .escopo(request.getEscopo())
                .descricao(request.getDescricao())
                .dataEmissao(request.getDataEmissao())
                .build();
    }

    public EmissaoCarbonoResponse toResponse(EmissaoCarbono emissao) {
        return EmissaoCarbonoResponse.builder()
                .id(emissao.getId())
                .empresaId(emissao.getEmpresa().getId())
                .empresaNome(emissao.getEmpresa().getNome())
                .quantidadeTco2e(emissao.getQuantidadeTco2e())
                .escopo(emissao.getEscopo())
                .descricao(emissao.getDescricao())
                .dataEmissao(emissao.getDataEmissao())
                .dataRegistro(emissao.getDataRegistro())
                .build();
    }
}