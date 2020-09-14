package br.com.cotacao.controller.dto;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RespostaCotacao {
    @Schema(name="value", description ="Cotações de compra e venda para a data", type = SchemaType.ARRAY)
    private List<Cotacao> value;
}
