package com.spotlight.api.controller;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.IssueManager;
import com.spotlight.core.service.impl.IssueManagerImpl;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/20/16.
 */


@Path("/")
@Api(value = "/", description = "Endpoints to manage issues")
public class IssueController {

    private static final Logger LOGGER = Logger.getLogger(IssueController.class);
    private IssueManager issueManager;

    public IssueController(){
        issueManager = new IssueManagerImpl();
    }

    @POST
    @Path("/issues")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveIssue(Issue issue) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to save issue - " + issue.toString());
        return Response.status(Response.Status.ACCEPTED).entity(issueManager.saveIssue(issue)).build();
    }

    @GET
    @Path("/issues")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssues() throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to get all issues");
        return Response.status(Response.Status.ACCEPTED).entity(issueManager.getAllIssues()).build();
    }

    @GET
    @Path("/issues/nearby/{lat}/{lon}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNearbyIssues(@PathParam("lat") double latitude, @PathParam("lon") double longitude) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to get nearby issues");
        return Response.status(Response.Status.ACCEPTED).entity(issueManager.getNearbyIssues(latitude, longitude)).build();
    }

    @GET
    @Path("/issues/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssue(@PathParam("id") String id) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to get issue - " + id);
        return Response.status(Response.Status.ACCEPTED).entity(issueManager.getIssueById(id)).build();
    }
}
