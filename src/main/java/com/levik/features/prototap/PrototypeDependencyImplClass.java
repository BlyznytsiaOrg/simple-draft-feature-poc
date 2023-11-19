package com.levik.features.prototap;

import java.util.UUID;

public class PrototypeDependencyImplClass {

    private UUID uuid = UUID.randomUUID();

    public void doSomething() {
        System.out.println("Instance " + uuid + " of PrototypeDependency is doing something");
    }
}
