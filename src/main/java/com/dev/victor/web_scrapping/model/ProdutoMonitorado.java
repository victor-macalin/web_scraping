package com.dev.victor.web_scrapping.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produtos")
public class ProdutoMonitorado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    @Column(length = 1000)
    private String url;

    @Column(length = 1000)
    private String nomeProduto;
    private BigDecimal precoAtual;
    private BigDecimal precoAnterior;
    private LocalDateTime dataCadastro;

    private LocalDateTime ultimaVerificacao;

    private Boolean ativo;
}

