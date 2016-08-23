package com.spotlight.api.controller;

import com.spotlight.core.beans.Complaint;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.service.ComplaintManager;
import com.spotlight.core.service.impl.ComplaintManagerImpl;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padmaka on 8/8/16.
 */

@Path("/")
@Api(value = "/", description = "Endpoints to manage Complaints")
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
    public Response saveComplaint(Complaint complaint) throws UnknownHostException, InvalidParameterException {

        LOGGER.info("Received request to save complaint - " + complaint.toString());
        return Response.status(Response.Status.ACCEPTED).entity(complaintManager.saveNewComplaint(complaint)).build();
    }

    @GET
    @Path("/complaints/{issueId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComplaints(@PathParam("issueId") String issueId) throws UnknownHostException {

        LOGGER.info("Received request to retrieve all complaints for the issue - " + issueId);
        return Response.status(Response.Status.ACCEPTED).entity(complaintManager.getAllComplaints(issueId)).build();
    }
}
