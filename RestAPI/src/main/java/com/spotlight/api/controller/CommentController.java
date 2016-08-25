package com.spotlight.api.controller;

import com.spotlight.core.beans.Comment;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.CommentManager;
import com.spotlight.core.service.impl.CommentManagerImpl;
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
@Api(value = "/", description = "Endpoints to manage comments")
public class CommentController {

    private static final Logger LOGGER = Logger.getLogger(CommentController.class);
    private CommentManager commentManager;

    public CommentController(){
        commentManager = new CommentManagerImpl();
    }

    @POST
    @Path("/comments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveComment(Comment comment) throws UnknownHostException, InvalidParameterException {

        LOGGER.info("Received request to save comment - " + comment.toString());
        return Response.status(Response.Status.ACCEPTED).entity(commentManager.saveNewComment(comment)).build();
    }

    @GET
    @Path("/comments/{issueId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComplaints(@PathParam("issueId") String issueId) throws UnknownHostException, InvalidParameterException {

        LOGGER.info("Received request to retrieve all comments for the issue - " + issueId);
        return Response.status(Response.Status.ACCEPTED).entity(commentManager.getAllComments(issueId)).build();
    }
}
