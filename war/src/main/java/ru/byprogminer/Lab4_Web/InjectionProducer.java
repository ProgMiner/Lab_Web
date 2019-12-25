package ru.byprogminer.Lab4_Web;

import ru.byprogminer.Lab4_Web.area.AreaService;
import ru.byprogminer.Lab4_Web.history.HistoryService;
import ru.byprogminer.Lab4_Web.sessions.SessionsService;
import ru.byprogminer.Lab4_Web.users.UsersService;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class InjectionProducer {

    @Produces @EJB
    private AreaService areaService;

    @Produces @EJB
    private HistoryService historyService;

    @Produces @EJB
    private SessionsService sessionsService;

    @Produces @EJB
    private UsersService usersService;
}
