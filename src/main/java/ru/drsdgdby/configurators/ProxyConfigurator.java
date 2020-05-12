package ru.drsdgdby.configurators;

public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object object, Class implClass);
}
