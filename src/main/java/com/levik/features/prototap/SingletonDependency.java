package com.levik.features.prototap;

public class SingletonDependency {

    private PrototypeDependency prototypeDependency;
    public PrototypeDependencyImplClass prototypeDependencyImplClass;

    public void setPrototypeDependencyInterface(PrototypeDependency prototypeDependency) {
        this.prototypeDependency = prototypeDependency;
    }

    public void setPrototypeDependencyClass(PrototypeDependencyImplClass prototypeDependencyImplClass) {
        this.prototypeDependencyImplClass = prototypeDependencyImplClass;
    }

    public void performAction() {
        if (prototypeDependency != null) {
            System.out.println(prototypeDependency);
            prototypeDependency.doSomething();
        } else {
            System.out.println("PrototypeDependency is not injected yet.");
        }
    }
}
