package br.com.cotacao.resource;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.jboss.logging.Logger;

import br.com.cotacao.controller.CotacaoController;
import br.com.cotacao.controller.dto.Cotacao;
import br.com.cotacao.controller.dto.RespostaCotacao;
import br.com.cotacao.util.DateUtils;

@Path("/cotacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CotacaoResource {

    @Inject
    private CotacaoController cotacaoController;

    private static final Logger LOG = Logger.getLogger(CotacaoResource.class);


    @GET
    @Path("{dataCotacao}")
    @Operation(
        summary = "Cotação do Dólar em uma determinada data",
        description = "Retorna a Cotação de Compra e a Cotação de Venda da moeda Dólar contra a unidade monetária corrente para a data informada.")
    @APIResponses(value = {
        @APIResponse(responseCode = "200", description = "Sucesso", content = @Content(example="{\n\t\"value\": [\n\t\t{\n\t\t\t\"id\":0,\n\t\t\t\"dataCotacao\":\"2020-02-28\",\n\t\t\t\"dataHoraCotacao\":\"2020-02-28T13:02:27.207\",\n\t\t\t\"compraCotacao\":0.0,\n\t\t\t\"vendaCotacao\":0.0,\n\t\t\t\"timestamp\":\"2020-09-13T19:07:56.264Z[UTC]\"\n\t\t}\n\t]\n}", schema = @Schema(implementation = RespostaCotacao.class))),
        @APIResponse(responseCode = "400", description = "Data inválida")
    })
    public Response getCotacao(
        @Parameter(description = "Data da cotação no formado MM-dd-uuuu", required = true)
        @PathParam("dataCotacao") String dataCotacao) {

        LOG.info("Buscando cotação para a data "+ dataCotacao);

        LocalDate localDate = DateUtils.getLocalDate(dataCotacao);
        if (localDate == null) {
            LOG.info("Data "+ dataCotacao + " inválida");
            return Response.ok("Data " + dataCotacao + " inválida.").status(Response.Status.BAD_REQUEST).build();
        }

        try {
            RespostaCotacao listCotacao = cotacaoController.getCotacao(localDate);

            if (listCotacao.getValue().size() > 0)
                LOG.info("Cotação salva no banco de dados para a data " + dataCotacao);
            else
                LOG.info("Não foi encontrada cotação para a data " + dataCotacao);
                
            return Response.ok(listCotacao).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

    }

}