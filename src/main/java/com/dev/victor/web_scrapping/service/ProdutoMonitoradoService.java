package com.dev.victor.web_scrapping.service;

import com.dev.victor.web_scrapping.model.ProdutoMonitorado;
import com.dev.victor.web_scrapping.repository.ProdutoMonitoradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoMonitoradoService {
    private final ProdutoMonitoradoRepository produtoMonitoradoRepository;

    public ProdutoMonitorado criarProduto (Long chatId, String url, String nome, BigDecimal preco) {
        ProdutoMonitorado produtoMonitorado = new ProdutoMonitorado();
        produtoMonitorado.setChatId(chatId);
        produtoMonitorado.setUrl(url);
        produtoMonitorado.setDataCadastro(LocalDateTime.now());
        produtoMonitorado.setAtivo(true);
        produtoMonitorado.setNomeProduto(nome);
        produtoMonitorado.setPrecoAtual(preco);
        return produtoMonitoradoRepository.save(produtoMonitorado);
    }
    public List<ProdutoMonitorado> getAll () {
        return produtoMonitoradoRepository.findAll();
    }
}
