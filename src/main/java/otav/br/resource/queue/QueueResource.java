package otav.br.resource.queue;

import jakarta.jms.JMSException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import otav.br.controller.queue.QueueController;
import otav.br.resource.queue.dto.ConnectionRequest;
import otav.br.resource.queue.dto.MessageDTO;

import java.util.List;

@Path("/queue")
@AllArgsConstructor
public class QueueResource {

    private QueueController queueController;

    @GET
    @Path("/message")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response receiveMessage(
            @QueryParam("isBrowse") @DefaultValue("false") boolean isBrowse,
            @QueryParam("amount") @DefaultValue("1") @Positive Integer amount,
            @QueryParam("timeout") @DefaultValue("1000") @Positive Integer timeout,
            @Valid @NotNull ConnectionRequest connectionRequest
    ) throws JMSException {

        List<MessageDTO> messages = queueController.receiveMessages(connectionRequest, amount, timeout, isBrowse);

        return Response.ok(messages).build();
    }

    @POST
    @Path("/{queueName}/message")
    public Response sendMessage() {
        return Response.ok().build();
    }

}
