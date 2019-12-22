package ru.byprogminer.Lab4_Web.api.v1.history;

import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.history.QueryEntity;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/history")
@Produces(MediaType.APPLICATION_JSON)
public class HistoryController {

    @EJB
    private HistoryService service;

    @GET
    @Path("/get/{userId}")
    public List<QueryDto> get(@PathParam("userId") long userId) {
        return service.getQueries(userId).stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @GET
    @Path("/get/{userId}/offset/{offset}/count/{count}")
    public List<QueryDto> get(
            @PathParam("userId") long userId,
            @PathParam("offset") long offset,
            @PathParam("count") long count
    ) {
        return service.getQueries(userId).stream().skip(offset).limit(count)
                .map(this::entityToDto).collect(Collectors.toList());
    }

    private QueryDto entityToDto(QueryEntity entity) {
        return new QueryDto(entity.getX(), entity.getY(), entity.getR(), entity.getResult());
    }
}
