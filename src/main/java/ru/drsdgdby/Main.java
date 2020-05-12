package ru.drsdgdby;

import ru.drsdgdby.policemen.Policeman;
import ru.drsdgdby.policemen.PolicemanImpl;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("ru.drsdgdby", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        context.getObject(CoronaDesinfector.class).start(new Room());
    }
}
