package com.dev.victor.web_scrapping.scheduler;

import com.dev.victor.web_scrapping.model.ProdutoMonitorado;
import com.dev.victor.web_scrapping.service.ProdutoMonitoradoService;
import com.dev.victor.web_scrapping.telegram.TelegramBotPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerMontoramento {

    private final ProdutoMonitoradoService produtoMonitoradoService;
    private final TelegramBotPrice telegramBotPrice;

    @Scheduled(fixedRate = 3600000)
    public void verifPrice() {
        List<ProdutoMonitorado> produtosMonitorados = produtoMonitoradoService.verifPrice();
        for (ProdutoMonitorado produto : produtosMonitorados) {
            telegramBotPrice.sendAlert(produto);

        }
    }
}
