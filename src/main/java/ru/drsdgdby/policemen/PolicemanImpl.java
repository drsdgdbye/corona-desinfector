package ru.drsdgdby.policemen;

import ru.drsdgdby.annotations.InjectByType;
import ru.drsdgdby.recommendators.Recommendator;

import javax.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {
    @InjectByType
    private Recommendator recommendator;

    @PostConstruct
    public void init() {
        System.out.println(recommendator.getClass());
    }

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("go go people");
    }
}
