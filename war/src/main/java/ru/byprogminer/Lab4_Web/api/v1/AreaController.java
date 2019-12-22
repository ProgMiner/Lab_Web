package ru.byprogminer.Lab4_Web.api.v1;

import ru.byprogminer.Lab4_Web.area.AreaService;
import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.history.QueryEntity;

import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/area")
@Produces(MediaType.APPLICATION_JSON)
public class AreaController {

    @EJB
    private AreaService service;

    @EJB
    private HistoryService historyService;

    @GET
    @Path("/check/r/{r}/x/{x}/y/{y}")
    public boolean check(
            @NotNull @PathParam("x") BigDecimal x,
            @NotNull @PathParam("y") BigDecimal y,
            @NotNull @PathParam("r") BigDecimal r
    ) {
        return service.checkPoint(x, y, r);
    }

    @POST
    @Path("/check")
    public boolean checkAndSave(
            @NotNull @FormParam("userId") long userId,
            @NotNull @FormParam("x") BigDecimal x,
            @NotNull @FormParam("y") BigDecimal y,
            @NotNull @FormParam("r") BigDecimal r
    ) {
        final boolean result = check(x, y, r);

        historyService.addQuery(new QueryEntity(null, userId, x, y, r, result));
        return result;
    }
}
