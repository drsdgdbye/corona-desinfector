package ru.drsdgdby.configurators;

import lombok.SneakyThrows;
import ru.drsdgdby.ApplicationContext;
import ru.drsdgdby.annotations.InjectByType;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @SneakyThrows
    @Override
    public void configure(Object object, ApplicationContext context) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);
                Object o = context.getObject(field.getType());
                field.set(object, o);
            }
        }
    }
}
