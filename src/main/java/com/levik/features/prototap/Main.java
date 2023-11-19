package com.levik.features.prototap;

public class Main {

    public static void main(String[] args) {
        DIContainer container = new DIContainer();

        container.registerClassWithInterface("dependencyPrototype", PrototypeDependencyImpl::new);
        SingletonDependency singletonInstance = new SingletonDependency();

        singletonInstance.setPrototypeDependencyInterface(container.getInterfaceDependency("dependencyPrototype"));

        singletonInstance.performAction();
        singletonInstance.performAction();
        singletonInstance.performAction();
    }
}
