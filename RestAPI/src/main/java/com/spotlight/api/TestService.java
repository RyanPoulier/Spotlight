package com.spotlight.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


/**
 * Created by Padmaka on 8/1/16.
 */

@Path("/")
@Api(value = "/", description = "Test service!")
public class TestService {

    @GET
    @Path("/ping")
    @ApiOperation(value = "Service Ping", notes = "This the service ping", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Something wrong in Server")
    })
    public Response ping() {
        return Response.status(200).entity("Hi there !").build();
    }
}
