package br.com.fiap.carbonoapi.mapper;

import br.com.fiap.carbonoapi.dto.request.CompensacaoCarbonoRequest;
import br.com.fiap.carbonoapi.dto.response.CompensacaoCarbonoResponse;
import br.com.fiap.carbonoapi.entity.CompensacaoCarbono;
import br.com.fiap.carbonoapi.entity.Empresa;
import org.springframework.stereotype.Component;

@Component
public class CompensacaoCarbonoMapper {

    public CompensacaoCarbono toEntity(CompensacaoCarbonoRequest request, Empresa empresa) {
        return CompensacaoCarbono.builder()
                .empresa(empresa)
                .tipo(request.getTipo())
                .quantidadeTco2e(request.getQuantidadeTco2e())
                .descricao(request.getDescricao())
                .certificadoUrl(request.getCertificadoUrl())
                .dataCompensacao(request.getDataCompensacao())
                .build();
    }

    public CompensacaoCarbonoResponse toResponse(CompensacaoCarbono compensacao) {
        return CompensacaoCarbonoResponse.builder()
                .id(compensacao.getId())
                .empresaId(compensacao.getEmpresa().getId())
                .empresaNome(compensacao.getEmpresa().getNome())
                .tipo(compensacao.getTipo())
                .quantidadeTco2e(compensacao.getQuantidadeTco2e())
                .descricao(compensacao.getDescricao())
                .certificadoUrl(compensacao.getCertificadoUrl())
                .dataCompensacao(compensacao.getDataCompensacao())
                .dataRegistro(compensacao.getDataRegistro())
                .build();
    }
}