package br.com.cotacao.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.core.UriBuilder;

import org.jboss.logging.Logger;

import br.com.cotacao.controller.dto.APIResponseCotacao;
import br.com.cotacao.controller.dto.APIResponseValue;
import br.com.cotacao.controller.dto.Cotacao;
import br.com.cotacao.controller.dto.RespostaCotacao;
import br.com.cotacao.entity.CotacaoEntity;
import br.com.cotacao.repository.CotacaoRepository;
import br.com.cotacao.util.DateUtils;

@ApplicationScoped
public class CotacaoController {

    @Inject
    CotacaoRepository cotacaoRepository;

    @Transactional
    public RespostaCotacao getCotacao(final LocalDate date) throws IOException, InterruptedException {
        String dateStr = DateUtils.getStringDate(date);

        String url = "https://olinda.bcb.gov.br/olinda/servico/PTAX/versao/v1/odata/CotacaoDolarDia(dataCotacao=@dataCotacao)?@dataCotacao='"
            +dateStr+"'&$top=100&$format=json&$select=cotacaoCompra,cotacaoVenda,dataHoraCotacao";

        final URI apiUri = UriBuilder.fromUri(url).build();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(apiUri)
            .header("Accept", "application/json")
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        Jsonb jsonb = JsonbBuilder.create();

        String jsonResponse = response.body();
        APIResponseValue dtoValue = jsonb.fromJson(jsonResponse, APIResponseValue.class);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<Cotacao> listCotacao = new ArrayList<>();
        
        for(APIResponseCotacao c : dtoValue.value) {
            CotacaoEntity cotacao = new CotacaoEntity();
            
            cotacao.timestamp(timestamp);
            cotacao.data(date);
            cotacao.dataHora(DateUtils.getLocalDateTime(c.dataHoraCotacao));
            cotacao.compra(c.cotacaoCompra);
            cotacao.venda(c.cotacaoVenda);
            cotacao.persist();
            
            listCotacao.add(mapCotacaoDto(cotacao));
        }

        RespostaCotacao resposta = new RespostaCotacao();
        resposta.setValue(listCotacao);

        return resposta;
    }

    private Cotacao mapCotacaoDto(CotacaoEntity entity) {
        Cotacao cotacao = new Cotacao();
        
        cotacao.setId(entity.id());
        cotacao.setTimestampRequisicao(entity.timestamp().toLocalDateTime());
        cotacao.setDataCotacao(entity.data());
        cotacao.setDataHoraCotacao(entity.dataHora());
        cotacao.setCompraCotacao(entity.compra());
        cotacao.setVendaCotacao(entity.venda());

        return cotacao;
    }
}
