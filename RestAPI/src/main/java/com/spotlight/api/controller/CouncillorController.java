package com.spotlight.api.controller;

import com.spotlight.core.beans.Comment;
import com.spotlight.core.beans.Councillor;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/25/16.
 */

@Path("/")
@Api(value = "/", description = "Endpoints to manage councillor")
public class CouncillorController {

    private static final Logger LOGGER = Logger.getLogger(CouncillorController.class);

    @POST
    @Path("/councillor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveComment(Councillor councillor) throws UnknownHostException, InvalidParameterException {

        LOGGER.info("Received request to save councillor - " + councillor.toString());
        return Response.status(Response.Status.ACCEPTED).entity(null).build();
    }

    @GET
    @Path("/councillor/{councillorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComplaints(@PathParam("councillorId") String councillorId) throws UnknownHostException {

        LOGGER.info("Received request to retrieve all councillor - " + councillorId);
        return Response.status(Response.Status.ACCEPTED).entity(null).build();
    }
}
