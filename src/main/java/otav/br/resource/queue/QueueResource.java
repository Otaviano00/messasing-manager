package otav.br.resource.queue;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/queue")
public class QueueResource {

    @POST
    @Path("/receive")
    public Response receiveMessage() {
        return Response.ok().build();
    }

    @POST
    @Path("/send")
    public Response sendMessage() {
        return Response.ok().build();
    }

    @POST
    @Path("/browse")
    public Response browseMessages() {
        return Response.ok().build();
    }

}
