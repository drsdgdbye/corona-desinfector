package ru.drsdgdby.configurators;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object replaceWithProxyIfNeeded(Object object, Class implClass) {
    //todo make support for @Deprecate above methods, not class
        if (implClass.isAnnotationPresent(Deprecated.class)) {
            if (implClass.getInterfaces().length == 0) {
                return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
            }
            return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
        } else {
            return object;
        }
    }

    private Object getInvocationHandlerLogic(Object object, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException {
        System.out.println("============ deprecated");
        return method.invoke(object, args);
    }
}
