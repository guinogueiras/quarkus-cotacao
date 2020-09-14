package br.com.cotacao.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Schema(description = "Tipo utilizado para retorno das cotações de compra e venda ")
public class Cotacao {
    @Schema(title="Id da requisição", description ="Id da requisição gravada na api")
    private Long id;
    
    @Schema(title="Timestamp da requisição", description ="Timestamp da requisição gravada na api")
    private LocalDateTime timestampRequisicao;
    
    @Schema(title="Data da cotação", description ="Data das cotações de compra e venda.")
    private LocalDate dataCotacao;
    
    @Schema(title="Data e hora da cotação", description ="Data, hora, minuto, segundo e milisegundo das cotações de compra e venda.")
    private LocalDateTime dataHoraCotacao;
    
    @Schema(title="Cotação de compra", description ="Cotação de compra do dólar contra a unidade monetária corrente: unidade monetária corrente/dólar americano.")
    private BigDecimal compraCotacao;
    
    @Schema(title="Cotação de venda", description ="Cotação de venda do dólar contra a unidade monetária corrente: unidade monetária corrente/dólar americano.")
    private BigDecimal vendaCotacao;
    
}
