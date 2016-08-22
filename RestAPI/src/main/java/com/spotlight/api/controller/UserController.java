package com.spotlight.api.controller;

import com.spotlight.core.beans.User;
import com.spotlight.core.exceptions.InvalidParameterException;
import com.spotlight.core.exceptions.InvalidUsernameOrPasswordException;
import com.spotlight.core.service.UserManager;
import com.spotlight.core.service.impl.UserManagerImpl;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;

/**
 * Created by Padmaka on 8/21/16.
 */

@Path("/")
@Api(value = "/", description = "Endpoints to manage users")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private UserManager userManager;

    public UserController(){
        userManager = new UserManagerImpl();
    }

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) throws InvalidParameterException, UnknownHostException {

        LOGGER.info("Received request to save user - " + user.getEmail());
        return Response.status(Response.Status.ACCEPTED).entity(userManager.saveUser(user)).build();
    }

    @POST
    @Path("/users/auth")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(User user) throws InvalidParameterException, UnknownHostException, InvalidUsernameOrPasswordException {

        LOGGER.info("Received request to get user - " + user.getEmail());
        return Response.status(Response.Status.ACCEPTED).entity(userManager.getUser(user)).build();
    }
}
