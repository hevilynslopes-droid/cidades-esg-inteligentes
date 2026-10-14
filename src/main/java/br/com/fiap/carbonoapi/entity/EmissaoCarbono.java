package br.com.fiap.carbonoapi.entity;

import br.com.fiap.carbonoapi.entity.enums.EscopoEmissao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_EMISSAO_CARBONO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmissaoCarbono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPRESA_ID", nullable = false)
    private Empresa empresa;

    @Column(name = "QUANTIDADE_TCO2E", nullable = false, precision = 15, scale = 4)
    private BigDecimal quantidadeTco2e;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESCOPO", nullable = false, length = 20)
    private EscopoEmissao escopo;

    @Column(name = "DESCRICAO", length = 500)
    private String descricao;

    @Column(name = "DATA_EMISSAO", nullable = false)
    private LocalDate dataEmissao;

    @CreationTimestamp
    @Column(name = "DATA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime dataRegistro;
}