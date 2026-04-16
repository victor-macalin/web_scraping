package com.dev.victor.web_scrapping.repository;

import com.dev.victor.web_scrapping.model.ProdutoMonitorado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoMonitoradoRepository extends JpaRepository<ProdutoMonitorado, Long> {
}
