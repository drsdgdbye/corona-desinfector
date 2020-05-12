package ru.drsdgdby.configurators;

import lombok.SneakyThrows;
import ru.drsdgdby.ApplicationContext;
import ru.drsdgdby.annotations.InjectProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> propertiesMap;

    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource("application.properties"))
                .getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        propertiesMap = lines.map(l -> l.split("="))
                .collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @SneakyThrows
    @Override
    public void configure(Object object, ApplicationContext context) {
        Class<?> implClass = object.getClass();

        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);

            if (annotation != null) {
                String value = annotation.value().isEmpty() ? propertiesMap.get(field.getName()) : propertiesMap.get(annotation.value());
                field.setAccessible(true);
                field.set(object, value);
            }
        }
    }
}
