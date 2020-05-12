package ru.drsdgdby;

import lombok.SneakyThrows;
import ru.drsdgdby.configurators.ObjectConfigurator;
import ru.drsdgdby.configurators.ProxyConfigurator;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {
    private final ApplicationContext context;
    private final List<ObjectConfigurator> configurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;
        for (Class<? extends ObjectConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ObjectConfigurator.class)) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        for (Class<? extends ProxyConfigurator> aClass : context.getConfig().getScanner().getSubTypesOf(ProxyConfigurator.class)) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {

        T instance = create(implClass);

        configure(instance);

        invokeInit(implClass, instance);

        instance = replaceWithProxyIfNeeded(implClass, instance);

        return instance;
    }


    private <T> T create(Class<T> implClass) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException {
        return implClass.getDeclaredConstructor().newInstance();
    }

    private <T> void configure(T instance) {
        configurators.forEach(objectConfigurator -> objectConfigurator.configure(instance, context));
    }

    private <T> void invokeInit(Class<T> implClass, T instance) throws IllegalAccessException, InvocationTargetException {
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(instance);
            }
        }
    }

    private <T> T replaceWithProxyIfNeeded(Class<T> implClass, T instance) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            instance = (T) proxyConfigurator.replaceWithProxyIfNeeded(instance, implClass);
        }
        return instance;
    }
}
