package br.com.fiap.carbonoapi.repository;

import br.com.fiap.carbonoapi.entity.AgendamentoReducao;
import br.com.fiap.carbonoapi.entity.enums.StatusAgendamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoReducaoRepository extends JpaRepository<AgendamentoReducao, Long> {

    Page<AgendamentoReducao> findByEmpresaId(Long empresaId, Pageable pageable);

    List<AgendamentoReducao> findByEmpresaIdAndStatus(Long empresaId, StatusAgendamento status);
}