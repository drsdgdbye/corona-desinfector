package ru.drsdgdby.configs;

import lombok.Getter;
import org.reflections.Reflections;
import ru.drsdgdby.configs.Config;

import java.util.Map;

public class JavaConfig implements Config {
    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        scanner = new Reflections(packageToScan);
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public <T> Class<T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, x -> {
            var classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + "has 0 or more than 1 implementations");
            }
            return classes.iterator().next();
        });
    }


}
