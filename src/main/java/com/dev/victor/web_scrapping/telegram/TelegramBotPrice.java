package com.dev.victor.web_scrapping.telegram;

import com.dev.victor.web_scrapping.model.ProdutoMonitorado;
import com.dev.victor.web_scrapping.scheduler.SchedulerMontoramento;
import com.dev.victor.web_scrapping.service.ProdutoMonitoradoService;
import com.dev.victor.web_scrapping.service.ScrapperService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class TelegramBotPrice extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String telegramUsername;

    private final ProdutoMonitoradoService produtoMonitoradoService;
    private final ScrapperService buscaNomePreco;


    public TelegramBotPrice(
            @Value("${telegram.bot.token}") String token,
            ProdutoMonitoradoService produtoService, ScrapperService scrapperService) {
        super(token);
        this.produtoMonitoradoService = produtoService;
        this.buscaNomePreco = scrapperService;
    }


    @Override
    public String getBotUsername() {
        return telegramUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String mensagemRecebida = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if (mensagemRecebida.startsWith("http://") || mensagemRecebida.startsWith("https://")) {
                try {
                    String nome = buscaNomePreco.buscarNomeProduto(mensagemRecebida);
                    BigDecimal preco = buscaNomePreco.buscarPreco(mensagemRecebida);
                    produtoMonitoradoService.criarProduto(chatId, mensagemRecebida, nome, preco);
                    SendMessage resposta = new SendMessage();
                    resposta.setChatId(chatId.toString());
                    resposta.setText(String.format(
                            "📦 Produto adicionado com sucesso ao monitoramento!\n\n" +
                                    "🛍️ Produto: %s\n" +
                                    "💰 Preço atual: R$ %s\n\n" +
                                    "🔍 Já estamos acompanhando esse item para você.\n" +
                                    "Assim que houver redução no preço, enviaremos uma atualização aqui no Telegram imediatamente!",
                            nome,
                            preco
                    ));
                    try {
                        execute(resposta);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Url nao encotrada");
                }
            }
        }
    }

    public void sendAlert(ProdutoMonitorado produto) {
        SendMessage ofertas = new SendMessage();
        ofertas.setChatId(produto.getChatId().toString());
        ofertas.setText(String.format(
                "📉 Boa notícia! O preço do produto que você está monitorando baixou!\n\n" +
                        "🛍️ Produto: %s\n" +
                        "💸 Preço anterior: R$ %s\n" +
                        "🔥 Novo preço: R$ %s\n" +
                        "🚀 Aproveite essa oportunidade antes que o valor mude novamente!\n\n" +
                        "🔗 Link do produto:\n%s",
                produto.getNomeProduto(),
                produto.getPrecoAnterior(),
                produto.getPrecoAtual(),
                produto.getUrl()));

        try {
            execute(ofertas);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
