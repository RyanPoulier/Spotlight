package com.spotlight.api.controller;

import com.spotlight.core.beans.Vote;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.VoteManager;
import com.spotlight.core.service.impl.VoteManagerImpl;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;

/**
 * Created by padmaka on 8/23/16.
 */

@Path("/")
@Api(value = "/", description = "Endpoints to manage votes")
public class VoteController {

    private static final Logger LOGGER = Logger.getLogger(VoteController.class);
    private VoteManager voteManager;

    public VoteController() {
        voteManager = new VoteManagerImpl();
    }

    @POST
    @Path("/votes")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveVote(Vote vote) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to save vote - " + vote.toString());
        return Response.status(Response.Status.ACCEPTED).entity(voteManager.saveNewVote(vote)).build();
    }

    @GET
    @Path("/votes/{issueId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssues(@PathParam("issueId") String issueId) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to get all votes for the given issue");
        return Response.status(Response.Status.ACCEPTED).entity(voteManager.getVotes(issueId)).build();
    }
}
