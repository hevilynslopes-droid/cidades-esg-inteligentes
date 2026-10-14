package br.com.fiap.carbonoapi.repository;

import br.com.fiap.carbonoapi.entity.EmissaoCarbono;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface EmissaoCarbonoRepository extends JpaRepository<EmissaoCarbono, Long> {

    Page<EmissaoCarbono> findByEmpresaId(Long empresaId, Pageable pageable);

    @Query("SELECT SUM(e.quantidadeTco2e) FROM EmissaoCarbono e WHERE e.empresa.id = :empresaId")
    BigDecimal sumQuantidadeByEmpresaId(@Param("empresaId") Long empresaId);
}