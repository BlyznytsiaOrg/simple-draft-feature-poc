package com.levik.features.prototap;

import java.util.UUID;

public class PrototypeDependencyImpl implements PrototypeDependency {
    private UUID uuid = UUID.randomUUID();

    @Override
    public void doSomething() {
        System.out.println("Instance " + uuid + " of PrototypeDependency is doing something");
    }
}
