package com.spotlight.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Padmaka on 8/1/16.
 */

@Path("/")
public class TestService {

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.status(200).entity("Hi there !").build();
    }
}
