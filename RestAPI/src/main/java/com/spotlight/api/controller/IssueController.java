package com.spotlight.api.controller;

import com.spotlight.core.beans.Issue;
import com.wordnik.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by Padmaka on 8/8/16.
 */

@Path("/")
@Api(value = "/", description = "Test service!")
public class IssueController {

    @POST
    @Path("/issue")
    @Consumes("application/json")
    public Response saveIssue(Issue issue) {

        

        return Response.status(Response.Status.ACCEPTED).entity("").build();
    }
}
