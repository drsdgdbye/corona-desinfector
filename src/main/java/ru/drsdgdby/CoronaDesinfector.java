package ru.drsdgdby;

import ru.drsdgdby.annotations.InjectByType;
import ru.drsdgdby.announcers.Announcer;
import ru.drsdgdby.policemen.Policeman;

@Deprecated
public class CoronaDesinfector {
    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

    public void start(Room room) {
        announcer.announce("начинаем дезинфекцию");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("можно заходить");

    }

    private void desinfect(Room room) {
        System.out.println("читаем молитву");
    }
}
