package br.com.fiap.carbonoapi.entity;

import br.com.fiap.carbonoapi.entity.enums.TipoCompensacao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_COMPENSACAO_CARBONO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompensacaoCarbono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPRESA_ID", nullable = false)
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", nullable = false, length = 30)
    private TipoCompensacao tipo;

    @Column(name = "QUANTIDADE_TCO2E", nullable = false, precision = 15, scale = 4)
    private BigDecimal quantidadeTco2e;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Column(name = "CERTIFICADO_URL", length = 500)
    private String certificadoUrl;

    @Column(name = "DATA_COMPENSACAO", nullable = false)
    private LocalDate dataCompensacao;

    @CreationTimestamp
    @Column(name = "DATA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;
}