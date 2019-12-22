package ru.byprogminer.Lab4_Web.api.v1.users;

import ru.byprogminer.Lab4_Web.users.UserEntity;
import ru.byprogminer.Lab4_Web.users.UsersService;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersController {

    @EJB
    private UsersService service;

    @GET
    @Path("/get/{userId}")
    public UserDto get(@PathParam("userId") long userId) {
        return entityToDto(service.getUser(userId));
    }

    @GET
    @Path("/find/{username}")
    public UserDto find(@NotNull @PathParam("username") String username) {
        return entityToDto(service.findUser(username));
    }

    @POST
    @Path("/create")
    public long create(@NotNull @FormParam("username") String username, @NotNull @FormParam("password") String password) {
        return service.createUser(username, password).getId();
    }

    @DELETE
    @Path("/remove")
    public void remove(@FormParam("userId") long userId) {
        final UserEntity user = service.getUser(userId);

        if (user != null) {
            service.removeUser(user);
        }
    }

    private UserDto entityToDto(UserEntity entity) {
        return new UserDto(entity.getId(), entity.getUsername());
    }
}
