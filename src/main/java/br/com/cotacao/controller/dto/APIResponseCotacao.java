package br.com.cotacao.controller.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APIResponseCotacao {
    public BigDecimal cotacaoCompra;
    public BigDecimal cotacaoVenda;

    public String dataHoraCotacao;
    
}
