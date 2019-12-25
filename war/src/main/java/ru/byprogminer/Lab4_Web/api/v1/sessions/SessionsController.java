package ru.byprogminer.Lab4_Web.api.v1.sessions;

import ru.byprogminer.Lab4_Web.api.v1.auth.AuthenticatedUser;
import ru.byprogminer.Lab4_Web.api.v1.auth.Secured;
import ru.byprogminer.Lab4_Web.sessions.SessionsService;
import ru.byprogminer.Lab4_Web.users.UserEntity;
import ru.byprogminer.Lab4_Web.users.UsersService;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
public class SessionsController {

    private final SessionsService service;
    private final UsersService usersService;

    private final UserEntity authenticatedUser;
    private final String authenticatedUserToken;

    @Deprecated
    public SessionsController() {
        service = null;
        usersService = null;
        authenticatedUser = null;
        authenticatedUserToken = null;
    }

    @Inject
    public SessionsController(
            SessionsService service,
            UsersService usersService,
            @AuthenticatedUser UserEntity authenticatedUser,
            @AuthenticatedUser String authenticatedUserToken
    ) {
        this.service = service;
        this.usersService = usersService;
        this.authenticatedUser = authenticatedUser;
        this.authenticatedUserToken = authenticatedUserToken;
    }

    @POST
    @Path("/create")
    public Response create(
            @NotNull @FormParam("username") String username,
            @NotNull @FormParam("password") String password
    ) {
        final UserEntity user = Objects.requireNonNull(usersService).findUser(username);

        if (user != null && usersService.checkPassword(user, password)) {
            final String token = Objects.requireNonNull(service).createSession(user);

            return Response.ok(token == null ? null : new SessionDto(user.getId(), token)).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Secured
    @Path("/destroy")
    public boolean destroy() {
        return Objects.requireNonNull(service).destroySession(authenticatedUser, authenticatedUserToken);
    }
}
