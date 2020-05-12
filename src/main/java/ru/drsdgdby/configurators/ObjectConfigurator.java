package ru.drsdgdby.configurators;

import ru.drsdgdby.ApplicationContext;

public interface ObjectConfigurator {
    void configure(Object object, ApplicationContext context);
}
