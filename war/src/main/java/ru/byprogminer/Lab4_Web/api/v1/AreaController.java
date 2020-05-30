package ru.byprogminer.Lab4_Web.api.v1;

import ru.byprogminer.Lab4_Web.api.v1.auth.AuthenticatedUser;
import ru.byprogminer.Lab4_Web.api.v1.auth.Secured;
import ru.byprogminer.Lab4_Web.area.Area;
import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.history.QueryEntity;
import ru.byprogminer.Lab4_Web.querycounter.QueryCounter;
import ru.byprogminer.Lab4_Web.users.UserEntity;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Objects;

@Secured
@Path("/area")
@Produces(MediaType.APPLICATION_JSON)
public class AreaController {

    private final Area service;
    private final HistoryService historyService;
    private final QueryCounter queryCounter;

    private final UserEntity authenticatedUser;

    @Deprecated
    public AreaController() {
        this.service = null;
        this.historyService = null;
        this.queryCounter = null;
        this.authenticatedUser = null;
    }

    @Inject
    public AreaController(
            Area service,
            HistoryService historyService,
            QueryCounter queryCounter,
            @AuthenticatedUser UserEntity authenticatedUser
    ) {
        this.service = service;
        this.historyService = historyService;
        this.queryCounter = queryCounter;
        this.authenticatedUser = authenticatedUser;
    }

    @GET
    @Path("/check/r/{r}/x/{x}/y/{y}")
    public boolean check(
            @NotNull @PathParam("x") BigDecimal x,
            @NotNull @PathParam("y") BigDecimal y,
            @NotNull @PathParam("r") BigDecimal r
    ) {
        try {
            final boolean result = Objects.requireNonNull(service).checkPoint(x, y, r);
            Objects.requireNonNull(queryCounter).sendCheckPointResult(result);

            return result;
        } catch (ValidationException exception) {
            Objects.requireNonNull(queryCounter).sendCheckPointValidationFailure(x, y, r);
            throw exception;
        }
    }

    @POST
    @Path("/check")
    public boolean checkAndSave(
            @NotNull @FormParam("x") BigDecimal x,
            @NotNull @FormParam("y") BigDecimal y,
            @NotNull @FormParam("r") BigDecimal r
    ) {
        try {
            final boolean result = check(x, y, r);
            Objects.requireNonNull(queryCounter).sendCheckPointResult(result);

            Objects.requireNonNull(historyService)
                    .addQuery(new QueryEntity(null, Objects.requireNonNull(authenticatedUser), x, y, r, result));

            return result;
        } catch (ValidationException exception) {
            Objects.requireNonNull(queryCounter).sendCheckPointValidationFailure(x, y, r);
            throw exception;
        }
    }
}
