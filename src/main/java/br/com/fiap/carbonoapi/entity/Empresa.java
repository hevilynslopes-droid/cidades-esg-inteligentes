package br.com.fiap.carbonoapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_EMPRESA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 200)
    private String nome;

    @Column(name = "CNPJ", nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(name = "SETOR", nullable = false, length = 100)
    private String setor;

    @Column(name = "PAIS", nullable = false, length = 100)
    private String pais;

    @Column(name = "ATIVA", nullable = false)
    private Boolean ativa = true;

    @CreationTimestamp
    @Column(name = "DATA_CADASTRO", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(name = "DATA_ATUALIZACAO", nullable = false)
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER)
    private List<EmissaoCarbono> emissoes;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER)
    private List<AgendamentoReducao> agendamentos;

    @OneToMany(mappedBy = "empresa", fetch = FetchType.EAGER)
    private List<CompensacaoCarbono> compensacoes;
}