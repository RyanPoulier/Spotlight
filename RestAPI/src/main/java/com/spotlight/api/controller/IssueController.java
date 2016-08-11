package com.spotlight.api.controller;

import com.spotlight.core.beans.Issue;
import com.spotlight.core.service.IssueManager;
import com.spotlight.core.service.impl.IssueManagerImpl;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padmaka on 8/8/16.
 */

@Path("/")
@Api(value = "/", description = "Test service!")
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
    public Response saveIssue(Issue issue) throws UnknownHostException {

        LOGGER.info("Received request to save issue - " + issue.toString());
        issueManager.saveNewIssue(issue);
        return Response.status(Response.Status.ACCEPTED).entity(issue).build();
    }

    @GET
    @Path("/issues")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIssues() throws UnknownHostException {

        LOGGER.info("Received request to retrieve all issues");
        List<Issue> issues = new ArrayList<>();
        issues = issueManager.getAllIssues();
        return Response.status(Response.Status.ACCEPTED).entity(issues).build();
    }
}
