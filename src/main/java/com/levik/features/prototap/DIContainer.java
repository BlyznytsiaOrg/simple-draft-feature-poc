package com.levik.features.prototap;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

class DIContainer {
    private final Map<String, Supplier<PrototypeDependency>> registryClassWithInterface = new HashMap<>();
    public void registerClassWithInterface(String key, Supplier<PrototypeDependency> supplier) {
        registryClassWithInterface.put(key, supplier);
    }

    public PrototypeDependency getInterfaceDependency(String key) {
        if (registryClassWithInterface.containsKey(key)) {
            PrototypeDependency originalInstance = registryClassWithInterface.get(key).get();
            Class<?>[] interfaces = originalInstance.getClass().getInterfaces();
            return (PrototypeDependency) Proxy.newProxyInstance(
                    originalInstance.getClass().getClassLoader(),
                    interfaces,
                    new GenericProxy<>(originalInstance)
            );
        } else {
            throw new IllegalArgumentException("Dependency '" + key + "' not found in the container.");
        }
    }
}
