package com.dev.victor.web_scrapping.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBotPrice extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String telegramUsername;
    public TelegramBotPrice(@Value("${telegram.bot.token}") String token) {
        super(token);
    }


    @Override
    public String getBotUsername() {
        return telegramUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String mensagemRecebida = update.getMessage().getText();
            System.out.println("Mensagem Recebida: " + mensagemRecebida);
            SendMessage sendMessage = new SendMessage();
            String message = "ola victor";
            sendMessage.getChatId();
        }
    }


}
