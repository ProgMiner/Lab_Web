package ru.byprogminer.Lab4_Web;

import ru.byprogminer.Lab4_Web.area.Area;
import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.querycounter.QueryCounter;
import ru.byprogminer.Lab4_Web.sessions.SessionsService;
import ru.byprogminer.Lab4_Web.users.UsersService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class InjectionProducer {

    @Produces @EJB
    private Area area;

    @Produces @EJB
    private HistoryService historyService;

    @Produces @EJB
    private SessionsService sessionsService;

    @Produces @EJB
    private UsersService usersService;

    @Produces @EJB
    private QueryCounter queryCounter;
}
