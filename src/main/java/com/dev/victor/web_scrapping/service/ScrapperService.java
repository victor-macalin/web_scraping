package com.dev.victor.web_scrapping.service;

import javassist.NotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;


@Service
public class ScrapperService {

    public String buscarNomeProduto (String url) throws IOException {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();

            Element titulo = doc.selectFirst("#productTitle");
            if (titulo != null) {
                return titulo.text().trim();
            }
            return "titulo nao encontrado";
        } catch (IOException e) {
            throw new IOException("erro ao buscar nome");
        }
    }

    public BigDecimal buscarPreco (String url) throws IOException {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Modzilla/5.0")
                    .get();
            Element preco = doc.selectFirst(".a-offscreen");
            if (preco != null) {
                String precoText = preco.text()
                        .replace("R$", "")
                        .replace(".", "")
                        .replace(",", ".")
                        .trim();
                return new BigDecimal(precoText);
        }
            return BigDecimal.ZERO;
        } catch (IOException e) {
            throw new RuntimeException("Preco nao encontrado");
        }

    }

}
