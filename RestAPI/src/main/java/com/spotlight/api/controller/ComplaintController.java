package com.spotlight.api.controller;

import com.google.gson.JsonObject;
import com.spotlight.core.beans.Issue;
import com.spotlight.core.service.ComplaintManager;
import com.spotlight.core.service.impl.ComplaintManagerImpl;
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
public class ComplaintController {

    private static final Logger LOGGER = Logger.getLogger(ComplaintController.class);
    private ComplaintManager complaintManager;

    public ComplaintController(){
        complaintManager = new ComplaintManagerImpl();
    }

    @POST
    @Path("/complaints")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveComplaint(JsonObject fullComplaint) throws UnknownHostException {

        LOGGER.info("Received request to save issue - " + fullComplaint.toString());
        JsonObject complaintObj = complaintManager.saveNewComplaint(fullComplaint);
        return Response.status(Response.Status.ACCEPTED).entity(complaintObj).build();
    }

    @GET
    @Path("/complaints")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComplaints() throws UnknownHostException {

        LOGGER.info("Received request to retrieve all issues");
        List<Issue> issues = new ArrayList<>();
        issues = complaintManager.getAllComplaints();
        return Response.status(Response.Status.ACCEPTED).entity(issues).build();
    }
}
