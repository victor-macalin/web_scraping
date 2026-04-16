package com.dev.victor.web_scrapping.scheduler;

import com.dev.victor.web_scrapping.model.ProdutoMonitorado;
import com.dev.victor.web_scrapping.service.ProdutoMonitoradoService;
import com.dev.victor.web_scrapping.service.ScrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerMontoramento {

    private final ProdutoMonitoradoService produtoMonitoradoService;
    private final ScrapperService scrapperService;

    @Scheduled(fixedRate = 3600000)
    public List<ProdutoMonitorado> verifPrice() {
        List<ProdutoMonitorado> produtosMonitorados = produtoMonitoradoService.getAll();
        return produtosMonitorados.stream()
                .filter(produto -> {
                    try {
                        BigDecimal precoDeBusca = scrapperService.buscarPreco(produto.getUrl());
                        if (produto.getPrecoAtual().compareTo(precoDeBusca) > 0) {
                            produto.setPrecoAnterior(produto.getPrecoAtual());
                            produto.setPrecoAtual(precoDeBusca);
                            return true;
                        }
                        return false;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

}
