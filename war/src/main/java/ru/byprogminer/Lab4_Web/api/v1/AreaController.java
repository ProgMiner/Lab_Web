package ru.byprogminer.Lab4_Web.api.v1;

import ru.byprogminer.Lab4_Web.api.v1.auth.AuthenticatedUser;
import ru.byprogminer.Lab4_Web.api.v1.auth.Secured;
import ru.byprogminer.Lab4_Web.area.AreaService;
import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.history.QueryEntity;
import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Objects;

@Secured
@Path("/area")
@Produces(MediaType.APPLICATION_JSON)
public class AreaController {

    private final AreaService service;
    private final HistoryService historyService;

    private final UserEntity authenticatedUser;

    @Deprecated
    public AreaController() {
        this.service = null;
        this.historyService = null;
        this.authenticatedUser = null;
    }

    @Inject
    public AreaController(
            AreaService service,
            HistoryService historyService,
            @AuthenticatedUser UserEntity authenticatedUser
    ) {
        this.service = service;
        this.historyService = historyService;
        this.authenticatedUser = authenticatedUser;
    }

    @GET
    @Path("/check/r/{r}/x/{x}/y/{y}")
    public boolean check(
            @NotNull @PathParam("x") BigDecimal x,
            @NotNull @PathParam("y") BigDecimal y,
            @NotNull @PathParam("r") BigDecimal r
    ) {
        return Objects.requireNonNull(service).checkPoint(x, y, r);
    }

    @POST
    @Path("/check")
    public boolean checkAndSave(
            @NotNull @FormParam("x") BigDecimal x,
            @NotNull @FormParam("y") BigDecimal y,
            @NotNull @FormParam("r") BigDecimal r
    ) {
        final boolean result = check(x, y, r);

        Objects.requireNonNull(historyService).addQuery(new QueryEntity(null,
                Objects.requireNonNull(authenticatedUser).getId(), x, y, r, result));

        return result;
    }
}
