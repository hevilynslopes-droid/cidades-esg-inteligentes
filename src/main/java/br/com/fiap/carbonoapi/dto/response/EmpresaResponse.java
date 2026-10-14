package br.com.fiap.carbonoapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaResponse {
    private Long id;
    private String nome;
    private String cnpj;
    private String setor;
    private String pais;
    private Boolean ativa;
    private LocalDateTime dataCadastro;
}