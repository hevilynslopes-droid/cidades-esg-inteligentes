package br.com.fiap.carbonoapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroResponse {

    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String caminho;
    private List<ErroCampo> errosCampos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErroCampo {
        private String campo;
        private String mensagem;
    }
}