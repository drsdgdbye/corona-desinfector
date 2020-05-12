package ru.drsdgdby.announcers;

import ru.drsdgdby.annotations.InjectByType;
import ru.drsdgdby.recommendators.Recommendator;

public class ConsoleAnnouncer implements Announcer {
    @InjectByType
    private Recommendator recommendator;

    @Override
    public void announce(String msg) {
        System.out.println(msg);
        recommendator.recommendate();
    }
}
