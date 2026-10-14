package br.com.fiap.carbonoapi.repository;

import br.com.fiap.carbonoapi.entity.CompensacaoCarbono;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface CompensacaoCarbonoRepository extends JpaRepository<CompensacaoCarbono, Long> {

    Page<CompensacaoCarbono> findByEmpresaId(Long empresaId, Pageable pageable);

    @Query("SELECT SUM(c.quantidadeTco2e) FROM CompensacaoCarbono c WHERE c.empresa.id = :empresaId")
    BigDecimal sumQuantidadeByEmpresaId(@Param("empresaId") Long empresaId);
}