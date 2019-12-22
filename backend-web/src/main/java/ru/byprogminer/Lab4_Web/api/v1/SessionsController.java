package ru.byprogminer.Lab4_Web.api.v1;

import ru.byprogminer.Lab4_Web.sessions.SessionsService;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionsController {

    @EJB
    private SessionsService service;

    @POST
    @Path("/create")
    public String create(@NotNull @FormParam("username") String username, @NotNull @FormParam("password") String password) {
        return service.createSession(username, password);
    }

    @DELETE
    @Path("/destroy")
    public boolean destroy(@NotNull @FormParam("username") String username, @NotNull @FormParam("token") String token) {
        return service.destroySession(username, token);
    }
}
