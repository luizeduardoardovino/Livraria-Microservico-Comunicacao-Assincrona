package com.livraria.livraria.repository;

import com.livraria.livraria.model.LivroHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroHistoricoRepository extends JpaRepository<LivroHistorico, Long> {
}
